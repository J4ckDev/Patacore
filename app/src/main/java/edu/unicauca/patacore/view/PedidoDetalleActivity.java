package edu.unicauca.patacore.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.data.db.SQLiteFood;
import edu.unicauca.patacore.model.Pedidos;

public class PedidoDetalleActivity extends AppCompatActivity {
    private EditText foodPriceUpdate, descriptionUpdate, foodNameUpdate, imgUpdate;
    private Button updateFoodButton;
    private SQLiteFood sqLiteFood;
    private long receivedPedidoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_detalle);

        sqLiteFood = new SQLiteFood(this);

        try {
            //get intent to get person id
            receivedPedidoId = getIntent().getLongExtra("id", 1);
            //int a = getIntent().getExtras().get("id", 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Pedidos pedidos = sqLiteFood.getPedidosDetalle(receivedPedidoId);
        //set field to this user data
        //foodNameUpdate.setText(pedidos.getNombre());
       // foodPriceUpdate.setText("a");
       // imgUpdate.setText("a");
        //descriptionUpdate.setText("b");

    }


    public void showToolbar(String title, boolean upBotton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upBotton);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingList);
    }
}
