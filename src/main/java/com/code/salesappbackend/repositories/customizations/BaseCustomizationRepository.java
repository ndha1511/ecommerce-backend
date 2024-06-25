package com.code.salesappbackend.repositories.customizations;

import com.code.salesappbackend.dtos.responses.PageResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseCustomizationRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> entityClass;

    public BaseCustomizationRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public PageResponse getPageData(int pageNo, int pageSize, String[] search, String[] sort) {
        String sql = String.format("select o from %s o where 1=1", entityClass.getName());
        StringBuilder queryBuilder = new StringBuilder(sql);
        Pattern pattern = Pattern.compile("(.*?)([<>]=?|:|-)(.*)");
        appendQueryBuilder(search, pattern, queryBuilder);

        Pattern patternSort = Pattern.compile("(\\w+?)(:)(asc|desc)");
        if(sort != null) {
            for(String s : sort) {
                Matcher matcher = patternSort.matcher(s);
                if(matcher.find()) {
                    String sortBy = String.format(" order by o.%s %s", matcher.group(1), matcher.group(3));
                    queryBuilder.append(sortBy);
                }
            }
        }
        Query query = entityManager.createQuery(queryBuilder.toString());
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        setValueParams(search, pattern, query);

        String sqlCount = String.format("select count(*) from %s o where 1=1", entityClass.getName());
        StringBuilder countQuery = new StringBuilder(sqlCount);
        appendQueryBuilder(search, pattern, countQuery);
        Query queryCount = entityManager.createQuery(countQuery.toString());
        setValueParams(search, pattern, queryCount);

        var data = query.getResultList();

        return PageResponse.builder()
                .data(data)
                .totalPage((long) Math.ceil(((long)queryCount.getSingleResult() * 1.0)/pageSize))
                .pageNo(pageNo)
                .totalElement(data.toArray().length)
                .build();
    }

    private void setValueParams(String[] search, Pattern pattern, Query queryCount) {
        if(search != null) {
            for(String s : search) {
                Matcher matcher = pattern.matcher(s);
                if(matcher.find()) {
                    var value = matcher.group(3);
                    if(OperatorQuery.getOperator(matcher.group(2)).equals("like")) {
                        value = String.format("%%%s%%", value);
                    }
                    queryCount.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, value);
                }
            }
        }
    }

    private void appendQueryBuilder(String[] search, Pattern pattern, StringBuilder countQuery) {
        if(search != null) {
            for(String s : search) {
                Matcher matcher = pattern.matcher(s);
                if(matcher.find()) {
                    String operator = OperatorQuery.getOperator(matcher.group(2));
                    String format = String.format(" and o.%s %s ?%s", matcher.group(1), operator,
                            Arrays.stream(search).toList().indexOf(s) + 1);
                    countQuery.append(format);
                }
            }
        }
    }
}
