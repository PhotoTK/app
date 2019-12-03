package com.cs495.phototk.ui.management;

import android.graphics.BitmapFactory;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class GearEdit extends AppCompatActivity {
    static final int REQUEST_CAMERA = 1;

    byte[] imageBlob = null;
    DatabaseReference myGear;
    Gears newgears = new Gears();
    boolean edit = false;

    EditText gearName;
    EditText gearOwner;
    EditText insurance;
    EditText warranty;
    EditText price;
    EditText detail;
    ImageView gearImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear_edit);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mCurrentUser = mAuth.getCurrentUser();
        String uid = mAuth.getUid();
        if (mCurrentUser != null) {
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            myGear  = rootRef.child("users").child(uid);
        }
        else{
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            myGear  = rootRef.child("gears");}
        initUI();
        handleBundle();

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
        Button returnButton = findViewById(R.id.button_return);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    public void saveItem() {

        String mGearName = gearName.getText().toString();
        if (gearName.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Gear name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }
        newgears.setGearName(mGearName);

        String mOwnerName = gearOwner.getText().toString();
        if (gearOwner.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Owner name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }
        newgears.setGearOwner(mOwnerName);

        String mInsurance = insurance.getText().toString();
        newgears.setInsurance(mInsurance);

        String mWarranty = warranty.getText().toString();
        newgears.setWarranty(mWarranty);

        double mPrice = 0.0;
        if (price.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter the gear price", Toast.LENGTH_LONG).show();
            return;
        } else {
            mPrice = Double.parseDouble(price.getText().toString());
        }
        newgears.setPrice(mPrice);

        String mDetail = detail.getText().toString();
        newgears.setDetail(mDetail);

        if (imageBlob == null && !edit) {
            Toast.makeText(getApplicationContext(), "Gear picture required", Toast.LENGTH_LONG).show();
            return;
        }
        if (imageBlob != null) {
            String base64Image = Base64.encodeToString(imageBlob, Base64.DEFAULT);
            newgears.setPic(base64Image);
        }
        //SAVE TO DATABASE
        if (edit) {
            myGear.child(newgears.getKey()).setValue(newgears);

            Toast.makeText(this, "Gear Updated", Toast.LENGTH_LONG).show();
            finish();
        } else {
            String id = myGear.push().getKey();
            newgears.setKey(id);
            myGear.child(id).setValue(newgears);

            Toast.makeText(this, "Gear Added", Toast.LENGTH_LONG).show();
            finish();
        }
        /*storageReference = storage.getReference();
        StorageReference imageRef = storageReference.child("gears/"+ id +".png");

        UploadTask uploadTask = imageRef.putBytes(imageBlob);
*/

    }

        void initUI(){
            gearName = (EditText) findViewById(R.id.edit_item_name);
            gearOwner = (EditText) findViewById(R.id.edit_owner_name);
            insurance = (EditText) findViewById(R.id.edit_insurance_date);
            warranty = (EditText) findViewById(R.id.edit_warranty_date);
            price = (EditText) findViewById(R.id.edit_item_price);
            detail = (EditText) findViewById(R.id.edit_detail);
            gearImage = (ImageView) findViewById(R.id.gear_photo);
        }

    private void handleBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            edit = bundle.getBoolean("edit");
            if(edit){
                Gears gears = getIntent().getParcelableExtra("gears");
                gearName.setText(gears.getGearName());
                gearOwner.setText(gears.getGearOwner());
                insurance.setText(gears.getInsurance());
                warranty.setText(gears.getWarranty());
                price.setText(gears.getPrice().toString());
                detail.setText(gears.getDetail());
                newgears.setKey(gears.getKey());
                newgears.setPic(gears.getPic());
                byte[] imageBytes = Base64.decode(gears.getPic(), Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                gearImage.setImageBitmap(decodedImage);
            }
        }
    }
}

