package io.hasura.db;

import io.hasura.auth.*;

class DBTestService {
    static final AuthService authService = new AuthService("https://auth.detached83.hasura-app.io/");
    public static final DBService db =
        new DBService(authService.getUrl(), "/api/1",
                      authService.getClient()
                      .newBuilder()
                      .addInterceptor(new HDBInterceptor())
                      .build());
}
