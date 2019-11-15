package com.cs495.phototk.ui.management;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;


import androidx.appcompat.app.AppCompatActivity;


import com.cs495.phototk.R;

import java.io.ByteArrayOutputStream;

public class GearEdit extends AppCompatActivity {
    static final int REQUEST_CAMERA = 1;
    ImageView gearImage;
    byte[] imageBlob = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear_edit);

        gearImage = (ImageView) findViewById(R.id.gear_photo);
        gearImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
        button_save();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                gearImage.setImageBitmap(image);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageBlob = stream.toByteArray();
            }
        }
    }

    public void button_save() {
        Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });
    }



    public void saveItem() {
        /*

        SAVE TO DATABASE

         */
        finish();
    }
}

