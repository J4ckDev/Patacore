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
import edu.unicauca.patacore.adapter.PedidosAddAdapterRecycler;
import edu.unicauca.patacore.model.Producto;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOrdenFragment extends Fragment {


    public AddOrdenFragment() {
        // Required empty public constructor
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_orden, container, false);

        RecyclerView pedidosRecycler = view.findViewById(R.id.recyclerAdd);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        pedidosRecycler.setLayoutManager(linearLayoutManager);

        PedidosAddAdapterRecycler pedidosAdapterRecyclerView =
                new PedidosAddAdapterRecycler(buildLista(),R.layout.productos, getActivity());
        pedidosRecycler.setAdapter(pedidosAdapterRecyclerView);
        //TOLBAR

        return view;

    }

    public ArrayList<Producto> buildLista(){
        ArrayList <Producto> listaProductos;
        listaProductos = new ArrayList<Producto>();
        listaProductos.add (new Producto (1025, "Carnita", "Carne deliciosa asadita", 0, R.drawable.macet));
        listaProductos.add (new Producto (1026, "Pollito Solito", "Pollito delicioso asadito", 0, R.drawable.panadero));
        listaProductos.add (new Producto (1026, "Pollito", "Pollito delicioso asadito", 0, R.drawable.panadero));
        return listaProductos;
    }
}
