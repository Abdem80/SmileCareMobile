package com.example.smilecaremobile.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smilecaremobile.R;
import com.example.smilecaremobile.api.API;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class testAPI extends AppCompatActivity {

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_api);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        API api = new API();

        token = getIntent().getStringExtra("token");

        TextView tokenView = (TextView) findViewById(R.id.token);
        Button seeTokenBtn = (Button) findViewById(R.id.seeTokenBtn);
        seeTokenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokenView.setText(token);
            }
        });

        EditText idUser = (EditText) findViewById(R.id.idUser);
        Button getUserBtn = (Button) findViewById(R.id.getUserBtn);
        TextView resultUser = (TextView) findViewById(R.id.resultUser);
        getUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.get(new API.ApiCallback() {

                    @Override
                    public void onSuccess(String response) {
                        resultUser.setText(response);
                    }

                    @Override
                    public void onFailure(String error) {
                        resultUser.setText("Erreur: " + error);
                    }
                }, "api/utilisateur/" + idUser.getText().toString(), token);
            }
        });

        EditText nom = (EditText) findViewById(R.id.nom);
        EditText prenom = (EditText) findViewById(R.id.prenom);
        EditText id_role = (EditText) findViewById(R.id.id_role);
        EditText dateNaissance = (EditText) findViewById(R.id.dateNaissance);
        EditText adresse = (EditText) findViewById(R.id.adresse);
        EditText telephone = (EditText) findViewById(R.id.telephone);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        Button sendBtn = (Button) findViewById(R.id.createUser);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = "{\"name\":\"" + nom.getText().toString() + "\"," +
                        "\"prenom\":\"" + prenom.getText().toString() + "\"," +
                        "\"id_role\":\"" + id_role.getText().toString() + "\"," +
                        "\"dateNaissance\":\"" + dateNaissance.getText().toString() + "\"," +
                        "\"addresse\":\"" + adresse.getText().toString() + "\"," +
                        "\"telephone\":\"" + telephone.getText().toString() + "\"," +
                        "\"email\":\"" + email.getText().toString() + "\"," +
                        "\"password\":\"" + password.getText().toString() + "\"}";
                api.post(new API.ApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println(body);
                    }

                    @Override
                    public void onFailure(String error) {
                        System.out.println("Didn't work");
                    }
                }, "api/utilisateurAdd", body, token);
            }
        });
    }
}