package edu.unicauca.patacore.view.fragment;


import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.adapter.PedidosAdapterRecyclerView;
import edu.unicauca.patacore.data.db.SQLiteFood;
import edu.unicauca.patacore.model.Pedidos;
/**
 * A simple {@link Fragment} subclass.
 */
public class ListarOrdenFragment extends Fragment {


    public ListarOrdenFragment() {
        // Required empty public constructor
    }
    int mesaOption=1;

    @SuppressLint("WrongConstant")

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // Inflate the layout for this fragment
        final View viewe= inflater.inflate(R.layout.fragment_listar_orden, container, false);
        //initialize the variables
        //TOLBAR
        //Toolbar toolbar = viewe.findViewById(R.id.toolbarMesa);
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Appbar page filter
        Spinner cmbToolbar = (Spinner) viewe.findViewById(R.id.CmbToolbar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ((AppCompatActivity) getActivity()).getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_mesa,
                new String[]{"Mesa 1 ", "Mesa 2 ", "Mesa 3 ", "Mesa 4", "Mesa 5", "Mesa 6"});

        adapter.setDropDownViewResource(R.layout.appbar_filter_mesa);

        cmbToolbar.setAdapter(adapter);

        cmbToolbar.setAdapter(adapter);

        cmbToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                switch (position) {
                    case 0:
                        mesaOption=1;
                        recyclerview(viewe,mesaOption);

                        Toast.makeText(getActivity(), "Spinner item 1!"+ mesaOption, Toast.LENGTH_SHORT).show();

                        break;
                    case 1:
                        mesaOption=2;
                        recyclerview(viewe,mesaOption);

                        Toast.makeText(getActivity(), "Spinner item 2!"+mesaOption, Toast.LENGTH_SHORT).show();

                        break;
                    case 2:
                        mesaOption=3;
                        recyclerview(viewe,mesaOption);

                        Toast.makeText(getActivity(), "Spinner item 3!" +mesaOption, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        mesaOption=4;
                        recyclerview(viewe,mesaOption);

                        Toast.makeText(getActivity(), "Spinner item 4!" +mesaOption, Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        mesaOption=5;
                        recyclerview(viewe,mesaOption);

                        Toast.makeText(getActivity(), "Spinner item 5!" +mesaOption, Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        mesaOption=6;
                        recyclerview(viewe,mesaOption);

                        Toast.makeText(getActivity(), "Spinner item 6!" +mesaOption, Toast.LENGTH_SHORT).show();
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //... Acciones al no existir ningún elemento seleccionado
               // recyclerview(viewe,1);

            }
        });

       // recyclerview(viewe);
        return viewe;
    }

/*    public void recyclerview(View view){
        SQLiteFood sqLiteFood = new SQLiteFood(getActivity());
        SQLiteDatabase db= sqLiteFood.getWritableDatabase();
        RecyclerView pedidosRecycler =view.findViewById(R.id.menuListRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        pedidosRecycler.setLayoutManager(linearLayoutManager);
        PedidosAdapterRecyclerView pedidosAdapterRecyclerView;
        pedidosAdapterRecyclerView=new PedidosAdapterRecyclerView(sqLiteFood.buildPedidos(),getActivity(),R.layout.cardview_list, getActivity());
        pedidosRecycler.setAdapter(pedidosAdapterRecyclerView);
        pedidosAdapterRecyclerView.notifyDataSetChanged();

    }*/
    public void recyclerview(View view, int mesa){
        SQLiteFood sqLiteFood = new SQLiteFood(getActivity());
        SQLiteDatabase db= sqLiteFood.getWritableDatabase();
        RecyclerView pedidosRecycler =view.findViewById(R.id.menuListRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        pedidosRecycler.setLayoutManager(linearLayoutManager);
        PedidosAdapterRecyclerView pedidosAdapterRecyclerView;
        pedidosAdapterRecyclerView=new PedidosAdapterRecyclerView(sqLiteFood.getPedidoEstado(mesa),getActivity(),R.layout.cardview_list, getActivity());
        pedidosRecycler.setAdapter(pedidosAdapterRecyclerView);
        pedidosAdapterRecyclerView.notifyDataSetChanged();

    }


    public void showToolbar(String title, boolean upBotton, View view){
         Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
         ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upBotton);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


}

 /* public ArrayList<Pedidos> buildLista(){
        ArrayList <Pedidos> pedidos= new ArrayList<>();


        pedidos.add(new Pedidos("pollo", "5000","https://image.freepik.com/foto-gratis/plato-pechuga-pollo_1205-4244.jpg",  "ddd", 2, 1, "a","a",2));
        //pedidos.add(new Pedidos("https://peru21.pe/resizer/GjiPoTh0tNBPixu-SjuZ58BFDpM=/980x528/smart/arc-anglerfish-arc2-prod-elcomercio.s3.amazonaws.com/public/ZCPPKN7SHBAA7HPUJHRUGHS32U.jpg", "pollo", "5000"  ));
        //pedidos.add(new Pedidos("https://cdn.pixabay.com/photo/2014/12/16/23/45/soup-570922_960_720.jpg", "pollo", "5000"  ));
        //pedidos.add(new Pedidos("https://cdn.colombia.com/sdi/2011/08/02/bandeja-paisa-500927.jpg", "pollo", "5000"  ));
        //pedidos.add(new Pedidos("https://www.reinadelaselva.pe/content/img_noticia/limonada.jpg", "pollo", "5000"  ));

        return pedidos;
    }*/
