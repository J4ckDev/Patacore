package edu.unicauca.patacore.view.fragment;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import edu.unicauca.patacore.R;
import edu.unicauca.patacore.adapter.PedidosMenuRecyclerView;
import edu.unicauca.patacore.data.db.SQLiteFood;
import edu.unicauca.patacore.view.AgregarPlatoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    FloatingActionButton fabBtnPedido;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_menu, container, false);
        View view= inflater.inflate(R.layout.fragment_menu, container, false);
        init(view);
        recyclerview(view);
        //initialize the variables
        //TOLBAR
        //título quitado
        showToolbar("", false, view);

        fabBtnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AgregarPlatoActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    public void init(View view){
        fabBtnPedido= view.findViewById(R.id.fabBtnPedido);
    }
    public void recyclerview(View view){
            SQLiteFood sqLiteFood = new SQLiteFood(getActivity());
        SQLiteDatabase db= sqLiteFood.getWritableDatabase();
        RecyclerView menuRecycler = view.findViewById(R.id.menuRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        menuRecycler.setLayoutManager(linearLayoutManager);
        PedidosMenuRecyclerView pedidosMenuRecyclerView =
                new PedidosMenuRecyclerView(sqLiteFood.buildListas(),getActivity(), R.layout.cardview_menu, getActivity());
        menuRecycler.setAdapter(pedidosMenuRecyclerView);
        pedidosMenuRecyclerView.notifyDataSetChanged();
    }

    public void showToolbar(String title, boolean upBotton, View view){
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upBotton);
    }



}