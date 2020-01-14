package edu.unicauca.patacore.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.data.db.SQLiteFood;
import edu.unicauca.patacore.model.Menu;
import edu.unicauca.patacore.view.fragment.MenuFragment;

public class AgregarPlatoActivity extends AppCompatActivity {
    EditText agregarNombre, agregarPrecio, agregarImagen, agregarDescripcion;
    Button btnAdd, btnDirMenu, btnBuscar;
    ImageView imageView;
    //public static SQLiteFood sqLiteFood;
    final int REQUEST_CODE_GALLERY=999;
    private SQLiteFood sqLiteFood; // class that extends SQLiteOpenHelper
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_plato);
        //variables
        init();
        //listen to add button
        sqLiteFood = new SQLiteFood(this);
        db= sqLiteFood.getWritableDatabase();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //agregar();
                validar();

            }
        });

        insertPedidosPrueba();
        insertProductosPrueba();


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


    public void agregar(){
        try {
            sqLiteFood.insertData(

                    agregarNombre.getText().toString().trim(),
                    agregarPrecio.getText().toString().trim(),
                    agregarImagen.getText().toString().trim(),
                    agregarDescripcion.getText().toString().trim()
                    //imageViewToByte(imageView)
            );
            Toast.makeText(getApplicationContext(), "plato Agregado", Toast.LENGTH_SHORT).show();
            agregarNombre.setText("");
            agregarPrecio.setText("");
            agregarImagen.setText("");
            agregarDescripcion.setText("");
            //imageView.setImageResource(R.drawable.panadero);
            goBackMenu();


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();

        }
    }
    public void validar(){
        agregarNombre.setError(null);
        agregarPrecio.setError(null);
        agregarImagen.setError(null);
        agregarDescripcion.setError(null);
        String nombre= agregarNombre.getText().toString();
        String precio=agregarPrecio.getText().toString();
        String imagen=agregarImagen.getText().toString();
        String description=agregarDescripcion.getText().toString();

        if (TextUtils.isEmpty(nombre)){
            agregarNombre.setError(getString(R.string.error_campo_obligatorio));
            agregarNombre.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(precio)){
            agregarPrecio.setError(getString(R.string.error_campo_obligatorio));
            agregarPrecio.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(imagen)){
            agregarImagen.setError(getString(R.string.error_campo_obligatorio));
            agregarImagen.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(description)){
            agregarDescripcion.setError(getString(R.string.error_campo_obligatorio));
            agregarDescripcion.requestFocus();
            return;

        }
        int precioInt= Integer.parseInt(precio);

        if (precioInt ==0){
            agregarPrecio.setError(getString(R.string.mayor_que_0));
            agregarPrecio.requestFocus();
            return;
        }
        else{
            agregar();
        }


        // Toast.makeText(getApplicationContext(), "Validacion Correcta", Toast.LENGTH_SHORT).show();

    }

    private void goBackMenu() {
        Intent intent= new Intent(AgregarPlatoActivity.this, ContainerActivity.class);
        startActivity(intent);
    }

      // agregamos a las variables lo que hay en los layaout
    public void  init(){
        agregarNombre = findViewById(R.id.agregarNombre);
        agregarPrecio = findViewById(R.id.agregarPrecio);
        agregarImagen = findViewById(R.id.agregarImagen);
        agregarDescripcion= findViewById(R.id.agregarDescripcion);
        btnAdd= findViewById(R.id.btnAdd);
        imageView=findViewById(R.id.imgPlato);
    }


    public void insertProductosPrueba (){
        sqLiteFood.insertData("Pollo", "5000","https://image.freepik.com/foto-gratis/plato-pechuga-pollo_1205-4244.jpg",  "ddd"  );
        sqLiteFood.insertData("Carne", "6000" , "https://peru21.pe/resizer/GjiPoTh0tNBPixu-SjuZ58BFDpM=/980x528/smart/arc-anglerfish-arc2-prod-elcomercio.s3.amazonaws.com/public/ZCPPKN7SHBAA7HPUJHRUGHS32U.jpg", "aa" );
        sqLiteFood.insertData("Arroz", "7000", "https://cdn.pixabay.com/photo/2014/12/16/23/45/soup-570922_960_720.jpg", "aa"  );
        sqLiteFood.insertData("Atun", "8000","https://cdn.colombia.com/sdi/2011/08/02/bandeja-paisa-500927.jpg",  "aa"  );
        sqLiteFood.insertData("Cerdo", "3000","https://www.reinadelaselva.pe/content/img_noticia/limonada.jpg",  "dd"  );
        sqLiteFood.insertData( "Callo", "20000", "https://image.freepik.com/foto-gratis/plato-pechuga-pollo_1205-4244.jpg", "pollitoo");
    }

    public void insertPedidosPrueba(){
        sqLiteFood.insertDataPedido("pollo", 5000,"https://image.freepik.com/foto-gratis/plato-pechuga-pollo_1205-4244.jpg",  "pollo a la plancha", "este esa en la nesa 1 tiene  pollo a la plancha ",1, 1, "a","a",2);
        sqLiteFood.insertDataPedido("papa", 4000,"https://peru21.pe/resizer/GjiPoTh0tNBPixu-SjuZ58BFDpM=/980x528/smart/arc-anglerfish-arc2-prod-elcomercio.s3.amazonaws.com/public/ZCPPKN7SHBAA7HPUJHRUGHS32U.jpg",  "pollo con papas", "esta en la mesa 1 tiene de imagen un pollo con papas\"",2, 1, "a","a",2);
        sqLiteFood.insertDataPedido("arroz", 3000,"https://cdn.pixabay.com/photo/2014/12/16/23/45/soup-570922_960_720.jpg",  "sopa", "aaa",3, 2, "a","a",2);
        sqLiteFood.insertDataPedido("jugo", 2000,"https://www.reinadelaselva.pe/content/img_noticia/limonada.jpg",  "limonadaaaa","limonada grande y fria mesa 3", 4, 3, "a","a",2);
    }
}
/* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode ==REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(getApplicationContext(), "No tienes permisos para acceder a la localizacion", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode== REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data !=null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
/*  private byte [] imageViewToByte(ImageView imageView) {
        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray= stream.toByteArray();
        return  byteArray;

    }*/
   /* private ArrayList<Menu> menu() {
        ArrayList <Menu> menu= new ArrayList<>();

     menu.add(new Menu(, "pollo", "5000"  ));
        menu.add(new Menu("https://peru21.pe/resizer/GjiPoTh0tNBPixu-SjuZ58BFDpM=/980x528/smart/arc-anglerfish-arc2-prod-elcomercio.s3.amazonaws.com/public/ZCPPKN7SHBAA7HPUJHRUGHS32U.jpg", "pollo", "5000"  ));
        menu.add(new Menu("https://cdn.pixabay.com/photo/2014/12/16/23/45/soup-570922_960_720.jpg", "pollo", "5000"  ));
        menu.add(new Menu("https://cdn.colombia.com/sdi/2011/08/02/bandeja-paisa-500927.jpg", "pollo", "5000"  ));
        menu.add(new Menu("https://www.reinadelaselva.pe/content/img_noticia/limonada.jpg", "pollo", "5000"  ));

        return menu;
    }*/
