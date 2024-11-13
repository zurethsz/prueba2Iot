package com.example.intentopruebaiot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intentopruebaiot.model.Invernadero;

public class agregarUbicacion extends AppCompatActivity {

    private EditText nombreUbicacionEditText;
    private EditText descripcionUbicacionEditText;
    private Button guardarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_ubicacion);

        // Referencias a los campos de texto y al botón
        nombreUbicacionEditText = findViewById(R.id.editTextName);
        descripcionUbicacionEditText = findViewById(R.id.editTextDescription);
        guardarButton = findViewById(R.id.buttonGuardarUbicacion);

        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de texto
                String nombre = nombreUbicacionEditText.getText().toString();
                String descripcion = descripcionUbicacionEditText.getText().toString();

                // Validar el nombre (obligatorio y entre 5 y 15 caracteres)
                if (nombre.isEmpty()) {
                    Toast.makeText(agregarUbicacion.this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (nombre.length() < 5 || nombre.length() > 15) {
                    Toast.makeText(agregarUbicacion.this, "El nombre debe tener entre 5 y 15 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar la descripción (opcional y no más de 30 caracteres)
                if (descripcion.length() > 30) {
                    Toast.makeText(agregarUbicacion.this, "La descripción no debe exceder los 30 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear el nuevo objeto Invernadero con los datos ingresados
                Invernadero nuevoInvernadero = new Invernadero(nombre, descripcion);

                // Devolver el invernadero creado como resultado
                Intent resultIntent = new Intent();
                resultIntent.putExtra("nuevoInvernadero", nuevoInvernadero);
                setResult(1, resultIntent);

                // Finalizar la actividad
                finish();
            }
        });
    }
}
