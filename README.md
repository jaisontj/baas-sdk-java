This Android SDK is for hauthy and hasuradb APIs.

#Structure

This SDK is composed of 2 parts.
The first part, like a normal SDK, provides an API to login, register, logout and make hasuradb queries.
The second part auto-generates relevant classes as a part of your app code so that you can use your hasuradb
tables as native Java objects. A gradle task needs to be run to auto-generate this code.


#Usage
##Step 0: Pre-requisites
Please make sure that your Gradle is >= 2.12

##Step 1: Include dependencies
This SDK is available via jitpack.
Add the following code to your build.gradle (at a module level, not necessarily the build.gradle of the entire project).

``build.gradle``
```
dependencies {
   compile 'com.github.hasura:baas-sdk-java:617ac3b58c'
}
...

buildscript {
   repositories {
      jcenter()
         maven { url "https://jitpack.io" }
   }
   dependencies {
      classpath 'com.github.hasura.baas-sdk-java:hasura-db-codegen:617ac3b58c'
   }
}

task generate << {

   // Read admin credentials
   Properties props = new Properties()
   props.load(new FileInputStream("hasura.properties"))

   // Build Generation Configuration
   // ------------------------------
   def cfg = new io.hasura.db.util.Configuration();
   cfg.setDir(props.getProperty("dir"))
   cfg.setPackageName(props.getProperty("package"))
   cfg.setDBUrl(props.getProperty("url"))
   cfg.setDBPrefix(props.getProperty("dbprefix"))
   cfg.setAdminAPIKey(props.getProperty("adminAPIKey"))

   // Run the code generator
   // ----------------------
   io.hasura.db.util.GenerationUtil.generate(cfg)
}
```

##Step 2: Add Hausra admin credentials
Next to the ``build.gradle`` file where the configuration mentioned above was added,
create a new file called ``hasura.properties``.
The gradle task created above, will use configuration values from this file to
automatically create classes in your application module. Let's say that our
app module is ``com.myapp`` then the following values in hasura.properties will
be appropriate.

``hasura.properties``
```
url=http://<YOUR-HASURA-PROJECT-IP>/data
adminAPIKey=<YOUR-VALID-HASURA-ADMIN-TOKEN>
package=com.myapp.db
dbprefix=
dir=src/main/java/com/myapp/db
```

##Step 3: Add a Hasura singleton class to intialize the DB and Auth components
The Hasura singleton class is just a convenient wrapper. You can use it as
per your own style. Using the file below as a base is recommended.

Let's say our app module is ``com.myapp``, and our Hasura project name is ``myproject`` then we can add a Hasura singleton
class as follows:

``Hasura.java``
```
package com.myapp;

import android.util.Log;

import io.hasura.auth.AuthService;
import io.hasura.db.DBService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class Hasura {

    public static AuthService auth;
    public static DBService db;
    private Integer userId;
    private String sessionId;
    private String role;

    public static Hasura getInstance() {
        return currentCtx;
    }

    public static void setClient() {

        // Ready
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient c = new OkHttpClient.Builder()
            .addInterceptor(new HasuraTokenInterceptor())
            .addInterceptor(logging)
            .build();

        Hasura.auth = new AuthService("http://auth.myproject.hasura-app.io", c);
        Hasura.db = new DBService("http://data.myproject.hasura-app.io/api/1", "", c);
    }

    public static Integer getCurrentUserId() {
        return currentCtx.userId;
    }

    public static String getCurrentRole() {
        return currentCtx.role;
    }

    public static String getCurrentSessionId () {
        return currentCtx.sessionId;
    }

    public static void setCurrentSession(Integer userId, String role, String sessionId) {
        Log.d("{{HASURA :: AUTH", "Setting current session");

        if (role == null) {
            role = "anonymous";
        }

        currentCtx.userId = userId;
        currentCtx.sessionId = sessionId;
        currentCtx.role = role;

        // Reset the current client so that we are logged in
        setClient();
    }

    private static final Hasura currentCtx = new Hasura();

}

```

Then add one more file as follows to automate the auth token handling:
``HasuraTokenInterceptor.java``
```
package com.myapp;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HasuraTokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        Log.d("{{HASURA INTERCEPTOR", request.headers().toString());
        String session = Hasura.getCurrentSessionId();
        String role = Hasura.getCurrentRole();

        if (session == null)  {
            response = chain.proceed(request);
        } else {
            Request newRequest = request.newBuilder()
                    .addHeader("Authorization", "Hasura " + session)
                    .addHeader("X-Hasura-Role", role)
                    .build();
            Log.d("{{HASURA INTERCEPTOR", newRequest.headers().toString());
            response = chain.proceed(newRequest);
        }

        return response;
    }
}
```