//sqLiteFood.queryData("CREATE TABLE IF NOT EXISTS FOOD(id_food INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOG)");
        /*btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(
                        AgregarPlatoActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });*/

/*
        //init
  btnDirMenu=findViewById(R.id.btnDirMenu);
        btnBuscar=findViewById(R.id.btnBuscar);
        //fin init

    private void saveMenuFood() {
        String name = editTxtNombre.getText().toString().trim();
        String price = editTxtPrecio.getText().toString().trim();
        byte[] image= imageViewToByte(imageView);



        //create new person
        Menu menu = new Menu(name, price, image);
        sqLiteFood.saveNewMenuFood(menu);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackMenu();

    }
//sqLiteFood = new SQLiteFood(this, "FoodDB.sqlite", null, 1);
        //crea la tabla food
        sqLiteFood.queryData("CREATE TABLE IF NOT EXISTS FOOD(id_food INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOG)");

lo que estaba en el botton agregar
try {
                    sqLiteFood.insertData(
                            editTxtNombre.getText().toString().trim(),
                            editTxtPrecio.getText().toString().trim(),

                            imageViewToByte(imageView)
                    );
                    Toast.makeText(getApplicationContext(), "Agregar plato", Toast.LENGTH_SHORT).show();
                    editTxtNombre.setText("");
                    editTxtPrecio.setText("");
                    imageView.setImageResource(R.mipmap.ic_launcher);

                } catch (Exception e) {
                    e.printStackTrace();

                }

*/
