package com.example.imagepro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {
    // Static initializer block to load OpenCV library as soon as the class is loaded.
    static {
        if(OpenCVLoader.initDebug()){
            Log.d("MainActivity: ","Opencv is loaded");
        }
        else {
            Log.d("MainActivity: ","Opencv failed to load");
        }
    }

    private Button camera_button;

    private Button storage_prediction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the UI layout for the Activity

        // Connect the camera_button variable with the actual button element from the layout.
        camera_button=findViewById(R.id.camera_button);
        // Here we Set a click listener on the camera_button to handle user taps on this button.
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CameraActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        // Connect the storage_prediction variable with the button from the layout.
        storage_prediction=findViewById(R.id.storage_prediction);
        // Set a click listener on the storage_prediction button to handle user interactions.
        storage_prediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is to start GalleryPredictionActivity and clear the activity stack.
                startActivity(new Intent(MainActivity.this, GalleryPredictionActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });

    }
}