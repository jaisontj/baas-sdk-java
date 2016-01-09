package io.hasura.db;

public class Relationship<R> {
    private String columns;
    private String name;

    public Relationship() {
        this.columns = null;
        this.name = null;
    }
    public Relationship<R> columns(SelectField<R> f1, SelectField<R> f2) {
        return this;
    }
}
