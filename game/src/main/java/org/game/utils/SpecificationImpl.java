package org.game.utils;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SpecificationImpl<T> implements Specification<T> {

    private final Filter filter;

    public SpecificationImpl(Filter filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String field = filter.getField();
        StringTokenizer tokenizer = new StringTokenizer(field, ".");
        int tokens = tokenizer.countTokens();
        Path path = null;
        if (tokens == 1)
            path = root.get(field);
        else if (tokens == 2)
            path = root.get(tokenizer.nextToken()).get(tokenizer.nextToken());
        else if (tokens == 3)
            path = root.get(tokenizer.nextToken()).get(tokenizer.nextToken()).get(tokenizer.nextToken());
        else if (tokens == 4)
            path = root.get(tokenizer.nextToken()).get(tokenizer.nextToken()).get(tokenizer.nextToken()).get(tokenizer.nextToken());
        switch (filter.getOperator()) {
            case EQUAL:
                if (filter.getType().equals(Filter.FilterType.STRING_ARRAY))
                    return criteriaBuilder.equal(criteriaBuilder.literal(filter.getValue()), criteriaBuilder.function("any", String.class, path));
                else if (filter.getType().equals(Filter.FilterType.LOCAL_DATE_TIME)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(filter.getValue(), formatter);
                    return criteriaBuilder.equal(criteriaBuilder.literal(localDateTime), path);
                } else
                    return criteriaBuilder.equal(path, filter.getType().equals(Filter.FilterType.BOOLEAN) ?
                            Boolean.valueOf(filter.getValue()) : (filter.getType().equals(Filter.FilterType.NUMERIC) ?
                            Long.valueOf(filter.getValue()) : (filter.getType().equals(Filter.FilterType.UUID) ?
                            UUID.fromString(filter.getValue()) : filter.getValue())));
            case NOT_EQUAL:
                if (filter.getType().equals(Filter.FilterType.STRING_ARRAY))
                    return criteriaBuilder.notEqual(criteriaBuilder.literal(filter.getValue()), criteriaBuilder.function("any", String.class, path));
                else if (filter.getType().equals(Filter.FilterType.LOCAL_DATE_TIME)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(filter.getValue(), formatter);
                    return criteriaBuilder.notEqual(criteriaBuilder.literal(localDateTime), path);
                } else
                    return criteriaBuilder.notEqual(path, filter.getType().equals(Filter.FilterType.BOOLEAN) ?
                            Boolean.valueOf(filter.getValue()) : (filter.getType().equals(Filter.FilterType.NUMERIC) ?
                            Long.valueOf(filter.getValue()) : (filter.getType().equals(Filter.FilterType.UUID) ?
                            UUID.fromString(filter.getValue()) : filter.getValue())));

            case CONTAINS:
                List<String> values = new ArrayList<>(Arrays.asList(filter.getValue().split(",")));
                if (filter.getType().equals(Filter.FilterType.STRING_ARRAY)){
                    List<Predicate> predicates = new ArrayList<>();
                    for (String value : values){
                        predicates.add(criteriaBuilder.equal(criteriaBuilder.literal(value), criteriaBuilder.function("any", String.class, path)));
                    }
                    return criteriaBuilder.or(predicates.toArray(predicates.toArray(new Predicate[0])));
                }
                else if (filter.getType().equals(Filter.FilterType.LOCAL_DATE_TIME)) {
                    List<Predicate> predicates = new ArrayList<>();
                    for (String value : values){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime localDateTime = LocalDateTime.parse(value, formatter);
                        predicates.add(criteriaBuilder.equal(criteriaBuilder.literal(localDateTime), criteriaBuilder.function("any", String.class, path)));
                    }
                    return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
                } else
                    return (path).in(filter.getType().equals(Filter.FilterType.BOOLEAN) ?
                            values.stream().map(value -> Boolean.valueOf(filter.getValue())).collect(Collectors.toList()) :
                            (filter.getType().equals(Filter.FilterType.NUMERIC) ?
                            values.stream().map(value -> Long.valueOf(filter.getValue())).collect(Collectors.toList()) :
                            (filter.getType().equals(Filter.FilterType.UUID) ?
                                    values.stream().map(value -> UUID.fromString(filter.getValue())).collect(Collectors.toList()) : values)));
            case NOT_CONTAINS:
                List<String> ninValues = new ArrayList<>(Arrays.asList(filter.getValue().split(",")));
                if (filter.getType().equals(Filter.FilterType.STRING_ARRAY)){
                    List<Predicate> predicates = new ArrayList<>();
                    for (String value : ninValues){
                        predicates.add(criteriaBuilder.notEqual(criteriaBuilder.literal(value), criteriaBuilder.function("any", String.class, path)));
                    }
                    return criteriaBuilder.and(predicates.toArray(predicates.toArray(new Predicate[0])));
                }
                else if (filter.getType().equals(Filter.FilterType.LOCAL_DATE_TIME)) {
                    List<Predicate> predicates = new ArrayList<>();
                    for (String value : ninValues){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime localDateTime = LocalDateTime.parse(value, formatter);
                        predicates.add(criteriaBuilder.notEqual(criteriaBuilder.literal(localDateTime), criteriaBuilder.function("any", String.class, path)));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                } else
                    return criteriaBuilder.or((path).in(filter.getType().equals(Filter.FilterType.BOOLEAN) ?
                            ninValues.stream().map(value -> Boolean.valueOf(filter.getValue())).collect(Collectors.toList()) :
                            (filter.getType().equals(Filter.FilterType.NUMERIC) ?
                            ninValues.stream().map(value -> Long.valueOf(filter.getValue())).collect(Collectors.toList()) :
                            (filter.getType().equals(Filter.FilterType.UUID) ?
                                    ninValues.stream().map(value -> UUID.fromString(filter.getValue())).collect(Collectors.toList()) : ninValues))).not(),
                path.isNull());

            case GREATER_THAN:
                if (filter.getType().equals(Filter.FilterType.LOCAL_DATE_TIME)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(filter.getValue(), formatter);
                    return criteriaBuilder.greaterThan(path, criteriaBuilder.literal(localDateTime));
                } else
                    return criteriaBuilder.greaterThan(path, Long.valueOf(filter.getValue()));

            case GREATER_THAN_OR_EQUAL:
                if (filter.getType().equals(Filter.FilterType.LOCAL_DATE_TIME)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(filter.getValue(), formatter);
                    return criteriaBuilder.greaterThanOrEqualTo(path, criteriaBuilder.literal(localDateTime));
                } else
                    return criteriaBuilder.greaterThanOrEqualTo(path, Long.valueOf(filter.getValue()));

            case LOWER_THAN:
                if (filter.getType().equals(Filter.FilterType.LOCAL_DATE_TIME)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(filter.getValue(), formatter);
                    return criteriaBuilder.lessThan(path, criteriaBuilder.literal(localDateTime));
                } else
                    return criteriaBuilder.lessThan(path, Long.valueOf(filter.getValue()));

            case LOWER_THAN_OR_EQUAL:
                if (filter.getType().equals(Filter.FilterType.LOCAL_DATE_TIME)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(filter.getValue(), formatter);
                    return criteriaBuilder.lessThanOrEqualTo(path, criteriaBuilder.literal(localDateTime));
                } else
                    return criteriaBuilder.lessThanOrEqualTo(path, Long.valueOf(filter.getValue()));
            case LIKE:
                if (filter.getType().equals(Filter.FilterType.STRING))
                    return criteriaBuilder.like(criteriaBuilder.upper(path), "%" + filter.getValue().toUpperCase() + "%");
            case NULL:
                return criteriaBuilder.isNull(path);
            case NOT_NULL:
                return criteriaBuilder.isNotNull(path);
        }
        return null;
    }
}
