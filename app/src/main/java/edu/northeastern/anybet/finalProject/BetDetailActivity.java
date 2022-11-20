package edu.northeastern.anybet.finalProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.anybet.R;
public class BetDetailActivity extends AppCompatActivity {

    TextView description;
    TextView amount;
    TextView status;
    TextView participant;
    TextView startTime;
    TextView endTime;
    Button completeBet;
    ImageView share;
    FirebaseDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anybet_bet_detail);

        db = FirebaseDatabase.getInstance();
        description = findViewById(R.id.tvBetDesc);
        amount = findViewById(R.id.tvBetAmount);
        status = findViewById(R.id.tvProgressStatus);
        participant = findViewById(R.id.tvParticipant);
        startTime = findViewById(R.id.tvStartTime);
        endTime = findViewById(R.id.tvEndTime);
        completeBet = (Button)findViewById(R.id.btnCompleteBet);
        share = findViewById(R.id.btnShare);

        Intent betIntent = getIntent();
        if (betIntent != null) {
            description.setText(betIntent.getStringExtra("description"));
            amount.setText(betIntent.getStringExtra("amount"));
            status.setText(betIntent.getStringExtra("status"));
            participant.setText(betIntent.getStringExtra("participant"));
            startTime.setText(betIntent.getStringExtra("startTime"));
            endTime.setText(betIntent.getStringExtra("endTime"));
            if (status.getText().equals("complete")) {
                completeBet.setText("Complete Bet");
            } else {
                completeBet.setText("Bet Completed");
            }

            completeBet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // todo update status, need to get reference of the bet and update its status
//                    db.getReference("bets").child(betIntent.getStringExtra("title"))
                }
            });

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // todo share message, maybe something like copy to the keyboard
                }
            });
        }



    }


}