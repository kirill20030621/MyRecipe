package com.example.myrecipe.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.myrecipe.R;
import com.example.myrecipe.entities.Recipe;

public class ContentActivity extends AppCompatActivity {

    TextView cuisine,name,instructions;
    ImageView imageView;
    RatingBar ratingBar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_content);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        cuisine = findViewById(R.id.tCuisine);
        name = findViewById(R.id.tName);
        instructions = findViewById(R.id.tInstructions);
        listView = findViewById(R.id.listIngredients);
        imageView = findViewById(R.id.imageView);
        ratingBar = findViewById(R.id.ratingBar);

        cuisine.setText(recipe.getCuisine());
        name.setText(recipe.getName());
        instructions.setText(String.join(" ",recipe.getInstructions()));
        Glide.with(getApplicationContext())
                .load(Uri.parse(recipe.getImage()))
                .into(imageView);
        ratingBar.setRating(recipe.getRating());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, recipe.getIngredients());
        listView.setAdapter(adapter);
    }
}