package io.hasura.db;

public class ArrayRelationship<R1, R2> extends Relationship<R2> {
    private int limit;
    private int offset;
    private String where;
}
