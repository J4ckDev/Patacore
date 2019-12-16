package edu.unicauca.patacore.view.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.adapter.PedidosAdapterRecyclerView;
import edu.unicauca.patacore.adapter.PedidosDelAdapterRecycler;
import edu.unicauca.patacore.model.Pedidos;

/**
 * A simple {@link Fragment} subclass.
 */
public class DelOrdenFragment extends Fragment {


    public DelOrdenFragment() {
        // Required empty public constructor
    }


    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_del_orden, container, false);

        RecyclerView pedidosRecycler = view.findViewById(R.id.recyclerDel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        pedidosRecycler.setLayoutManager(linearLayoutManager);
        PedidosDelAdapterRecycler pedidosDelAdapterRecycler =
                new PedidosDelAdapterRecycler(buildLista(),R.layout.recycler_delete, getActivity());
        pedidosRecycler.setAdapter(pedidosDelAdapterRecycler);
        //TOLBAR

        return view;

    }

    public ArrayList<Pedidos> buildLista(){
        ArrayList <Pedidos> pedidos= new ArrayList<>();

        //pedidos.add(new Pedidos( "https://image.freepik.com/foto-gratis/plato-pechuga-pollo_1205-4244.jpg","Pedido en preparacion", "10/20/2019"));
       // pedidos.add(new Pedidos( "https://image.freepik.com/foto-gratis/plato-pechuga-pollo_1205-4244.jpg", "Pedido en preparación", "10/20/2019"));
        //pedidos.add(new Pedidos( "https://image.freepik.com/foto-gratis/plato-pechuga-pollo_1205-4244.jpg", "Pedido en preparación", "10/20/2019"));
        return pedidos;
    }

}
