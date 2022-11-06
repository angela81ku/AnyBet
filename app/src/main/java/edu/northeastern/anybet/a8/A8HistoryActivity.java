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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.anybet.R;
import edu.northeastern.anybet.a8.realtimeDatabase.models.Message;
import edu.northeastern.anybet.a8.realtimeDatabase.models.Sticker;

public class A8HistoryActivity extends AppCompatActivity {
    String curUser;
    FirebaseDatabase db;
    List<Sticker> stickers = new ArrayList<>();
    RecyclerView stickersRecyclerView;
    StickerAdaptor stickersAdaptor;
    Map<String, Integer> countMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a8_history);

        db = FirebaseDatabase.getInstance();
        stickers = new ArrayList<>();
        stickersRecyclerView = findViewById(R.id.rvSent);
        stickersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stickersAdaptor = new StickerAdaptor(this.stickers, this);
        stickersRecyclerView.setAdapter(stickersAdaptor);
        countMap = new HashMap<>();
        Intent intent = getIntent();
        if (intent != null) {
            curUser = intent.getStringExtra("username");
        }


        DatabaseReference ref =  db.getReference("messages");
        ref.orderByChild("recipient").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message msg = snapshot.getValue(Message.class);
                if (msg.getSender().equals(curUser)) {
                    String id = msg.getStickerId();
                    if (!countMap.containsKey(id)) {
                        Sticker s = new Sticker(msg.getStickerId(), 1);
                        stickers.add(s);
                        countMap.put(id, stickers.size() - 1);
                    } else {
                        int index = countMap.get(id);
                        int count = stickers.get(index).getCount();
                        stickers.get(index).setCount(count + 1);
                    }

                    stickersAdaptor.notifyItemChanged(stickersAdaptor.getItemCount() - 1);
//                    System.out.println(msg.getStickerId());
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