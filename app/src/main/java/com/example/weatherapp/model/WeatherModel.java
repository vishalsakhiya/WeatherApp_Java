package com.example.weatherapp.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherModel {

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("generationtime_ms")
    private double generationTimeMs;

    @SerializedName("utc_offset_seconds")
    private int utcOffsetSeconds;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("timezone_abbreviation")
    private String timezoneAbbreviation;

    @SerializedName("elevation")
    private double elevation;

    @SerializedName("daily_units")
    private DailyUnits dailyUnits;

    @SerializedName("daily")
    private Daily daily;

    // Getter and Setter methods

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getGenerationTimeMs() {
        return generationTimeMs;
    }

    public void setGenerationTimeMs(double generationTimeMs) {
        this.generationTimeMs = generationTimeMs;
    }

    public int getUtcOffsetSeconds() {
        return utcOffsetSeconds;
    }

    public void setUtcOffsetSeconds(int utcOffsetSeconds) {
        this.utcOffsetSeconds = utcOffsetSeconds;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezoneAbbreviation() {
        return timezoneAbbreviation;
    }

    public void setTimezoneAbbreviation(String timezoneAbbreviation) {
        this.timezoneAbbreviation = timezoneAbbreviation;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public DailyUnits getDailyUnits() {
        return dailyUnits;
    }

    public void setDailyUnits(DailyUnits dailyUnits) {
        this.dailyUnits = dailyUnits;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    // Nested classes for nested JSON objects

    public static class DailyUnits {
        @SerializedName("time")
        private String time;

        @SerializedName("weathercode")
        private String weatherCode;

        @SerializedName("temperature_2m_max")
        private String temperature2mMax;

        @SerializedName("temperature_2m_min")
        private String temperature2mMin;

        @SerializedName("rain_sum")
        private String rainSum;

        @SerializedName("snowfall_sum")
        private String snowfallSum;

        @SerializedName("sunrise")
        private String sunrise;

        @SerializedName("windspeed_10m_max")
        private String windspeed10mMax;

        // Getter and Setter methods
        // ...

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getWeatherCode() {
            return weatherCode;
        }

        public void setWeatherCode(String weatherCode) {
            this.weatherCode = weatherCode;
        }

        public String getTemperature2mMax() {
            return temperature2mMax;
        }

        public void setTemperature2mMax(String temperature2mMax) {
            this.temperature2mMax = temperature2mMax;
        }

        public String getTemperature2mMin() {
            return temperature2mMin;
        }

        public void setTemperature2mMin(String temperature2mMin) {
            this.temperature2mMin = temperature2mMin;
        }

        public String getRainSum() {
            return rainSum;
        }

        public void setRainSum(String rainSum) {
            this.rainSum = rainSum;
        }

        public String getSnowfallSum() {
            return snowfallSum;
        }

        public void setSnowfallSum(String snowfallSum) {
            this.snowfallSum = snowfallSum;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getWindspeed10mMax() {
            return windspeed10mMax;
        }

        public void setWindspeed10mMax(String windspeed10mMax) {
            this.windspeed10mMax = windspeed10mMax;
        }
        // Note: Implement getter and setter methods for each field
    }

    public static class Daily {
        @SerializedName("time")
        private List<String> time;

        @SerializedName("weathercode")
        private List<Integer> weatherCode;

        @SerializedName("temperature_2m_max")
        private List<Double> temperature2mMax;

        @SerializedName("temperature_2m_min")
        private List<Double> temperature2mMin;

        @SerializedName("rain_sum")
        private List<Double> rainSum;

        @SerializedName("snowfall_sum")
        private List<Double> snowfallSum;

        @SerializedName("sunrise")
        public List<String> sunrise;

        @SerializedName("windspeed_10m_max")
        private List<Double> windspeed10mMax;

        // Getter and Setter methods
        // ...

        public List<String> getTime() {
            return time;
        }

        public void setTime(List<String> time) {
            this.time = time;
        }

        public List<Integer> getWeatherCode() {
            return weatherCode;
        }

        public void setWeatherCode(List<Integer> weatherCode) {
            this.weatherCode = weatherCode;
        }

        public List<Double> getTemperature2mMax() {
            return temperature2mMax;
        }

        public void setTemperature2mMax(List<Double> temperature2mMax) {
            this.temperature2mMax = temperature2mMax;
        }

        public List<Double> getTemperature2mMin() {
            return temperature2mMin;
        }

        public void setTemperature2mMin(List<Double> temperature2mMin) {
            this.temperature2mMin = temperature2mMin;
        }

        public List<Double> getRainSum() {
            return rainSum;
        }

        public void setRainSum(List<Double> rainSum) {
            this.rainSum = rainSum;
        }

        public List<Double> getSnowfallSum() {
            return snowfallSum;
        }

        public void setSnowfallSum(List<Double> snowfallSum) {
            this.snowfallSum = snowfallSum;
        }

        public List<String> getSunrise() {
            return sunrise;
        }

        public void setSunrise(List<String> sunrise) {
            this.sunrise = sunrise;
        }

        public List<Double> getWindspeed10mMax() {
            return windspeed10mMax;
        }

        public void setWindspeed10mMax(List<Double> windspeed10mMax) {
            this.windspeed10mMax = windspeed10mMax;
        }

        // Note: Implement getter and setter methods for each field
    }
}