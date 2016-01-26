package io.hasura.db;

import io.hasura.core.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import java.lang.reflect.Type;

import java.util.List;
import java.util.ArrayList;

import java.util.HashSet;

import java.io.IOException;

public class UpdateQuery<R> extends QueryWithReturning<UpdateQuery<R>, R> {
    private static Gson gson =
        new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();
    private JsonObject updObj;
    private JsonObject whereExp;
    private DBService db;
    private Table<R> table;

    public UpdateQuery(DBService db, Table<R> table) {
        super();
        this.updObj = new JsonObject();
        this.whereExp = null;
        this.table = table;
        this.db = db;
    }

    public UpdateQuery<R> fromRetSet(HashSet<String> retSet) {
        this.retSet = retSet;
        return this;
    }

    public <T> UpdateQuery<R> set(PGField<R, T> fld, T val) {
        Type valType = new TypeToken<T>() {}.getType();
        this.updObj.add(fld.getColumnName(), gson.toJsonTree(val, valType));
        return this;
    }

    public <T> UpdateQuery<R> setAndReturn(PGField<R, T> fld, T value) {
        this.set(fld, value);
        this.returning(fld);
        return this;
    }

    public UpdateQuery<R> where(Condition<R> c) {
        this.whereExp = c.getBoolExp();
        return this;
    }

    public Call<UpdateResult<R>, DBException> build() {
        /* Create the query object */
        JsonObject query = new JsonObject();
        query.add("values", this.updObj);
        if (this.whereExp != null)
            query.add("where", this.whereExp);

        if (this.retSet.size() != 0) {
            JsonArray retArr = new JsonArray();
            for (String retCol : this.retSet)
                retArr.add(new JsonPrimitive(retCol));
            query.add("returning", retArr);
        }

        String opUrl = "/table/" + table.getTableName() + "/update";
        return db.mkCall(opUrl, gson.toJson(query), new DBResponseConverter<>(table.getUpdResType()));
    }
}
