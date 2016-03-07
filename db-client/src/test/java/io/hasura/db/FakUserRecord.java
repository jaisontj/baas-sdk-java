package io.hasura.db;

import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.ArrayList;

public class FakUserRecord {

    private String email;
    private String phoneNumber;
    private Integer tmId;
    private Integer id;
    private Boolean isActive;
    private FileRecord profilePic;
    private ArrayList<ReviewRecord> reviews;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
