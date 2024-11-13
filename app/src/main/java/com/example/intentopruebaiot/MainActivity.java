package com.example.intentopruebaiot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intentopruebaiot.model.Invernadero;
import com.example.intentopruebaiot.model.Sensor;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Invernadero> listGreenHouse = new ArrayList<>();
    ListView listView;
    CustomBaseAdaptr customBaseAdaptr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonNavigate = findViewById(R.id.button);

        listGreenHouse.add(new Invernadero("Invernadero 1",  "Descripción 1"));

        listView = findViewById(R.id.listViewLocations);
        customBaseAdaptr = new CustomBaseAdaptr(this, listGreenHouse);
        listView.setAdapter(customBaseAdaptr);



        // Evento al hacer clic en un invernadero
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el invernadero seleccionado
                Invernadero invernaderoSeleccionado = listGreenHouse.get(position);

                // Pasar el objeto Invernadero a la siguiente actividad
                Intent pantalla = new Intent(MainActivity.this, DetailsLocation.class);
                pantalla.putExtra("invernadero", invernaderoSeleccionado); // Pasar el objeto completo
                //startActivity(pantalla);
                startActivityForResult(pantalla, 1);
            }
        });

        // Evento al hacer clic en el botón para agregar un nuevo invernadero
        buttonNavigate.setOnClickListener(v -> {


            // Navegar a la actividad para agregar un nuevo invernadero
            Intent pantalla = new Intent(MainActivity.this, agregarUbicacion.class);
            startActivityForResult(pantalla, 1); // Usamos startActivityForResult para recibir el invernadero creado

        });
    }

    // Método para recibir el invernadero creado desde la actividad agregarUbicacion
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            if (resultCode == 1) {
                Invernadero nuevoInvernadero = (Invernadero) data.getSerializableExtra("nuevoInvernadero");
                System.out.println("console log ");
                // Agregar el nuevo invernadero a la lista
                listGreenHouse.add(nuevoInvernadero);

                // Notificar al adaptador que la lista ha cambiado
                customBaseAdaptr.notifyDataSetChanged();

                // Mostrar un mensaje de éxito
                Toast.makeText(MainActivity.this, "Invernadero agregado correctamente", Toast.LENGTH_SHORT).show();
            } else if (resultCode == 2) {


                for (Invernadero invernadero : listGreenHouse) {
                    Invernadero invernaderoActualizado = (Invernadero) data.getSerializableExtra("invernaderoActualizado");
                    if (invernadero.getNombre().equals(invernaderoActualizado.getNombre())) {
                        System.out.println(invernadero.getNombre());
                        System.out.println(invernaderoActualizado.getSensores());
                        invernadero.setSensores(invernaderoActualizado.getSensores());
                        customBaseAdaptr.notifyDataSetChanged();
                        listView.setAdapter(customBaseAdaptr);
                        Toast.makeText(MainActivity.this, "Invernadero actualizado correctamente", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }





        }


    }
}

