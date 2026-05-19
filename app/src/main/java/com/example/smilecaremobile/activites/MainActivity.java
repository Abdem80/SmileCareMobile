package com.example.smilecaremobile.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smilecaremobile.R;
import com.example.smilecaremobile.session.SessionManager;

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
        ImageButton rdv_history = findViewById(R.id.main_btn_rdv_history);
        ImageButton rdv_add = findViewById(R.id.main_btn_rdv_add);
        Button services = findViewById(R.id.main_btn_services);
        ImageButton local = findViewById(R.id.main_btn_local);
        Button apiTestBtn = (Button) findViewById(R.id.button);
        Button btnInscription = findViewById(R.id.btn_test_inscription);

        rdv_history.setOnClickListener(this);
        rdv_add.setOnClickListener(this);
        services.setOnClickListener(this);
        local.setOnClickListener(this);
        apiTestBtn.setOnClickListener(this);
        btnInscription.setOnClickListener(this);

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
        //if(itemId == R.id.menu_compte){

        //}
        //if(itemId == R.id.menu_paiements){

        //}
        if(itemId == R.id.menu_logout){
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.supprimerSession();
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.button){
            Intent intent = new Intent(MainActivity.this, testAPI.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.btn_test_inscription){
            Intent intent = new Intent(MainActivity.this, InscriptionActivity.class);
            startActivity(intent);
        }
        //if (v.getId()==R.id.main_btn_rdv_history){

        //}
        //else if (v.getId()==R.id.main_btn_rdv_add){

        //}
        //else if (v.getId()==R.id.main_btn_services){

        //}
        //else if (v.getId()==R.id.main_btn_local){

        //}
    }
}