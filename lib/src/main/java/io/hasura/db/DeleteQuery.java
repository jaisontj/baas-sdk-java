package io.hasura.db;

import com.google.gson.*;
import com.google.gson.reflect.*;
import java.lang.reflect.Type;

import java.util.List;
import java.util.ArrayList;

import java.util.HashSet;

import java.io.IOException;

public class DeleteQuery<R> extends QueryWithReturning<DeleteQuery<R>, R>{
    private static String url = "/api/1/table/";
    private static Gson gson =
        new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();
    private JsonObject whereExp;
    private DBService rm;
    private Table<R> table;

    public DeleteQuery<R> fromRetSet(HashSet<String> retSet) {
        this.retSet = retSet;
        return this;
    }

    public DeleteQuery(DBService rm, Table<R> table) {
        super();
        this.whereExp = null;
        this.table = table;
        this.rm = rm;
    }

    public DeleteQuery<R> where(Condition<R> c) {
        this.whereExp = c.getBoolExp();
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
