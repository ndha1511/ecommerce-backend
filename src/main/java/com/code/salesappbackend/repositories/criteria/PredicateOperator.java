package com.code.salesappbackend.repositories.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PredicateOperator {
    private String fieldName;
    private String operator;
    private Object value;
    private boolean orPredicate;
}
