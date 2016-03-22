package io.hasura.sample.todo;

import android.util.Log;

import io.hasura.auth.AuthService;
import io.hasura.db.DBService;
import okhttp3.OkHttpClient;
import okhttp3.JavaNetCookieJar;
import okhttp3.logging.HttpLoggingInterceptor;

import java.net.CookieManager;
import java.net.CookiePolicy;


public class Hasura {
    public final static OkHttpClient okHttpClient = buildOkHttpClient();
    public final static AuthService auth = new AuthService("http://40.83.122.181", okHttpClient);
    public final static DBService db = new DBService("http://40.83.122.181/data", "", okHttpClient);

    static OkHttpClient buildOkHttpClient() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
    }

    private Integer userId;

    public static Integer getCurrentUserId() {
        return currentCtx.userId;
    }

    public static void setUserId(Integer userId) {
        currentCtx.userId = userId;
        Log.d("user_id", userId.toString());
    }

    public static void unsetUserId() {
        currentCtx.userId = null;
        Log.d("Hasura Context", "unset current user id");
    }

    private static final Hasura currentCtx = new Hasura();
}
