package com.cs495.phototk.ui.weather;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.cs495.phototk.R;
import com.cs495.phototk.ui.weather.Common.Common;
import com.cs495.phototk.ui.weather.Model.WeatherResult;
import com.cs495.phototk.ui.weather.Retorfit.IOpenWeatherMap;
import com.cs495.phototk.ui.weather.Retorfit.RetorfitClient;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class weather_today extends Fragment {

    ImageView image_weather;
    TextView text_city_name, text_humidity, text_sunrise, text_sunset, text_temp_c, text_temp_f, text_pressure, text_description, text_cloud, text_wind_d,text_wind_s,text_temp, text_time, text_geo;
    LinearLayout weather_panel;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    static weather_today instance;

    public static weather_today getInstance() {
        if(instance == null)
            instance = new weather_today();
        return instance;
    }

    public weather_today() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retorfit = RetorfitClient.getInstance();
        mService = retorfit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_weather_today, container, false);
        image_weather = (ImageView)itemView.findViewById(R.id.image_weather);
        text_humidity = (TextView)itemView.findViewById(R.id.text_humidity);
        text_sunrise = (TextView)itemView.findViewById(R.id.text_sunrise);
        text_sunset = (TextView)itemView.findViewById(R.id.text_sunset);
        text_wind_d = (TextView)itemView.findViewById(R.id.text_wind_d);
        text_wind_s = (TextView)itemView.findViewById(R.id.text_wind_s);
        text_pressure = (TextView)itemView.findViewById(R.id.text_pressure);
        text_description = (TextView)itemView.findViewById(R.id.text_description);
        text_cloud = (TextView)itemView.findViewById(R.id.text_cloud);
        text_temp = (TextView)itemView.findViewById(R.id.text_temp);
        text_time = (TextView)itemView.findViewById(R.id.text_time);
        text_geo = (TextView)itemView.findViewById(R.id.text_geo);
        text_city_name = (TextView)itemView.findViewById(R.id.text_city_name);
        text_temp_f = (TextView)itemView.findViewById(R.id.text_temp_f);
        text_temp_c = (TextView)itemView.findViewById(R.id.text_temp_c);

        weather_panel = (LinearLayout)itemView.findViewById(R.id.weather_panel);
        loading = (ProgressBar)itemView.findViewById(R.id.loading);

        getWeatherInformation();

        return itemView;
    }

    private void getWeatherInformation() {
        compositeDisposable.add(mService.getWeatherByLatlng(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metic")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {

                        //load image
                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/wn/")
                                .append(weatherResult.getWeather().get(0).getIcon())
                        .append(".png").toString()).into(image_weather);

                        //load info
                        text_city_name.setText(weatherResult.getName());
                        text_description.setText(new StringBuilder("Weather in ")
                        .append(weatherResult.getName()).toString());
                        text_temp.setText(new StringBuilder(
                                String.valueOf(weatherResult.getMain().getTemp())).append("°K").toString());
                        text_temp_f.setText(new StringBuilder(Common.convertKelvinToFahrenheit(weatherResult.getMain().getTemp())).append("°F , "));
                        text_temp_c.setText(new StringBuilder(Common.convertKelvinToCelsius(weatherResult.getMain().getTemp())).append("°C"));
                        text_time.setText(Common.convertUnixToDate(weatherResult.getDt()));
                        text_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append("hpa").toString());
                        text_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append("%").toString());
                        text_sunrise.setText(Common.convertUnixToHour(weatherResult.getSys().getSunrise()));
                        text_sunset.setText(Common.convertUnixToHour(weatherResult.getSys().getSunset()));
                        text_geo.setText(new StringBuilder(weatherResult.getCoord().toString()).toString());
                        text_wind_d.setText(new StringBuilder(Common.toTextualDescription(weatherResult.getWind().getDeg())).append(" (").append(weatherResult.getWind().getDeg()).append(")").toString());
                        text_cloud.setText(new StringBuilder(String.valueOf(weatherResult.getClouds().getAll())).append("%").toString());
                        text_wind_s.setText(new StringBuilder(String.valueOf(weatherResult.getWind().getSpeed())).append(" meter/sec").toString());
                        //display
                        weather_panel.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);



                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })


        );
    }

}
