package edu.unicauca.patacore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.model.Pedidos;

public class PedidosDelAdapterRecycler extends RecyclerView.Adapter<PedidosDelAdapterRecycler.PedidosAViewHolder>{


    public PedidosDelAdapterRecycler(ArrayList<Pedidos> pedidosArrayList, int resource, Activity activity) {
        this.pedidosArrayList = pedidosArrayList;
        this.resource = resource;
        this.activity = activity;
    }

    private ArrayList<Pedidos> pedidosArrayList;
    private int resource;
    private Activity activity;

    /*public PedidosAdapterRecyclerView(ArrayList<Pedidos> buildLista, int cardview_menu, FragmentActivity activity) {

    }*/


    @NonNull
    @Override
    public PedidosAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new PedidosAViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosAViewHolder holder, int position) {
        //TODA LA LISTA DE ELEMENTOS

       Pedidos pedido = pedidosArrayList.get(position);
       //holder.textNameCard.setText(pedido.get());
       //holder.textDateCard.setText(pedido.getTxtPrecio());
       //holder.textDateActCard.setText(pedido.getImgCard());
    }

    @Override
    public int getItemCount() {

        return  pedidosArrayList.size();
    }

    public class PedidosAViewHolder extends RecyclerView.ViewHolder {


        public Pedidos pedidos;
        TextView textNameCard;
        TextView textDateCard;
        TextView textDateActCard;
        ImageView imgCard;

        //TODOS LOS VIEW QUE COMPONEN A LA CARD
        public PedidosAViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCard = itemView.findViewById(R.id.imgCard);
            textNameCard = itemView.findViewById(R.id.textNameCard);
            textDateCard = itemView.findViewById(R.id.textDateCard);
            textDateActCard = itemView.findViewById(R.id.textDatActCard);

        }




    }
}
