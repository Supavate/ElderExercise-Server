package com.example.elderexserver.exercise;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Exercise {
    @Id
    private Integer id;
    private String name;
    private String description;
    private String video_url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video) {
        this.video_url = video;
    }
}
