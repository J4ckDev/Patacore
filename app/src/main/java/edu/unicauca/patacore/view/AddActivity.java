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
import edu.unicauca.patacore.data.db.SQLiteFood;
import edu.unicauca.patacore.data.utilidades.Utilidades;
import edu.unicauca.patacore.model.Menu;
import edu.unicauca.patacore.model.Pedido;
import edu.unicauca.patacore.model.Pedidos;
import edu.unicauca.patacore.model.Producto;

public class AddActivity extends AppCompatActivity {

    ArrayList<Pedidos> listaMenu;
    RecyclerView recyclerProductos;
    PedidosNewAdapterRecycler adapter;
    Context context;

    FloatingActionButton fabActualizarPedido;

    SQLiteFood sqLiteFood;
    int mesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_orden);
        mesa = Integer.parseInt(getIntent().getExtras().get("mesa").toString());
        getSupportActionBar().setTitle("Mesa " + String.valueOf(mesa));

        context = getApplicationContext();
        sqLiteFood = new SQLiteFood(context);
        FloatingActionButton btnAct = (FloatingActionButton) findViewById(R.id.fbtnActualizar);


        recyclerProductos = findViewById(R.id.recyclerProductos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerProductos.setLayoutManager(linearLayoutManager);



        adapter = new PedidosNewAdapterRecycler(context, AddActivity.this, mesa);
        //adapter = new PedidosNewAdapterRecycler(context);
        recyclerProductos.setAdapter(adapter);
        fabActualizarPedido = (FloatingActionButton) findViewById(R.id.fbtnActualizar);
        fabActualizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaMenu = sqLiteFood.consPedido(mesa, 1);
                AlertDialog.Builder alerta = new AlertDialog.Builder(AddActivity.this);
                alerta.setMessage(sqLiteFood.cambiosPedido(mesa))
                        .setTitle("Cambios pedido")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sqLiteFood.eliminarTotalPedido(mesa, 2);
                                for (int i = 0; i < listaMenu.size(); i++) {
                                    Menu menu = new Menu();
                                    menu.setTxtNombre(listaMenu.get(i).getNombre());
                                    menu.setCantidad(listaMenu.get(i).getCantidad());
                                    menu.setTxtPrecio(String.valueOf(listaMenu.get(i).getPrecio()));
                                    menu.setAnotacion(listaMenu.get(i).getAnotacion());
                                    menu.setTxtDescription(listaMenu.get(i).getDescription());
                                    menu.setImg(listaMenu.get(i).getImagen());
                                    sqLiteFood.regPedido(mesa, 2, menu);
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
}