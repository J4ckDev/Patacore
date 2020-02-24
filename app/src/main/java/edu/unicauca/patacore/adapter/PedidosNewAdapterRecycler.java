package edu.unicauca.patacore.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import edu.unicauca.patacore.R;
import edu.unicauca.patacore.data.ConexionSQLiteHelper;
import edu.unicauca.patacore.model.Pedidos;
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

    public PedidosNewAdapterRecycler(Context context, Activity activity, int mesa) {
        this.mesa= mesa;
        this.context = context;
        this.activity = activity;
        sqLiteFood = new SQLiteFood(context);
        listaMenu = new ArrayList<Menu>();
        listaMenu = sqLiteFood.buildListas();
        actualizarEstado();
    }


    private void actualizarEstado() {
        ArrayList<Pedidos> listaProdPedidos = sqLiteFood.consPedido(mesa, 1);

        int tArrPPed = listaProdPedidos.size();

        int tArrProd = listaMenu.size();
        for (int i = 0; i < tArrPPed; i++) {
            for (int e = 0; e < tArrProd; e++) {
                if (listaMenu.get(e).getTxtNombre().equals(listaProdPedidos.get(i).getNombre())) {
                    if (listaProdPedidos.get(i).getCantidad() > 0) {
                        listaMenu.get(e).setSelected(true);
                        listaMenu.get(e).setCantidad(listaProdPedidos.get(i).getCantidad());
                        listaMenu.get(e).setAnotacion(listaProdPedidos.get(i).getAnotacion());
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
        EditText etiAnotacion;

        Button btnMas;
        Button btnMenos;

        CheckBox checkProducto;


        public ViewHolderProductos(View itemView) {
            super(itemView);
            context = itemView.getContext();
            etiNombre = (TextView) itemView.findViewById(R.id.nombreProd);
            etiInformacion = (TextView) itemView.findViewById(R.id.descProducto);
            etiFoto = (ImageView) itemView.findViewById(R.id.imagenProd);
            etiAnotacion = (EditText) itemView.findViewById(R.id.txtAnotacion);
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
                        listaMenu.get(indice).setAnotacion(etiAnotacion.getText().toString());
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
                            listaMenu.get(indice).setAnotacion(etiAnotacion.getText().toString());
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
                        listaMenu.get(indice).setAnotacion(etiAnotacion.getText().toString());
                        listaMenu.get(indice).setSelected(checkProducto.isChecked());
                        if (checkProducto.isChecked() && listaMenu.get(indice).getCantidad()==0){
                            listaMenu.get(indice).setCantidad(1);
                            numProd.setText(String.valueOf(1));
                        }
                        if (checkProducto.isChecked() && !(sqLiteFood.existe(mesa, 1, listaMenu.get(indice).getTxtNombre()))) {
                            sqLiteFood.regPedido(mesa,1,listaMenu.get(indice));
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
    public void onBindViewHolder(final ViewHolderProductos holder, int position) {

        holder.etiNombre.setText(listaMenu.get(position).getTxtNombre());
        holder.etiInformacion.setText(listaMenu.get(position).getTxtDescription());
        holder.etiFoto.setImageResource(R.drawable.panadero);
        holder.etiAnotacion.setText(listaMenu.get(position).getAnotacion());
        Picasso.with(activity)
                .load(listaMenu.get(position).getImg())
                .resize(120, 120)
                .error(R.drawable.panadero)
                .into(holder.etiFoto);
        holder.numProd.setText(String.valueOf(listaMenu.get(position).getCantidad()));
        holder.checkProducto.setChecked(listaMenu.get(position).getSelected());

        holder.setOnClickListeners();

        holder.etiAnotacion.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String nombre = (String) holder.etiNombre.getText();
                int indice = holder.buscarProducto(nombre);

                if (indice != -1) {
                    listaMenu.get(indice).setAnotacion(holder.etiAnotacion.getText().toString());
                    listaMenu.get(indice).setSelected(holder.checkProducto.isChecked());
                    if (holder.checkProducto.isChecked() && listaMenu.get(indice).getCantidad() == 0) {
                        listaMenu.get(indice).setCantidad(1);

                    }
                    if (holder.checkProducto.isChecked()) {
                        sqLiteFood.eliminarPedido(mesa, 1, listaMenu.get(indice));
                        sqLiteFood.regPedido(mesa, 1, listaMenu.get(indice));
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaMenu.size();
    }

}
