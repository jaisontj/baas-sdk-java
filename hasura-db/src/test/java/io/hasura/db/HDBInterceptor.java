package io.hasura.db;

import okhttp3.*;
import java.io.IOException;

class HDBInterceptor implements Interceptor {
    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer q7x3d7uyxbyxw2p8jfsh4oj2c78siyru")
            .addHeader("X-Hasura-User-Id", "0")
            .addHeader("X-Hasura-Role", "admin")
            .build();
        Response response = chain.proceed(newRequest);
        return response;
    }
}
