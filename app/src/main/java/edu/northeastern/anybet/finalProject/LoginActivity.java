package edu.northeastern.anybet.finalProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.anybet.R;
import edu.northeastern.anybet.a8.A8UsersActivity;
import edu.northeastern.anybet.a8.realtimeDatabase.DAO.FirebaseDAO;
import edu.northeastern.anybet.a8.realtimeDatabase.models.StickerUser;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername;
    Button btnLogin;

    FirebaseDAO dao;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anybet_login);

//        dao = new FirebaseDAO();
//        db = FirebaseDatabase.getInstance();
//        editTextUsername = findViewById(R.id.edit_text_username);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(view -> {
//            String username = editTextUsername.getText().toString();

//            db.getReference("users").child(username).get()
//                    .addOnCompleteListener(task -> {
//                        if (task.getResult().getValue(StickerUser.class) == null) {
//                            // if new user, add user to database
//                            StickerUser user = new StickerUser(username);
//                            dao.addUser(user);
//                        }
//                    });
//
//            //go to send message activity
            Intent intent = new Intent(this, HomePageActivity.class);
//            intent.putExtra("username", username);
            startActivity(intent);
        });
    }
}
