package com.example.weatherapp.retrofit;

import com.example.weatherapp.model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Methods {
    @GET("?daily=weathercode,temperature_2m_max,temperature_2m_min,rain_sum,snowfall_sum,sunrise,windspeed_10m_max&timezone=GMT")
    Call<WeatherModel>getAllData(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("start_date") String start_date,
            @Query("end_date") String end_date
    );

}
