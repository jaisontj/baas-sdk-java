package io.hasura.sample.todo;

import android.app.Application;

import io.hasura.core.*;

/**
 * Created by loki on 18/08/16.
 */

public class TodoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Hasura.init(getApplicationContext(),"https://auth.nonslip53.hasura-app.io","https://data.nonslip53.hasura-app.io");
    }
}
