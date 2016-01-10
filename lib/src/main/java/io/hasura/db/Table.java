package io.hasura.db;

import java.util.List;
import java.lang.reflect.Type;

public interface Table<R> {

    public String getTableName();

    public Type getSingleType();
    public Type getListType();
    // public static
}
