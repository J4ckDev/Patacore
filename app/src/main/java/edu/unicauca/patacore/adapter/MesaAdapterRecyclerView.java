package edu.unicauca.patacore.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import edu.unicauca.patacore.R;
import edu.unicauca.patacore.data.db.SQLiteFood;
import edu.unicauca.patacore.model.Mesa;
import edu.unicauca.patacore.view.AddActivity;

public class MesaAdapterRecyclerView extends RecyclerView.Adapter<MesaAdapterRecyclerView.MesaAViewHolder> {




    private ArrayList<Mesa> mesaArrayList;
    private int resource;
    private Activity activity;
    private String[] mData;
    private LayoutInflater mInflater;
    private SQLiteFood sqLiteFood;

    public MesaAdapterRecyclerView(ArrayList<Mesa> mesaArrayList, int resource, Activity activity) {
        this.mesaArrayList = mesaArrayList;
        this.resource = resource;
        this.activity = activity;

    }

    @NonNull
    @Override
    public MesaAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new MesaAViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MesaAViewHolder holder, final int position) {
        final Mesa mesa = mesaArrayList.get(position);
        holder.txtCardMesa.setText(mesa.getTxtCardMesa());
        Picasso.with(activity)
                .load(mesa.getImgCardMesa())
                .resize(120, 120)
                .placeholder(R.drawable.panadero)
                .error(R.drawable.panadero)
                .into(holder.imgCardMesa);

        holder.imgCardMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(activity, AddActivity.class);
                    intent.putExtra("mesa", mesa.getTxtCardMesa());
                    activity.startActivity(intent);
            }
        });
        final Context contexto = holder.imgCardMesa.getContext();
        final int mesaInt = Integer.parseInt(mesa.getTxtCardMesa());
        this.sqLiteFood = new SQLiteFood(contexto);
        holder.imgCardMesa.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(contexto);
                alerta.setMessage("Eliminar el pedido de la mesa "+mesaInt+"?")
                        .setTitle("Elminar pedido")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                sqLiteFood.eliminarTotalPedido(mesaInt, 1);
                                sqLiteFood.eliminarTotalPedido(mesaInt, 2);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.show();


                return true;
            }
        });


    }

    @Override
    public int getItemCount(){
        return  mesaArrayList.size();

    }


    public class MesaAViewHolder extends RecyclerView.ViewHolder {

        public Mesa mesa;
        TextView txtCardMesa;
        ImageView imgCardMesa;


        public MesaAViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCardMesa = itemView.findViewById(R.id.imgCardMesa);
            txtCardMesa = itemView.findViewById(R.id.txtCardMesa);
        }
    }
}
