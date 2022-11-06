package edu.northeastern.anybet.a8.realtimeDatabase.models;

public class ReceivedSticker {
    private String stickerId;
    private String sendUser;
    private String sendTime;


    public ReceivedSticker(String stickerId, String sendUser, String sendTime) {
        this.stickerId = stickerId;
        this.sendUser = sendUser;
        this.sendTime = sendTime;
    }

    public String getStickerId() {
        return stickerId;
    }

    public String getSendUser() {
        return sendUser;
    }

    public String getSendTime() {
        return sendTime;
    }
}
