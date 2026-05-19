package com.example.smilecaremobile.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
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
import com.example.smilecaremobile.modeles.Service;
import com.example.smilecaremobile.modeles.ServicesAdapter;

import org.json.JSONException;

import java.util.ArrayList;

public class ServicesActivity extends AppCompatActivity implements View.OnClickListener{
    private String token;
    private ArrayList<Service> services = new ArrayList<Service>();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_services);

        //Initialisation du menu (toolbar)
        Toolbar toolbar = findViewById(R.id.menu);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_services);

        //Bouton retour
        Button btn_return = findViewById(R.id.rtr);
        btn_return.setOnClickListener(this);

        //Token
        token = getIntent().getStringExtra("token");

        //Requete API
        API api = new API();
        //API services
        api.get(new API.ApiCallback() {

            @Override
            public void onSuccess(String response) throws JSONException {
                runOnUiThread (() -> {
                    ArrayList<String> ids = JSONDataExtractor.ExtractList("id", response);
                    ArrayList<String> names = JSONDataExtractor.ExtractList("name", response);
                    ArrayList<String> descriptions = JSONDataExtractor.ExtractList("description", response);
                    ArrayList<String> durees = JSONDataExtractor.ExtractList("duree", response);
                    ArrayList<String> categories = JSONDataExtractor.ExtractList("id_type", response);

                    for (int i = 0; i < ids.size(); i++){
                        ServicesActivity.this.services.add(new Service(Integer.parseInt(ids.get(i)), names.get(i), descriptions.get(i),Integer.parseInt(durees.get(i)), categories.get(i)));

                        if (i + 1 == ids.size()){
                            System.out.println("INTO IF");
                            ServicesAdapter myAdapter = new ServicesAdapter(ServicesActivity.this, services);
                            recyclerView.setAdapter(myAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ServicesActivity.this));
                        }

                    }
                });
            }

            @Override
            public void onFailure(String error) {
                System.out.println("ERREUR [API SERVICES]");
                System.out.println(error);
            }
        }, "api/services", token);

        //API types services


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.rtr){
            finish();
        }
    }
}