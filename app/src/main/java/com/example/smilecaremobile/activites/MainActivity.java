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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import com.example.smilecaremobile.R;
import com.example.smilecaremobile.api.API;
import com.example.smilecaremobile.api.API;
import com.example.smilecaremobile.api.JSONDataExtractor;
import com.example.smilecaremobile.modeles.RendezVous;
import com.example.smilecaremobile.modeles.RendezVousAdapter;
import com.example.smilecaremobile.modeles.ServicesAdapter;
import com.example.smilecaremobile.session.SessionManager;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String token;
    private ArrayList<RendezVous> rendezVous = new ArrayList<RendezVous>();
    private API api;

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

        //API
        api = new API();

        api.getToken(new API.ApiCallback() {
            @Override
            public void onSuccess(String response) throws JSONException {
                runOnUiThread(() -> {
                    MainActivity.this.token = response;

        //Initialisation des Rendez-Vous
                    SessionManager sessionManager = new SessionManager(MainActivity.this);
                    long id = sessionManager.getIdUtilisateur();
                    System.out.println(id);

                    api.get(new API.ApiCallback() {
                        @Override
                        public void onSuccess(String response) throws JSONException {
                            runOnUiThread(() -> {
                                String data = JSONDataExtractor.Extract("data", response);
                                ArrayList<String> idsRdv = JSONDataExtractor.ExtractList("id", data);
                                ArrayList<String> rdvService = JSONDataExtractor.ExtractList("service", data);
                                ArrayList<String> dhRdv = JSONDataExtractor.ExtractList("heure_date", data);
                                ArrayList<String> dRdv = JSONDataExtractor.ExtractList("dentiste", data);

                                System.out.println(idsRdv.toString());
                                System.out.println(rdvService.toString());
                                System.out.println(dhRdv.toString());

                                for (int i = 0; i < idsRdv.size(); i++) {
                                    MainActivity.this.rendezVous.add(new RendezVous(Integer.parseInt(idsRdv.get(i)), rdvService.get(i), dhRdv.get(i), dRdv.get(i)));
                                }

                                System.out.println("WORKED");
                                RecyclerView rdvRecycler = (RecyclerView) findViewById(R.id.rdvRecyclerView);

                                RendezVousAdapter rdvAdapter = new RendezVousAdapter(MainActivity.this, MainActivity.this.rendezVous);
                                rdvRecycler.setAdapter(rdvAdapter);
                                rdvRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            });
                        }

                        @Override
                        public void onFailure(String error) {
                            runOnUiThread(() -> {
                                System.out.println("Could not get rdv");
                            });
                        }
                    }, "api/rendezvous/user/" + 1, token);
                });
            }

            @Override
            public void onFailure(String error) {
                System.out.println(error);
            }
        });

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
        Intent intent = new Intent(MainActivity.this, MainActivity.class);

        if(v.getId()==R.id.btn_test_inscription){
            intent = new Intent(MainActivity.this, InscriptionActivity.class);
        }
        //if (v.getId()==R.id.main_btn_rdv_history){

        //}
        else if (v.getId()==R.id.main_btn_rdv_add){
            intent = new Intent(MainActivity.this, AddRendezVous.class);
        }
        //else if (v.getId()==R.id.main_btn_services){

        //}
        //else if (v.getId()==R.id.main_btn_rdv_add){

        //}
        else if (v.getId()==R.id.main_btn_services){
            intent = new Intent(MainActivity.this, ServicesActivity.class);
        }
        //else if (v.getId()==R.id.main_btn_local){

        //}
        intent.putExtra("token", token);
        startActivity(intent);
    }
}