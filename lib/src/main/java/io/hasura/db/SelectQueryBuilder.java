package io.hasura.db;

public class SelectQueryBuilder {
    private String columns;
    private String whereExp;
    private int limit;
    private int offset;

    public SelectQueryBuilder() {
        this.columns = null;
        this.whereExp = null;
        this.limit = -1;
        this.offset = -1;
    }
}
