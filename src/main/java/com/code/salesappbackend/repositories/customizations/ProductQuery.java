package com.code.salesappbackend.repositories.customizations;

import com.code.salesappbackend.dtos.responses.PageResponse;
import com.code.salesappbackend.dtos.responses.products.ProductUserResponse;
import com.code.salesappbackend.models.Product;
import com.code.salesappbackend.models.ProductPrice;
import jakarta.persistence.Query;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Arrays;



@Repository
public class ProductQuery extends BaseCustomizationRepository<Product> {

    public ProductQuery() {
        super(Product.class);
    }

    private String getQuery(String query) {
        return String.format("select distinct %s " +
                "from Product p " +
                "left join ProductPrice pp " +
                "on p = pp.product and pp.expiredDate > current date where (pp.discountedPrice = " +
                "(select max(pp2.discountedPrice) from ProductPrice pp2 where pp2.product = p) or pp.discountedPrice is null) ", query);
    }

    // select p, max(pp.discountedPrice) as discountPrice from Product p left join ProductPrice pp on p = pp.product and pp.expiredDate > current date group by p.id
    @Override
    public PageResponse<?> getPageData(int pageNo, int pageSize, String[] search, String[] sort) {

        Class<Product> productClass = Product.class;
        Class<ProductPrice> productPriceClass = ProductPrice.class;


        StringBuilder queryBuilder = new StringBuilder(getQuery("new com.code.salesappbackend.dtos.responses.products.ProductUserResponse(p, pp.discount, pp.discountedPrice, pp.expiredDate)"));

        appendQueryBuilder(search, queryBuilder, " %s p.%s %s ?%s");
        System.out.println(queryBuilder);
        sortBy(queryBuilder, " order by p.%s %s", sort);
        TypedQuery<ProductUserResponse> query = entityManager.createQuery(queryBuilder.toString(), ProductUserResponse.class);
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        setValueParams(search, query);

        var data = query.getResultList();

        // count element
        StringBuilder countQueryBuilder = new StringBuilder(getQuery("count(*) "));

        appendQueryBuilder(search, countQueryBuilder, " %s p.%s %s ?%s");


        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        setValueParams(search, countQuery);

        return PageResponse.builder()
                .data(data)
                .totalPage((long) Math.ceil(((long) countQuery.getSingleResult() * 1.0) / pageSize))
                .pageNo(pageNo)
                .totalElement(data.size())
                .build();
    }
}
