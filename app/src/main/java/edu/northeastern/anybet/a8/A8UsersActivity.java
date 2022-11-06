package edu.northeastern.anybet.a8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import edu.northeastern.anybet.R;
import edu.northeastern.anybet.a8.realtimeDatabase.DAO.FirebaseDAO;
import edu.northeastern.anybet.a8.realtimeDatabase.models.Message;
import edu.northeastern.anybet.a8.realtimeDatabase.models.StickerUser;

public class A8UsersActivity extends AppCompatActivity {

    TextView a8UserUsername;
    Button a8UserSendButton;
    EditText a8UserReceiveUsernameInput;
    String stickerId = "";
    Button a8HistoryButton;
    Button a8StickersButton;

    String curUser;

    FirebaseDAO dao;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a8_users);

        a8UserUsername = findViewById(R.id.a8User_username);
        a8UserSendButton = findViewById(R.id.a8User_send_button);
        a8UserReceiveUsernameInput = findViewById(R.id.a8User_receiveUsername_Input);
        a8StickersButton = findViewById(R.id.a8User_stickers_Button);
        a8HistoryButton = findViewById(R.id.a8User_receiveHistory_Button);

        dao = new FirebaseDAO();
        db = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            curUser = intent.getStringExtra("username");
            a8UserUsername.setText("Hello, " + curUser);
        }

        // send message button
        a8UserSendButton.setOnClickListener(view -> {
            String recipient = a8UserReceiveUsernameInput.getText().toString();

            // check if recipient exist
            db.getReference("users").child(recipient).get()
                    .addOnCompleteListener(task -> {
                        if (task.getResult().getValue(StickerUser.class) == null) {
                            // if not a valid recipient, show warning
                            Toast.makeText(A8UsersActivity.this, "Sorry, unknown recipient.", Toast.LENGTH_SHORT).show();
                        } else {
                            // send new message
                            if(stickerId.equals("")){
                                Toast.makeText(this, "Please select a sticker before send",
                                        Toast.LENGTH_SHORT).show();
                            }else if(recipient.equals("") ){
                                Toast.makeText(this, "Please type a receiver before send",
                                        Toast.LENGTH_SHORT).show();
                            }else if( recipient.equals(curUser)){
                                Toast.makeText(this, "Please type another user other than yourself before send",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                            Message message = new Message(curUser, recipient, stickerId);
                            dao.addMessage(message);
                                Toast.makeText(this, "You sent a sticker to " + recipient+"! ",
                                        Toast.LENGTH_SHORT).show();

                            // update sticker count
                            updateStickerCount(stickerId);}
                        }
                    });
        });

        db.getReference("messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message msg = snapshot.getValue(Message.class);

                if (msg.getRecipient().equals(curUser)) {
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                                "YOUR_CHANNEL_NAME",
                                NotificationManager.IMPORTANCE_DEFAULT);
                        channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
                        mNotificationManager.createNotificationChannel(channel);
                    }
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
                            .setSmallIcon(getStickerPath(msg.getStickerId())) // notification icon
                            .setContentTitle("New Sticker") // title for notification
                            .setContentText("User: " + msg.getSender() + " sent you a new sticker!")// message for notification
                            .setAutoCancel(true); // clear notification after click
//                Intent intent = new Intent(getApplicationContext(), ACTIVITY_NAME.class);
//                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                mBuilder.setContentIntent(pi);
                    mNotificationManager.notify(0, mBuilder.build());
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

    private int getStickerPath(String stickerId) {
        if (stickerId.equals("sticker1")) {
            return R.drawable.sticker1;
        } else if (stickerId.equals("sticker2")) {
            return R.drawable.sticker2;
        } else return R.drawable.sticker3;
    }


    private void updateStickerCount(String stickerId) {
        db.getReference("users").child(curUser).get()
                .addOnCompleteListener(updateTask -> {
                    StickerUser user = updateTask.getResult().getValue(StickerUser.class);
                    if (user != null) {
                        Map<String, Integer> stickerCount = user.getStickerCount();
                        if (stickerCount != null) {
                            stickerCount.put(stickerId, stickerCount.getOrDefault(stickerId, 0) + 1);
                        } else {
                            stickerCount = new HashMap<>();
                            stickerCount.put(stickerId, 1);
                        }
                        System.out.println(stickerCount);
                        Map<String, Object> update = new HashMap<>();
                        update.put("/" + curUser + "/stickerCount", stickerCount);
                        db.getReference("users").updateChildren(update);
                    }
                });
    }

    public void onStickerSelect(View view){
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.a8User_sticker1_radioButton:
//                Toast.makeText(this, "flower is selected",Toast.LENGTH_SHORT).show();
                stickerId = "sticker1";
                break;
            case R.id.a8User_sticker2_radioButton:
//                Toast.makeText(this, "couch is selected",Toast.LENGTH_SHORT).show();
                stickerId = "sticker2";
                break;
            case R.id.a8User_sticker3_radioButton:
//                Toast.makeText(this, "star is selected",Toast.LENGTH_SHORT).show();
                stickerId = "sticker3";
                break;
        }
    }

    public void clickHistory(View view){

        Intent intent = new Intent(this, A8HistoryActivity.class);
        intent.putExtra("username", curUser);
        startActivity(intent);

    }

    public void clickStickers(View view){

        Intent intent = new Intent(this, A8StickersActivity.class);
        intent.putExtra("username", curUser);
        startActivity(intent);

    }
}