package com.example.smilecaremobile.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smilecaremobile.R;
import com.example.smilecaremobile.api.API;

import org.json.JSONException;
import org.json.JSONObject;

public class NouveauMotDePasseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nouveau_mot_de_passe);

        String courriel = getIntent().getStringExtra("courriel");

        EditText etNouveauMdp = findViewById(R.id.et_nouveau_mdp);
        EditText etConfirmerMdp = findViewById(R.id.et_confirmer_mdp);
        Button btnConfirmer = findViewById(R.id.btn_confirmer);

        btnConfirmer.setOnClickListener(v -> {
            String nouveauMdp = etNouveauMdp.getText().toString().trim();
            String confirmerMdp = etConfirmerMdp.getText().toString().trim();

            if (nouveauMdp.isEmpty()) {
                etNouveauMdp.setError("Mot de passe requis");
                etNouveauMdp.requestFocus();
                return;
            }
            if (nouveauMdp.length() < 8) {
                etNouveauMdp.setError("Minimum 8 caractères");
                etNouveauMdp.requestFocus();
                return;
            }
            if (!nouveauMdp.equals(confirmerMdp)) {
                etConfirmerMdp.setError("Les mots de passe ne correspondent pas");
                etConfirmerMdp.requestFocus();
                return;
            }

            JSONObject body = new JSONObject();
            try {
                body.put("courriel", courriel);
                body.put("mot_de_passe", nouveauMdp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            new API().post(new API.ApiCallback() {
                @Override
                public void onSuccess(String response) {
                    runOnUiThread(() -> {
                        Toast.makeText(NouveauMotDePasseActivity.this,
                                "Mot de passe modifié avec succès", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NouveauMotDePasseActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    });
                }

                @Override
                public void onFailure(String error) {
                    runOnUiThread(() -> Toast.makeText(NouveauMotDePasseActivity.this,
                            "Erreur lors de la modification du mot de passe", Toast.LENGTH_SHORT).show());
                }
            }, "api/reinitialiserMotDePasse", body.toString(), "");
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
