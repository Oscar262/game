package org.game.utils;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationFilters <T>{

    private List<Filter> filters = new ArrayList<>();

    public List<Filter> getFilters() {return filters;}

    protected void addFilter(String field, String value, Filter.FilterType type){
        if (value != null)
            filters.add(new Filter(field, value, type));
    }

    public Specification<T> build(){
        Specification<T> result = null;
        for (Filter filter : filters){
            if (result == null)
                result = new SpecificationImpl(filter);
            else
                result = Specification.where(result).and(new SpecificationImpl(filter));
        }
        return result;
    }

    public enum FilterType{
        STRING,
        BOOLEAN,
        NUMERIC
    }
}
