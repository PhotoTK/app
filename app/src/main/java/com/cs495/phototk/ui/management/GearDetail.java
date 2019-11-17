package com.cs495.phototk.ui.management;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Base64;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;




import androidx.appcompat.app.AppCompatActivity;


import com.cs495.phototk.R;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class GearDetail extends AppCompatActivity {
    static final int REQUEST_CAMERA = 1;
    ImageView gearImage;
    byte[] imageBlob = null;
    DatabaseReference myGear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear_detail);

        myGear = FirebaseDatabase.getInstance().getReference("gears");

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
                image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
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
        EditText gearName = (EditText) findViewById(R.id.edit_item_name);
        EditText gearOwner = (EditText) findViewById(R.id.edit_owner_name);
        EditText insurance = (EditText) findViewById(R.id.edit_insurance_date);
        EditText warranty = (EditText) findViewById(R.id.edit_warranty_date);
        EditText price = (EditText) findViewById(R.id.edit_item_price);
        EditText detail = (EditText) findViewById(R.id.edit_detail);


        String mGearName = gearName.getText().toString();
        if (gearName.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Gear name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        String mOwnerName = gearOwner.getText().toString();
        if (gearOwner.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Owner name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        String mInsurance = insurance.getText().toString();
        String mWarranty = warranty.getText().toString();

        double mPrice = 0.0;
        if (price.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter the gear price", Toast.LENGTH_LONG).show();
            return;
        } else {
            mPrice = Double.parseDouble(price.getText().toString());
        }

        String mDetail = detail.getText().toString();


        if (imageBlob == null) {
            Toast.makeText(getApplicationContext(), "Gear picture required", Toast.LENGTH_LONG).show();
            return;
        }



        //SAVE TO DATABASE

        String id = myGear.push().getKey();
        String base64Image = Base64.encodeToString(imageBlob, Base64.DEFAULT);
        Gears newGear =  new Gears(id,mGearName,mOwnerName,mInsurance,mWarranty,mPrice,mDetail,base64Image);

        myGear.child(id).setValue(newGear);
        /*storageReference = storage.getReference();
        StorageReference imageRef = storageReference.child("gears/"+ id +".png");

        UploadTask uploadTask = imageRef.putBytes(imageBlob);
*/


        Toast.makeText(this,"Gear Added", Toast.LENGTH_LONG).show();
        finish();
    }
}

