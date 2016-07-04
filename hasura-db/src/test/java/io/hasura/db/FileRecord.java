package io.hasura.db;

import org.apache.commons.lang3.builder.ToStringBuilder;
import java.sql.Date;

public class FileRecord {

    private String filename;
    private String serverPath;
    private Integer id;
    private Date created;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
