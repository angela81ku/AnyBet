package edu.northeastern.anybet.realtimeDatabase.models;

public class Sticker {
    private String stickerId;
    private String stickerPath;

    public Sticker() {

    }

    public Sticker(String stickerId, String stickerPath){
        this.stickerId = stickerId;
        this.stickerPath = stickerPath;
    }
}
