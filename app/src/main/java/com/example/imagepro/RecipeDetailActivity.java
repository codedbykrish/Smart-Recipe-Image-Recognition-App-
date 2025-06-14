package com.example.imagepro;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Find and initialize the TextViews
        TextView titleTextView = findViewById(R.id.title);
        TextView detailsTextView = findViewById(R.id.details);

        // Retrieve the recipe name passed from GalleryPredictionActivity
        String recipeName = getIntent().getStringExtra("RECIPE_NAME");
        if (recipeName != null) {
            // Set the title TextView to display the name of the recipe.
            titleTextView.setText(recipeName);
            if (recipeName.contains("Potato Onion Soup")) {
                detailsTextView.setText(getString(R.string.potato_onion_soup_recipe));
            }
            if (recipeName.contains("Tomato Garlic Pasta")) {
                detailsTextView.setText(getString(R.string.tomato_garlic_pasta_recipe));
            }
            if (recipeName.contains("Potato Soup")) {
                detailsTextView.setText(getString(R.string.potato_soup_recipe));
            }
        }
    }
}
