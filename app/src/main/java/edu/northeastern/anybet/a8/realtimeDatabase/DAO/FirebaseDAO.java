package edu.northeastern.anybet.a8.realtimeDatabase.DAO;

import com.google.firebase.database.DatabaseReference;

import edu.northeastern.anybet.a8.realtimeDatabase.models.Message;
import edu.northeastern.anybet.a8.realtimeDatabase.models.StickerUser;

public class FirebaseDAO {

    private final DatabaseReference databaseReference;

    public FirebaseDAO(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public void addUser(StickerUser user) {
        if (user != null) {
            databaseReference.child("users").push().setValue(user);
        }
    }

    public void addMessage(Message message) {
        if (message != null) {
            databaseReference.child("messages").push().setValue(message);
        }
    }
}
