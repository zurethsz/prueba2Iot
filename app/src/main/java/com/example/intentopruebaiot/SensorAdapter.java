package com.example.intentopruebaiot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.intentopruebaiot.model.Sensor;

import java.util.List;

public class SensorAdapter extends BaseAdapter {

    private Context context;
    private List<Sensor> sensors;

    public SensorAdapter(Context context, List<Sensor> sensors) {
        this.context = context;
        this.sensors = sensors;
    }

    @Override
    public int getCount() {
        return sensors.size();
    }

    @Override
    public Object getItem(int position) {
        return sensors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Si convertView es null, inflamos la vista
        View view = (convertView == null) ? inflater.inflate(R.layout.activity_custom_list_view, parent, false) : convertView;

        Sensor sensor = sensors.get(position);

        TextView nombreText = view.findViewById(R.id.textViewNombreSensor);
        ImageView imagenView = view.findViewById(R.id.imageViewSensor);

        nombreText.setText(sensor.getNombre());
        imagenView.setImageResource(sensor.getImagen());

        return view;
    }
}