package io.hasura.db;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class RequestMaker {
    public static final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        System.out.println(json);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
            .url(this.dbUrl + url)
            .header("X-Hasura-Role", "admin")
            .header("X-Hasura-User-Id", "0")
            .post(body)
            .build();
        Response response = client.newCall(request).execute();
        String respStr = response.body().string();
        System.out.println(respStr);
        return respStr;
    }

    private String dbUrl;

    public RequestMaker(String dbUrl) {
        this.dbUrl = dbUrl;
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
