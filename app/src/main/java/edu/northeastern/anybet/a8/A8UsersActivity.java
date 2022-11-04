package edu.northeastern.anybet.a8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.anybet.R;
import edu.northeastern.anybet.a8.realtimeDatabase.DAO.FirebaseDAO;
import edu.northeastern.anybet.a8.realtimeDatabase.models.Message;

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

        Intent intent = getIntent();
        if (intent != null) {
            curUser = intent.getStringExtra("username");
            a8UserUsername.setText("Hello, " + curUser);
        }

        // send message button
        a8UserSendButton.setOnClickListener(view -> {
            //todo update stickerId
            String recipient = a8UserReceiveUsernameInput.getText().toString();
            String stickerId = "1";
            Message message = new Message(curUser, recipient, stickerId);
            dao.addMessage(message);

//            // add sticker count
//            db.getReference("users").equalTo(curUser)
        });
    }
}