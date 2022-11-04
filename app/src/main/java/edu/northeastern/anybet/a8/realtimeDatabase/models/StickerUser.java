package edu.northeastern.anybet.a8.realtimeDatabase.models;

import java.util.HashMap;

public class StickerUser {

    private String userName;
    private HashMap<String, Integer> stickerCount;
    // {String stickerId: int count}
    public StickerUser() {

    }

    public StickerUser(String userName) {
        this.userName = userName;
        this.stickerCount = new HashMap<>();
    }

}
