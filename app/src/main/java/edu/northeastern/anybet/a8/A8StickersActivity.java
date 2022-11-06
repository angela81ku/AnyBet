package edu.northeastern.anybet.a8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.northeastern.anybet.R;
import edu.northeastern.anybet.a8.realtimeDatabase.models.Message;
import edu.northeastern.anybet.a8.realtimeDatabase.models.ReceivedSticker;
import edu.northeastern.anybet.a8.realtimeDatabase.models.Sticker;

public class A8StickersActivity extends AppCompatActivity {
    String curUser;
    FirebaseDatabase db;
    List<ReceivedSticker> stickers = new ArrayList<>();
    RecyclerView stickersRecyclerView;
    ReceivedStickerAdaptor stickersAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a8_stickers);

        db = FirebaseDatabase.getInstance();
        stickers = new ArrayList<>();
        stickersRecyclerView = findViewById(R.id.rvReceived);
        stickersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stickersAdaptor = new ReceivedStickerAdaptor(this.stickers, this);
        stickersRecyclerView.setAdapter(stickersAdaptor);
        Intent intent = getIntent();
        if (intent != null) {
            curUser = intent.getStringExtra("username");
        }
        DatabaseReference ref =  db.getReference("messages");
        ref.orderByChild("recipient").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message msg = snapshot.getValue(Message.class);
                if (msg.getRecipient().equals(curUser)) {
                    ReceivedSticker sticker = new ReceivedSticker(msg.getStickerId(), msg.getSender(), msg.getDateTime());
                    stickers.add(sticker);
                    stickersAdaptor.notifyItemChanged(stickersAdaptor.getItemCount() - 1);
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}