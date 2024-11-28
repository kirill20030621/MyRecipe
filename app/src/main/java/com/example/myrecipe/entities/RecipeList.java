package com.example.myrecipe.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeList {
    @SerializedName("recipes")
    List<Recipe> recipeList;

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }
}
