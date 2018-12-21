package com.alexolirib.tasks.entities;

public class PriorityEntity {
    private Integer Id;
    private String Description;

    public PriorityEntity(Integer Id, String Description) {
        this.Id = Id;
        this.Description = Description;
    }

    public PriorityEntity() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
