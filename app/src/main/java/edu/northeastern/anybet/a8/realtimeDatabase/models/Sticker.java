package edu.northeastern.anybet.a8.realtimeDatabase.models;

public class Sticker {
    private String stickerId;
    private String stickerPath;

    public Sticker() {

    }

    public Sticker(String stickerId, String stickerPath){
        this.stickerId = stickerId;
        this.stickerPath = stickerPath;
    }

    public String getStickerId() {
        return stickerId;
    }

    public String getStickerPath() {
        return stickerPath;
    }


}
