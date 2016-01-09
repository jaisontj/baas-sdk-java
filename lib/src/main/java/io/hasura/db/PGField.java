package io.hasura.db;

public class PGField<R, T> implements SelectField <R> {
    private String columnName;

    public String toQCol() {
        return columnName;
    }

}
