package com.fatecerss.tcc.apprendendo.model;

/**
 * Created by Sandro on 17/06/2018.
 */

public class Message {

    private String userId;
    private String message;

    public Message(){

    }

    public Message(String negotiationId, String message) {
        this.userId = negotiationId;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String negotiationId) {
        this.userId = negotiationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
