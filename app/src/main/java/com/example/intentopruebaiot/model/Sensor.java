package com.example.intentopruebaiot.model;

import com.example.intentopruebaiot.R; // Asegúrate de importar los recursos de la app

import java.io.Serializable;

public class Sensor implements Serializable {
    private static int idCounter = 1; // Contador para generar IDs únicos

    private int id;  // ID único para cada sensor
    private String tipo;
    private String nombre;
    private String descripcion;
    private int imagen;

    // Constructor
    public Sensor(String tipo, String nombre, String descripcion) {
        this.id = idCounter++;  // Asigna un ID único y lo incrementa para el siguiente sensor
        this.tipo = tipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = asignarImagen(tipo); // Asignar la imagen automáticamente
    }

    // Método para asignar la imagen según el tipo de sensor
    private int asignarImagen(String tipo) {
        switch (tipo.toLowerCase()) {
            case "humedad":
                return R.drawable.humiditysensor;
            case "temperatura":
                return R.drawable.temperaturesensor;
            // Agrega más tipos de sensores si es necesario
            default:
                return R.drawable.temperaturesensor;
        }
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
        this.imagen = asignarImagen(tipo); // Actualiza la imagen si cambia el tipo
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
