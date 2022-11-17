package edu.northeastern.anybet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.northeastern.anybet.a8.LoginActivity;
import edu.northeastern.anybet.finalProject.HomePageActivity;
import edu.northeastern.anybet.webservice.WebServiceActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickWebService(View view){

        Intent intent = new Intent(this, WebServiceActivity.class);
        startActivity(intent);

    }

    public void clickDatabase(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void clickAbout(View view){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
    public void clickAnyBet(View view){
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }
}