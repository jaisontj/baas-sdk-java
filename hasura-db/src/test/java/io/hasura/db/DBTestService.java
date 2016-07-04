package io.hasura.db;

import io.hasura.auth.*;

class DBTestService {
    static final AuthService authService = new AuthService("http://127.0.0.1:8080");
    public static final DBService db =
        new DBService(authService.getUrl(), "/api/1",
                      authService.getClient()
                      .newBuilder()
                      .addInterceptor(new HDBInterceptor())
                      .build());
}
