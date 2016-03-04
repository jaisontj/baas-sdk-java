package io.hasura.db;

import java.lang.reflect.Type;

public abstract class Table<R> {

    private String tableName;

    public String getTableName() {
        return this.tableName;
    }

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public abstract Type getInsResType();
    public abstract Type getSelResType();
    public abstract Type getUpdResType();
    public abstract Type getDelResType();
}
