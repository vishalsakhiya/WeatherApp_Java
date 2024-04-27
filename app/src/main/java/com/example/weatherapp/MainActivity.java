package com.example.weatherapp;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.Geocoder;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.adapter.CustomAdapter;
import com.example.weatherapp.adapter.HorizontalAdapter;
import com.example.weatherapp.model.LocationModel;
import com.example.weatherapp.model.WeatherModel;
import com.example.weatherapp.retrofit.Methods;
import com.example.weatherapp.retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements HorizontalAdapter.onDaysSelectedInterface {

    String provider;
    LocationManager locationManager;
    private TextView txtCity;
    private TextView txtCurrentTemp;
    private TextView txtWeatherType;
    Geocoder geocoder;
    List<Address> addresses;
    private TextView txtWind;
    private  TextView txtRain, txtSunRise;
    private RelativeLayout progress;
    private ImageView currentWeatherIcon;
    RecyclerView recyclerWeather;
    ArrayList<LocationModel> locationModels;
    LocationModel currentLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, false);
        txtCity = findViewById(R.id.txtCity);
        txtCurrentTemp = findViewById(R.id.txtCurrentTemp);
        txtCurrentTemp.setText("0°");
        txtWeatherType =findViewById(R.id.txtWeatherType);
        txtWind = findViewById(R.id.txtWind);
        txtRain = findViewById(R.id.txtRain);
        txtSunRise = findViewById(R.id.txtSunRise);
        currentWeatherIcon = findViewById(R.id.currentWeatherIcon);
        progress = findViewById(R.id.progress);
        recyclerWeather = findViewById(R.id.recyclerWeather);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerWeather.setLayoutManager(layoutManager);

        String txt = Constant.loadJSONFromAsset(this, "state_location.json");
        Type type = new TypeToken<ArrayList<LocationModel>>() {}.getType();
        locationModels = new Gson().fromJson(txt,type);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED &&
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

            callWeatherAPI(locationModels.get(0));
        } else {
           Location location = locationManager.getLastKnownLocation(Objects.requireNonNull(locationManager.getBestProvider(criteria,false)));
            double lat, longitude;
            if (location != null) {
                lat = location.getLatitude();
                longitude = location.getLongitude();
            } else {
                lat = 21.202745;
                longitude = 72.838829;
            }
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(lat, longitude, 1);
                assert addresses != null;
                String city = addresses.get(0).getLocality();
                txtCity.setText(city);

                callWeatherAPI(locationModels.get(0));
            } catch (IOException e){
                Log.v("TTT", "IOException...... e: " + e);
                progress.setVisibility(View.INVISIBLE);
                throw new RuntimeException(e);
            }

