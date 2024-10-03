package com.legacy.demo;

import com.legacy.demo.Issue;

public class IssueDto {
    private Integer id;
    private String description;
    private String location;
    private String status;

    public IssueDto(Issue issue){
        this.id = issue.getId();
        this.description = issue.getDescription();
        this.location = issue.getLocation();
        this.status = issue.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
