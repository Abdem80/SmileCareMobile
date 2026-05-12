package com.example.smilecaremobile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    //Initialisation du menu (toolbar)
        Toolbar toolbar = findViewById(R.id.menu);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

    //Initialisation des boutons
        Button rdv_history = findViewById(R.id.main_btn_rdv_history);
        Button rdv_add = findViewById(R.id.main_btn_rdv_add);
        Button services = findViewById(R.id.main_btn_services);
        Button local = findViewById(R.id.main_btn_local);

        rdv_history.setOnClickListener(this);
        rdv_add.setOnClickListener(this);
        services.setOnClickListener(this);
        local.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.menu_compte){

        }
        if(itemId == R.id.menu_paiements){

        }
        if(itemId == R.id.menu_logout){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        if (v.getId()==R.id.main_btn_rdv_history){

        }
        else if (v.getId()==R.id.main_btn_rdv_add){

        }
        else if (v.getId()==R.id.main_btn_services){

        }
        else if (v.getId()==R.id.main_btn_local){

        }
    }
}