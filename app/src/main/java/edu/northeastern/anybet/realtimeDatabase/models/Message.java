package edu.northeastern.anybet.realtimeDatabase.models;

public class Message {
    private String dateTime;
    private StickerUser sender;
    private StickerUser recipient;
    private Sticker sticker;

    private Message() {

    }

    private Message(String dateTime, StickerUser sender, StickerUser recipient, Sticker sticker) {
        this.dateTime = dateTime;
        this.sender = sender;
        this.recipient = recipient;
        this.sticker = sticker;
    }

}
