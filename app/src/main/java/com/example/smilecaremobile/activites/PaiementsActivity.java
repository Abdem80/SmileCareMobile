package com.example.smilecaremobile.activites;

import android.content.Intent;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smilecaremobile.R;
import com.example.smilecaremobile.api.API;
import com.example.smilecaremobile.api.JSONDataExtractor;
import com.example.smilecaremobile.modeles.Paiement;
import com.example.smilecaremobile.modeles.PaiementAdapter;
import com.example.smilecaremobile.modeles.Service;
import com.example.smilecaremobile.modeles.ServicesAdapter;
import com.example.smilecaremobile.session.SessionManager;

import org.json.JSONException;

import java.util.ArrayList;

public class PaiementsActivity extends AppCompatActivity implements View.OnClickListener{
    private String token;
    private RecyclerView recyclerView;
    private ArrayList<Paiement> paiements = new ArrayList<Paiement>();
    private ArrayList<String> dates = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_paiements);

        //Initialisation du menu (toolbar)
        Toolbar toolbar = findViewById(R.id.menu);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_paiements);

        //Bouton retour
        Button btn_return = findViewById(R.id.rtr);
        btn_return.setOnClickListener(this);

        //Token
        token = getIntent().getStringExtra("token");

        //ID utilisateur
        SessionManager m = new SessionManager(this);

        Long id = m.getIdUtilisateur();

        //Requete API
        API api = new API();

        //API dates (rdv)
        api.get(new API.ApiCallback() {
            @Override
            public void onSuccess(String response) throws JSONException {
                runOnUiThread (() -> {
                    ArrayList<String> dates = JSONDataExtractor.ExtractList("heure_date", response);

                    for (int i = 0; i < dates.size(); i++){
                        PaiementsActivity.this.dates.add(dates.get(i).toString().split(" ")[0]);
                    }
                });
            }
            @Override
            public void onFailure(String error) {
                System.out.println("ERREUR [DATE PAIEMENTS]");
                System.out.println(error);
            }
        }, "api/rendezvous/user/1", token);

        //API paiements
        api.get(new API.ApiCallback() {

            @Override
            public void onSuccess(String response) throws JSONException {
                runOnUiThread (() -> {
                    ArrayList<String> ids = JSONDataExtractor.ExtractList("id", response);
                    ArrayList<String> montants = JSONDataExtractor.ExtractList("montant", response);
                    ArrayList<String> etats = JSONDataExtractor.ExtractList("etat", response);
                    ArrayList<String> types = JSONDataExtractor.ExtractList("type", response);

                    for (int i = 0; i < ids.size(); i++){
                        PaiementsActivity.this.paiements.add(new Paiement(Integer.parseInt(ids.get(i)), Float.parseFloat(montants.get(i)), PaiementsActivity.this.dates.get(i), etats.get(i), types.get(i)));

                        if (i + 1 == ids.size()){
                            System.out.println("INTO IF PAIEMENTS");
                            PaiementAdapter myAdapter = new PaiementAdapter(PaiementsActivity.this, paiements);
                            recyclerView.setAdapter(myAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(PaiementsActivity.this));
                        }

                    }
                });
            }

            @Override
            public void onFailure(String error) {
                System.out.println("ERREUR [PAIEMENTS]");
                System.out.println(error);
            }
        }, "api/paiements", token);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.menu_compte){
            Intent intent = new Intent(PaiementsActivity.this, ModifierProfilActivity.class);
            intent.putExtra("token", token);
            startActivity(intent);
            finish();
        }
        if(itemId == R.id.menu_logout){
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.supprimerSession();
            startActivity(new Intent(PaiementsActivity.this, LoginActivity.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.rtr){
            finish();
        }
    }
}