package com.example.weatherapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.weatherapp.R;

public class CustomAdapter extends androidx.cursoradapter.widget.CursorAdapter {

    public CustomAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.location_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtLocation = (TextView) view.findViewById(R.id.txtLocation);
        String body = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        txtLocation.setText(body);
    }
}

