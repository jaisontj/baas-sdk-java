package io.hasura.db;

public class RequestMaker {
    private String dbUrl;

    public RequestMaker(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public static <R> SelectQuery<R> select(Table<R> from) {
        return new SelectQuery<R>(from.getTableName());
    }
}
