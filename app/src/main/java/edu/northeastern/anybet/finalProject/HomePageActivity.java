package edu.northeastern.anybet.finalProject;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.anybet.R;


public class HomePageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private View btnAddNewBet;
    private Spinner spinnerBetStatus;

    TextView anybetUserUsername;

    String curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anybet_homepage);


        btnAddNewBet = findViewById(R.id.btn_addNewBet);
        spinnerBetStatus = findViewById(R.id.spinnerBetStatus);
        anybetUserUsername = findViewById(R.id.anybet_user_username2);

        Intent loginIntent = getIntent();
        if (loginIntent != null) {
            curUser = loginIntent.getStringExtra("username");
            anybetUserUsername.setText("Hello, " + curUser);
        }

        String[] betStatus = getResources().getStringArray(R.array.betStatus);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                betStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBetStatus.setAdapter(adapter);
        btnAddNewBet.setOnClickListener(view -> {

            Intent intent = new Intent(this, AddBetActivity.class);

            startActivity(intent);
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
