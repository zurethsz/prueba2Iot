package com.example.intentopruebaiot.model;

import com.example.intentopruebaiot.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Invernadero implements Serializable {
    private String nombre;
    private String descripcion;
    private List<Sensor> sensores;
    private static final int imagen = R.drawable.greenhouse;

    // Constructor
    public Invernadero(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sensores = new ArrayList<>();
    }

    // Método para agregar un sensor al invernadero
    public void agregarSensor(Sensor sensor) {
        sensores.add(sensor);
    }

    // Método para eliminar un sensor por su ID
    public void eliminarSensorPorId(int id) {
        for (Sensor sensor : sensores) {
            if (sensor.getId() == id) {
                sensores.remove(sensor);  // Eliminar el sensor de la lista
                break;
            }
        }
    }

    // Getters y Setters
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

    public List<Sensor> getSensores() {
        return sensores;
    }

    public void setSensores(List<Sensor> sensores) {
        this.sensores = sensores;
    }

    public static int getImagen() {
        return imagen;
    }
}
