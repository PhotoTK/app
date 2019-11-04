package com.cs495.phototk.ui.weather.Common;

import android.location.Location;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// openweathermap API key
public class Common {
    public static final String APP_ID = "441f12daa399d43d845a461e576c039d";
    public static Location current_location=null;
    private static DecimalFormat df2 = new DecimalFormat("##.##");

    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm: EEE MM yyyy");
        String formatted = sdf.format(date);
        return  formatted;
    }

    public static String convertUnixToHour(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formatted = sdf.format(date);
        return  formatted;
    }

    public static String convertKelvinToFahrenheit(double temp) {
        double tempf = ((temp*9/5)-459.67);
        String formatted = String.valueOf(df2.format(tempf));
        return formatted;
    }

    public static String convertKelvinToCelsius(double temp) {
        double tempc = (temp-273.15);
        String formatted = String.valueOf(df2.format(tempc));
        return formatted;
    }
}
