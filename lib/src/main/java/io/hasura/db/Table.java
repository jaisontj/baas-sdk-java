package io.hasura.db;

public interface Table<R> {

    public String getTableName();

    public R parseJSON(String json);
    // public static
}
