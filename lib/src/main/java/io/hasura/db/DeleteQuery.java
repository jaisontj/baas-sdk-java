package io.hasura.db;

import com.google.gson.*;
import com.google.gson.reflect.*;
import java.lang.reflect.Type;

import java.util.List;
import java.util.ArrayList;

import java.util.Set;
import java.util.HashSet;

import java.io.IOException;

public class DeleteQuery<R> {
    private static String url = "/api/1/table/";
    private static Gson gson =
        new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();
    private JsonObject whereExp;
    private Set<String> retSet;
    private RequestMaker rm;
    private Table<R> table;

    public DeleteQuery(RequestMaker rm, Table<R> table) {
        this.whereExp = null;
        this.retSet = new HashSet();
        this.table = table;
        this.rm = rm;
    }

    public DeleteQuery<R> where(Condition<R> c) {
        this.whereExp = c.getBoolExp();
        return this;
    }

    public <T1> DeleteQuery<R> returning(PGField<R, T1> f1) {
        this.retSet.add(f1.getColumnName());
        return this;
    }

    public <T1, T2> DeleteQuery<R> returning(PGField<R, T1> f1, PGField<R, T2> f2) {
        this.retSet.add(f1.getColumnName());
        this.retSet.add(f2.getColumnName());
        return this;
    }

    public <T1, T2, T3> DeleteQuery<R> returning(PGField<R, T1> f1, PGField<R, T2> f2, PGField<R, T3> f3) {
        this.retSet.add(f1.getColumnName());
        this.retSet.add(f2.getColumnName());
        this.retSet.add(f3.getColumnName());
        return this;
    }

    public DeleteResult<R> execute() throws IOException {
        /* Create the query object */
        JsonObject query = new JsonObject();
        if (this.whereExp != null)
            query.add("where", this.whereExp);

        if (this.retSet.size() != 0) {
            JsonArray retArr = new JsonArray();
            for (String retCol : this.retSet)
                retArr.add(new JsonPrimitive(retCol));
            query.add("returning", retArr);
        }

        String opUrl = url + table.getTableName() + "/delete";
        String response = rm.post(opUrl, gson.toJson(query));
        return gson.fromJson(response, table.getDelResType());
    }
}
