package com.example.intentopruebaiot;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intentopruebaiot.model.Invernadero;
import com.example.intentopruebaiot.model.Sensor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DetailsLocation extends AppCompatActivity {

    ListView listView;
    TextView nombreUbicacionTextView;
    TextView descripcionUbicacionTextView;
    Button buttonAddSensor;
    Button backButton;
    Invernadero invernadero;
    SensorAdapter sensorAdapter;
    EditText searchSensorEditText; // Referencia al EditText de búsqueda

    Invernadero invernaderoActualizadoSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_location);

        // Inicializar vistas
        listView = findViewById(R.id.listViewSensor);
        nombreUbicacionTextView = findViewById(R.id.textViewNombreInvernadero);
        descripcionUbicacionTextView = findViewById(R.id.textViewDescriptionUbicacion);
        buttonAddSensor = findViewById(R.id.buttonAddSensor);
        searchSensorEditText = findViewById(R.id.searchSensorEditText); // Inicializar EditText de búsqueda

        // Obtener el invernadero desde el Intent
        Intent intent = getIntent();
        invernadero = (Invernadero) intent.getSerializableExtra("invernadero");
        invernaderoActualizadoSensor  = (Invernadero) intent.getSerializableExtra("invernaderoActualizadoSensor");

        if (invernaderoActualizadoSensor != null) {
            invernadero = invernaderoActualizadoSensor;
        }

        // Mostrar los detalles del invernadero en los TextViews
        nombreUbicacionTextView.setText(invernadero.getNombre());
        descripcionUbicacionTextView.setText(invernadero.getDescripcion());

        // Crear el adaptador para los sensores del invernadero
        sensorAdapter = new SensorAdapter(this, invernadero.getSensores());
        listView.setAdapter(sensorAdapter);

        // Configurar el clic en un item de la lista de sensores
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Sensor sensorSeleccionado = (Sensor) sensorAdapter.getItem(position); // Obtener el sensor desde el adaptador

            // Enviar el invernadero y el sensor a la pantalla de detalles del sensor
            Intent intentDetailsSensor = new Intent(DetailsLocation.this, DetailsSensor.class);
            intentDetailsSensor.putExtra("invernadero", invernadero);
            intentDetailsSensor.putExtra("sensor", sensorSeleccionado);
            System.out.println("DetailsSensor enviando informacion a detailsSensor : " + sensorSeleccionado);
            startActivityForResult(intentDetailsSensor, position);  // Usamos la posición como requestCode
        });

        // Configurar el botón para agregar un nuevo sensor
        buttonAddSensor.setOnClickListener(v -> {
            Intent pantallaSensor = new Intent(DetailsLocation.this, addSensor.class);
            startActivityForResult(pantallaSensor, 0);  // Usamos 0 para indicar que es un nuevo sensor
        });

        // Configurar el TextWatcher para filtrar la lista de sensores
        searchSensorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                filterSensors(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void filterSensors(String query) {
        ArrayList<Sensor> filteredList = new ArrayList<>();

        // Filtrar los sensores por nombre
        for (Sensor sensor : invernadero.getSensores()) {
            if (sensor.getNombre().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(sensor);
            }
        }

        // Ordenar la lista filtrada por nombre
        Collections.sort(filteredList, new Comparator<Sensor>() {
            @Override
            public int compare(Sensor sensor1, Sensor sensor2) {
                return sensor1.getNombre().compareToIgnoreCase(sensor2.getNombre());
            }
        });

        // Actualizar el adaptador con la lista filtrada y ordenada
        sensorAdapter = new SensorAdapter(this, filteredList);
        listView.setAdapter(sensorAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1 && data != null) {

            // Si es un nuevo sensor
            if (data.hasExtra("sensorNuevo")) {
                Sensor nuevoSensor = (Sensor) data.getSerializableExtra("sensorNuevo");

                // Agregar el nuevo sensor a la lista de sensores del invernadero
                invernadero.agregarSensor(nuevoSensor);  // Método que agrega el nuevo sensor
                System.out.println("nuevo sensor agregado");

                // Notificar al adaptador que se ha agregado un nuevo sensor
                sensorAdapter.notifyDataSetChanged();
            }

            // Si es un sensor eliminado
            if (data.hasExtra("sensorIdEliminado")) {
                int sensorIdEliminado = data.getIntExtra("sensorIdEliminado", -1);

                if (sensorIdEliminado != -1) {
                    // Eliminar el sensor de la lista de sensores usando el ID
                    invernadero.eliminarSensorPorId(sensorIdEliminado);  // Método que elimina el sensor por ID
                    System.out.println("sensor eliminado");

                    // Notificar al adaptador que se ha eliminado un sensor
                    sensorAdapter.notifyDataSetChanged();
                }
            }

            // Si se ha recibido un invernadero actualizado (del regreso de DetailsSensor)
            if (data.hasExtra("sensorModificado")) {
                Sensor sensorModificado = (Sensor) data.getSerializableExtra("sensorModificado");
                System.out.println("entra a la modificacion con : " + invernadero.getSensores());

                if (sensorModificado == null) return;

                int index = -1;
                for (int i = 0; i < invernadero.getSensores().size(); i++) {
                    if (invernadero.getSensores().get(i).getId() == sensorModificado.getId()) {
                        index = i;
                        break;
                    }
                }

                if (index != -1) {
                    invernadero.getSensores().set(index, sensorModificado);
                }

                System.out.println("termina el ciclo con: " + invernadero.getSensores());

                // Notificar al adaptador que se ha actualizado el invernadero
                sensorAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void irAMainActivity(View view) {

        // Crear un Intent para enviar el invernadero actualizado
        Intent resultIntent = new Intent(DetailsLocation.this,MainActivity.class);
        resultIntent.putExtra("invernaderoActualizado", invernadero);  // Enviar solo el invernadero actualizado
        setResult(2, resultIntent);


        finish();
    }
}
