package edu.northeastern.anybet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class WebServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
    }
    public void clickSearch(android.view.View view){

        Intent intent = new Intent(this, DictionaryActivity.class);
        startActivity(intent);

    }

}