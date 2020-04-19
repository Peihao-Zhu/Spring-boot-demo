package com.zph.springbootapi.resource;

public class ErrorResource {
    //显示错误原因
    private String message;

    public ErrorResource(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
