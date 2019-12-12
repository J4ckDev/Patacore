package edu.unicauca.patacore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.unicauca.patacore.data.utilidades.Utilidades;


public class ConexionSQLiteHelper extends SQLiteOpenHelper {



    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Crear las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_PRODUCTO);
        db.execSQL(Utilidades.CREAR_TABLA_PEDIDO);
    }

    // Valida versiones anteriores
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_PRODUCTO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_PEDIDO);
        onCreate(db);
    }
}
