package edu.unicauca.patacore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import edu.unicauca.patacore.R;
import edu.unicauca.patacore.model.Producto;

public class PedidosAddAdapterRecycler extends RecyclerView.Adapter<PedidosAddAdapterRecycler.ViewHolderProductos>{

    ArrayList<Producto> listaProductos;

    private int resource;
    private Activity activity;

    public PedidosAddAdapterRecycler(ArrayList<Producto> listaProductos, int resource, Activity activity) {
        this.listaProductos = listaProductos;
        this.resource = resource;
        this.activity = activity;
    }


    public class ViewHolderProductos extends RecyclerView.ViewHolder {
        TextView etiNombre,etiInformacion;
        ImageView etiFoto;


        public ViewHolderProductos(View itemView) {
            super(itemView);
            etiNombre = (TextView) itemView.findViewById(R.id.nombreProd);
            etiInformacion = (TextView) itemView.findViewById(R.id.descProducto);
            etiFoto = (ImageView) itemView.findViewById(R.id.imagenProd);
        }
    }




    @Override
    public ViewHolderProductos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productos, null, false);
        return new ViewHolderProductos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderProductos holder, int position) {
        holder.etiNombre.setText(listaProductos.get(position).getNombre());
        holder.etiInformacion.setText(listaProductos.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

}
