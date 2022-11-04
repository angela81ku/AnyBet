package edu.northeastern.anybet.a8.realtimeDatabase.models;

import java.util.HashMap;
import java.util.Map;

public class StickerUser {

    private String userName;
    private Map<String, Integer> stickerCount;
    // {String stickerId: int count}
    public StickerUser() {

    }

    public StickerUser(String userName) {
        this.userName = userName;
        this.stickerCount = new HashMap<>();
    }

    public String getUserName() {
        return userName;
    }

    public Map<String, Integer> getStickerCount() {
        return stickerCount;
    }

    public int getCount(String stickerId) {
        return this.stickerCount.get(stickerId);
    }
}
