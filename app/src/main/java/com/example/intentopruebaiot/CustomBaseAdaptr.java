package com.example.intentopruebaiot;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.intentopruebaiot.model.Invernadero;

import java.util.List;

public class CustomBaseAdaptr extends BaseAdapter {

    private Activity context;
    private List<Invernadero> invernaderos;

    public CustomBaseAdaptr(Activity context, List<Invernadero> invernaderos) {
        this.context = context;
        this.invernaderos = invernaderos;
    }

    @Override
    public int getCount() {
        return invernaderos.size();
    }

    @Override
    public Object getItem(int position) {
        return invernaderos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        // Si convertView es null, inflamos la vista
        View view = (convertView == null) ? inflater.inflate(R.layout.activity_custom_list_view, parent, false) : convertView;

        Invernadero invernadero = invernaderos.get(position);
        TextView nombreText = view.findViewById(R.id.textViewNombreSensor);
        ImageView imagenView = view.findViewById(R.id.imageViewSensor);

        if(invernadero != null){

            nombreText.setText(invernadero.getNombre());
            imagenView.setImageResource(invernadero.getImagen());

        }
        return view;
    }
}
