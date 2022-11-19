package edu.northeastern.anybet.finalProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import edu.northeastern.anybet.R;

public class AddBetActivity extends AppCompatActivity {

    public EditText betTitle;
    public EditText betPrice;
    public String participant1;
    public EditText participant2;
    public double longitude;
    public double latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anybet_add_bet);
        Intent intent = getIntent();
        if (intent != null) {
            participant1 = intent.getStringExtra("username");
        }

        betTitle = findViewById(R.id.textInputBetContent);
        betPrice = findViewById(R.id.editTextTextInputNumberSigned);

//        participant2 = findViewById(R.id.)

    }
    public void clickCreateBet(View view){
        Intent intent = new Intent(this, BetDetailActivity.class);
        startActivity(intent);
    }
}