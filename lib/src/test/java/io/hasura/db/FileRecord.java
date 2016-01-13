package io.hasura.db;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class FileRecord {

    private String filename;
    private String serverPath;
    private Integer id;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
