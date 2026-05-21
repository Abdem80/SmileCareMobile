package com.example.smilecaremobile.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smilecaremobile.R;
import com.example.smilecaremobile.api.API;
import com.example.smilecaremobile.api.JSONDataExtractor;
import com.example.smilecaremobile.session.SessionManager;

import org.json.JSONException;

import java.util.ArrayList;

public class AddRendezVous extends AppCompatActivity {

    private String token;
    private API api;

    private ArrayList<String> idsOfServices = new ArrayList<>();
    private ArrayList<String> idsOfDentistes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_rendez_vous);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Initialisation du menu (toolbar)
        Toolbar toolbar = findViewById(R.id.menu);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Button btn_return = findViewById(R.id.retour);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        api = new API();

        token = getIntent().getStringExtra("token");

        api.get(new API.ApiCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    ArrayList<String> options = new ArrayList<>();

                    ArrayList<String> names = JSONDataExtractor.ExtractList("name", response);
                    ArrayList<String> ids = JSONDataExtractor.ExtractList("id", response);
                    for(int i = 0; i < names.size(); i++) {
                        options.add(names.get(i));
                        AddRendezVous.this.idsOfServices.add(ids.get(i));
                    }
                    System.out.println(AddRendezVous.this.idsOfServices.toString());

                    Spinner servicesSpinner = (Spinner) findViewById(R.id.services);
                    ArrayAdapter<String> adapterServices = new ArrayAdapter<String>(AddRendezVous.this, android.R.layout.simple_spinner_item, options);
                    adapterServices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    servicesSpinner.setAdapter(adapterServices);
                });
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Services didn't work :[");
            }
        }, "api/services", token);

        api.get(new API.ApiCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    ArrayList<String> dentistes = new ArrayList<>();

                    ArrayList<String> names = JSONDataExtractor.ExtractList("name", response);
                    ArrayList<String> ids = JSONDataExtractor.ExtractList("id", response);
                    for(int i = 0; i < names.size(); i++) {
                        dentistes.add(names.get(i).toString());
                        AddRendezVous.this.idsOfDentistes.add(ids.get(i));
                    }

                    Spinner dentistesSpinner = (Spinner) findViewById(R.id.dentistes);
                    ArrayAdapter<String> adapterDentistes = new ArrayAdapter<String>(AddRendezVous.this, android.R.layout.simple_spinner_item, dentistes);
                    adapterDentistes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dentistesSpinner.setAdapter(adapterDentistes);
                });
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Dentistes didn't work :[");
            }
        }, "api/utilisateurs/4", token);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Spinner dentistesSpinner = (Spinner) findViewById(R.id.dentistes);
        Spinner servicesSpinner = (Spinner) findViewById(R.id.services);
        EditText dateRDV = (EditText) findViewById(R.id.dateRDV);
        EditText commentaires = (EditText) findViewById(R.id.commentaire);
        Button sendBtn = (Button) findViewById(R.id.sendRDV);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("ID service at " + servicesSpinner.getSelectedItemPosition() + " : " + idsOfServices.get(servicesSpinner.getSelectedItemPosition()));
                System.out.println("ID dentiste at " + dentistesSpinner.getSelectedItemPosition() + " : " + idsOfDentistes.get(dentistesSpinner.getSelectedItemPosition()));
                // On récupère l'ID de l'utilisateur connecté via le SessionManager.
                // getIdCentral() lit le fichier session_central.txt qui a été écrit lors du login.
                // maintenant c'est l'ID réel du client connecté.
                SessionManager sessionManager = new SessionManager(AddRendezVous.this);
                long idUtilisateur = sessionManager.getIdCentral();

                String body = "{\"id_user\":\"" + idUtilisateur + "\"," +
                                "\"id_dentiste\":\"" + idsOfDentistes.get(dentistesSpinner.getSelectedItemPosition()) + "\"," +
                                "\"id_etat\":\"" + 1 + "\"," +
                                "\"id_service\":\"" + idsOfServices.get(servicesSpinner.getSelectedItemPosition()) + "\"," +
                                "\"heure_date\":\"" + dateRDV.getText() + "\"," +
                                "\"commentaire\":\"" + commentaires.getText() + "\"}";

                api.post(new API.ApiCallback() {
                    @Override
                    public void onSuccess(String response) throws JSONException {
                        runOnUiThread(() -> {
                            System.out.println(body);
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        System.out.println(error);
                    }
                }, "api/rendezvous", body, token);
            }
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
            Intent intent = new Intent(AddRendezVous.this, ModifierProfilActivity.class);
            intent.putExtra("token", token);
            startActivity(intent);
        }
        if(itemId == R.id.menu_paiements){
            Intent intent = new Intent(AddRendezVous.this, PaiementsActivity.class);
            intent.putExtra("token", token);
            startActivity(intent);
            finish();
        }
        if(itemId == R.id.menu_logout){
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.supprimerSession();
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}