package io.hasura.db;

import io.hasura.core.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import java.lang.reflect.Type;

import java.util.List;
import java.util.ArrayList;

import java.util.HashSet;

import java.io.IOException;

public class InsertQuery<R> extends QueryWithReturning<InsertQuery<R>, R> {
    private static Gson gson =
        new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();

    private JsonObject currentObj;
    private JsonArray insObjs;
    private DBService db;
    private Table<R> table;

    public InsertQuery<R> fromRetSet(HashSet<String> retSet) {
        this.retSet = retSet;
        return this;
    }

    public InsertQuery(DBService db, Table<R> table) {
        super();
        this.currentObj = new JsonObject();
        this.insObjs = new JsonArray();
        this.table = table;
        this.db = db;
    }

    public <T> InsertQuery<R> set(PGField<R, T> fld, T val) {
        Type valType = new TypeToken<T>() {}.getType();
        this.currentObj.add(fld.getColumnName(), gson.toJsonTree(val, valType));
        return this;
    }

    public <T> InsertQuery<R> newRecord() {
        this.insObjs.add(this.currentObj);
        this.currentObj = new JsonObject();
        return this;
    }

    public Call<InsertResult<R>, DBException> build() {
        /* Create the query object */
        this.insObjs.add(this.currentObj);
        JsonObject query = new JsonObject();
        query.add("objects", this.insObjs);
        JsonArray retArr = new JsonArray();
        for (String retCol : this.retSet)
            retArr.add(new JsonPrimitive(retCol));
        query.add("returning", retArr);

        String opUrl = "/table/" + table.getTableName() + "/insert";
        return db.mkCall(opUrl, gson.toJson(query), new DBResponseConverter<>(table.getInsResType()));
    }
}
