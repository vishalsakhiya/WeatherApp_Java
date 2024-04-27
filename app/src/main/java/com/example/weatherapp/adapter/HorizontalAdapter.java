package com.example.weatherapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherapp.model.WeatherModel;
import com.example.weatherapp.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {
    private WeatherModel.Daily dataArray;
    private Context context;
    private onDaysSelectedInterface daysSelectedInterface;
    private int selectedPosition;

    public HorizontalAdapter(Context context, WeatherModel.Daily dataArray, onDaysSelectedInterface daysSelectedInterface) {
        this.context = context;
        this.dataArray = dataArray;
        this.daysSelectedInterface = daysSelectedInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (selectedPosition == position)
            holder.layoutWeatherDay.setBackground(context.getDrawable(R.drawable.weathercard_background_selected));
        else holder.layoutWeatherDay.setBackground(context.getDrawable(R.drawable.weathercard_background));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = df.parse(dataArray.sunrise.get(position));
        } catch (Exception e) {
            Log.e("Error:","Exception " + e);
        }
        holder.sampleText.setText(new SimpleDateFormat("EEEE").format(date));
        holder.txtTemp.setText(dataArray.getTemperature2mMax().get(position).toString()+ "°");
        holder.imgWeather.setImageDrawable(context.getResources().getDrawable(getWeatherIcon(dataArray.getWeatherCode().get(position))));
        holder.layoutWeatherDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                daysSelectedInterface.onDaysSelected(dataArray, position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataArray.sunrise.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sampleText, txtTemp;
        ImageView imgWeather;
        LinearLayout layoutWeatherDay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sampleText = itemView.findViewById(R.id.sampleText);
            txtTemp = itemView.findViewById(R.id.txtTemp);
            imgWeather = itemView.findViewById(R.id.imgWeather);
            layoutWeatherDay = itemView.findViewById(R.id.layoutWeatherDay);
        }
    }

    public Integer getWeatherIcon(int code) {
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

    public interface onDaysSelectedInterface {
        void onDaysSelected(WeatherModel.Daily daily, int position);
    }
}
