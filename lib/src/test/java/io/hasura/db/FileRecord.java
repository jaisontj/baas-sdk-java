package io.hasura.db;

public class FileRecord {

    private String filename;
    private String serverPath;

    public FileRecord(String filename, String serverPath) {
        this.filename = filename;
        this.serverPath = serverPath;
    }

    public String toString() {
        return "filename : " + filename + ", server_path : " + serverPath;
    }
}
