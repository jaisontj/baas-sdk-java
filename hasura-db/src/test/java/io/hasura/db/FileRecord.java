package io.hasura.db;

import org.apache.commons.lang3.builder.ToStringBuilder;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class FileRecord {

    private String filename;
    private String serverPath;
    private Integer id;
    private Date created;
    private Timestamp created_ts;
    private Time created_t;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
