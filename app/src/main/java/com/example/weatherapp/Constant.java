package com.example.weatherapp;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class Constant {
    public static String getWeatherType (int code) {
        if ( code == 0) {
            return "CLEAR";
        } else if ( code == 1) {
            return "CLEAR";
        } else if (code == 2) {
            return "PARTLY_CLOUDY";
        } else if ( code == 3) {
            return "OVERCAST";
        } else if ( code >= 45 && code <= 48) {
            return "CLOUDY";
        } else if ( code >= 51 && code <= 55) {
            return "MODERATE_RAIN_AT_TIMES";
        } else if ( code >= 56 && code <= 57) {
            return "CLOUDY";
        } else if ( code >= 61 && code <= 65) {
            return "LIGHT_RAIN";
        } else if (code >= 66 && code <= 67) {
            return "HEAVY_RAIN";
        } else if ( code >= 71 && code <= 75) {
            return "OTHER";
        } else if ( code == 77) {
            return "MIST";
        } else if ( code >= 80 && code <= 82) {
            return "MODERATE_OR_HEAVY_RAIN_SHOWER";
        } else if ( code >= 85 && code <= 86) {
            return "OTHER";
        } else if (code == 95) {
            return "MODERATE_OR_HEAVY_RAIN_WITH_THUNDER";
        } else if (code >= 96 && code <= 99) {
            return "MODERATE_OR_HEAVY_FREEZING_RAIN";
        } else  {
            return  "";
        }
    };

    public static Integer getWeatherIcon(int code) {
        if ( code == 0) {
            return R.drawable.sun;
        } else if ( code == 1) {
            return R.drawable.sun;
        } else if (code == 2) {
            return R.drawable.partlycloudy;
        } else if ( code == 3) {
            return R.drawable.cloud;
        } else if ( code >= 45 && code <= 48) {
            return R.drawable.cloud;
        } else if ( code >= 51 && code <= 55) {
            return R.drawable.moderaterain;
        } else if ( code >= 56 && code <= 57) {
            return R.drawable.cloud;
        } else if ( code >= 61 && code <= 65) {
            return R.drawable.moderaterain;
        } else if (code >= 66 && code <= 67) {
            return R.drawable.heavyrain;
        } else if ( code >= 71 && code <= 75) {
            return R.drawable.moderaterain;
        } else if ( code == 77) {
            return R.drawable.mist;
        } else if ( code >= 80 && code <= 82) {
            return R.drawable.heavyrain;
        } else if ( code >= 85 && code <= 86) {
            return R.drawable.moderaterain;
        } else if (code == 95) {
            return R.drawable.heavyrain;
        } else if (code >= 96 && code <= 99) {
            return R.drawable.heavyrain;
        } else  {
            return R.drawable.sun;
        }
    };

    public static String loadJSONFromAsset(Context context, String jsonFilename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonFilename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
