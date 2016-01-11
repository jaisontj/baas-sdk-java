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

    public FakUserRecord(String email, String phoneNumber, Integer tmId, Integer id, Boolean isActive, FileRecord profilePic, ArrayList<ReviewRecord> reviews) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.tmId = tmId;
        this.id = id;
        this.isActive = isActive;
        this.profilePic = profilePic;
        this.reviews = reviews;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
