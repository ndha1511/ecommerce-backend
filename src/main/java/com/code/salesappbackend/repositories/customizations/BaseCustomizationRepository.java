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
    protected EntityManager entityManager;

    protected final Class<T> entityClass;

    protected static final Pattern FILTER_PATTERN = Pattern.compile("(.*?)([<>]=?|:|-|!)([^-]*)-?(or)?");

    protected static final Pattern SORT_PATTERN = Pattern.compile("(\\w+?)(:)(asc|desc)");

    protected BaseCustomizationRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected PageResponse<?> getPageData(int pageNo, int pageSize, String[] search, String[] sort)  {
        String sql = String.format("select o from %s o where 1=1", entityClass.getName());
        StringBuilder queryBuilder = new StringBuilder(sql);
        appendQueryBuilder(search, queryBuilder, " %s o.%s %s ?%s");
        sortBy(queryBuilder, " order by o.%s %s", sort);
        Query query = entityManager.createQuery(queryBuilder.toString());
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        setValueParams(search, query);

        String sqlCount = String.format("select count(*) from %s o where 1=1", entityClass.getName());
        StringBuilder countQuery = new StringBuilder(sqlCount);
        appendQueryBuilder(search, countQuery, " %s o.%s %s ?%s");

        Query queryCount = entityManager.createQuery(countQuery.toString());
        setValueParams(search, queryCount);

        var data = query.getResultList();

        return PageResponse.builder()
                .data(data)
                .totalPage((long) Math.ceil(((long) queryCount.getSingleResult() * 1.0) / pageSize))
                .pageNo(pageNo)
                .totalElement(data.size())
                .build();
    }

    protected void setValueParams(String[] search, Query query) {
        if (search != null) {
            for (String s : search) {
                Matcher matcher = FILTER_PATTERN.matcher(s);
                if (matcher.find()) {
                    String operator = OperatorQuery.getOperator(matcher.group(2));
                    if (!operator.isEmpty()) {
                        var value = matcher.group(3);
                        if (OperatorQuery.getOperator(matcher.group(2)).equals("like")) {
                            value = String.format("%%%s%%", value);
                        }
                        query.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, value);
                    }
                }
            }
        }
    }

    protected void sortBy(StringBuilder queryBuilder, String queryFormat, String... sort) {
        if (sort != null) {
            for (String s : sort) {
                Matcher matcher = SORT_PATTERN.matcher(s);
                if (matcher.find()) {
                    String sortBy = String.format(queryFormat, matcher.group(1), matcher.group(3));
                    queryBuilder.append(sortBy);
                }
            }
        }
    }

    protected void appendQueryBuilder(String[] search, StringBuilder queryBuilder, String queryFormat) {
        if(search != null) {
            for(String s : search) {
                Matcher matcher = FILTER_PATTERN.matcher(s);
                if(matcher.find()) {
                    String operator = OperatorQuery.getOperator(matcher.group(2));
                    String format = String.format(queryFormat, matcher.group(4) != null ? "or" : "and",
                            matcher.group(1), operator,
                            Arrays.stream(search).toList().indexOf(s) + 1);
                    queryBuilder.append(format);
                }
            }
        }
    }


}
