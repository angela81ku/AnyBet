package edu.northeastern.anybet.a8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import edu.northeastern.anybet.R;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a8_login);

        editTextUsername = findViewById(R.id.edit_text_username);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString();

            //todo add user to database
            //todo go to send message activity
        });
    }
}