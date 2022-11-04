package edu.northeastern.anybet.webservice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.anybet.R;
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
    List<Word> displayWordList = new ArrayList<>();
    RecyclerView wordRecyclerView;
    private WordAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        searchInput = findViewById(R.id.searchInput);
        loadingPB = findViewById(R.id.loadingPB);
        wordRecyclerView = findViewById(R.id.allWordsRecyclerView);
        wordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        wordAdapter = new WordAdapter(displayWordList, this);
        wordRecyclerView.setAdapter(wordAdapter);

    }
    public void clickSearch(android.view.View view){
        wordRecyclerView.getRecycledViewPool().clear();
        wordAdapter.notifyDataSetChanged();
        displayWordList.removeAll(displayWordList);
        searchWord(searchInput.getText().toString());

    }

    private void searchWord(String word) {
        loadingPB.setVisibility(View.VISIBLE);
        String curWord = word;
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
                    Toast.makeText(getApplicationContext(),"The API doesn't support this word",Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d(TAG, "Call Success!");
                List<JsonElement> responseBody = response.body();
                for (JsonElement ele : responseBody) {
                    JsonObject wordObject = ele.getAsJsonObject();
                    JsonElement phoneticElement = wordObject.get("phonetic");
                    if (phoneticElement == null) {
                        JsonArray phoneticsElement = wordObject.getAsJsonArray("phonetics");
                        for (JsonElement jsonElement: phoneticsElement) {
                             JsonObject phonetics =  jsonElement.getAsJsonObject();
                             phoneticElement = phonetics.get("text");
                             Log.d(TAG, "phoneticElement try"+ phoneticElement);
                             if(phoneticElement != null){
                                break;
                            }
                        }

                        if(phoneticElement == null){
                            Toast.makeText(getApplicationContext(),"The API doesn't support this word",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    String phonetic = phoneticElement.getAsString();
                    Log.d(TAG, "phonetic after retrieve string"+ phonetic);

                    JsonArray meanings = wordObject.getAsJsonArray("meanings");

                    for (JsonElement jsonElement: meanings) {
                        JsonObject jo = jsonElement.getAsJsonObject();
                        String partOfSpeech = jo.get("partOfSpeech").getAsString();
                        JsonArray definitions = jo.getAsJsonArray("definitions");
                        StringBuilder defs = new StringBuilder();
                        for (JsonElement defElement : definitions) {
                            JsonObject defObject = defElement.getAsJsonObject();
                            String definition = defObject.get("definition").getAsString();
                            defs.append('▶');
                            defs.append(definition);
                            defs.append("\n\n");

                        }
                        Word newWord = new Word(curWord, phonetic, partOfSpeech, defs.toString());

                        displayWordList.add(newWord);
                        wordAdapter.notifyItemChanged(wordAdapter.getItemCount() - 1);
                    }

                }
                responseData = responseBody.toString();
            }

            @Override
            public void onFailure(Call<List<JsonElement>> call, Throwable t) {
                Log.d(TAG, "Cannot find this word" + t.getMessage());
            }
        });
    }

}