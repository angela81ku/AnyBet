package edu.northeastern.anybet.a8.realtimeDatabase.models;

public class Message {
    private String dateTime;
    private String sender;
    private String recipient;
    private String stickerId;

    private Message() {

    }

    private Message(String dateTime, String sender, String recipient, String sticker) {
        this.dateTime = dateTime;
        this.sender = sender;
        this.recipient = recipient;
        this.stickerId = stickerId;
    }

}
