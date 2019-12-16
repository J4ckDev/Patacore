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

    public SQLiteFood(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public void insertDataPedido(String nombre, String cantidad, String mesa, String fecha, String hora, String estado){

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO "+ BDMenu.TABLA_PEDIDO+" (nombre,cantidad, mesa, fecha, hora, estado) VALUES (?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, nombre);
        statement.bindString(2, cantidad);
        statement.bindString(3, mesa);
        statement.bindString(4, fecha);
        statement.bindString(5, hora);
        statement.bindString(6, estado);
        statement.executeInsert();

    }
    public ArrayList<Pedidos> buildPedidos() {
        ArrayList<Pedidos> pedidosrrayList = new ArrayList<>();
        String query = "SELECT * FROM "+BDMenu.TABLE_MENU;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Pedidos pedidos;

        if (cursor.moveToFirst()) {
            do {
                pedidos = new Pedidos();
                pedidos.setImgCard(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_IMAGE)));
                pedidos.setTxtNombre(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_NAME)));
                pedidos.setTxtPrecio(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_PRICE)));
                //menu.setTxtDescription(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_DESCRIPTION)));
                // menu.setImage(cursor.getBlob(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_IMAGE)));
                pedidosrrayList.add(pedidos);
            } while (cursor.moveToNext());
        }
        return pedidosrrayList;
    }
    public ArrayList<Pedidos> getPedido(int id) {
        ArrayList<Pedidos> pedidosrrayList = new ArrayList<>();
        //String query = "SELECT * FROM "+BDMenu.TABLE_MENU;
        String query = "SELECT  * FROM " + BDMenu.TABLE_MENU + " WHERE id_food=" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Pedidos pedidos;

        if (cursor.moveToFirst()) {
            do {
                pedidos = new Pedidos();
                pedidos.setImgCard(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_IMAGE)));
                pedidos.setTxtNombre(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_NAME)));
                pedidos.setTxtPrecio(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_PRICE)));
                //menu.setTxtDescription(cursor.getString(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_DESCRIPTION)));
                // menu.setImage(cursor.getBlob(cursor.getColumnIndex(BDMenu.COLUMN_FOOD_IMAGE)));
                pedidosrrayList.add(pedidos);
            } while (cursor.moveToNext());
        }
        return pedidosrrayList;


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
