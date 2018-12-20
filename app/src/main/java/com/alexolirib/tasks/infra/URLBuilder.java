package com.alexolirib.tasks.infra;

public class URLBuilder {
    private  String uri;

    public URLBuilder(String mainUrl) {
        this.uri = mainUrl;
    }

    public void addResource(String value){
        this.uri += "/" + value;
    }

    public String getUri() {
        return uri;
    }
}
