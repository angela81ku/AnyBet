package edu.northeastern.anybet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DictionaryActivity extends AppCompatActivity {
    private static final String TAG = "DictionaryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        //todo
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String responseData = bundle.getString("responseData");
            System.out.println(TAG);
            System.out.println(responseData);
        }
    }
}