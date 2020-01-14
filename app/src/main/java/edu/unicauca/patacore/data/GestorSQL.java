package edu.unicauca.patacore.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;
import edu.unicauca.patacore.data.utilidades.Utilidades;
import edu.unicauca.patacore.model.Pedido;
import edu.unicauca.patacore.model.Producto;

public class GestorSQL {
    private Context context;
    private Gson gson;
    private ArrayList<Producto> listaProductos;
    private ArrayList<Pedido> listaPedidos;
    ConexionSQLiteHelper conn;

    public GestorSQL(Context context) {
        this.context = context;
        conn = new ConexionSQLiteHelper(context, "pedidos", null, 1);
        gson = new Gson();
    }


    public void regPedido(int mesa, String fecha, String hora, String nombre, int estado, int cantidad) {
        //ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context, "pedido", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ID, mesa);
        values.put(Utilidades.CAMPO_FECHA, fecha);
        values.put(Utilidades.CAMPO_HORA, hora);
        values.put(Utilidades.CAMPO_ESTADO, estado);
        values.put(Utilidades.CAMPO_NOM_PROD, nombre);
        values.put(Utilidades.CAMPO_CANT_PRODUCTO, cantidad);

        Long idResultante = db.insert(Utilidades.TABLA_PEDIDO, Utilidades.CAMPO_NOM_PROD, values);

        db.close();
    }

    public Pedido consPedido(int mesa, int estado) {

        SQLiteDatabase db = conn.getReadableDatabase();
        // + " WHERE "+Utilidades.CAMPO_ID+"="+String.valueOf(mesa) +" AND "+Utilidades.CAMPO_ESTADO+" =1"
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PEDIDO + " WHERE " + Utilidades.CAMPO_ID + "=" + String.valueOf(mesa) + " AND " + Utilidades.CAMPO_ESTADO + " =" + String.valueOf(estado), null);
        listaProductos = new ArrayList<Producto>();
        Pedido pedido = new Pedido();

        while (cursor.moveToNext()) {
            pedido.setMesa(cursor.getInt(0));
            pedido.setFecha(cursor.getString(1));
            pedido.setHora(cursor.getString(2));
            pedido.setEstado(cursor.getInt(3));
            Producto producto = new Producto();
            producto.setNombre(cursor.getString(4));
            producto.setCantidad(cursor.getInt(5));
            listaProductos.add(producto);
        }
        pedido.setProductos(listaProductos);
        return pedido;
    }

    public boolean existe(int idPedido, int estado, String nombre) {
        Pedido pedido = consPedido(idPedido, estado);
        ArrayList<String> codsPedido = new ArrayList<String>();


        for (int i = 0; i < pedido.getProductos().size(); i++) {
            codsPedido.add(pedido.getProductos().get(i).getNombre());
        }
        Toast.makeText(context, "Prods. Pedido: " + codsPedido.size(), Toast.LENGTH_SHORT).show();
        if (buscarProductoEnPedido(codsPedido, nombre) != -1) {
            return true;
        } else {
            return false;
        }
    }


    public void actualizarPedido(int mesa, int estado, Producto producto) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parametros = {String.valueOf(mesa), "1", producto.getNombre()};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NOM_PROD, producto.getNombre());
        values.put(Utilidades.CAMPO_CANT_PRODUCTO, producto.getCantidad());
        values.put(Utilidades.CAMPO_ESTADO, String.valueOf(estado));
        db.update(Utilidades.TABLA_PEDIDO, values, Utilidades.CAMPO_ID + "=? AND " + Utilidades.CAMPO_ESTADO + "=? AND " + Utilidades.CAMPO_NOM_PROD + "=?", parametros);

        db.close();
    }

    public void eliminarPedido(int mesa, int estado, Producto producto) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {String.valueOf(mesa), String.valueOf(estado), producto.getNombre()};
        db.delete(Utilidades.TABLA_PEDIDO, Utilidades.CAMPO_ID + "=? AND " + Utilidades.CAMPO_ESTADO + "=? AND " + Utilidades.CAMPO_NOM_PROD + "=?", parametros);
        db.close();
    }

    public void eliminarTotalPedido(int mesa, int estado) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {String.valueOf(mesa), String.valueOf(estado)};

        db.delete(Utilidades.TABLA_PEDIDO, Utilidades.CAMPO_ID + "=? AND " + Utilidades.CAMPO_ESTADO + "=?", parametros);

        db.close();
    }

    private int buscarPedido(int mesa) {
        int size = listaPedidos.size();
        int posicion = -1;
        int i = 0;
        for (i = 0; i < size; i++) {
            if (listaPedidos.get(i).getMesa() == mesa) {
                posicion = i;
                break;
            }
        }
        return posicion;
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
        Pedido newPedido = consPedido(idPedido, 1);

        String cambios = "";
        Pedido pedido = consPedido(idPedido, 2);
        ArrayList<String> codsPedido = new ArrayList<String>();
        for (int i = 0; i < pedido.getProductos().size(); i++) {
            codsPedido.add(pedido.getProductos().get(i).getNombre());
        }
        ArrayList<String> codsNewPedido = new ArrayList<String>();
        for (int i = 0; i < newPedido.getProductos().size(); i++) {
            codsNewPedido.add(newPedido.getProductos().get(i).getNombre());
        }
        int tCP = codsNewPedido.size();
        int i = 0;
        for (i = 0; i < tCP; i++) {
            int pos = buscarProductoEnPedido(codsPedido, codsNewPedido.get(i));
            if (pos != -1) {
                // Modificar el producto
                int diferencia = newPedido.getProductos().get(i).getCantidad() - pedido.getProductos().get(pos).getCantidad();
                if (diferencia >= 1) {
                    cambios += "Añadir " + String.valueOf(diferencia) + " " + codsPedido.get(pos) + "\n";
                } else if (diferencia < 0) {
                    diferencia = -diferencia;
                    cambios += "Quitar " + String.valueOf(diferencia) + " " + codsPedido.get(pos) + "\n";
                }
            } else {
                cambios += "Añadir " + String.valueOf(newPedido.getProductos().get(i).getCantidad()) + " " + codsNewPedido.get(i) + "\n";
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
                if (pedido.getProductos().get(i).getCantidad() > 0) {
                    cambios += "Quitar " + String.valueOf(pedido.getProductos().get(i).getCantidad()) + " " + codsPedido.get(i) + "\n";
                }
            }
        }


        return cambios;
    }
}