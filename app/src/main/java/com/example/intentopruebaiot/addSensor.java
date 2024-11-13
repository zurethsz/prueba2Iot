package com.example.intentopruebaiot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intentopruebaiot.model.Sensor;

public class addSensor extends AppCompatActivity {

    private EditText editTextNombreSensor;
    private EditText editTextDescripcionSensor;
    private Spinner spinnerTipoSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sensor);

        // Inicializar las vistas
        editTextNombreSensor = findViewById(R.id.editTextText2);
        editTextDescripcionSensor = findViewById(R.id.editTextText3);
        spinnerTipoSensor = findViewById(R.id.mySpinner);

        // Agregar opciones al Spinner
        String[] options = {"Humedad", "Temperatura"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoSensor.setAdapter(adapter);
    }

    // Método para validar y agregar el sensor
    public void agregarSensor(View view) {
        String nombre = editTextNombreSensor.getText().toString();
        String descripcion = editTextDescripcionSensor.getText().toString();
        String tipo = spinnerTipoSensor.getSelectedItem().toString();

        // Validaciones
        if (nombre.isEmpty() || nombre.length() < 5 || nombre.length() > 15) {
            Toast.makeText(this, "El nombre del sensor debe tener entre 5 y 15 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (descripcion.length() > 30) {
            Toast.makeText(this, "La descripción no puede exceder los 30 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear el nuevo sensor
        Sensor nuevoSensor = new Sensor(tipo, nombre, descripcion);

        // Crear el Intent para enviar el sensor a la actividad anterior
        Intent resultIntent = new Intent();
        resultIntent.putExtra("sensorNuevo", nuevoSensor);

        setResult(1, resultIntent);
        finish();
    }
}
