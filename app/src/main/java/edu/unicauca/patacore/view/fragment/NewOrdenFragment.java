package edu.unicauca.patacore.view.fragment;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.unicauca.patacore.MainMenuActivity;
import edu.unicauca.patacore.R;
import edu.unicauca.patacore.data.ConexionSQLiteHelper;
import edu.unicauca.patacore.data.GestorSQL;
import edu.unicauca.patacore.model.Pedido;
import edu.unicauca.patacore.model.Producto;
import edu.unicauca.patacore.adapter.PedidosNewAdapterRecycler;
import edu.unicauca.patacore.data.utilidades.Utilidades;
import edu.unicauca.patacore.view.AddActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrdenFragment extends Fragment {

    ArrayList<Producto> listaProductos;
    RecyclerView recyclerProductos;
    PedidosNewAdapterRecycler adapter;
    Context context;
    GestorSQL gestorSQL;
    FloatingActionButton fabActualizarPedido;

    public NewOrdenFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = inflater.getContext();

        FloatingActionButton btnAct = (FloatingActionButton) container.findViewById(R.id.fbtnActualizar);

        View view = inflater.inflate(R.layout.fragment_new_orden, container, false);

        recyclerProductos = view.findViewById(R.id.recyclerProductos);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        recyclerProductos.setLayoutManager(linearLayoutManager);


        gestorSQL = new GestorSQL(context);
        //llenarProductos ();
        //consultarListaProductos();
        //regPedido();
        //actualizarEstadoSelected();
        //consultaSQL();

        adapter = new PedidosNewAdapterRecycler(context, getActivity(), 1);
        //adapter = new PedidosNewAdapterRecycler(context);
        recyclerProductos.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }






}