package edu.unicauca.patacore.view.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import edu.unicauca.patacore.R;
import edu.unicauca.patacore.data.GestorSQL;
import edu.unicauca.patacore.model.Producto;
import edu.unicauca.patacore.adapter.PedidosNewAdapterRecycler;
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

        adapter = new PedidosNewAdapterRecycler(context, getActivity(), 1);
        recyclerProductos.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }






}