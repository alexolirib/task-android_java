package com.alexolirib.tasks.entities;

import java.util.Date;

public class TaskEntity {

    private Integer Id;
    private Integer PriorityId;
    private String Description;
    private Date DueDate;
    private Boolean Complete;

    public TaskEntity() {
    }

    public TaskEntity(Integer id, Integer priorityId, String description, Date dueDate, Boolean complete) {
        Id = id;
        PriorityId = priorityId;
        Description = description;
        DueDate = dueDate;
        Complete = complete;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getPriorityId() {
        return PriorityId;
    }

    public void setPriorityId(Integer priorityId) {
        PriorityId = priorityId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getDueDate() {
        return DueDate;
    }

    public void setDueDate(Date dueDate) {
        DueDate = dueDate;
    }

    public Boolean getComplete() {
        return Complete;
    }

    public void setComplete(Boolean complete) {
        Complete = complete;
    }
}
