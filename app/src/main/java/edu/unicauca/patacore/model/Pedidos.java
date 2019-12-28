package edu.unicauca.patacore.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Pedidos {
    private int id;
    private String nombre;
    private int precio;
    private String imagen;
    private String anotacion;
    private String description;
    private int cantidad;
    private int mesa;
    private String fecha;
    private String hora;
    private int estado;

    public Pedidos(String nombre, int precio, String imagen, String description, String anotacion, int cantidad, int mesa, String fecha, String hora, int estado) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.description = description;
        this.anotacion = anotacion;
        this.cantidad = cantidad;
        this.mesa = mesa;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public Pedidos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getAnotacion() {
        return anotacion;
    }

    public void setAnotacion(String anotacion) {
        this.anotacion = anotacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}


