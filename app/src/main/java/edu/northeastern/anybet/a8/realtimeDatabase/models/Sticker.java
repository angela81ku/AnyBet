package edu.northeastern.anybet.a8.realtimeDatabase.models;

public class Sticker {
    private String stickerId;
    private int count;
//    private String stickerPath;

    public Sticker() {

    }

    public Sticker(String stickerId, int count) {
        this.stickerId = stickerId;
        this.count = count;
    }

//    public Sticker(String stickerId, String stickerPath){
//        this.stickerId = stickerId;
//        this.stickerPath = stickerPath;
//    }

    public String getStickerId() {
        return stickerId;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

//    public String getStickerPath() {
//        return stickerPath;
//    }


}
