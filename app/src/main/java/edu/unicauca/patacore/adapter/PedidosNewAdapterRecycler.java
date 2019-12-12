package edu.unicauca.patacore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.data.ConexionSQLiteHelper;
import edu.unicauca.patacore.data.GestorSQL;
import edu.unicauca.patacore.model.Pedido;
import edu.unicauca.patacore.model.Producto;


public class PedidosNewAdapterRecycler extends RecyclerView.Adapter<PedidosNewAdapterRecycler.ViewHolderProductos> {


    ConexionSQLiteHelper conn;
    GestorSQL gestorSQL;

    ArrayList<Producto> listaProductos;
    ArrayList<Pedido> listaPedidos;
    Context context;

    Gson gson = new Gson ();


    public PedidosNewAdapterRecycler(ArrayList<Producto> listaProductos, Context context) {
        this.listaProductos = listaProductos;
        gestorSQL = new GestorSQL(context);
    }

    public PedidosNewAdapterRecycler (Context context){
        this.context = context;
        gestorSQL = new GestorSQL(context);
        listaProductos = gestorSQL.consultarProductos();
        actualizarEstado();
    }

    public Pedido getPedido (){
        Pedido pedido = new Pedido();
        pedido.setProductos(listaProductos);
        return pedido;
    }

    private void actualizarEstado () {
        Pedido pedido = gestorSQL.consPedido(1,1);

        ArrayList<Producto> listaProdPedidos = pedido.getProductos();
        int tArrPPed = listaProdPedidos.size();

        //Toast.makeText(context, "Tamaño productos pedidos: " + listaProdPedidos.get(0).getNombre(), Toast.LENGTH_SHORT).show();
        int tArrProd = listaProductos.size();
        for (int i = 0; i < tArrPPed; i++) {
            for (int e = 0; e < tArrProd; e++) {
                if (listaProductos.get(e).getNombre().equals(listaProdPedidos.get(i).getNombre())) {
                    if (listaProdPedidos.get(i).getCantidad()>0) {
                        listaProductos.get(e).setSelected(true);
                        listaProductos.get(e).setCantidad(listaProdPedidos.get(i).getCantidad());
                    }
                    break;
                }
            }
        }

    }



    public class ViewHolderProductos extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView etiNombre,etiInformacion;
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

        void setOnClickListeners (){
            btnMas.setOnClickListener(this);
            btnMenos.setOnClickListener(this);
            checkProducto.setOnClickListener(this);
        }

        int buscarProducto (String nombre){
            int indice = -1;
            for (int i=0;i<listaProductos.size();i++){
                if (listaProductos.get(i).getNombre().equals(nombre)){
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
            switch (v.getId()){
                case R.id.btnMas:

                    if (indice != -1){
                        int newCantidad = listaProductos.get(indice).getCantidad()+1;
                        listaProductos.get(indice).setCantidad(newCantidad);
                        if (checkProducto.isChecked()) {
                            gestorSQL.actualizarPedido(1,1, listaProductos.get(indice));
                        }
                        numProd.setText(String.valueOf(newCantidad));

                    }
                    break;

                case R.id.btnMenos:
                    if (indice != -1){
                        int cantActual = listaProductos.get(indice).getCantidad();
                        if (cantActual > 1) {
                            int newCantidad = cantActual -1;
                            listaProductos.get(indice).setCantidad(newCantidad);
                            if (checkProducto.isChecked()) {
                                gestorSQL.actualizarPedido(1, 1, listaProductos.get(indice));
                            }
                            numProd.setText(String.valueOf(newCantidad));
                        }
                    }
                    break;

                case R.id.checkProd:
                    if (indice != -1){
                        listaProductos.get(indice).setSelected(checkProducto.isChecked());

                        if (checkProducto.isChecked() && !(gestorSQL.existe(1,1, listaProductos.get(indice).getNombre()))) {

                            gestorSQL.regPedido(1, "afasd", "fadfsd", listaProductos.get(indice).getNombre(), 1, listaProductos.get(indice).getCantidad());
                        }else{
                            gestorSQL.eliminarPedido(1,1, listaProductos.get(indice));
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

        holder.etiNombre.setText(listaProductos.get(position).getNombre());
        holder.etiInformacion.setText(listaProductos.get(position).getDescripcion());
        holder.etiFoto.setImageResource(listaProductos.get(position).getImagen());
        holder.numProd.setText(String.valueOf(listaProductos.get(position).getCantidad()));
        holder.checkProducto.setChecked(listaProductos.get(position).getSelected());

        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

}
