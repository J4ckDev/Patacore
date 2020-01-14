package edu.unicauca.patacore.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.data.db.SQLiteFood;
import edu.unicauca.patacore.model.Pedidos;

public class PedidoDetalleActivity extends AppCompatActivity {
    private TextView txtNombreDetalle, txtMenuDetalle,numCantidadDetalle, txtContAnotaDetalle ;
    ImageView imgPedidoDetalle;
    private Button updateFoodButton;
    private SQLiteFood sqLiteFood;
    private Long receivedPedidoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_detalle);
        init();
        sqLiteFood = new SQLiteFood(this);

        try {
            //get intent to get person id
            //receivedPedidoId = getIntent().getLongExtra("ID", 1);
            receivedPedidoId=getIntent().getLongExtra("ID",4);
            //int a = getIntent().getExtras().get("id", 1);
            //getSupportActionBar().setTitle(getIntent().getExtras().get("mesa").toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        Pedidos pedidos = sqLiteFood.getPedidosDetalle(receivedPedidoId);
        //set field to this user data
        txtNombreDetalle.setText(pedidos.getNombre());
        txtMenuDetalle.setText(pedidos.getDescription());
        numCantidadDetalle.setText(String.valueOf( pedidos.getCantidad()));
        txtContAnotaDetalle.setText(pedidos.getAnotacion());
        Picasso.with(this)
                .load(pedidos.getImagen())
                .resize(100, 100)
                .centerCrop()
                .error(R.drawable.panadero)
                .into(imgPedidoDetalle);
     //   holder.txtDescriptionMenu.setText(pedidos.getImagen());
        //.resize(120, 120)


    }
    private void init() {
        txtNombreDetalle = findViewById(R.id.txtNombreDetalle);
        txtMenuDetalle = findViewById(R.id.txtMenuDetalle);
        numCantidadDetalle = findViewById(R.id.numCantidadDetalle);
        txtContAnotaDetalle = findViewById(R.id.txtContAnotaDetalle);
        imgPedidoDetalle = findViewById(R.id.imgPedidoDetalle);



    }


    public void showToolbar(String title, boolean upBotton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(upBotton);
            getSupportActionBar().setDisplayShowHomeEnabled(upBotton);
        }
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingList);
    }
}
