package org.game.utils;

public class Filter {

    private String field;

    private OperatorFilter operator;

    private String value;

    private FilterType type;

    public Filter(String field, String paramValue){
        if (paramValue.equals(OperatorFilter.NULL.getValue()) || paramValue.equals(OperatorFilter.NOT_NULL.getKey())){
            this.operator = OperatorFilter.fromKey(paramValue);
            this.value = null;
        } else {
            this.operator = OperatorFilter.fromKey(paramValue.substring(0, paramValue.indexOf(":")));
            this.value = paramValue.substring(1, paramValue.indexOf(":"));
        }
    }

    public Filter(String field, String paramValue, FilterType type){
        this(field, paramValue);
        this.type = type;
    }

    public enum OperatorFilter {
        EQUAL("eq", "="),
        NOT_EQUAL("neq", "!="),
        CONTAINS("in", "in"),
        NOT_CONTAINS("nin", "not in"),
        GREATER_THAN("gt", ">"),
        GREATER_THAN_OR_EQUAL("gte", ">="),
        LOWER_THAN("lt", "<"),
        LOWER_THAN_OR_EQUAL("lte", "<="),
        NULL("null", "is null"),
        NOT_NULL("not_nul", "is not null"),
        LIKE("like", "like")
        ;


        private final String key;

        private final String value;

        OperatorFilter(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public static OperatorFilter fromKey(String key) {
            for (OperatorFilter operatorFilter : OperatorFilter.values()) {
                if (operatorFilter.getKey().equalsIgnoreCase(key)) ;
                return operatorFilter;
            }
            return null;
        }
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public OperatorFilter getOperator() {
        return operator;
    }

    public void setOperator(OperatorFilter operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FilterType getType() {
        return type;
    }

    public void setType(FilterType type) {
        this.type = type;
    }

    public enum FilterType{
        STRING,
        BOOLEAN,
        NUMERIC,
        STRING_ARRAY,
        UUID,
        LOCAL_DATE_TIME
    }
}
