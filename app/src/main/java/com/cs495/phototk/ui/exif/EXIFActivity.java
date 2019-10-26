package com.cs495.phototk.ui.exif;
//API24

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.R;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EXIFActivity extends AppCompatActivity{
    private Spinner spinner;
    private static final String[] paths = {"item1", "item 2", "item 3"};
    private static final int RQS_OPEN_IMAGE = 1;
    Button buttonOpen;
    Button buttonEdit;
    EditText textEdit;
    TextView textUri;
    ImageView imageView;
    Uri targetUri = null;
    String strPhotoPath;
    ExifInterface exif;
    ExifInterface exifInterface;

    View.OnClickListener buttonOpenOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/jpeg");
                    startActivityForResult(intent, RQS_OPEN_IMAGE);
                }
            };

   View.OnClickListener buttonEditOnClickListener =
            new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String content = textEdit.getText().toString().trim();
                    setExifInfo(targetUri,content);
                }
            };

    View.OnClickListener textUriOnClickListener =
            new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (targetUri != null){
                        Bitmap bm;
                        try {
                            bm = BitmapFactory.decodeStream(
                                    getContentResolver()
                                            .openInputStream(targetUri));
                            imageView.setImageBitmap(bm);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

    View.OnClickListener imageOnClickListener =
            new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    showExif(targetUri);
                }
            };

    void showExif(Uri photoUri){
        if(photoUri != null){

            ParcelFileDescriptor parcelFileDescriptor = null;

            try {
                parcelFileDescriptor = getContentResolver().openFileDescriptor(photoUri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

                exifInterface = new ExifInterface(fileDescriptor);
                String exif="Exif: " + fileDescriptor.toString();
                exif += "\nIMAGE_LENGTH: " +
                        exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
                exif += "\nIMAGE_WIDTH: " +
                        exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
                exif += "\n DATETIME: " +
                        exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                exif += "\n TAG_MAKE: " +
                        exifInterface.getAttribute(ExifInterface.TAG_MAKE);
                exif += "\n TAG_MODEL: " +
                        exifInterface.getAttribute(ExifInterface.TAG_MODEL);
                exif += "\n TAG_ORIENTATION: " +
                        exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
                exif += "\n TAG_WHITE_BALANCE: " +
                        exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);
                exif += "\n TAG_FOCAL_LENGTH: " +
                        exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
                exif += "\n TAG_FLASH: " +
                        exifInterface.getAttribute(ExifInterface.TAG_FLASH);
                exif += "\nGPS related:";
                exif += "\n TAG_GPS_DATESTAMP: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);
                exif += "\n TAG_GPS_TIMESTAMP: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP);
                exif += "\n TAG_GPS_LATITUDE: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                exif += "\n TAG_GPS_LATITUDE_REF: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
                exif += "\n TAG_GPS_LONGITUDE: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                exif += "\n TAG_GPS_LONGITUDE_REF: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
                exif += "\n TAG_GPS_ALTITUDE: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_ALTITUDE);
                exif += "\n TAG_GPS_ALTITUDE: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF);
                exif += "\n TAG_USER_COMMENT: " +
                        exifInterface.getAttribute(ExifInterface.TAG_USER_COMMENT);
                exif += "\n TAG_COPYRIGHT: " +
                        exifInterface.getAttribute(ExifInterface.TAG_COPYRIGHT);
                exif += "\n TAG_ARTIST: " +
                        exifInterface.getAttribute(ExifInterface.TAG_ARTIST);
                exif += "\n TAG_COPYRIGHT: " +
                        exifInterface.getAttribute(ExifInterface.TAG_COPYRIGHT);
                parcelFileDescriptor.close();

                Toast.makeText(getApplicationContext(),
                        exif,
                        Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Something wrong:\n" + e.toString(),
                        Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Something wrong:\n" + e.toString(),
                        Toast.LENGTH_LONG).show();
            }

            strPhotoPath = photoUri.getPath();

        }else{
            Toast.makeText(getApplicationContext(),
                    "photoUri == null",
                    Toast.LENGTH_LONG).show();
        }
    }

    void setExifInfo(Uri photoUri, String s){
        if(photoUri != null){
            ParcelFileDescriptor parcelFileDescriptor = null;
            try {
                parcelFileDescriptor = getContentResolver().openFileDescriptor(photoUri, "rw");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                exifInterface = new ExifInterface(fileDescriptor);
                String tag_choice = new String (String.valueOf(spinner.getSelectedItem()));
                if(tag_choice.equals("TAG_MAKE")) {
                    exifInterface.setAttribute(ExifInterface.TAG_MAKE, s);
                }
                else if(tag_choice.equals("TAG_MODEL")) {
                    exifInterface.setAttribute(ExifInterface.TAG_MODEL, s);
                }
                else if(tag_choice.equals("TAG_USER_COMMENT")) {
                    exifInterface.setAttribute(ExifInterface.TAG_USER_COMMENT, s);
                }
                else if(tag_choice.equals("TAG_GPS_LATITUDE_REF")) {
                    exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, s);
                }
                else if(tag_choice.equals("TAG_GPS_LATITUDE")) {
                    exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE, s);
                }
                else if(tag_choice.equals("TAG_GPS_LONGITUDE_REF")) {
                    exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, s);
                }
                else if(tag_choice.equals("TAG_GPS_LONGITUDE")) {
                    exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, s);
                }
                else if(tag_choice.equals("TAG_GPS_DATESTAMP")) {
                    exifInterface.setAttribute(ExifInterface.TAG_GPS_DATESTAMP, s);
                }
                else if(tag_choice.equals("TAG_GPS_ALTITUDE_REF")) {
                    exifInterface.setAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF, s);
                }
                else if(tag_choice.equals("TAG_GPS_ALTITUDE")) {
                    exifInterface.setAttribute(ExifInterface.TAG_GPS_ALTITUDE, s);
                }
                else if(tag_choice.equals("TAG_COPYRIGHT")) {
                    exifInterface.setAttribute(ExifInterface.TAG_COPYRIGHT, s);
                }
                else if(tag_choice.equals("TAG_ARTIST")) {
                    exifInterface.setAttribute(ExifInterface.TAG_ARTIST, s);
                }
                else{
                    exifInterface.setAttribute(ExifInterface.TAG_DATETIME, s);
                }
                try {
                    exifInterface.saveAttributes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Something wrong:\n" + e.toString(),
                        Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Something wrong:\n" + e.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),
                    "photoUri == null",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void addItemsOnSpinner() {

        spinner = (Spinner) findViewById(R.id.Tag_Spinner);
        List<String> list = new ArrayList<String>();
        list.add("TAG_USER_COMMENT");
        list.add("TAG_MAKE");
        list.add("TAG_MODEL");
        list.add("TAG_DATETIME");
        list.add("TAG_ARTIST");
        list.add("TAG_COPYRIGHT");
        list.add("TAG_EXIF_VERSION");
        list.add("TAG_GPS_ALTITUDE");
        list.add("TAG_GPS_ALTITUDE_REF");
        list.add("TAG_GPS_DATESTAMP");
        list.add("TAG_GPS_LONGITUDE");
        list.add("TAG_GPS_LONGITUDE_REF");
        list.add("TAG_GPS_DEST_LONGITUDE");
        list.add("TAG_GPS_DEST_LONGITUDE_REF");
        list.add("TAG_GPS_LATITUDE");
        list.add("TAG_GPS_LATITUDE_REF");
        list.add("TAG_GPS_DEST_LATITUDE");
        list.add("TAG_GPS_DEST_LONGITUDE_REF");
        list.add("");
        list.add("");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exif);

        addItemsOnSpinner();

        buttonOpen = (Button) findViewById(R.id.opendocument);
        buttonOpen.setOnClickListener(buttonOpenOnClickListener);

        buttonEdit = (Button) findViewById(R.id.btn_EXIF_edit);
        buttonEdit.setOnClickListener(buttonEditOnClickListener);

        textEdit = (EditText) findViewById(R.id.editdocument);

        textUri = (TextView) findViewById(R.id.texturi);
        textUri.setOnClickListener(textUriOnClickListener);

        imageView = (ImageView)findViewById(R.id.image);
        imageView.setOnClickListener(imageOnClickListener);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            Uri dataUri = data.getData();

            if (requestCode == RQS_OPEN_IMAGE) {
                targetUri = dataUri;
                textUri.setText(dataUri.toString());
                imageView.setImageBitmap(null);
            }
        }

    }
}