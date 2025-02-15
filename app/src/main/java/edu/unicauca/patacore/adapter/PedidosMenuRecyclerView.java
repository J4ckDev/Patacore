package edu.unicauca.patacore.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import edu.unicauca.patacore.R;
import edu.unicauca.patacore.data.db.SQLiteFood;
import edu.unicauca.patacore.model.Menu;
import edu.unicauca.patacore.view.EditarPlatoActivity;

public class PedidosMenuRecyclerView extends RecyclerView.Adapter<PedidosMenuRecyclerView.PedidosAViewHolder> {
    private ArrayList<Menu> menuArrayList;
    private List<Menu> menuList;
    private int resource;
    private Activity activity;
    private Context context;
    private RecyclerView mRecyclerV;

    public PedidosMenuRecyclerView(ArrayList<Menu> menuArrayList, Context context, int resource, Activity activity) {
        this.menuArrayList = menuArrayList;
        this.context = context;
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

        final Menu menu= menuArrayList.get(position);
        holder.txtIdMenu.setText(String.valueOf( menu.getId()));
        holder.txtNombre.setText(menu.getTxtNombre());
        holder.txtPrecio.setText(menu.getTxtPrecio());
        Picasso.with(activity)
                .load(menu.getImg())
                .resize(120, 120)
                .error(R.drawable.panadero)
                .into(holder.img_card_menu);
        holder.txtDescriptionMenu.setText(menu.getTxtDescription());

       //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Menu de Opciones");
                builder.setMessage("deseas actualizar o eliminar?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //go to update activity
                        goToUpdateActivity(menu.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteFood sqLiteFood = new SQLiteFood(context);
                        sqLiteFood.deleteMenuRecord(menu.getId(), context);
                        menuArrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, menuArrayList.size());
                        notifyDataSetChanged();


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });


    }
    private void goToUpdateActivity(long menuId){
        Intent goToUpdate = new Intent(context, EditarPlatoActivity.class);
        goToUpdate.putExtra("MENU_ID", menuId);
        context.startActivity(goToUpdate);
    }


    @Override
    public int getItemCount() {
        return menuArrayList.size();
    }


    public class PedidosAViewHolder extends RecyclerView.ViewHolder {


        //public Menu menu
        TextView txtNombre,txtPrecio, txtDescriptionMenu, txtIdMenu;
        ImageView img_card_menu;
        public View layout;



        //TODOS LOS VIEW QUE COMPONEN A LA CARD
        public PedidosAViewHolder(@NonNull View itemView) {

            super(itemView);
            layout= itemView;
            txtIdMenu=itemView.findViewById(R.id.txtIdMenu);
            img_card_menu = itemView.findViewById(R.id.img_card_menu);
            txtNombre = itemView.findViewById(R.id.txtNombreMenu);
            txtPrecio = itemView.findViewById(R.id.txtPrecioMenu);
            txtDescriptionMenu= itemView.findViewById(R.id.txtDescriptionMenu);



        }

        public void add(int position, Menu menu) {
            menuArrayList.add(position, menu);
            notifyItemInserted(position);
        }

        public void removem(int position) {
            menuArrayList.remove(position);
            notifyItemRemoved(position);
        }


    }
}
