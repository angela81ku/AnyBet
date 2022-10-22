package edu.northeastern.anybet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.JsonElement;

import java.util.List;

import edu.northeastern.anybet.models.IDictionaryAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceActivity extends AppCompatActivity {
    private static final String TAG = "WebServiceActivity";

    private Retrofit retrofit;
    private IDictionaryAPI api;

    private EditText searchInput;
    private ProgressBar loadingPB;

    private String responseData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        searchInput = findViewById(R.id.searchInput);
        loadingPB = findViewById(R.id.loadingPB);
    }
    public void clickSearch(android.view.View view){
        searchWord(searchInput.getText().toString());
    }

    private void searchWord(String word) {
        loadingPB.setVisibility(View.VISIBLE);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/api/v2/entries/en/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(IDictionaryAPI.class);

        Call<List<JsonElement>> call = api.getWordMeanings(word);

        call.enqueue(new Callback<List<JsonElement>>() {
            @Override
            public void onResponse(Call<List<JsonElement>> call, Response<List<JsonElement>> response) {
                loadingPB.setVisibility(View.GONE);

                if(!response.isSuccessful()){
                    Log.d(TAG, "Cannot find this word" + response.code());
                    return;
                }

                Log.d(TAG, "Call Successed!");
                List<JsonElement> responseBody = response.body();
                for (JsonElement ele : responseBody) {
                    //todo
                }
                responseData = responseBody.toString();
                System.out.println(TAG);
                System.out.println(responseData);
                Intent intent = new Intent(WebServiceActivity.this, DictionaryActivity.class);
                intent.putExtra("responseData", responseData);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<JsonElement>> call, Throwable t) {
                Log.d(TAG, "Cannot find this word" + t.getMessage());
            }
        });
    }

}