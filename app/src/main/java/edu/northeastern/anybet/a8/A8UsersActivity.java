package edu.northeastern.anybet.a8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import edu.northeastern.anybet.R;

public class A8UsersActivity extends AppCompatActivity {

    TextView a8UserUsername;

    String curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a8_users);

        a8UserUsername = findViewById(R.id.a8User_username);

        Intent intent = getIntent();
        if (intent != null) {
            curUser = intent.getStringExtra("username");
            a8UserUsername.setText("Hello, " + curUser);
        }
    }
}