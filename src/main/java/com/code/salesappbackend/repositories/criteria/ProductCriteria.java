package com.code.salesappbackend.repositories.criteria;

import com.code.salesappbackend.dtos.responses.PageResponse;
import com.code.salesappbackend.models.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class ProductCriteria {

    @PersistenceContext
    private EntityManager entityManager;

    public PageResponse getPageDataCriteria(int pageNo, int pageSize, String[] search, String[] sort) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        // builder -> and, or, >=, <=, equal, =, like, between
        CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
        // criteria -> select, where, group, having, order by,
        Root<Product> root = criteriaQuery.from(Product.class);
        // root -> join, ánh xạ với entity

        List<Predicate> predicates = createPredicate(root, builder, search, false);
        List<Predicate> orPredicates = createPredicate(root, builder, search, true);
        if (!orPredicates.isEmpty()) {
            Predicate orPredicate = builder.or(orPredicates.toArray(Predicate[]::new));
            predicates.add(orPredicate);
        }

        criteriaQuery.where(predicates.toArray(Predicate[]::new));
        // sort
        sortBy(root, builder, criteriaQuery, sort);

        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        // list product
        List<Product> products = query.getResultList();

        // đếm số trang
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaCountQuery = countBuilder.createQuery(Long.class);
        Root<Product> countRoot = criteriaCountQuery.from(Product.class);
        criteriaCountQuery.select(countBuilder.count(countRoot));
        List<Predicate> predicatesCount = createPredicate(countRoot, countBuilder, search, false);
        List<Predicate> orPredicatesCount = createPredicate(countRoot, countBuilder, search, true);
        if (!orPredicatesCount.isEmpty()) {
            Predicate orPredicate = countBuilder.or(orPredicatesCount.toArray(Predicate[]::new));
            predicatesCount.add(orPredicate);
        }
        criteriaCountQuery.where(predicatesCount.toArray(Predicate[]::new));
        Query countQueryRs = entityManager.createQuery(criteriaCountQuery);
        Long count = (Long) countQueryRs.getSingleResult();

        return PageResponse.builder()
                .totalPage((long)Math.ceil(count * 1.0/pageSize))
                .data(products)
                .pageNo(pageNo)
                .totalElement(products.size())
                .build();
    }

    private List<Predicate> createPredicate(Root<Product> root, CriteriaBuilder builder, String[] search, boolean orPredicate) {
        PredicateQuery<Product> predicateQuery = new PredicateQuery<>(root, builder);
        List<Predicate> predicates = new ArrayList<>();
        Pattern pattern = Pattern.compile("(.*?)([<>]=?|:|-)([^-]*)-?(or)?");
        if(search != null) {
            for (String s: search) {
                Matcher matcher = pattern.matcher(s);
                if(matcher.find()) {
                    PredicateOperator predicateOperator = new PredicateOperator(
                            matcher.group(1),
                            matcher.group(2),
                            matcher.group(3),
                            matcher.group(4) != null
                    );
                    if(orPredicate && predicateOperator.isOrPredicate()) {
                        predicates.add(predicateQuery.toPredicate(predicateOperator));
                    } else if(!orPredicate && !predicateOperator.isOrPredicate()) {
                        predicates.add(predicateQuery.toPredicate(predicateOperator));
                    }
                }
            }
        }
        return predicates;
    }



    private void sortBy(Root<Product> root, CriteriaBuilder builder, CriteriaQuery<Product> criteriaQuery, String[] sort) {
        Pattern pattern = Pattern.compile("(.*?)(:)(asc|desc)");
        if(sort != null) {
            for (String s: sort) {
                Matcher matcher = pattern.matcher(s);
                if(matcher.find()) {
                    if(matcher.group(3).equals("asc")) {
                        criteriaQuery.orderBy(builder.asc(root.get(matcher.group(1))));
                    } else {
                        criteriaQuery.orderBy(builder.desc(root.get(matcher.group(1))));
                    }

                }
            }
        }
    }
}
