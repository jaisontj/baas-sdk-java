package io.hasura.db;

import io.hasura.core.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import java.lang.reflect.Type;

import java.util.List;
import java.util.ArrayList;

import java.util.HashSet;

import java.io.IOException;

public class DeleteQuery<R> extends QueryWithReturning<DeleteQuery<R>, R>{
    private static Gson gson =
        new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();
    private JsonObject whereExp;
    private DBService db;
    private Table<R> table;

    public DeleteQuery<R> fromRetSet(HashSet<String> retSet) {
        this.retSet = retSet;
        return this;
    }

    public DeleteQuery(DBService db, Table<R> table) {
        super();
        this.whereExp = null;
        this.table = table;
        this.db = db;
    }

    public DeleteQuery<R> where(Condition<R> c) {
        this.whereExp = c.getBoolExp();
        return this;
    }

    public Call<DeleteResult<R>, DeleteException> build() {
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

        String opUrl = "/table/" + table.getTableName() + "/delete";
        return db.mkCall(opUrl, gson.toJson(query), new DeleteConverter<>(table.getDelResType()));
    }
}
