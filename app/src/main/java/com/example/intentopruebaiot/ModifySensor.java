package com.example.intentopruebaiot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intentopruebaiot.model.Invernadero;
import com.example.intentopruebaiot.model.Sensor;
import android.widget.ArrayAdapter;

import android.widget.Spinner;

public class ModifySensor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_sensor);

        Invernadero invernadero = (Invernadero) getIntent().getSerializableExtra("invernadero");

        // Obtener el objeto Sensor desde el Intent
        Sensor sensor = (Sensor) getIntent().getSerializableExtra("sensor");

        // Inicializar los campos EditText
        EditText editTextSensorName = findViewById(R.id.editTextSensorName);
        EditText editTextSensorDescription = findViewById(R.id.editTextSensorDescripcion);

        // Rellenar los campos con los datos del sensor recibido
        editTextSensorName.setText(sensor.getNombre());
        editTextSensorDescription.setText(sensor.getDescripcion());

        // Configurar el Spinner con los tipos de sensores
        Spinner mySpinner = findViewById(R.id.mySpinner);

        // Crear un ArrayAdapter usando el array de sensores de strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sensor_types, android.R.layout.simple_spinner_item);

        // Especificar el layout que se usará cuando el Spinner muestre los elementos
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Aplicar el adaptador al Spinner
        mySpinner.setAdapter(adapter);

        // Configurar el botón para modificar el sensor
        Button buttonModify = findViewById(R.id.buttonModify);
        buttonModify.setOnClickListener(v -> {
            // Obtener el nuevo nombre y descripción del sensor
            String newSensorName = editTextSensorName.getText().toString();
            String newSensorDescription = editTextSensorDescription.getText().toString();

            // Obtener el tipo de sensor seleccionado del Spinner
            String selectedSensorType = mySpinner.getSelectedItem().toString();

            // Validaciones para los campos
            if (newSensorName.isEmpty()) {
                Toast.makeText(ModifySensor.this, "Por favor ingrese un nombre para el sensor", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newSensorDescription.length() > 30) {
                Toast.makeText(ModifySensor.this, "La descripción no puede exceder los 30 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }

            // Actualizar el sensor con los nuevos datos
            sensor.setNombre(newSensorName);
            sensor.setDescripcion(newSensorDescription);
            sensor.setTipo(selectedSensorType);

            // Buscar el sensor en la lista del invernadero y actualizarlo
            for (int i = 0; i < invernadero.getSensores().size(); i++) {
                if (invernadero.getSensores().get(i).getId() == sensor.getId()) {
                    invernadero.getSensores().set(i, sensor);  // Reemplazar el sensor viejo con el nuevo
                    break;
                }
            }

            // Pasar el objeto Invernadero actualizado al siguiente Intent
            Intent intent = new Intent();
            intent.putExtra("invernadero", invernadero); // Pasar el invernadero actualizado
            intent.putExtra("sensor", sensor); // Pasar el sensor actualizado
            setResult(1, intent);
            finish();
        });
    }
}
