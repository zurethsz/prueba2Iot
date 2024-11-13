package com.example.intentopruebaiot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intentopruebaiot.model.Invernadero;
import com.example.intentopruebaiot.model.Sensor;

public class DetailsLocation extends AppCompatActivity {

    ListView listView;
    TextView nombreUbicacionTextView;
    TextView descripcionUbicacionTextView;
    Button buttonAddSensor;
    Button backButton;
    Invernadero invernadero;
    SensorAdapter sensorAdapter;
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
            Sensor sensorSeleccionado = invernadero.getSensores().get(position);

            // Enviar el invernadero y el sensor a la pantalla de detalles del sensor
            Intent intentDetailsSensor = new Intent(DetailsLocation.this, DetailsSensor.class);
            intentDetailsSensor.putExtra("invernadero", invernadero);
            intentDetailsSensor.putExtra("sensor", sensorSeleccionado);
            startActivityForResult(intentDetailsSensor, position);  // Usamos la posición como requestCode

        });

        // Configurar el botón para agregar un nuevo sensor
        buttonAddSensor.setOnClickListener(v -> {
            Intent pantallaSensor = new Intent(DetailsLocation.this, addSensor.class);
            startActivityForResult(pantallaSensor, 0);  // Usamos 0 para indicar que es un nuevo sensor

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1 && data != null) {
            // Si es un sensor modificado
            if (data.hasExtra("sensorModificado")) {
                Sensor sensorModificado = (Sensor) data.getSerializableExtra("sensorModificado");

                // Actualizar el sensor modificado en la lista usando la posición pasada en requestCode
                if (invernadero.getSensores().size() > 0) {
                    invernadero.getSensores().set(resultCode, sensorModificado);  // Usamos el requestCode como índice
                    sensorAdapter.notifyDataSetChanged();
                }
            }

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
            if (data.hasExtra("invernaderoActualizadoSensor")) {
                Invernadero invernaderoActualizado = (Invernadero) data.getSerializableExtra("invernaderoActualizadoSensor");

                // Actualizar el invernadero con los sensores modificados
                invernadero.setSensores(invernaderoActualizado.getSensores());
                // Notificar al adaptador que se ha actualizado el invernadero
                sensorAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refrescar la lista de sensores cuando se regrese a esta actividad
        sensorAdapter.notifyDataSetChanged();
    }

    public void irAMainActivity(View view) {

        // Crear un Intent para enviar el invernadero actualizado
        Intent resultIntent = new Intent(DetailsLocation.this,MainActivity.class);
        resultIntent.putExtra("invernaderoActualizado", invernadero);  // Enviar solo el invernadero actualizado
        setResult(2, resultIntent);


        finish();
    }
}
