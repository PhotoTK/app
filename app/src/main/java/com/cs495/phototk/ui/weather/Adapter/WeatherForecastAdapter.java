package com.cs495.phototk.ui.weather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs495.phototk.R;
import com.cs495.phototk.ui.weather.Common.Common;
import com.cs495.phototk.ui.weather.Model.WeatherForecast;
import com.squareup.picasso.Picasso;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    Context context;
    WeatherForecast weatherForecast;

    public WeatherForecastAdapter(Context context, WeatherForecast weatherForecast) {
        this.context = context;
        this.weatherForecast = weatherForecast;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.weather_forecast_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //load image
        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/wn/")
                .append(weatherForecast.list.get(position).weather.get(0).getIcon())
                .append(".png").toString()).into(holder.image_weather);

        holder.text_date.setText(new StringBuilder(Common.convertUnixToDate(weatherForecast.list.get(position).dt)));
        holder.text_description.setText(new StringBuilder(weatherForecast.list.get(position).weather.get(0).getDescription()));
        holder.text_temp_c.setText(new StringBuilder(String.valueOf(weatherForecast.list.get(position).main.getTemp())).append("°C"));
        holder.text_temp.setText(new StringBuilder(Common.convertCelsiusToKelvin(weatherForecast.list.get(position).main.getTemp())).append("°K"));
        holder.text_temp_f.setText(new StringBuilder(Common.convertCelsiusToFahrenheit(weatherForecast.list.get(position).main.getTemp())).append("°F"));
    }

    @Override
    public int getItemCount() {
        return weatherForecast.list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text_date,text_description,text_temp,text_temp_f,text_temp_c;
        ImageView image_weather;
        public MyViewHolder(View itemView){
            super(itemView);
            image_weather = (ImageView)itemView.findViewById(R.id.image_weather);
            text_date = (TextView)itemView.findViewById(R.id.text_date);
            text_description = (TextView)itemView.findViewById(R.id.text_description);
            text_temp = (TextView)itemView.findViewById(R.id.text_temp);
            text_temp_c = (TextView)itemView.findViewById(R.id.text_temp_c);
            text_temp_f = (TextView)itemView.findViewById(R.id.text_temp_f);

        }
    }
}
