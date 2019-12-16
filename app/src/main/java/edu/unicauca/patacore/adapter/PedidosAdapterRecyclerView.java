package edu.unicauca.patacore.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.CollationElementIterator;
import java.util.ArrayList;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.model.Menu;
import edu.unicauca.patacore.model.Pedidos;
import edu.unicauca.patacore.view.PedidoDetalleActivity;

public class PedidosAdapterRecyclerView extends RecyclerView.Adapter<PedidosAdapterRecyclerView.PedidosAViewHolder>{


    private ArrayList<Pedidos> pedidosArrayList;

    private int resource;
    private Activity activity;
    private Context context;
    private RecyclerView mRecyclerV;
    Menu menu;


    public PedidosAdapterRecyclerView(ArrayList<Pedidos> pedidosArrayList, Context context, int resource, Activity activity) {
        this.pedidosArrayList = pedidosArrayList;
        this.context = context;
        this.resource = resource;
        this.activity = activity;
    }
    public PedidosAdapterRecyclerView(ArrayList<Pedidos> pedidosArrayList, int resource, Activity activity) {
        this.pedidosArrayList = pedidosArrayList;
        this.resource = resource;
        this.activity = activity;
    }





    @NonNull
    @Override

    public PedidosAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent, false);
        return new PedidosAViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull PedidosAViewHolder holder, final int position) {
        //TODA LA LISTA DE ELEMENTOS


       final Pedidos pedido = pedidosArrayList.get(position);
       holder.txtNombre.setText(pedido.getNombre());
       holder.txtPrecio.setText(String.valueOf(pedido.getPrecio()));
      // holder.img_card_list.setImageResource(pedidosArrayList.get(position).getImg());
       Picasso.with(activity)
               .load(pedido.getImagen())
               .resize(120, 120)
               .error(R.drawable.panadero)
               .into(holder.img_card_list);
        //.placeholder(R.drawable.panadero)

       holder.btnPrep.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //Intent intent = new Intent(activity, PedidoDetalleActivity.class);
               //intent.putExtra("id", pedidosArrayList.get(position).getId());
               // activity.startActivity(intent);
               goToUpdateActivity(pedido.getId());


           }
       });


    }
    private void goToUpdateActivity(long menuId){
        Intent goToUpdate = new Intent(activity, PedidoDetalleActivity.class);
        goToUpdate.putExtra("MENU_ID", menuId);
        activity.startActivity(goToUpdate);
    }

    @Override
    public int getItemCount() {
        return  pedidosArrayList.size();
    }

    public class PedidosAViewHolder extends RecyclerView.ViewHolder {


        public Pedidos pedidos;
        TextView txtNombre;
        TextView txtPrecio;
        ImageView img_card_list;
        Button btnPrep;


        //TODOS LOS VIEW QUE COMPONEN A LA CARD
        public PedidosAViewHolder(@NonNull View itemView) {
            super(itemView);
            btnPrep = itemView.findViewById(R.id.btnPrep);
            img_card_list = itemView.findViewById(R.id.img_card_list);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);

        }




    }
}
