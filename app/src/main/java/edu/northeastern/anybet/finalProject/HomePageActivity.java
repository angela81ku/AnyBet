package edu.northeastern.anybet.finalProject;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.anybet.R;
import edu.northeastern.anybet.a8.realtimeDatabase.models.Message;
import edu.northeastern.anybet.a8.realtimeDatabase.models.ReceivedSticker;
import edu.northeastern.anybet.finalProject.realtimeDatabase.models.Bet;


public class HomePageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private View btnAddNewBet;
    private Spinner spinnerBetStatus;
    FirebaseDatabase db;
    RecyclerView betsRecyclerView;
    BetAdaptor betAdaptor;
    List<Bet> bets;
    List<Bet> inProgressBets;
    List<Bet> completedBets;
    List<String> ids;

    TextView anybetUserUsername;

    String curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anybet_homepage);

        db = FirebaseDatabase.getInstance();
        bets = new ArrayList<>();
        inProgressBets = new ArrayList<>();
        completedBets = new ArrayList<>();
        ids = new ArrayList<>();
        betsRecyclerView = findViewById(R.id.recyclerViewBetHomePage);
        betsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        betAdaptor = new BetAdaptor(this.bets, this);
        betsRecyclerView.setAdapter(betAdaptor);


        btnAddNewBet = findViewById(R.id.btn_addNewBet);
        spinnerBetStatus = findViewById(R.id.spinnerBetStatus);
        anybetUserUsername = findViewById(R.id.anybet_user_username2);

        DatabaseReference ref = db.getReference("bets");

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
        spinnerBetStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String status = adapterView.getItemAtPosition(i).toString();
                // todo: update recycler view base on the status
                if (status.equals("All")) {
                    betAdaptor.changeData(bets);
                } else if (status.equals("Complete")) {
                    betAdaptor.changeData(completedBets);
                } else {
                    betAdaptor.changeData(inProgressBets);
                }
                betAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAddNewBet.setOnClickListener(view -> {

            Intent intent = new Intent(this, AddBetActivity.class);
            intent.putExtra("username", curUser);
            startActivity(intent);
        });
        BetClickListener betClickListener = new BetClickListener() {
            @Override
            public void onBetClick(int position) {
                Bet bet = bets.get(position);
                Intent intent = new Intent(getContext(), BetDetailActivity.class);// todo check if this work?
                intent.putExtra("id", ids.get(position));
                intent.putExtra("title", bet.getTitle());
                intent.putExtra("description", bet.getDescription());
                intent.putExtra("amount", bet.getBetPrice());
                intent.putExtra("status", bet.getBetStatus());
                intent.putExtra("participant", bet.getParticipant1() + ", " + bet.getParticipant2());
                intent.putExtra("startTime", bet.getBetStartTime());
                intent.putExtra("endTime", bet.getBetEndTime());
                intent.putExtra("latitude", bet.getLatitude());
                intent.putExtra("longitude", bet.getLongitude());
                startActivity(intent);
            }
        };

        betAdaptor.setOnItemClickListener(betClickListener);


        ref.addChildEventListener(new ChildEventListener() { // todo check if we need to order by something?
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                Bet bet = snapshot.getValue(Bet.class);
                if (bet.getParticipant1().equals(curUser) || bet.getParticipant2().equals(curUser)) {
                    if (bet.getBetStatus().equals("in progress")) {
                        inProgressBets.add(bet);
                    } else {
                        completedBets.add(bet);
                    }
                    System.out.println("key:" + key);
                    bets.add(bet);
                    ids.add(snapshot.getKey());
                    betAdaptor.notifyItemChanged(betAdaptor.getItemCount() - 1);
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public HomePageActivity getContext() {
        return this;
    }
}
