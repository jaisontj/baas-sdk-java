package io.hasura.db;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import io.hasura.core.*;

import java.lang.reflect.Type;
import java.io.IOException;

public class DBService {
    public static final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient client;

    private String dbUrl;
    private String dbPrefix;

    public DBService(String dbUrl, String dbPrefix, OkHttpClient client) {
        this.dbUrl  = dbUrl;
        this.dbPrefix = dbPrefix;
        this.client = client;
    }

    public <T, E extends Exception> Call<T, E> mkCall(String url, String jsonBody, Converter<T, E> converter) {
        RequestBody reqBody = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
            .url(this.dbUrl + this.dbPrefix + url)
            .post(reqBody)
            .build();
        return new Call<>(client.newCall(request), converter);
    }

    public <R> SelectQuery<R> select(Table<R> table) {
        return new SelectQuery<R>(this, table);
    }

    public <R> InsertQuery<R> insert(Table<R> table) {
        return new InsertQuery<R>(this, table);
    }

    public <R> UpdateQuery<R> update(Table<R> table) {
        return new UpdateQuery<R>(this, table);
    }

    public <R> DeleteQuery<R> delete(Table<R> table) {
        return new DeleteQuery<R>(this, table);
    }
}
