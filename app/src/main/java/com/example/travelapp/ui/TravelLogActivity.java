package com.example.travelapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;

public class TravelLogActivity extends AppCompatActivity {

    private LinearLayout containerLayout;
    private int editTextCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_log);

        containerLayout = findViewById(R.id.containerLayout);

        // Existing EditText and ImageView Section
        EditText travelLogEditText = findViewById(R.id.travelLogEditText);
        Button uploadImageButton = findViewById(R.id.uploadImageButton);
        ImageView uploadedImageView = findViewById(R.id.uploadedImageView);

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch the image picker
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        // "Add More" Button
        Button addMoreButton = findViewById(R.id.addMoreButton);
        addMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add a new EditText
                EditText newEditText = new EditText(TravelLogActivity.this);
                newEditText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                newEditText.setHint("Additional Text " + editTextCount);
                containerLayout.addView(newEditText);

                // Add a new Upload Image Button
                Button newUploadImageButton = new Button(TravelLogActivity.this);
                newUploadImageButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                newUploadImageButton.setText("Upload Image " + editTextCount);
                newUploadImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Launch the image picker
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, editTextCount);
                    }
                });
                containerLayout.addView(newUploadImageButton);

                editTextCount++;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // Display the selected image in an ImageView
            ImageView uploadedImageView = new ImageView(this);
            uploadedImageView.setImageURI(selectedImageUri);
            uploadedImageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            containerLayout.addView(uploadedImageView);
        }
    }
}



