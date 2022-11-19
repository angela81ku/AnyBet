package edu.northeastern.anybet.finalProject.realtimeDatabase.DAO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.anybet.finalProject.realtimeDatabase.models.Bet;
import edu.northeastern.anybet.finalProject.realtimeDatabase.models.BetUser;

public class FirebaseDAO {

    private final DatabaseReference databaseReference;

    public FirebaseDAO() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void addUser(BetUser user) {
        if (user != null) {
            databaseReference.child("betUsers").child(user.getUsername()).setValue(user);
        }
    }

    public void addBet(Bet bet) {
        if (bet != null) {
            databaseReference.child("bets").push().setValue(bet);
        }
    }
}
