package edu.northeastern.anybet.a8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

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
                            //todo update stickerId
                            String stickerId = "sticker2";
                            Message message = new Message(curUser, recipient, stickerId);
                            dao.addMessage(message);

                            // update sticker count
                            updateStickerCount(stickerId);
                        }
                    });
        });
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
}