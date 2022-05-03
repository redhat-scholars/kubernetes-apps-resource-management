package com.redhat.developers;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;

import javax.persistence.Entity;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message extends PanacheEntity {

    public String content;
    public String language;
    public String country;

    public int update(String content, String language) {
        return update("content= :content where language= :language ",
                Parameters.with("content", content)
                        .and("language", language));
    }
}