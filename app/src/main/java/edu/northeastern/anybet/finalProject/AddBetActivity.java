package edu.northeastern.anybet.finalProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.northeastern.anybet.R;

public class AddBetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anybet_add_bet);
    }
    public void clickCreateBet(View view){
        Intent intent = new Intent(this, BetDetailActivity.class);
        startActivity(intent);
    }
}