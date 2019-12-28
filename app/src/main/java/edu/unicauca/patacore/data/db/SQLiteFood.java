package edu.unicauca.patacore.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.unicauca.patacore.data.ConexionSQLiteHelper;
import edu.unicauca.patacore.data.utilidades.Utilidades;
import edu.unicauca.patacore.model.Menu;
import edu.unicauca.patacore.model.Pedido;
import edu.unicauca.patacore.model.Pedidos;
import edu.unicauca.patacore.model.Producto;
import edu.unicauca.patacore.view.fragment.MenuFragment;

public class SQLiteFood extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "foodBD.sqlite";
    public static final int DATABASE_VERSION = 1 ;
    /*public SQLiteFood(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }*/
    Context context;
    public SQLiteFood(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //SQLiteDatabase database = getWritableDatabase();
        //sqLiteDatabase.execSQL(BDMenu.DELETE_TABLA_MENU);
        sqLiteDatabase.execSQL(BDMenu.CREATE_TABLA_MENU);
        sqLiteDatabase.execSQL(BDMenu.CREAR_TABLA_PEDIDO);
        //CREAR PEDIDO

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionAnterior, int versionNueva) {
        sqLiteDatabase.execSQL(BDMenu.DELETE_TABLA_MENU);
        sqLiteDatabase.execSQL(BDMenu.DELETE_TABLA_PEDIDO);

        onCreate(sqLiteDatabase);
    }

    /**create record**/
    public void saveNewMenuFood(Menu menu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BDMenu.COLUMN_FOOD_NAME, menu.getTxtNombre());
        values.put(BDMenu.COLUMN_FOOD_PRICE, menu.getTxtPrecio());
        //values.put(BDMenu.COLUMN_FOOD_IMAGE, menu.getImage());
        // insert
        db.insert(BDMenu.TABLE_MENU,null, values);
        db.close();
    }
    //public void insertData(String name, String price, byte[] imagePlato){
    public void insertData(String name, String price, String imagePlato, String description){

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO "+ BDMenu.TABLE_MENU+" (name, price, image, description) VALUES (?, ?, ?,?)";
        //String sql = "INSERT INTO FOOD VALUES (NULL, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindString(3, imagePlato);
        statement.bindString(4, description);

        //statement.bindBlob(3, imagePlato);
        statement.executeInsert();

    }

    //PARA CREAR LA TABLA FOOD ES LA QUE CREA LOS PLATOS
    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);

    }
    //CON ARRAYLIST

    public ArrayList<Menu> buildListas() {
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        String query = "SELECT *FROM " +BDMenu.TABLE_MENU;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Menu menu;
            if (cursor.moveToFirst()) {
                do {
                    menu = new Menu();
                    menu.setId(cursor.getInt(cursor.getColumnIndex(BDMenu.COLUMN_ID)));
                    menu.setTxtNombre(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_NAME)));
                    menu.setTxtPrecio(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_PRICE)));
                    menu.setImg(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_IMAGE)));
                    menu.setTxtDescription(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_DESCRIPTION)));
                    // menu.setImage(cursor.getBlob(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_IMAGE)));
                    menuArrayList.add(menu);
                } while (cursor.moveToNext());
            }


       // db.close();

        return menuArrayList;
    }



     public Menu getMenuFood(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + BDMenu.TABLE_MENU + " WHERE id_food="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Menu receivedMenu = new Menu();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedMenu.setTxtNombre(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_NAME)));
            receivedMenu.setTxtPrecio(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_PRICE)));
            receivedMenu.setImg(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_IMAGE)));
            receivedMenu.setTxtDescription(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_DESCRIPTION)));

        }
        return receivedMenu;
    }


    /**delete record**/
    public void deleteMenuRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL("DELETE FROM "+ BDMenu.TABLE_MENU +" WHERE id_food='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

    /**update record**/
   public void updateMenuFoodRecord(long foodId, Context context, Menu updatedmenu) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names

       db.execSQL("UPDATE "+ BDMenu.TABLE_MENU +" SET name = '"+updatedmenu.getTxtNombre()+"', " +
               "price = '"+ updatedmenu.getTxtPrecio() +"',image ='"+ updatedmenu.getImg()+"'," +
               " description= '"+updatedmenu.getTxtDescription()+"' WHERE id_food= '" + foodId +"'");
// "+ updatedmenu.getImg()+"

        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();


    }
    /**CRUD PEDIDO**/

    /**en listar pedido**/

    public void insertDataPedido(String nombre, int precio, String imagen, String description, String anotacion, int cantidad, int mesa, String fecha, String hora, int estado){


        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO "+ BDMenu.TABLA_PEDIDO+" (nombre, precio, imagen,description, anotacion, cantidad, mesa, fecha, hora, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, nombre);
        statement.bindString(2, String.valueOf(precio));
        statement.bindString(3, imagen);
        statement.bindString(4, description);
        statement.bindString(5, anotacion);
        statement.bindString(6, String.valueOf(cantidad));
        statement.bindString(7, String.valueOf(mesa));
        statement.bindString(8, fecha);
        statement.bindString(9, hora);
        statement.bindString(10, String.valueOf(estado));
        statement.executeInsert();

    }

    public void regPedido(int mesa, int estado, Menu menu) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BDMenu.CAMPO_MESA, mesa);
        values.put(BDMenu.CAMPO_FECHA, "");
        values.put(BDMenu.CAMPO_HORA, "");
        values.put(BDMenu.CAMPO_ESTADO, estado);
        values.put(BDMenu.CAMPO_NOM_PROD, menu.getTxtNombre());
        values.put(BDMenu.CAMPO_CANT_PRODUCTO, menu.getCantidad());
        values.put(BDMenu.CAMPO_PRECIO, menu.getTxtPrecio());
        values.put(BDMenu.CAMPO_ANOTACIONES, menu.getAnotacion());
        values.put(BDMenu.CAMPO_DESCRIPTION, menu.getTxtDescription());
        Long idResultante = db.insert(BDMenu.TABLA_PEDIDO, BDMenu.CAMPO_NOM_PROD, values);
    }


    public ArrayList<Pedidos> buildPedidos() {
        ArrayList<Pedidos> pedidosrrayList = new ArrayList<>();
        String query = "SELECT * FROM "+BDMenu.TABLA_PEDIDO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Pedidos pedidos;

        if (cursor.moveToFirst()) {
            do {
                pedidos = new Pedidos();
                pedidos.setNombre(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_NOM_PROD)));
                pedidos.setPrecio(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_PRECIO)));
                pedidos.setImagen(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_IMAGE)));
                pedidos.setAnotacion(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_ANOTACIONES)));
                pedidos.setCantidad(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_CANT_PRODUCTO)));
                pedidos.setMesa(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_MESA)));
                pedidos.setFecha(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_FECHA)));
                pedidos.setHora(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_HORA)));
                pedidos.setEstado(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_ESTADO)));

                pedidosrrayList.add(pedidos);
            } while (cursor.moveToNext());
        }
        return pedidosrrayList;

    }

    public ArrayList<Pedidos> buildPedidosPrueba() {
        ArrayList<Pedidos> pedidosrrayList = new ArrayList<>();
        String query = "SELECT * FROM "+BDMenu.TABLA_PEDIDO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Pedidos pedidos;

        if (cursor.moveToFirst()) {
            do {
                pedidos = new Pedidos();
                pedidos.setNombre(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_NOM_PROD)));
                pedidos.setPrecio(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_PRECIO)));
                pedidos.setImagen(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_IMAGE)));

                pedidosrrayList.add(pedidos);
            } while (cursor.moveToNext());
        }
        return pedidosrrayList;

    }


    public Pedidos getPedidosDetalle(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + BDMenu.TABLA_PEDIDO + " WHERE id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Pedidos pedidos = new Pedidos();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            pedidos.setNombre(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_NOM_PROD)));
            pedidos.setPrecio(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_PRECIO)));
            pedidos.setImagen(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_IMAGE)));
            pedidos.setDescription(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_DESCRIPTION)));
            pedidos.setAnotacion(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_ANOTACIONES)));
            pedidos.setCantidad(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_CANT_PRODUCTO)));
            pedidos.setMesa(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_MESA)));
            pedidos.setFecha(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_FECHA)));
            pedidos.setHora(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_HORA)));
            pedidos.setEstado(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_ESTADO)));

        }
        return pedidos;
    }

    public ArrayList<Pedidos> getPedido(long id) {
        ArrayList<Pedidos> pedidosrrayList = new ArrayList<>();
        //String query = "SELECT * FROM "+BDMenu.TABLE_MENU;
        String query = "SELECT  * FROM " + BDMenu.TABLA_PEDIDO + " WHERE id=" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Pedidos pedidos;

        if (cursor.moveToFirst()) {
            do {
                pedidos = new Pedidos();
                pedidos.setNombre(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_NOM_PROD)));
                pedidos.setPrecio(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_PRECIO)));
                pedidos.setImagen(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_IMAGE)));
                pedidos.setDescription(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_DESCRIPTION)));
                pedidos.setAnotacion(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_ANOTACIONES)));
                pedidos.setCantidad(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_CANT_PRODUCTO)));
                pedidos.setMesa(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_MESA)));
                pedidos.setFecha(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_FECHA)));
                pedidos.setHora(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_HORA)));
                pedidos.setEstado(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_ESTADO)));
                pedidosrrayList.add(pedidos);
            } while (cursor.moveToNext());
        }
        return pedidosrrayList;


    }

    public ArrayList<Pedidos> getPedidoEstado(int mesa) {
        ArrayList<Pedidos> pedidosrrayList = new ArrayList<>();
        //String query = "SELECT * FROM "+BDMenu.TABLE_MENU;
        String query = "SELECT  * FROM " + BDMenu.TABLA_PEDIDO + " WHERE mesa=" + mesa + " and estado = 2 ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Pedidos pedidos;

        if (cursor.moveToFirst()) {
            do {
                pedidos = new Pedidos();
                pedidos.setNombre(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_NOM_PROD)));
                pedidos.setPrecio(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_PRECIO)));
                pedidos.setImagen(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_IMAGE)));
                pedidos.setDescription(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_DESCRIPTION)));
                pedidos.setAnotacion(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_ANOTACIONES)));
                pedidos.setCantidad(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_CANT_PRODUCTO)));
                pedidos.setMesa(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_MESA)));
                pedidos.setFecha(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_FECHA)));
                pedidos.setHora(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_HORA)));
                pedidos.setEstado(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_ESTADO)));
                pedidosrrayList.add(pedidos);
            } while (cursor.moveToNext());
        }
        return pedidosrrayList;
    }

    public ArrayList<Pedidos> consPedido (int mesa, int estado) {
        ArrayList<Pedidos> pedidosrrayList = new ArrayList<>();
        //String query = "SELECT * FROM "+BDMenu.TABLE_MENU;
        String query = "SELECT  * FROM " + BDMenu.TABLA_PEDIDO + " WHERE mesa=" + mesa + " and estado ="+ estado;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Pedidos pedidos;

        if (cursor.moveToFirst()) {
            do {
                pedidos = new Pedidos();
                pedidos.setNombre(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_NOM_PROD)));
                pedidos.setPrecio(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_PRECIO)));
                pedidos.setImagen(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_IMAGE)));
                pedidos.setDescription(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_DESCRIPTION)));
                pedidos.setAnotacion(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_ANOTACIONES)));
                pedidos.setCantidad(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_CANT_PRODUCTO)));
                pedidos.setMesa(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_MESA)));
                pedidos.setFecha(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_FECHA)));
                pedidos.setHora(cursor.getString(cursor.getColumnIndex(BDMenu.CAMPO_HORA)));
                pedidos.setEstado(cursor.getInt(cursor.getColumnIndex(BDMenu.CAMPO_ESTADO)));
                pedidosrrayList.add(pedidos);
            } while (cursor.moveToNext());
        }
        return pedidosrrayList;
    }

    public boolean existe(int idPedido, int estado, String nombre) {
        ArrayList<Pedidos> pedidos = consPedido(idPedido, estado);
        ArrayList<String> codsPedido = new ArrayList<String>();


        for (int i = 0; i < pedidos.size(); i++) {
            codsPedido.add(pedidos.get(i).getNombre());
        }
        if (buscarProductoEnPedido(codsPedido, nombre) != -1) {
            return true;
        } else {
            return false;
        }
    }

    public void actualizarPedido(int mesa, int estado, Menu pedido) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] parametros = {String.valueOf(mesa), "1", pedido.getTxtNombre()};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NOM_PROD, pedido.getTxtNombre());
        values.put(Utilidades.CAMPO_CANT_PRODUCTO, pedido.getCantidad());
        values.put(Utilidades.CAMPO_ESTADO, String.valueOf(estado));
        db.update(BDMenu.TABLA_PEDIDO, values, BDMenu.CAMPO_MESA + "=? AND " + BDMenu.CAMPO_ESTADO + "=? AND " + BDMenu.CAMPO_NOM_PROD + "=?", parametros);

        db.close();
    }

    public void eliminarPedido(int mesa, int estado, Menu producto) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] parametros = {String.valueOf(mesa), String.valueOf(estado), producto.getTxtNombre()};
        db.delete(BDMenu.TABLA_PEDIDO, BDMenu.CAMPO_MESA + "=? AND " + BDMenu.CAMPO_ESTADO + "=? AND " + BDMenu.CAMPO_NOM_PROD + "=?", parametros);
        db.close();
    }

    public void eliminarTotalPedido(int mesa, int estado) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] parametros = {String.valueOf(mesa), String.valueOf(estado)};

        db.delete(BDMenu.TABLA_PEDIDO, BDMenu.CAMPO_MESA + "=? AND " + BDMenu.CAMPO_ESTADO + "=?", parametros);

        db.close();
    }

    private int buscarProductoEnPedido(ArrayList<String> listaCodigos, String codigo) {
        int size = listaCodigos.size();
        int posicion = -1;
        int i = 0;
        for (i = 0; i < size; i++) {
            if (listaCodigos.get(i).equals(codigo)) {
                posicion = i;
                break;
            }
        }
        return posicion;
    }

    public String cambiosPedido(int idPedido) {
        ArrayList<Pedidos> newPedido = consPedido(idPedido, 1);

        String cambios = "";
        ArrayList<Pedidos> pedido = consPedido(idPedido, 2);

        ArrayList<String> codsPedido = new ArrayList<String>();
        for (int i = 0; i < pedido.size(); i++) {
            codsPedido.add(pedido.get(i).getNombre());
        }
        ArrayList<String> codsNewPedido = new ArrayList<String>();
        for (int i = 0; i < newPedido.size(); i++) {
            codsNewPedido.add(newPedido.get(i).getNombre());
        }
        int tCP = codsNewPedido.size();
        int i = 0;
        for (i = 0; i < tCP; i++) {
            int pos = buscarProductoEnPedido(codsPedido, codsNewPedido.get(i));
            if (pos != -1) {
                // Modificar el producto
                int diferencia = newPedido.get(i).getCantidad() - pedido.get(pos).getCantidad();
                if (diferencia >= 1) {
                    cambios += "Añadir " + String.valueOf(diferencia) + " " + codsPedido.get(pos) + "\n";
                } else if (diferencia < 0) {
                    diferencia = -diferencia;
                    cambios += "Quitar " + String.valueOf(diferencia) + " " + codsPedido.get(pos) + "\n";
                }
            } else {
                cambios += "Añadir " + String.valueOf(newPedido.get(i).getCantidad()) + " " + codsNewPedido.get(i) + "\n";
            }
        }

        // Recorrer pedidos anteriores
        tCP = codsPedido.size();
        for (i = 0; i < tCP; i++) {
            int pos = buscarProductoEnPedido(codsNewPedido, codsPedido.get(i));
            if (pos != -1) {
                // Modificar el producto
                //int diferencia = newPedido.getProductos().get(i).getCantidad() - pedido.getProductos().get(pos).getCantidad();
            } else {
                if (pedido.get(i).getCantidad() > 0) {
                    cambios += "Quitar " + String.valueOf(pedido.get(i).getCantidad()) + " " + codsPedido.get(i) + "\n";
                }
            }
        }


        return cambios;
    }



}

/*


    //PARA CREAR LA TABLA FOOD ES LA QUE CREA LOS PLATOS
    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);

    }
    public void insertData(String name, String price, byte[] imagePlato){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO FOOD VALUES (NULL, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, imagePlato);
        statement.executeInsert();

    }
    public Cursor getData(String sql){
        SQLiteDatabase database= getReadableDatabase();
        return database.rawQuery(sql, null);

    }*/
