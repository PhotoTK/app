package com.cs495.phototk.ui.weather.Retorfit;

import com.cs495.phototk.ui.weather.Model.WeatherForecast;
import com.cs495.phototk.ui.weather.Model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<WeatherResult> getWeatherByLatlng(@Query("lat") String lat,
                                                 @Query("lon") String lng,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit);

    @GET("forecast")
    Observable<WeatherForecast> getForecastByLatlng(@Query("lat") String lat,
                                                    @Query("lon") String lng,
                                                    @Query("appid") String appid,
                                                    @Query("units") String unit);
}
