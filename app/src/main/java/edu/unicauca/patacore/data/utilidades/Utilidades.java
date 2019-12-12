package edu.unicauca.patacore.data.utilidades;

/**
 * Created by CHENAO on 7/05/2017.
 */

public class Utilidades {

    //Constantes campos tabla usuario
    public static final String TABLA_PRODUCTO ="producto";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_DESCRIPCION ="descripcion";
    public static final String CAMPO_CANTIDAD ="cantidad";      //quitar
    public static final String CAMPO_IMAGEN ="imagen";
    public static final String CAMPO_SELECTED ="selected";   //quitar


    public static final String CREAR_TABLA_PRODUCTO ="CREATE TABLE " +
            ""+ TABLA_PRODUCTO +" ("+CAMPO_ID+
            " INTEGER, "+CAMPO_NOMBRE+" TEXT,"+ CAMPO_DESCRIPCION +" TEXT,"+CAMPO_CANTIDAD
            + " INTEGER,"+CAMPO_IMAGEN + " INTEGER,"+CAMPO_SELECTED + " INTEGER)";


    //Constantes campos tabla pedido
    public static final String TABLA_PEDIDO ="pedido";
    public static final String CAMPO_FECHA ="fecha";
    public static final String CAMPO_HORA ="hora";
    public static final String CAMPO_ESTADO ="estado";
    public static final String CAMPO_NOM_PROD = "nombre";
    public static final String CAMPO_CANT_PRODUCTO ="cantidad";


    public static final String CREAR_TABLA_PEDIDO ="CREATE TABLE " +
            ""+ TABLA_PEDIDO +" ("+CAMPO_ID+
            " INTEGER, "+CAMPO_FECHA+" TEXT,"+ CAMPO_HORA +" TEXT,"+ CAMPO_ESTADO +" INTEGER,"
            +CAMPO_NOM_PROD+" TEXT,"+CAMPO_CANT_PRODUCTO + " INTEGER)";

}
