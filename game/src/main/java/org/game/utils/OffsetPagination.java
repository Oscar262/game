package org.game.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Pattern;
import java.awt.*;
import java.util.*;
import java.util.List;

public class OffsetPagination implements Pageable {

    private final static int LIMIT = 50;
    private final static int OFFSET = 0;

    private int limit;
    private int offset;

    private LinkedHashSet<@Pattern(regexp = "^[a-zA-Z_0-9.,]+(_asc|_desc)$", message = "invalid 'sort' format (field_asc or field_desc)") String> sort;
    private Sort pageableSort;

    private Map<String, String> fieldsMap;

    public OffsetPagination() {
        this.limit = LIMIT;
        this.offset = OFFSET;
        this.pageableSort = Sort.unsorted();
    }

    public OffsetPagination(Map<String, String> fieldsMap){
        this.limit = LIMIT;
        this.offset = OFFSET;
        this.fieldsMap = fieldsMap;
        this.pageableSort = Sort.unsorted();
    }

    public OffsetPagination(int offset, int limit, Sort sort, Map<String, String> fieldsMap){
        if (offset < 0)
            offset = OFFSET;
        if (limit < 1)
            limit = LIMIT;
        this.limit = LIMIT;
        this.offset = OFFSET;
        this.fieldsMap = fieldsMap;
        this.pageableSort = sort;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setSort(LinkedHashSet<String> sort) {
        this.sort = sort;
        this.pageableSort = initPageableSort();
    }

    public Sort getPageableSort() {
        return pageableSort;
    }

    public Map<String, String> getFieldsMap() {
        return fieldsMap;
    }

    @Override
    public int getPageNumber(){return offset / limit;}

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return pageableSort;
    }

    @Override
    public Pageable next() {
        return new OffsetPagination((int)getOffset() + getPageSize(), getPageSize(), getSort(), getFieldsMap());
    }

    public OffsetPagination previous(){
        return hasPrevious() ? new OffsetPagination((int)getOffset() - getPageSize(), getPageSize(), getSort(), getFieldsMap()) : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetPagination(0, getPageSize(), getSort(), getFieldsMap());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return new OffsetPagination(0, pageNumber, getSort(), getFieldsMap());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    public Sort initPageableSort(){
        List<Sort.Order> orders = new ArrayList<>();
        if (sort != null){
            for (String sortField : sort){
                String direction = sortField.substring(sortField.lastIndexOf("_") + 1);
                String field = sortField.substring(0,sortField.lastIndexOf("_"));
                if (fieldsMap != null && fieldsMap.containsKey(field))
                    field = fieldsMap.get(field);
                String camelCaseField = toCamelCase(field, '_');
                Sort.Order order = new Sort.Order(Sort.Direction.fromString(direction), camelCaseField);
                orders.add(order);
            }
        }
        return Sort.by(orders);
    }

    private String toCamelCase(String text, char delimiter) {
        StringBuilder builder = new StringBuilder();
        boolean shouldConvertNextCharToLower = true;
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (currentChar == delimiter)
                shouldConvertNextCharToLower = false;
            else if (shouldConvertNextCharToLower)
                builder.append(currentChar);
            else {
                builder.append(Character.toUpperCase(currentChar));
                shouldConvertNextCharToLower = true;
            }
        }
        return builder.toString();
    }
}
