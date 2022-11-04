package edu.northeastern.anybet.a8.realtimeDatabase.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String dateTime;
    private String sender;
    private String recipient;
    private String stickerId;

    public Message() {

    }

    public Message(String sender, String recipient, String stickerId) {
        this.sender = sender;
        this.recipient = recipient;
        this.stickerId = stickerId;

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateTime = dateTime.format(formatter);
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
