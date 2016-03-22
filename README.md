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

Let's say our app module is ``com.myapp``, then we can add a Hasura singleton
class as follows:

``Hasura.java``
```
package com.myapp;

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
   public final static AuthService auth = new AuthService("http://<YOUR-HASURA-PROJECT-IP>", okHttpClient);
   public final static DBService db = new DBService("http://<YOUR-HASURA-PROJECT-IP>/data", "", okHttpClient);

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
```




