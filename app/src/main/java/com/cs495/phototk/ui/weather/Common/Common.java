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
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd EEE MM yyyy");
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

    public static String toTextualDescription(double degree) {
        if (degree>337.5) return "Northerly";
        if (degree>292.5) return "North Westerly";
        if(degree>247.5) return "Westerly";
        if(degree>202.5) return "South Westerly";
        if(degree>157.5) return "Southerly";
        if(degree>122.5) return "South Easterly";
        if(degree>67.5) return "Easterly";
        if(degree>22.5) return "North Easterly";
        return "Northerly";
    }

    public static String convertCelsiusToFahrenheit(double temp) {
        double tempf = ((temp/(9/5))+32);
        String formatted = String.valueOf(df2.format(tempf));
        return formatted;
    }

    public static String convertCelsiusToKelvin(double temp) {
        double tempk = (temp+273.15);
        String formatted = String.valueOf(df2.format(tempk));
        return formatted;
    }
}
