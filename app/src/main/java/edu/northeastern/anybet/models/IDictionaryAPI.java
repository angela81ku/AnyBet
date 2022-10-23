package edu.northeastern.anybet.models;

import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IDictionaryAPI {

    @GET("{word}")
    Call<List<JsonElement>> getWordMeanings(@Path("word") String word);

}
