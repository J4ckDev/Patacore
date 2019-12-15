package edu.unicauca.patacore.view;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.adapter.PedidosNewAdapterRecycler;
import edu.unicauca.patacore.view.fragment.AddOrdenFragment;
import edu.unicauca.patacore.view.fragment.DelOrdenFragment;
import edu.unicauca.patacore.view.fragment.ListarOrdenFragment;
import edu.unicauca.patacore.view.fragment.MenuFragment;
import edu.unicauca.patacore.view.fragment.MesaFragment;
import edu.unicauca.patacore.view.fragment.NewOrdenFragment;

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        BottomNavigationView bottombar = findViewById(R.id.bottombar);


        bottombar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {


                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int item =menuItem.getItemId();
                        switch (item) {
                            case R.id.menuTab:
                                addFragment(new MenuFragment());
                                break;
                            case R.id.pedidoTab:
                                addFragment(new MesaFragment());
                                break;
                            case R.id.listarTab:
                                addFragment(new ListarOrdenFragment());
                                break;
                            case R.id.delTab:
                                addFragment(new DelOrdenFragment());
                                break;
                        }

                        return true;
                    }
                });
        bottombar.setSelectedItemId(R.id.menuTab);

    }

                    private void addFragment(Fragment fragment) {
                        if (null != fragment) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, fragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }

 //TOOLBAR
  //NO PROBLEM !!!!
    public void showToolbar(String title, boolean upBotton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upBotton);


    }
}
