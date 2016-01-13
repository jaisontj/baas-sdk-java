package io.hasura.db.util;

import java.util.HashMap;

public class Configuration {
    private String hasuraDBUrl;
    private String packageName;
    private String dir;

    public Configuration(String hasuraDBUrl, String packageName, String dir) {
        this.hasuraDBUrl = hasuraDBUrl;
        this.packageName = packageName;
        this.dir = dir;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getDir() {
        return this.dir;
    }

    public String getDBUrl() {
        return this.hasuraDBUrl;
    }
}
