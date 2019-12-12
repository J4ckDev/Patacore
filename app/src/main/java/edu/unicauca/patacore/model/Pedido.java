package edu.unicauca.patacore.model;

import java.util.ArrayList;

public class Pedido {
    private int mesa;
    private String fecha;
    private String hora;
    private int estado;
    private ArrayList<Producto> productos;



    public Pedido (){};


    public Pedido(int mesa, String fecha, String hora, int estado, ArrayList<Producto> productos) {
        this.mesa = mesa;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.productos = productos;
    }

    public int getMesa() {
        return this.mesa;
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

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
}
