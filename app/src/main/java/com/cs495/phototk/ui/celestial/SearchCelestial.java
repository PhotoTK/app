package com.cs495.phototk.ui.celestial;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.R;


public class SearchCelestial extends AppCompatActivity {
    private Button search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_celestial);
        DataBase helper1 = new DataBase(this, "celestial", null, 1);
        search = (Button) findViewById(R.id.btnButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text;
                EditText edit = (EditText) findViewById(R.id.search_edit);
                text = edit.getText().toString();
                SQLiteDatabase dbc1 = helper1.getWritableDatabase();
                Cursor cur = dbc1.query(true, "celestial", new String[]{"time","location","pathWidth","centralDuration"}, "id=?", new String[]{text},null, null, null, null);
                String time = null;
                String location = null;
                String pathWidth = null;
                String centralDuration = null;
                while (cur.moveToNext()) {
                    time = cur.getString(cur.getColumnIndex("time"));
                    location = cur.getString(cur.getColumnIndex("location"));
                    pathWidth = cur.getString(cur.getColumnIndex("pathWidth"));
                    centralDuration = cur.getString(cur.getColumnIndex("centralDuration"));
                    LinearLayout output =(LinearLayout) findViewById(R.id.search_celestial);
                    TextView op =new TextView(SearchCelestial.this);
                    op.setText("Time: " + (time) + "\n" + "Location: " + (location) + "\n" + "PathWidth: " + (pathWidth) + "mi" + "\n" + "CentralDuration: " + (centralDuration));
                    op.setTextSize(20);
                    output.addView(op);
                }
                dbc1.close();
            }
        });
    }
}
