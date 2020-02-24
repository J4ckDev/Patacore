package edu.unicauca.patacore.view.fragment;


import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.unicauca.patacore.R;
import edu.unicauca.patacore.adapter.PedidosAdapterRecyclerView;
import edu.unicauca.patacore.data.db.SQLiteFood;
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
                recyclerview(viewe,position+1);
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
}
