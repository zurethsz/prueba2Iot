package com.example.intentopruebaiot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intentopruebaiot.model.Invernadero;
import com.example.intentopruebaiot.model.Sensor;

public class DetailsSensor extends AppCompatActivity {

    private Invernadero invernadero;  // Variable para el invernadero
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_sensor);

        // Obtener el sensor y el invernadero desde el Intent de la actividad anterior
        invernadero = (Invernadero) getIntent().getSerializableExtra("invernadero");
        sensor = (Sensor) getIntent().getSerializableExtra("sensor");

        System.out.println(" Details Sensor : " +sensor.getNombre() +  ", descripcion: " + sensor.getDescripcion() );

        // Inicializar las vistas de texto
        TextView nombreSensorTextView = findViewById(R.id.tvSensorName);
        TextView descripcionSensorTextView = findViewById(R.id.tvSensorDescription);

        // Establecer los valores del sensor
        nombreSensorTextView.setText(sensor.getNombre());
        descripcionSensorTextView.setText(sensor.getDescripcion());

        // Configurar el botón de modificar
        Button buttonNavigate = findViewById(R.id.buttonModify);
        buttonNavigate.setOnClickListener(v -> {
            Intent modificarSensor = new Intent(DetailsSensor.this, ModifySensor.class);
            modificarSensor.putExtra("sensor", sensor);
            modificarSensor.putExtra("invernadero", invernadero);
            startActivityForResult(modificarSensor,1);
        });

        // Configurar el botón de eliminar
        Button buttonEliminar = findViewById(R.id.button3);
        buttonEliminar.setOnClickListener(v -> eliminarSensor());
        System.out.println("detailsSensor : " + invernadero.getSensores());
    }

    // Método para eliminar el sensor
    private void eliminarSensor() {
        // Mostrar un mensaje de confirmación
        Toast.makeText(this, "Sensor eliminado", Toast.LENGTH_SHORT).show();

        // Crear el Intent para enviar el ID del sensor eliminado
        Intent resultIntent = new Intent();
        resultIntent.putExtra("sensorIdEliminado", sensor.getId());  // Enviar el ID del sensor

        // Establecer el resultado como OK y agregar el Intent
        setResult(1, resultIntent);

        // Finalizar la actividad y regresar a DetailsLocation
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("entrando Sqlito");
        System.out.println(data);
        if (resultCode == 1 && data != null) {
            System.out.println("entrando Sqlito 2 la venganza");
            invernadero = (Invernadero) data.getSerializableExtra("invernadero");
            sensor = (Sensor) data.getSerializableExtra("sensor");
            TextView nombreSensorTextView = findViewById(R.id.tvSensorName);
            TextView descripcionSensorTextView = findViewById(R.id.tvSensorDescription);

            // Establecer los valores del sensor
            nombreSensorTextView.setText(sensor.getNombre());
            descripcionSensorTextView.setText(sensor.getDescripcion());
        }

    }

    public void irADetailsLocation(View view) {

        Intent pantalla = new Intent(DetailsSensor.this, DetailsLocation.class);
        pantalla.putExtra("invernaderoActualizadoSensor", invernadero);
        System.out.println("detailsSensor : " +invernadero.getSensores());
        startActivityForResult(pantalla, 1); // Usamos startActivityForResult para recibir el invernadero creado
        finish();
    }
}
