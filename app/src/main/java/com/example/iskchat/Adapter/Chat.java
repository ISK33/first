package com.example.iskchat.Adapter;

public class Chat {

    private String  sender, reciver,message;
    private String status,type;
    private String time;

    public Chat( String sender, String reciver,String message,String status,String time,String type) {
        this.message = message;
        this.sender = sender;
        this.reciver = reciver;
        this.status = status;
        this.type=type;
        this.time=time;
    }

    public Chat() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}