//           if(location != null){
//               double lat = location.getLatitude();
//               double longitude = location.getLongitude();
//               geocoder = new Geocoder(this, Locale.getDefault());
//
//               try {
//                   addresses = geocoder.getFromLocation(lat, longitude, 1);
//                   assert addresses != null;
//                   String city = addresses.get(0).getLocality();
//                   txtCity.setText(city);
//
//                   callWeatherAPI(locationModels.get(0));
//               } catch (IOException e){
//                   Log.v("TTT", "IOException...... e: " + e);
//                   progress.setVisibility(View.INVISIBLE);
//                   throw new RuntimeException(e);
//               }
//           }
        }
    }

    public void callWeatherAPI(LocationModel locationModel) {
        currentLocation = locationModel;
        progress.setVisibility(View.VISIBLE);
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.setFirstDayOfWeek(Calendar.SUNDAY);
        firstCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String start_date = firstCalendar.get(Calendar.YEAR) + "-" +String.format("%02d", (firstCalendar.get(Calendar.MONTH)+1)) + "-" + String.format("%02d", firstCalendar.get(Calendar.DATE));

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setFirstDayOfWeek(Calendar.SUNDAY);
        endCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        String end_date = endCalendar.get(Calendar.YEAR) + "-" + String.format("%02d", (endCalendar.get(Calendar.MONTH)+1)) + "-" + String.format("%02d", endCalendar.get(Calendar.DATE));

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<WeatherModel> call = methods.getAllData(locationModel.getLatitude().toString(), locationModel.getLongitude().toString(), start_date, end_date);
        call.enqueue(new Callback<WeatherModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call <WeatherModel> call, @NonNull Response<WeatherModel> response) {
                assert response.body() != null;
                progress.setVisibility(View.INVISIBLE);
                setViewByPosition(response.body().getDaily(), 0, false);

                Log.v("TTT", "response: " + new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<WeatherModel> call, @NonNull Throwable t) {
                Log.v("TTT", "onFailure...... Failed");
                progress.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onDaysSelected(WeatherModel.Daily daily, int position) {
        setViewByPosition(daily, position, true);
    }

    public void setViewByPosition(WeatherModel.Daily daily, int position, boolean isDaySelected) {
        txtCity.setText(currentLocation.getName());
        currentWeatherIcon.setImageDrawable(getResources().getDrawable(Constant.getWeatherIcon(
                daily.getWeatherCode().get(position))));

        txtCurrentTemp.setText(daily.getTemperature2mMin().get(position).toString()+ "°");
        txtWeatherType.setText(Constant.getWeatherType(daily.getWeatherCode().get(position)).replace("_", " ").toLowerCase());
        txtWind.setText((daily.getWindspeed10mMax().get(position).intValue())+ " km");
        txtRain.setText(daily.getRainSum().get(position).intValue()+"%");

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); // , Locale.ENGLISH
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date objDate = dateFormat.parse(daily.sunrise.get(position));
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm a");
            dateFormat2.setTimeZone(TimeZone.getDefault());
            String finalDate = dateFormat2.format(objDate);
            txtSunRise.setText(finalDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (!isDaySelected) {
            HorizontalAdapter adapter = new HorizontalAdapter(MainActivity.this, daily, MainActivity.this);
            recyclerWeather.setAdapter(adapter);
        }
    }

    MatrixCursor lastValidSearchResult = new MatrixCursor(new String[]{"_id","name", "state", "latitude", "longitude"});
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem action_done = menu.findItem(R.id.action_search);
        menuIconColor(action_done, Color.WHITE);

        String[] columnNames = {"_id","name", "state", "latitude", "longitude"};
        MatrixCursor cursor = new MatrixCursor(columnNames);
        String[] temp = new String[5];
        int id = 0;
        for(LocationModel item : locationModels){
            temp[0] = Integer.toString(id++);
            temp[1] = item.getName();
            temp[2] = item.getState();
            temp[3] = item.getLatitude().toString();
            temp[4] = item.getLongitude().toString();
            cursor.addRow(temp);
        }

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        androidx.cursoradapter.widget.CursorAdapter cursorAdapter = new CustomAdapter(this, cursor);
        searchView.setSuggestionsAdapter(cursorAdapter);
//        searchView.getSuggestionsAdapter().changeCursor(cursor);
        cursorAdapter.setFilterQueryProvider(constraint -> {
            // if the search string is empty, then return the original cursor with all results from the original query
            if(constraint == null || constraint.equals(""))
            {
                lastValidSearchResult = cursor;
                return cursor;
            }
            MatrixCursor filteredValues = new MatrixCursor(new String[]{"_id","name", "state", "latitude", "longitude"});
            cursor.moveToFirst();
            do {
                String stateName = cursor.getString(cursor.getColumnIndex("name"));
                if (stateName.toLowerCase().contains(constraint.toString().toLowerCase())) {
                    filteredValues.addRow(new String[]{
                            cursor.getString(cursor.getColumnIndex("_id")),
                            cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getString(cursor.getColumnIndex("state")),
                            cursor.getString(cursor.getColumnIndex("latitude")),
                            cursor.getString(cursor.getColumnIndex("longitude"))
                    });
                }
            } while(cursor.moveToNext());

            if(filteredValues.getCount() == 0)
            {
                return lastValidSearchResult;
            }
            lastValidSearchResult = filteredValues;
            return filteredValues;
        });

        LinearLayout ll = (LinearLayout)searchView.getChildAt(0);
        LinearLayout ll2 = (LinearLayout)ll.getChildAt(2);
        LinearLayout ll3 = (LinearLayout)ll2.getChildAt(1);
        SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete)ll3.getChildAt(0);
        autoComplete.setHint("Search By State");

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @SuppressLint("Range")
            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor1 = (Cursor)cursorAdapter.getItem(position);
                String selectedItem = cursor1.getString(cursor1.getColumnIndex("name"));
                for (LocationModel locationModel : locationModels) {
                    if (locationModel.getName().toLowerCase().contains(selectedItem.toLowerCase())) {
                        autoComplete.setText(locationModel.getName());
                        callWeatherAPI(locationModel);
                        return true;
                    }
                }
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                Log.w("TTT", "onSuggestionSelect: " + position);
                return false;
            }
        });
        return true;
    }

    public void menuIconColor(MenuItem menuItem, int color) {
        Drawable drawable = menuItem.getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

}
