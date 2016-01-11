package io.hasura.db;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ReviewRecord {

    private Integer rating;
    private Integer id;
    private Boolean isActive;

    public ReviewRecord(Integer rating, Integer id, Boolean isActive) {
        this.rating = rating;
        this.id = id;
        this.isActive = isActive;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
