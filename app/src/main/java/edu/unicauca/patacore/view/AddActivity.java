package edu.unicauca.patacore.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.adapter.PedidosNewAdapterRecycler;
import edu.unicauca.patacore.data.ConexionSQLiteHelper;
import edu.unicauca.patacore.data.GestorSQL;
import edu.unicauca.patacore.data.utilidades.Utilidades;
import edu.unicauca.patacore.model.Pedido;
import edu.unicauca.patacore.model.Producto;

public class AddActivity extends AppCompatActivity {

    ArrayList<Producto> listaProductos;
    RecyclerView recyclerProductos;
    PedidosNewAdapterRecycler adapter;
    Context context;
    GestorSQL gestorSQL;
    FloatingActionButton fabActualizarPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_orden);
        getSupportActionBar().setTitle(getIntent().getExtras().get("mesa").toString());

        context = getApplicationContext();

        FloatingActionButton btnAct = (FloatingActionButton) findViewById(R.id.fbtnActualizar);



        recyclerProductos = findViewById(R.id.recyclerProductos);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        recyclerProductos.setLayoutManager(linearLayoutManager);


        gestorSQL = new GestorSQL(context);
        llenarProductos ();
        //consultarListaProductos();
        //regPedido();
        //actualizarEstadoSelected();
        //consultaSQL();

        adapter = new PedidosNewAdapterRecycler(context);
        //adapter = new PedidosNewAdapterRecycler(context);
        recyclerProductos.setAdapter(adapter);
        fabActualizarPedido = (FloatingActionButton)findViewById(R.id.fbtnActualizar);
        fabActualizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaProductos = gestorSQL.consPedido(1,1).getProductos();
                AlertDialog.Builder alerta = new AlertDialog.Builder(AddActivity.this);
                alerta.setMessage(gestorSQL.cambiosPedido(1))
                        .setTitle("Cambios pedido")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gestorSQL.eliminarTotalPedido(1,2);
                                for (int i=0; i<listaProductos.size(); i++){
                                    gestorSQL.regPedido(1, "afasd", "fadfsd", listaProductos.get(i).getNombre(), 2, listaProductos.get(i).getCantidad());
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.show();
            }
        });

    }

    private void llenarProductos (){
        listaProductos = new ArrayList<Producto>();
        listaProductos.add (new Producto (1025, "Carnita", "Carne deliciosa asadita", 0, R.drawable.macet));
        listaProductos.add (new Producto (1026, "Pollito", "Pollito delicioso asadito", 0, R.drawable.panadero));
        listaProductos.add (new Producto (1026, "Pollito", "Pollito delicioso asadito", 0, R.drawable.panadero));
        gestorSQL.registrarProducto (1025, "Carnita", "Carne deliciosa asadita", 2, R.drawable.macet, 0);
        gestorSQL.registrarProducto(1026, "Sancocho", "Sancocho rico claro que si", 3, R.drawable.panadero, 0);
        gestorSQL.registrarProducto(1027, "Arroz", "Arrocillo", 1, R.drawable.panadero, 0);
    }

    private void regPedido(){
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //gestorSQL.regPedido(1, hourFormat.format(date), dateFormat.format(date), "Carnita", 1, listaProductos.get(1).getCantidad());
        //gestorSQL.regPedido(1, hourFormat.format(date), dateFormat.format(date), "Sancocho", 1, listaProductos.get(2).getCantidad());
        gestorSQL.regPedido(1, hourFormat.format(date), dateFormat.format(date), "Arroz", 1, listaProductos.get(2).getCantidad());
    }

    private void actualizarEstadoSelected (){
        Pedido pedido = gestorSQL.consPedido(1,2);

        ArrayList<Producto> listaProdPedidos = pedido.getProductos();
        int tArrPPed = listaProdPedidos.size();

        Toast.makeText(context, "Tamaño productos pedidos: " + listaProdPedidos.get(0).getNombre(), Toast.LENGTH_SHORT).show();
        int tArrProd = listaProductos.size();
        int contador = 0;
        for (int i=0; i< tArrPPed; i++){
            for (int e=0; e<tArrProd; e++){
                if (listaProductos.get(e).getNombre().equals(listaProdPedidos.get(i).getNombre())){
                    listaProductos.get(e).setSelected(true);
                    contador ++;
                    //Toast.makeText(context, "Encontrado en " + contador, Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    //listaProductos.get(e).setSelected(false);
                    //Toast.makeText(context, "No Encontrado en ", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void consultarListaProductos() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context, "producto", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        Producto producto = null;
        // listaUsuarios=new ArrayList<Usuario>();
        //select * from usuarios
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_PRODUCTO,null);
        listaProductos = new ArrayList<Producto>();
        while (cursor.moveToNext()){
            producto= new Producto();
            producto.setCodigo(cursor.getInt(0));
            producto.setNombre(cursor.getString(1));
            producto.setDescripcion(cursor.getString(2));
            producto.setCantidad(cursor.getInt(3));
            producto.setImagen(cursor.getInt(4));
            boolean selected = false;
            if (cursor.getInt(5) == 1){
                selected = true;
            }
            producto.setSelected(selected);
            listaProductos.add(producto);
        }
    }





    private void registroSQL (int codigo, String nombre, String descripcion, int cantidad, int imagen, int selected){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context, "producto", null, 1);
        SQLiteDatabase db =conn.getWritableDatabase();

        // insert into usuario (id, nombre, descripcion) values (123, 'Pollo', 'Pollo asado pues ome');

        String insert = "INSERT INTO "+Utilidades.TABLA_PRODUCTO+ " ( "
                +Utilidades.CAMPO_ID+","+Utilidades.CAMPO_NOMBRE+","+Utilidades.CAMPO_DESCRIPCION+
                ","+Utilidades.CAMPO_IMAGEN +","+Utilidades.CAMPO_CANTIDAD+","+Utilidades.CAMPO_SELECTED+")"
                +" VALUES ("+String.valueOf(codigo)+",'"+nombre+"',"+"'"+descripcion+"',"+String.valueOf(cantidad)
                +","+ String.valueOf(imagen)+","+String.valueOf(selected)+")";

        db.execSQL(insert);

        db.close ();
    }
}
