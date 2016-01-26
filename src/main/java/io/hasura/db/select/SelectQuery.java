package io.hasura.db;

import io.hasura.core.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import java.lang.reflect.Type;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class SelectQuery<R> extends QueryWithProjection<SelectQuery<R>, R> {

    private static Gson gson =
        new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();

    private JsonObject whereExp;
    private int limit;
    private int offset;
    private DBService db;

    private Table<R> table;

    public SelectQuery<R> fromColumns(JsonArray columns) {
        this.columns = columns;
        return this;
    }

    public SelectQuery(DBService db, Table<R> table) {
        super();
        this.whereExp = null;
        this.limit = -1;
        this.offset = -1;
        this.table = table;
        this.db = db;
    }

    public SelectQuery<R> where(Condition<R> c) {
        this.whereExp = c.getBoolExp();
        return this;
    }

    public SelectQuery<R> limit(int limit) {
        this.limit = limit;
        return this;
    }

    public SelectQuery<R> offset(int offset) {
        this.offset = offset;
        return this;
    }

    public Call<List<R>, DBException> build() {
        /* Create the query object */
        JsonObject query = new JsonObject();
        query.add("columns", this.columns);
        if (this.whereExp != null)
            query.add("where", this.whereExp);
        if (this.limit != -1)
            query.add("limit", new JsonPrimitive(this.limit));
        if (this.offset != -1)
            query.add("offset", new JsonPrimitive(this.offset));

        String opUrl = "/table/" + table.getTableName() + "/select";
        return db.mkCall(opUrl, gson.toJson(query), new DBResponseConverter<>(table.getSelResType()));
    }
}
