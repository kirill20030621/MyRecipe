package com.example.myrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.activities.ContentActivity;
import com.example.myrecipe.adapters.CategoryAdapter;
import com.example.myrecipe.adapters.OnClickListener;
import com.example.myrecipe.adapters.RecipeAdapter;
import com.example.myrecipe.entities.Recipe;
import com.example.myrecipe.entities.RecipeList;
import com.example.myrecipe.retrofit.RetrofitClient;
import com.example.myrecipe.retrofit.ServiceApi;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tagggg";

    RecyclerView recipesView, categoriesView;
    EditText tToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recipesView = findViewById(R.id.rvRecipes);
        categoriesView = findViewById(R.id.rvCategories);
        tToSearch = findViewById(R.id.tToSearch);
        ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);

        try {
            Call<RecipeList> call = service.getRecipes();
            call.enqueue(new Callback<RecipeList>() {
                @Override
                public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        RecipeList recipeList = response.body();
                        List<Recipe> recipies = recipeList.getRecipeList();

                        for (Recipe recipe : recipies) {
                            Log.i(TAG, recipe.getName());
                        }
                        fillRecipesView(recipies);
                        fillCategoryView(recipies);
                        searchRecipe(recipies);
                    }
                }

                @Override
                public void onFailure(Call<RecipeList> call, Throwable t) {
                    Log.e(TAG, "Network error:", t);
                }
            });

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }

    }

    void fillRecipesView(List<Recipe> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recipesView.setLayoutManager(linearLayoutManager);

        RecipeAdapter adapter = new RecipeAdapter(list, getApplicationContext());
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra("recipe", list.get(position));
                startActivity(intent);
            }
        });
        recipesView.setAdapter(adapter);
    }

    void fillRecipesView(List<Recipe> list, String category) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recipesView.setLayoutManager(linearLayoutManager);

        List<Recipe> newList = list.stream()
                .filter(x -> x.getCuisine().equals(category))
                .collect(Collectors.toList());
        RecipeAdapter adapter = new RecipeAdapter(newList, getApplicationContext());
        recipesView.setAdapter(adapter);
    }

    void fillCategoryView(List<Recipe> list) {
        List<String> categories = list.stream()
                .map(Recipe::getCuisine)
                .distinct()
                .collect(Collectors.toList());
        Collections.sort(categories);
        categories.add(0, "All");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoriesView.setLayoutManager(linearLayoutManager);

        CategoryAdapter adapter = new CategoryAdapter(categories, getApplicationContext());
        categoriesView.setAdapter(adapter);

        adapter.setOnClickListener((OnClickListener) position -> {
            adapter.notifyItemChanged(adapter.selectedPosition);
            adapter.selectedPosition = position;
            adapter.notifyItemChanged(adapter.selectedPosition);
            if (categories.get(position) == "All") {
                fillRecipesView(list);
            } else
                fillRecipesView(list, categories.get(position));
        });
    }

    void searchRecipe(List<Recipe> list) {
        tToSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = tToSearch.getText().toString();
                List<Recipe> newList = list.stream()
                        .filter(x -> x.getName().toLowerCase().contains(text.toLowerCase()) ||
                                x.getIngredients().contains(text))
                        .collect(Collectors.toList());
                fillRecipesView(newList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

}