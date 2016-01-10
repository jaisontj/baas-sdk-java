package io.hasura.db;

import com.google.gson.*;
import com.google.gson.reflect.*;
import java.lang.reflect.Type;

import java.util.List;
import java.util.ArrayList;

import java.util.Set;
import java.util.HashSet;

import java.io.IOException;

public class InsertQuery<R> {
    private static String url = "/api/1/table/";
    private static Gson gson =
        new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();

    private JsonObject insObj;
    private Set<String> retSet;
    private RequestMaker rm;
    private Table<R> table;

    public InsertQuery(RequestMaker rm, Table<R> table) {
        this.insObj = new JsonObject();
        this.retSet = new HashSet();
        this.table = table;
        this.rm = rm;
    }

    public <T> InsertQuery<R> set(PGField<R, T> fld, T val) {
        Type valType = new TypeToken<T>() {}.getType();
        this.insObj.add(fld.getColumnName(), gson.toJsonTree(val, valType));
        return this;
    }

    public <T> InsertQuery<R> setAndReturn(PGField<R, T> fld, T value) {
        this.set(fld, value);
        this.retSet.add(fld.getColumnName());
        return this;
    }

    public <T1> InsertQuery<R> returning(PGField<R, T1> f1) {
        this.retSet.add(f1.getColumnName());
        return this;
    }

    public <T1, T2> InsertQuery<R> returning(PGField<R, T1> f1, PGField<R, T2> f2) {
        this.retSet.add(f1.getColumnName());
        this.retSet.add(f2.getColumnName());
        return this;
    }

    public <T1, T2, T3> InsertQuery<R> returning(PGField<R, T1> f1, PGField<R, T2> f2, PGField<R, T3> f3) {
        this.retSet.add(f1.getColumnName());
        this.retSet.add(f2.getColumnName());
        this.retSet.add(f3.getColumnName());
        return this;
    }

    public InsertResult<R> execute() throws IOException {
        /* Create the query object */
        JsonObject query = new JsonObject();
        query.add("object", this.insObj);
        JsonArray retArr = new JsonArray();
        for (String retCol : this.retSet)
            retArr.add(new JsonPrimitive(retCol));
        query.add("returning", retArr);

        String opUrl = url + table.getTableName() + "/insert";
        String response = rm.post(opUrl, gson.toJson(query));
        return gson.fromJson(response, table.getInsResType());
    }
}
