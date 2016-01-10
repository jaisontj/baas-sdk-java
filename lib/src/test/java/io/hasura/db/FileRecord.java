package io.hasura.db;

public class FileRecord {

    private String filename;
    private String serverPath;
    private Integer id;

    public FileRecord(Integer id, String filename, String serverPath) {
        this.id = id;
        this.filename = filename;
        this.serverPath = serverPath;
    }

    public String toString() {
        return "id : " + this.id + ", filename : " + filename + ", server_path : " + serverPath;
    }
}
