package edu.northeastern.anybet.finalProject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import edu.northeastern.anybet.R;
public class BetDetailActivity extends AppCompatActivity {

    TextView description;
    TextView amount;
    TextView status;
    TextView participant;
    TextView startTime;
    TextView endTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anybet_bet_detail);
        description = findViewById(R.id.tvBetDesc);
        amount = findViewById(R.id.tvBetAmount);
        status = findViewById(R.id.tvProgressStatus);
        participant = findViewById(R.id.tvParticipant);
        startTime = findViewById(R.id.tvStartTime);
        endTime = findViewById(R.id.tvEndTime);

    }
}