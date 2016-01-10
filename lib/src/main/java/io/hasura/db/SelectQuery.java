package io.hasura.db;

import com.google.gson.*;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class SelectQuery<R> {

    private static String url = "/api/1/table/";
    private static Gson gson = new Gson();

    private JsonArray columns;
    private JsonObject whereExp;
    private int limit;
    private int offset;
    private RequestMaker rm;

    private Table<R> table;
    public SelectQuery(RequestMaker rm, Table<R> table) {
        this.columns  = new JsonArray();
        this.whereExp = null;
        this.limit = -1;
        this.offset = -1;
        this.table = table;
        this.rm = rm;
    }

    public SelectQuery<R> columns(SelectField<R> f1, SelectField<R> f2) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        return this;
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

    public List<R> fetch() throws IOException {
        /* Create the query object */
        JsonObject query = new JsonObject();
        query.add("columns", this.columns);
        if (this.whereExp != null)
            query.add("where", this.whereExp);
        if (this.limit != -1)
            query.add("limit", new JsonPrimitive(this.limit));
        if (this.offset != -1)
            query.add("offset", new JsonPrimitive(this.offset));

        String tableSelUrl = url + table.getTableName() + "/select";
        String response = rm.post(tableSelUrl, gson.toJson(query));
        return gson.fromJson(response, table.getListType());
    }
}
