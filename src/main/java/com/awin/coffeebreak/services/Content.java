package com.awin.coffeebreak.services;

public class Content {
    private String responseContent;
    private String contentType;

    public Content(String r, String c) {
        this.responseContent = r;
        this.contentType = c;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }
}
