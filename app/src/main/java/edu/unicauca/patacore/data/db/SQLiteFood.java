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

import edu.unicauca.patacore.data.utilidades.Utilidades;
import edu.unicauca.patacore.model.Menu;
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
        //sqLiteDatabase.execSQL(BDMenu.DELETE_TABLA_MEN);
        sqLiteDatabase.execSQL(BDMenu.CREATE_TABLE_FOOD);


        //CREAR PEDIDO

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(BDMenu.DELETE_TABLA_MENU);


       /* String query;
        query = "DROP TABLE IF EXISTS profiles";
        database.execSQL(query);*/
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
        String sql = "INSERT INTO FOOD (name, price, image, description) VALUES (?, ?, ?,?)";
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

        String query = "SELECT * FROM  FOOD";
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


        //db.close();

        return menuArrayList;
    }

    /**Query only 1 record**/
    public List<Menu> menuList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + BDMenu.TABLE_MENU;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + BDMenu.TABLE_MENU + " ORDER BY "+ filter;
        }

        List<Menu> personLinkedList = new LinkedList<>();
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
                personLinkedList.add(menu);
            } while (cursor.moveToNext());
        }


        return personLinkedList;
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

       db.execSQL("UPDATE "+ BDMenu.TABLE_MENU+" SET name = '"+updatedmenu.getTxtNombre()+"', " +
               "price = '"+ updatedmenu.getTxtPrecio() +"',image ='"+ updatedmenu.getImg()+"'," +
               " description= '"+updatedmenu.getTxtDescription()+"' WHERE id_food= '" + foodId +"'");
// "+ updatedmenu.getImg()+"

        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();


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
