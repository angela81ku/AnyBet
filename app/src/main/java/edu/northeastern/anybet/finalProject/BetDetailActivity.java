package edu.northeastern.anybet.finalProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.northeastern.anybet.R;
public class BetDetailActivity extends AppCompatActivity {

    TextView title;
    TextView description;
    TextView amount;
    TextView status;
    TextView participant;
    TextView startTime;
    TextView endTime;
    Button completeBet;
    ImageView back;
    ImageView share;
    ImageView imgLocationMap;

    FirebaseDatabase db;

    private ClipboardManager myClipboard;
    private ClipData myClip;


    private static final String TAG = "BetDetailActivity";

    private static final String STATIC_MAP_URL_FORMAT = "https://maps.googleapis.com/maps/api/staticmap?center=%s,%s&zoom=15&size=300x300&markers=color:red|%s,%s&key=AIzaSyA0NYTCZVBDNbwLqc0KEH9p1_ON1USdfHE";
    private static final Double DEFAULT_LATITUDE = 40.714728;
    private static final Double DEFAULT_LONGITUDE = -73.998672;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anybet_bet_detail);

        db = FirebaseDatabase.getInstance();
        title = findViewById(R.id.tvBetTitle);
        description = findViewById(R.id.tvBetDesc);
        amount = findViewById(R.id.tvBetAmount);
        status = findViewById(R.id.tvProgressStatus);
        participant = findViewById(R.id.tvParticipant);
        startTime = findViewById(R.id.tvStartTime);
        endTime = findViewById(R.id.tvEndTime);
        completeBet = (Button)findViewById(R.id.btnCompleteBet);
        back = findViewById(R.id.btnReturn);
        share = findViewById(R.id.btnShare);
        imgLocationMap = findViewById(R.id.imgLocationMap);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent betIntent = getIntent();
        if (betIntent != null) {
            title.setText(betIntent.getStringExtra("title"));
            description.setText(betIntent.getStringExtra("description"));
            amount.setText(betIntent.getStringExtra("amount"));
            status.setText(betIntent.getStringExtra("status"));
            participant.setText(betIntent.getStringExtra("participant"));
            startTime.setText(betIntent.getStringExtra("startTime"));
            endTime.setText(betIntent.getStringExtra("endTime"));
            Double latitude = betIntent.getDoubleExtra("latitude", DEFAULT_LATITUDE);
            Double longitude = betIntent.getDoubleExtra("longitude", DEFAULT_LONGITUDE);
            loadLocationMap(imgLocationMap, latitude, longitude);

            if (status.getText().equals("complete")) {
                completeBet.setText("Complete Bet");
            } else {
                completeBet.setText("Bet Completed");
            }
            completeBet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = betIntent.getStringExtra("id");
                    db.getReference("bets").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Map<String, Object> postValues = new HashMap<>();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                postValues.put(ds.getKey(),ds.getValue());
                            }
                            String updatedStatus = status.getText().equals("complete") ? "in progress" : "complete";
                            postValues.put("betStatus", updatedStatus);
                            status.setText(updatedStatus);
                            completeBet.setText(updatedStatus.equals("complete") ? "Bet Completed" : "Complete Bet");
                            db.getReference("bets").child(id).updateChildren(postValues);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // todo share message, maybe something like copy to the keyboard
                    myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    String text;
                    text = "Bet: " + betIntent.getStringExtra("title") +"\n" +
                            "Participants: " + betIntent.getStringExtra("participant") + "\n" +
                            "start from: " + betIntent.getStringExtra("startTime") + "\n" +
                            "To: " + betIntent.getStringExtra("endTime") + "\n";

                    myClip = ClipData.newPlainText("text", text);
                    myClipboard.setPrimaryClip(myClip);

                    Toast.makeText(getApplicationContext(), "Share Message generated",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void loadLocationMap(ImageView imageView, Double latitude, Double longitude) {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            Bitmap bitmap = null;
            String url = String.format(STATIC_MAP_URL_FORMAT, latitude, longitude, latitude, longitude);
            try (InputStream in = new URL(url).openStream()) {
                bitmap = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                Log.d(TAG, "Unable to open static map API");
            }

            Bitmap finalBitmap = bitmap;
            handler.post(() -> {
                imageView.setImageBitmap(finalBitmap);
            });
        });
    }

}