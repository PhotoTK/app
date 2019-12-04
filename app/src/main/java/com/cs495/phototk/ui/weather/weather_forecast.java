package com.cs495.phototk.ui.weather;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs495.phototk.R;
import com.cs495.phototk.ui.weather.Adapter.WeatherForecastAdapter;
import com.cs495.phototk.ui.weather.Common.Common;
import com.cs495.phototk.ui.weather.Model.WeatherForecast;
import com.cs495.phototk.ui.weather.Retorfit.IOpenWeatherMap;
import com.cs495.phototk.ui.weather.Retorfit.RetorfitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class weather_forecast extends Fragment {

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    TextView text_city_name,text_geo;
    RecyclerView recycler_forecast;

    static weather_forecast instance;

    public static weather_forecast getInstance() {
        if(instance == null)
            instance = new weather_forecast();
        return instance;
    }

    public weather_forecast() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetorfitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_weather_forecast,container,false);

        text_city_name = (TextView)itemView.findViewById(R.id.weather_forecast_city_name);
        text_geo = (TextView)itemView.findViewById(R.id.weather_forecast_geo_coord);

        recycler_forecast = (RecyclerView)itemView.findViewById(R.id.weather_forecast_recycler);
        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        getForecastWeatherInformation();

        return itemView;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void getForecastWeatherInformation() {
        compositeDisposable.add(mService.getForecastByLatlng(
                String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecast>() {
                    @Override
                    public void accept(WeatherForecast weatherForecast) throws Exception {
                        displayForecastWeather(weatherForecast);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ERROR",""+throwable.getMessage());
                    }
                })
        );

    }

    private void displayForecastWeather(WeatherForecast weatherForecast) {
        text_city_name.setText(new StringBuilder(weatherForecast.city.name));
        text_geo.setText(new StringBuilder(weatherForecast.city.coord.toString()));

        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(),weatherForecast);
        recycler_forecast.setAdapter(adapter);
    }

}
