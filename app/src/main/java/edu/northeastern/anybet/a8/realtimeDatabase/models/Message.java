package edu.northeastern.anybet.a8.realtimeDatabase.models;

public class Message {
    private String dateTime;
    private String sender;
    private String recipient;
    private String stickerId;

    public Message() {

    }

    public Message(String dateTime, String sender, String recipient, String stickerId) {
        this.dateTime = dateTime;
        this.sender = sender;
        this.recipient = recipient;
        this.stickerId = stickerId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public String getStickerId() {
        return stickerId;
    }
}
