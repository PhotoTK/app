package com.cs495.phototk.ui.management;

import android.app.Activity;
import android.widget.Button;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import java.util.List;
import java.util.ArrayList;


/*public class GearListAdapter extends AppCompatActivity {
    private LayoutInflater mLayoutInflator;

    public GearListAdapter(Context context, int flag) {
        super(context, c, flag);
        this.mLayoutInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        return mLayoutInflator.inflate(R.layout.list_gear, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context) {
        LinearLayout listItemView = (LinearLayout) view.findViewById(R.id.listGear);
        TextView gearName = (TextView) view.findViewById(R.id.name);
        TextView gearOwner = (TextView) view.findViewById(R.id.owner);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView detail = (TextView) view.findViewById(R.id.detail);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference gearsRef = rootRef.child("gears");

        gearsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Gears gears = dataSnapshot.getValue(Gears.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        });
    }

}*/


public class GearListAdapter extends BaseAdapter {

    private Activity activity;
    private List<Gears> listGear;

    public GearListAdapter(Activity activity, List<Gears> listGear){
        this.activity = activity;
        this.listGear = listGear;
    }

    @Override
    public int getCount() {
        return listGear.size();
    }

    @Override
    public Object getItem(int position) {
        return listGear.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.list_gear, null);

            holder = new ViewHolder();
            holder.gearName = convertView.findViewById(R.id.name);
            holder.gearOwner = convertView.findViewById(R.id.owner);
            holder.price = convertView.findViewById(R.id.price);
            holder.insurance = convertView.findViewById(R.id.insurance);
            holder.warranty = convertView.findViewById(R.id.warranty);
            holder.detail = convertView.findViewById(R.id.detail);
            holder.imageView = convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.gearName.setText(listGear.get(position).getGearName());
        holder.gearOwner.setText("Owner : " + listGear.get(position).getGearOwner());
        holder.price.setText("Price : " + listGear.get(position).getPrice() + " $");
        holder.insurance.setText("Insurance date : " + listGear.get(position).getInsurance());
        holder.warranty.setText("Warranty date : " + listGear.get(position).getWarranty());
        holder.detail.setText("Detail : " + listGear.get(position).getDetail());

        byte[] imageBytes = Base64.decode(listGear.get(position).getPic(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        holder.imageView.setImageBitmap(decodedImage);

        return convertView;
    }

    class ViewHolder{
        TextView gearName;// = (TextView) view.findViewById(R.id.name);
        TextView gearOwner;// = (TextView) view.findViewById(R.id.owner);
        TextView insurance;
        TextView warranty;
        TextView price; //= (TextView) view.findViewById(R.id.price);
        TextView detail;// = (TextView) view.findViewById(R.id.detail);
        ImageView imageView;// = (ImageView) view.findViewById(R.id.imageView);
    }
}
