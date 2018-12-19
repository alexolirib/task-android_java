package com.alexolirib.tasks.entities;

public class APIResponse {
    private String json;
    private Integer statusCode;

    public APIResponse() {
    }

    public APIResponse(String json, Integer statusCode) {
        this.json = json;
        this.statusCode = statusCode;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
