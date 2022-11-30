package edu.northeastern.anybet.finalProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.northeastern.anybet.R;
import edu.northeastern.anybet.finalProject.realtimeDatabase.DAO.FirebaseDAO;
import edu.northeastern.anybet.finalProject.realtimeDatabase.models.Bet;
import edu.northeastern.anybet.finalProject.realtimeDatabase.models.BetUser;

public class AddBetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText betTitle;
    private EditText betPrice;
    private String participant1;
    private double longitude;
    private double latitude;
    private LocationManager locationManager;
    private String locationProvider = null;
    private EditText betDescription;
    private String title;
    private String price;
    private String otherParticipant;
    private String description;
    private String startDate;
    private String endDate;
    private FirebaseDAO dao;
    private FirebaseDatabase db;
    private DatePickerDialog datePickerDialog;
    private Button btnDate;
    private ImageView btnBack;
    private int year;
    private int month;
    private int day;
    private ArrayList<String> userList = new ArrayList<>();
    private Spinner userSpin;


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
        db.getReference("betUsers").orderByChild("username").addValueEventListener(userListListener);

        betTitle = findViewById(R.id.betContentTxt);
        betPrice = findViewById(R.id.editTextTextInputNumberSigned);
        userSpin = findViewById(R.id.userSpinner);
        userSpin.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, userList);
        arrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        betDescription = findViewById(R.id.editTextBetDes);

        initDatePicker();
        btnDate = findViewById(R.id.btnDatePicker);
        btnDate.setText(getTodayDate());
        btnBack = findViewById(R.id.btnReturn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void clickCreateBet(View view){
        title = betTitle.getText().toString();
        price = betPrice.getText().toString();
        startDate = getTodayDate();
        endDate = btnDate.getText().toString();
        description = betDescription.getText().toString();

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

        if (!title.equals("") && !price.equals("") && !otherParticipant.equals("") && !description.equals("")) {
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
                            Bet bet = new Bet(title, price, participant1, otherParticipant, startDate, endDate, longitude, latitude,description);
                            dao.addBet(bet);
                            Toast.makeText(this, "The bet is created.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

        }else{
            if(title.equals("")){
                Toast.makeText(this, "Please enter bet title before submitting a bet",
                        Toast.LENGTH_SHORT).show();
            }else if(price.equals("")){
                Toast.makeText(this, "Please enter bet amount before submitting a bet",
                        Toast.LENGTH_SHORT).show();
            }else if(description.equals("")){
                Toast.makeText(this, "Please enter bet description before submitting a bet",
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
    private void getDate(){
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        month = month + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
    }
    private String getTodayDate(){
        getDate();
        return makeDateString(day, month, year);
    }
    private void initDatePicker(){

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btnDate.setText(date);
            }
        };

        getDate();
        datePickerDialog = new DatePickerDialog(this,  dateSetListener, year, month -1, day);
        // to avoid user select a time prior than today, because it doesn't make sense to
        // make a history bet
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    private String FormatMonth(int month)
    {
        if(month == 1)
            return "JAN";
        else if(month == 2)
            return "FEB";
        else if(month == 3)
            return "MAR";
        else if(month == 4)
            return "APR";
        else if(month == 5)
            return "MAY";
        else if(month == 6)
            return "JUN";
        else if(month == 7)
            return "JUL";
        else if(month == 8)
            return "AUG";
        else if(month == 9)
            return "SEP";
        else if(month == 10)
            return "OCT";
        else if(month == 11)
            return "NOV";
        else
            return "DEC";

    }
    private String makeDateString(int day, int month, int year)
    {
        return FormatMonth(month) + " " + day + " ," + year;
    }
    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    private ValueEventListener userListListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            userList.clear();
            for (DataSnapshot u : snapshot.getChildren()) {
                String curName = u.getValue(BetUser.class).getUsername();
                if (!curName.equals(participant1)) {
                    userList.add(curName);
                }
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(AddBetActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, userList);
            arrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            userSpin.setAdapter(arrayAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        otherParticipant = parent.getItemAtPosition(position).toString();
        System.out.println("the other P");
        System.out.println(otherParticipant);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}