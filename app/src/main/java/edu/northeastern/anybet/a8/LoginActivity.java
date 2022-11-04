package edu.northeastern.anybet.a8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import edu.northeastern.anybet.R;
import edu.northeastern.anybet.a8.realtimeDatabase.DAO.FirebaseDAO;
import edu.northeastern.anybet.a8.realtimeDatabase.models.StickerUser;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername;
    Button btnLogin;

    FirebaseDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a8_login);
        dao = new FirebaseDAO();
        editTextUsername = findViewById(R.id.edit_text_username);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString();

            //add user to database
            StickerUser user = new StickerUser(username);
            dao.addUser(user);

            //go to send message activity
            Intent intent = new Intent(this, A8UsersActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
    }
}