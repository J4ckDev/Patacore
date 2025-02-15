package edu.unicauca.patacore.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import java.util.ArrayList;
import edu.unicauca.patacore.R;
import edu.unicauca.patacore.adapter.MesaAdapterRecyclerView;
import edu.unicauca.patacore.model.Mesa;

/**
 * A simple {@link Fragment} subclass.
 */
public class MesaFragment extends Fragment {


    public MesaFragment() {
        // Required empty public constructor
    }

    private GridLayout gridLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int numberOfColumns = 2;
        View view= inflater.inflate(R.layout.fragment_mesa, container, false);
        RecyclerView mesaRecycler =view.findViewById(R.id.mesaRecycler);

        //mesaRecycler.setLayoutManager(linearLayoutManager);
        mesaRecycler.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        MesaAdapterRecyclerView mesaAdapterRecyclerView =
                new MesaAdapterRecyclerView(buildLista(),R.layout.cardview_mesa, getActivity());
        mesaRecycler.setAdapter(mesaAdapterRecyclerView);


        return view;
    }

    private ArrayList<Mesa> buildLista() {
        ArrayList <Mesa> mesa= new ArrayList<>();
        mesa.add(new Mesa("https://clipartstation.com/wp-content/uploads/2017/11/piring-clipart-8.png", "1"));
        mesa.add(new Mesa("https://clipartstation.com/wp-content/uploads/2017/11/piring-clipart-8.png", "2"));
        mesa.add(new Mesa("https://clipartstation.com/wp-content/uploads/2017/11/piring-clipart-8.png", "3"));
        mesa.add(new Mesa("https://clipartstation.com/wp-content/uploads/2017/11/piring-clipart-8.png", "4"));
        mesa.add(new Mesa("https://clipartstation.com/wp-content/uploads/2017/11/piring-clipart-8.png", "5"));
        mesa.add(new Mesa("https://clipartstation.com/wp-content/uploads/2017/11/piring-clipart-8.png", "6"));


        return mesa;

    }



}
