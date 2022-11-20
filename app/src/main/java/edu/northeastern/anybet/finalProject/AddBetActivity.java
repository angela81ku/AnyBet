package edu.northeastern.anybet.finalProject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import edu.northeastern.anybet.R;
import edu.northeastern.anybet.finalProject.realtimeDatabase.DAO.FirebaseDAO;
import edu.northeastern.anybet.finalProject.realtimeDatabase.models.Bet;
import edu.northeastern.anybet.finalProject.realtimeDatabase.models.BetUser;

public class AddBetActivity extends AppCompatActivity {

    public EditText betTitle;
    public EditText betPrice;
    public String participant1;
    public EditText participant2;
    public double longitude;
    public double latitude;
    private LocationManager locationManager;
    private String locationProvider = null;
    public String betStartTime;

    public String title;
    public String price;
    public String otherParticipant;
    public FirebaseDAO dao;
    public FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anybet_add_bet);
        Intent intent = getIntent();
        dao = new FirebaseDAO();
        db = FirebaseDatabase.getInstance();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            //request
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }else{
            //location manager
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //provider
            List<String> providers = locationManager.getProviders(true);
            if (providers.contains(LocationManager.GPS_PROVIDER)) {
                locationProvider = LocationManager.GPS_PROVIDER;
            } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER;
            } else {
                Toast.makeText(this, "Can't find the location", Toast.LENGTH_SHORT).show();
                return;
            }
            //get location
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        }

        if (intent != null) {
            participant1 = intent.getStringExtra("username");
        }

        betTitle = findViewById(R.id.betContentTxt);
        betPrice = findViewById(R.id.editTextTextInputNumberSigned);
        participant2 = findViewById(R.id.editTextUsername);



    }

    public void clickCreateBet(View view){
        title = betTitle.getText().toString();
        price = betPrice.getText().toString();
        otherParticipant = participant2.getText().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        betStartTime = dateTime.format(formatter);

        // TODO: Fix the location service to get latitude and longitude
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            //request
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }else{
            //location manager
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //provider
            List<String> providers = locationManager.getProviders(true);
            if (providers.contains(LocationManager.GPS_PROVIDER)) {
                locationProvider = LocationManager.GPS_PROVIDER;
            } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER;
            } else {
                Toast.makeText(this, "Can't find the location", Toast.LENGTH_SHORT).show();
                return;
            }
            //get location
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        }

        if (!title.equals("") && !price.equals("") && !otherParticipant.equals("")) {
            if (otherParticipant.equals(participant1)) {
                Toast.makeText(this, "Please enter another user other than yourself before submitting a bet",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // check if other participant exist
            db.getReference("betUsers").child(otherParticipant).get()
                    .addOnCompleteListener(task -> {
                        if (task.getResult().getValue(BetUser.class) == null) {
                            Toast.makeText(this, "The user is unknown.", Toast.LENGTH_SHORT).show();
                        }else {
                            Bet bet = new Bet(title, price, participant1, otherParticipant,betStartTime, longitude, latitude);
                            dao.addBet(bet);
                            Toast.makeText(this, "The bet is created.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, BetDetailActivity.class);

                            // TODO: Find out what to pass (through intent )to the bet detail page

                            startActivity(intent);
                        }
                    });

        }else{
            if(title.equals("")){
                Toast.makeText(this, "Please enter bet title before submitting a bet",
                        Toast.LENGTH_SHORT).show();
            }else if(price.equals("")){
                Toast.makeText(this, "Please enter bet amount before submitting a bet",
                        Toast.LENGTH_SHORT).show();
            }else if(otherParticipant.equals(participant1)){
                Toast.makeText(this, "Please enter another user other than yourself before submitting a bet",
                        Toast.LENGTH_SHORT).show();
            }else {

            }
        }


    }

    public LocationListener locationListener = new LocationListener() {
        //status change
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        // enable
        @Override
        public void onProviderEnabled(String provider) {
        }
        // disable
        @Override
        public void onProviderDisabled(String provider) {
        }
        //location change
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
    };
}