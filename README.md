This Android SDK is for hauthy and hasuradb APIs.

#Structure

This SDK is composed of 2 parts.
The first part, like a normal SDK, provides an API to login, register, logout and make hasuradb queries.
The second part auto-generates relevant classes as a part of your app code so that you can use your hasuradb
tables as native Java objects. A gradle task needs to be run to auto-generate this code.


#Usage
##Step 1: Include dependencies##
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
