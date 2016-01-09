package io.hasura.db;

public class SelectQuery<R> {
    private SelectQueryBuilder queryBuilder;
    private String tableName;

    public SelectQuery(String tableName) {
        this.queryBuilder = new SelectQueryBuilder();
        this.tableName = tableName;
    }

    public SelectQuery<R> columns(SelectField<R> f1, SelectField<R> f2) {
        return this;
    }
}
