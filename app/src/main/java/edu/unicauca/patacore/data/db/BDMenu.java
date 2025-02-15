package edu.unicauca.patacore.data.db;

public class BDMenu {

    public static final String TABLE_MENU = "FOOD";
    public static final String COLUMN_ID = "id_food";
    public static final String COLUMN_FOOD_NAME = "name";
    public static final String COLUMN_FOOD_PRICE = "price";
    public static final String COLUMN_FOOD_IMAGE = "image";
    public static final String COLUMN_FOOD_DESCRIPTION = "description";


    public static final String CREATE_TABLA_MENU="CREATE TABLE IF NOT EXISTS "+ TABLE_MENU +
            "("+COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_FOOD_NAME +" VARCHAR,"+
            COLUMN_FOOD_PRICE +" VARCHAR,"+
            COLUMN_FOOD_IMAGE +" VARCHAR," +
            COLUMN_FOOD_DESCRIPTION +" VARCHAR)";

    public static final String CREATE_TABLE_FOOD= ("CREATE TABLE IF NOT EXISTS "+TABLE_MENU+
            "(id_food INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image VARCHAR, description VARCHAR)");

    public static final String DELETE_TABLA_MENU="DROP TABLE IF EXISTS "+ TABLE_MENU;
    public static final String DROP="DROP TABLE IF EXISTS "+ TABLE_MENU;

    //PEDIDOS

    //Constantes campos tabla pedido
    public static final String TABLA_PEDIDO ="pedido";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOM_PROD = "nombre";
    public static final String CAMPO_PRECIO = "precio";
    public static final String CAMPO_IMAGE = "imagen";
    public static final String CAMPO_DESCRIPTION = "description";
    public static final String CAMPO_ANOTACIONES = "anotacion";
    public static final String CAMPO_CANT_PRODUCTO ="cantidad";
    public static final String CAMPO_MESA ="mesa";
    public static final String CAMPO_FECHA ="fecha";
    public static final String CAMPO_HORA ="hora";
    public static final String CAMPO_ESTADO ="estado";


    public static final String CREAR_TABLA_PEDIDO ="CREATE TABLE IF NOT EXISTS " + TABLA_PEDIDO +
            " ("+CAMPO_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_NOM_PROD+" TEXT, "+
            CAMPO_PRECIO+" INTEGER, "+ CAMPO_IMAGE+" TEXT, " +  CAMPO_DESCRIPTION+" TEXT, " +  CAMPO_ANOTACIONES+" TEXT, "
            +CAMPO_CANT_PRODUCTO + " INTEGER, "+ CAMPO_MESA +  " INTEGER, "
            +CAMPO_FECHA+" TEXT, "+ CAMPO_HORA +" TEXT, "+ CAMPO_ESTADO +" INTEGER)";

    public static final String DELETE_TABLA_PEDIDO="DROP TABLE IF EXISTS "+ TABLA_PEDIDO;
};





