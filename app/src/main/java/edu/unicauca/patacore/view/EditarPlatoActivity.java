package edu.unicauca.patacore.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.data.db.SQLiteFood;
import edu.unicauca.patacore.model.Menu;

public class EditarPlatoActivity extends AppCompatActivity {
    private EditText foodPriceUpdate, descriptionUpdate, foodNameUpdate, imgUpdate;
    private Button updateFoodButton;
    private SQLiteFood sqLiteFood;
    private long receivedPersonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_plato);

        //init
        init();

        sqLiteFood = new SQLiteFood(this);

        try {
            //get intent to get person id
            receivedPersonId = getIntent().getLongExtra("MENU_ID", 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        /***populate user data before update***/
        Menu menu = sqLiteFood.getMenuFood(receivedPersonId);
        //set field to this user data
        foodNameUpdate.setText(menu.getTxtNombre());
        foodPriceUpdate.setText(menu.getTxtPrecio());
        imgUpdate.setText(menu.getImg());
        descriptionUpdate.setText(menu.getTxtDescription());

        //listen to add button click to update
        updateFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                validar();

            }
        });

        //Agregarle a la barra la opción de regresar atrás
        Toolbar toolbar = findViewById(R.id.toolbar);
        //ActionBar actionBar=getSupportActionBar();
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
       
    }
    private void goBackMenu() {
        Intent intent= new Intent(this, ContainerActivity.class);
        startActivity(intent);
    }

    private void updatePerson() {
        //create updated person
        String name = foodNameUpdate.getText().toString().trim();
        String price = foodPriceUpdate.getText().toString().trim();
        String img = imgUpdate.getText().toString().trim();
        String description = descriptionUpdate.getText().toString().trim();
        Menu updatedPerson = new Menu(name, price, img, description);

        //call dbhelper update
         sqLiteFood.updateMenuFoodRecord(receivedPersonId, this, updatedPerson);
        goBackMenu();
    }

    public void validar(){
        foodNameUpdate.setError(null);
        foodPriceUpdate.setError(null);
        imgUpdate.setError(null);
        descriptionUpdate.setError(null);
        String nombre= foodNameUpdate.getText().toString();
        String precio=foodPriceUpdate.getText().toString();
        String imagen=imgUpdate.getText().toString();
        String description=descriptionUpdate.getText().toString();

        if (TextUtils.isEmpty(nombre)){
            foodNameUpdate.setError(getString(R.string.error_campo_obligatorio));
            foodNameUpdate.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(precio)){
            foodPriceUpdate.setError(getString(R.string.error_campo_obligatorio));
            foodPriceUpdate.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(imagen)){
            imgUpdate.setError(getString(R.string.error_campo_obligatorio));
            imgUpdate.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(description)){
            descriptionUpdate.setError(getString(R.string.error_campo_obligatorio));
            descriptionUpdate.requestFocus();
            return;

        }
        int precioInt= Integer.parseInt(precio);

        if (precioInt ==0){
            foodPriceUpdate.setError(getString(R.string.mayor_que_0));
            foodPriceUpdate.requestFocus();
            return;
        }
        else{
            updatePerson();
        }


        // Toast.makeText(getApplicationContext(), "Validacion Correcta", Toast.LENGTH_SHORT).show();

    }

    private void init() {
        foodNameUpdate = (EditText)findViewById(R.id.foodNameUpdate);
        foodPriceUpdate = (EditText)findViewById(R.id.foodPriceUpdate);
        imgUpdate = (EditText)findViewById(R.id.imgUpdate);
        descriptionUpdate = (EditText)findViewById(R.id.descriptionUpdate);
        updateFoodButton = (Button)findViewById(R.id.updateFoodButton);
    }

}
