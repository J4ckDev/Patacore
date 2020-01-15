package edu.unicauca.patacore.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.data.ConexionSQLiteHelper;
import edu.unicauca.patacore.data.GestorSQL;
import edu.unicauca.patacore.data.db.BDMenu;
import edu.unicauca.patacore.model.Pedido;
import edu.unicauca.patacore.model.Pedidos;
import edu.unicauca.patacore.model.Producto;
import edu.unicauca.patacore.model.Menu;
import edu.unicauca.patacore.data.db.SQLiteFood;


public class PedidosNewAdapterRecycler extends RecyclerView.Adapter<PedidosNewAdapterRecycler.ViewHolderProductos> {

    int mesa;
    ConexionSQLiteHelper conn;
    SQLiteFood sqLiteFood;

    ArrayList<Menu> listaMenu;

    Context context;

    Gson gson = new Gson();

    Activity activity;


    public PedidosNewAdapterRecycler(ArrayList<Menu> listaMenu, Context context) {
        this.listaMenu = listaMenu;
        sqLiteFood = new SQLiteFood(context);
    }

    public PedidosNewAdapterRecycler(Context context, Activity activity, int mesa) {
        this.mesa= mesa;
        this.context = context;
        this.activity = activity;
        sqLiteFood = new SQLiteFood(context);
        ArrayList<Menu> lst = sqLiteFood.buildListas();
        listaMenu = new ArrayList<Menu>();
        listaMenu = sqLiteFood.buildListas();
        actualizarEstado();
    }


    private void actualizarEstado() {
        ArrayList<Pedidos> listaProdPedidos = sqLiteFood.consPedido(mesa, 1);

        int tArrPPed = listaProdPedidos.size();

        //Toast.makeText(context, "Tamaño productos pedidos: " + listaProdPedidos.get(0).getNombre(), Toast.LENGTH_SHORT).show();
        int tArrProd = listaMenu.size();
        for (int i = 0; i < tArrPPed; i++) {
            for (int e = 0; e < tArrProd; e++) {
                if (listaMenu.get(e).getTxtNombre().equals(listaProdPedidos.get(i).getNombre())) {
                    if (listaProdPedidos.get(i).getCantidad() > 0) {
                        listaMenu.get(e).setSelected(true);
                        listaMenu.get(e).setCantidad(listaProdPedidos.get(i).getCantidad());
                    }
                    break;
                }
            }
        }

    }


    public class ViewHolderProductos extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView etiNombre, etiInformacion;
        ImageView etiFoto;
        EditText numProd;

        Button btnMas;
        Button btnMenos;

        CheckBox checkProducto;


        public ViewHolderProductos(View itemView) {
            super(itemView);
            context = itemView.getContext();
            etiNombre = (TextView) itemView.findViewById(R.id.nombreProd);
            etiInformacion = (TextView) itemView.findViewById(R.id.descProducto);
            etiFoto = (ImageView) itemView.findViewById(R.id.imagenProd);
            btnMas = (Button) itemView.findViewById(R.id.btnMas);
            btnMenos = (Button) itemView.findViewById(R.id.btnMenos);
            numProd = (EditText) itemView.findViewById(R.id.txtNumProd);
            checkProducto = (CheckBox) itemView.findViewById(R.id.checkProd);

        }

        void setOnClickListeners() {
            btnMas.setOnClickListener(this);
            btnMenos.setOnClickListener(this);
            checkProducto.setOnClickListener(this);
        }

        int buscarProducto(String nombre) {
            int indice = -1;
            for (int i = 0; i < listaMenu.size(); i++) {
                if (listaMenu.get(i).getTxtNombre().equals(nombre)) {
                    indice = i;
                    break;
                }
            }
            return indice;
        }

        @Override
        public void onClick(View v) {
            // Pasar el código de producto
            String nombre = (String) etiNombre.getText();
            int indice = buscarProducto(nombre);
            switch (v.getId()) {
                case R.id.btnMas:

                    if (indice != -1) {
                        int newCantidad = listaMenu.get(indice).getCantidad() + 1;
                        listaMenu.get(indice).setCantidad(newCantidad);
                        if (checkProducto.isChecked()) {
                            sqLiteFood.actualizarPedido(mesa, 1, listaMenu.get(indice));
                        }
                        numProd.setText(String.valueOf(newCantidad));

                    }
                    break;

                case R.id.btnMenos:
                    if (indice != -1) {
                        int cantActual = listaMenu.get(indice).getCantidad();
                        if (cantActual > 1) {
                            int newCantidad = cantActual - 1;
                            listaMenu.get(indice).setCantidad(newCantidad);
                            if (checkProducto.isChecked()) {
                                sqLiteFood.actualizarPedido(mesa, 1, listaMenu.get(indice));
                            }
                            numProd.setText(String.valueOf(newCantidad));
                        }
                    }
                    break;

                case R.id.checkProd:
                    if (indice != -1) {
                        listaMenu.get(indice).setSelected(checkProducto.isChecked());
                        if (checkProducto.isChecked() && listaMenu.get(indice).getCantidad()==0){
                            listaMenu.get(indice).setCantidad(1);
                            numProd.setText(String.valueOf(1));
                        }
                        if (checkProducto.isChecked() && !(sqLiteFood.existe(mesa, 1, listaMenu.get(indice).getTxtNombre()))) {
                            sqLiteFood.regPedido(mesa,1,listaMenu.get(indice));
                            //Toast.makeText(context, "Registrado", Toast.LENGTH_SHORT).show();
                        } else {
                            sqLiteFood.eliminarPedido(mesa, 1, listaMenu.get(indice));
                        }

                    }
                    break;
            }
        }
    }


    @Override
    public ViewHolderProductos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productos, null, false);
        ViewHolderProductos vis = new ViewHolderProductos(view);
        return vis;
    }

    @Override
    public void onBindViewHolder(ViewHolderProductos holder, int position) {

        holder.etiNombre.setText(listaMenu.get(position).getTxtNombre());
        holder.etiInformacion.setText(listaMenu.get(position).getTxtDescription());
        holder.etiFoto.setImageResource(R.drawable.panadero);

        Picasso.with(activity)
                .load(listaMenu.get(position).getImg())
                .resize(120, 120)
                .error(R.drawable.panadero)
                .into(holder.etiFoto);
        holder.numProd.setText(String.valueOf(listaMenu.get(position).getCantidad()));
        holder.checkProducto.setChecked(listaMenu.get(position).getSelected());

        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return listaMenu.size();
    }

}
