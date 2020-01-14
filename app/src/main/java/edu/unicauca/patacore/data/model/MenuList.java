package edu.unicauca.patacore.data.model;

import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.adapter.PedidosMenuRecyclerView;
import edu.unicauca.patacore.model.Menu;

public class MenuList extends AppCompatActivity {

    GridView gridView;
    ArrayList<Menu> list;
    PedidosMenuRecyclerView pedidosMenuRecyclerView=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu);
        init();



    }
    public void  init(){

        list = new ArrayList<>();

    }
}
