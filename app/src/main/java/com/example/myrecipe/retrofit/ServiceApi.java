package com.example.myrecipe.retrofit;

import com.example.myrecipe.entities.Recipe;
import com.example.myrecipe.entities.RecipeList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceApi {
    @GET("recipes")
    Call<RecipeList> getRecipes();
}
