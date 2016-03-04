package io.hasura.db.util;

public class Configuration {
    private String hasuraDBUrl;
    private String dbPrefix;
    private String packageName;
    private String dir;
    private String adminAPIKey;

    public String getPackageName() {
        return this.packageName;
    }

    public String getAdminAPIKey() {
        return this.adminAPIKey;
    }

    public String getDir() {
        return this.dir;
    }

    public String getDBUrl() {
        return this.hasuraDBUrl;
    }

    public String getDBPrefix() {
        return this.dbPrefix;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setAdminAPIKey(String adminAPIKey) {
        this.adminAPIKey = adminAPIKey;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setDBUrl(String dbUrl) {
        this.hasuraDBUrl = dbUrl;
    }

    public void setDBPrefix(String dbPrefix) {
        this.dbPrefix = dbPrefix;
    }
}
