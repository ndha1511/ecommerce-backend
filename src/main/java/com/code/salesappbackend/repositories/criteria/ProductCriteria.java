package com.code.salesappbackend.repositories.criteria;


import com.code.salesappbackend.dtos.responses.PageResponse;
import com.code.salesappbackend.models.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductCriteria implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public PageResponse getProductByName(String name, int pageNo, int pageSize) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> product = criteria.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();
        if(StringUtils.hasLength(name)) {
            predicates.add(builder.equal(product.get("productName"), name));
        }
        Predicate[] predicatesArray = predicates.toArray(Predicate[]::new);
        criteria.where(predicatesArray);
        criteria.orderBy(builder.asc(product.get("productName")));

        return null;
    }
}
