package com.cs495.phototk.ui.celestial;

import android.content.ContentValues;
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
        DataBase helper = new DataBase(this);
        SQLiteDatabase dbc = helper.getWritableDatabase();
        dbc.beginTransaction();
        ContentValues val = new ContentValues();
        val.put("id","1");
        val.put("time","2019/December/26");
        val.put("location","China");
        val.put("pathWidth",12);
        val.put("centralDuration","16s");
        dbc.insert("celestial","null",val);

        ContentValues val1 = new ContentValues();
        val.put("id","2");
        val.put("time","2020/June/21");
        val.put("location","America");
        val.put("pathWidth",20);
        val.put("centralDuration","1min40s");
        dbc.insert("celestial","null",val);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_celestial);
        search = (Button) findViewById(R.id.btnButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
                String text;
                EditText edit = (EditText)findViewById(R.id.search_edit);
                text = edit.getText().toString();
                Cursor cur = dbc.query(true, "celestial", new String[]{"time","location","pathWidth","centralDuration"}, "id=?", new String[]{text},null, null, null, null);
                while (cur.moveToNext()) {
                    String time = cur.getString(cur.getColumnIndex("time"));
                    String location = cur.getString(cur.getColumnIndex("location"));
                    String pathWidth = cur.getString(cur.getColumnIndex("pathWidth"));
                    String centralDuration = cur.getString(cur.getColumnIndex("centralDuration"));
                    LinearLayout output =(LinearLayout) findViewById(R.id.search_celestial);
                    TextView op =new TextView(SearchCelestial.this);
                    op.setText((time) + " " + (location) + " " + (pathWidth) + "m" + " " + (centralDuration));
                    op.setTextSize(20);
                    output.addView(op);
                    break;
                }
                dbc.close();
            }
        });
    }
}
