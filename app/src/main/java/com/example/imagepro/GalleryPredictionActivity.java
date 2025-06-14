package com.example.imagepro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GalleryPredictionActivity extends AppCompatActivity {
    private Button select_image;
    private ImageView image_v;
    private objectDetectionClass objectDetectionClass;
    int SELECT_PICTURE=200;
    private LinearLayout  recipesView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_prediction);

        select_image =findViewById(R.id.select_image);
        image_v =findViewById(R.id.image_v);
        recipesView = findViewById(R.id.recipes_view);  // Initialize the TextView

        // Attempt to load the object detection model
        try{
            // I used input size of 320 for this model
            objectDetectionClass =new objectDetectionClass(getAssets(),"custom_model.tflite","custom_label.txt",320);
            Log.d("MainActivity","Model is successfully loaded");
        }
        catch (IOException e){
            Log.d("MainActivity","Getting some error");
            e.printStackTrace();
        }

        // Set up a listener for the image selection button
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_chooser();
            }
        });
    }

    // Method to open the image chooser
    private void image_chooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i,"Select picture"),SELECT_PICTURE);

    }

    // Method to determine which recipes can be made based on the detected ingredients
    private List<String> findRecipes(List<String> ingredients) {
        // Below i have written the logic to find recipes based on detected ingredients
        List<String> recipes = new ArrayList<>();
        if (ingredients.contains("tomato") && ingredients.contains("garlic")) {
            recipes.add("Recipe You Can Make:  Tomato Garlic Pasta");
        }
        if (ingredients.contains("potato") && ingredients.contains("onion")) {
            recipes.add("Recipe You Can Make:  Potato Onion Soup");
        }
        if (ingredients.contains("potato")) {
            recipes.add("Recipe You Can Make:  Potato Soup");
        }
        return recipes;
    }

    // Method to display recipes on the screen
    private void displayRecipes(List<String> recipes) {
        recipesView.removeAllViews(); // This Clears previous views

        for (String recipe : recipes) {
            TextView recipeTextView = new TextView(this);
            recipeTextView.setText(recipe);
            recipeTextView.setTextSize(18);
            recipeTextView.setTextColor(Color.BLACK); // This is to Ensure text color is set
            recipeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Chance the size of text

            recipeTextView.setPadding(10, 10, 10, 10);

            // Set an onClickListener for each recipe(textView) to open the recipe details when clicked
            recipeTextView.setOnClickListener(v -> {
                Intent intent = new Intent(GalleryPredictionActivity.this, RecipeDetailActivity.class);
                intent.putExtra("RECIPE_NAME", recipe);
                startActivity(intent);
            });

            ((LinearLayout) recipesView).addView(recipeTextView);
        }
    }

    // Method to handle the result from image chooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check if the result is OK and there is data
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData(); // Get the URI of the selected image
            if (selectedImageUri != null) {
                Bitmap bitmap = null;
                try {
                    // Retrieve the image from the content resolver as a Bitmap
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace(); // Log the exception if something goes wrong
                }

                // Prepare a Mat object to hold the RGB data of the Bitmap
                Mat selected_image = new Mat(bitmap.getHeight(), bitmap.getWidth(), CvType.CV_8UC4);
                // Convert the Bitmap to a Mat object
                Utils.bitmapToMat(bitmap, selected_image);

                // Process the image using the objectDetectionClass
                // `recognizePhoto` processes the image and returns a pair containing the processed image and detected ingredients
                // This code here is used to handle the new return type
                Pair<Mat, List<String>> result = objectDetectionClass.recognizePhoto(selected_image);
                selected_image = result.first;  // The processed image
                List<String> detectedIngredients = result.second; // The list of detected ingredients

                // Convert the processed Mat back to a Bitmap to display in an ImageView
                Bitmap bitmap1 = Bitmap.createBitmap(selected_image.cols(), selected_image.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(selected_image, bitmap1);
                image_v.setImageBitmap(bitmap1);    // Set the ImageView to show the processed image

                // To Find recipes based on the detected ingredients
                List<String> recipes = findRecipes(detectedIngredients);
                displayRecipes(recipes); // This is to Display the recipes in the UI
            }
        }
    }
}