package com.alexolirib.tasks.entities;

public class HeaderEntity {
    private String token;
    private String personKey;
    private String name;

    public HeaderEntity() {
    }

    public HeaderEntity(String token, String personKey, String name) {
        this.token = token;
        this.personKey = personKey;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPersonKey() {
        return personKey;
    }

    public void setPersonKey(String personKey) {
        this.personKey = personKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
