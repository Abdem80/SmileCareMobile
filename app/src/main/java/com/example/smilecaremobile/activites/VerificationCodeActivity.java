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

public class VerificationCodeActivity extends AppCompatActivity {

    private String courriel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verification_code);

        courriel = getIntent().getStringExtra("courriel");

        EditText etCode = findViewById(R.id.et_code);
        Button btnEnvoyer = findViewById(R.id.btn_envoyer);
        Button btnVerifier = findViewById(R.id.btn_verifier);

        btnEnvoyer.setOnClickListener(v -> envoyerCode());

        btnVerifier.setOnClickListener(v -> {
            String code = etCode.getText().toString().trim();

            if (code.isEmpty()) {
                etCode.setError("Code requis");
                etCode.requestFocus();
                return;
            }

            JSONObject body = new JSONObject();
            try {
                body.put("courriel", courriel);
                body.put("code", code);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            new API().post(new API.ApiCallback() {
                @Override
                public void onSuccess(String response) {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(VerificationCodeActivity.this, NouveauMotDePasseActivity.class);
                        intent.putExtra("courriel", courriel);
                        startActivity(intent);
                    });
                }

                @Override
                public void onFailure(String error) {
                    runOnUiThread(() -> Toast.makeText(VerificationCodeActivity.this,
                            "Code invalide ou expiré", Toast.LENGTH_SHORT).show());
                }
            }, "api/verifierCode", body.toString(), "");
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void envoyerCode() {
        JSONObject body = new JSONObject();
        try {
            body.put("courriel", courriel);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new API().post(new API.ApiCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> Toast.makeText(VerificationCodeActivity.this,
                        "Code envoyé par courriel", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> Toast.makeText(VerificationCodeActivity.this,
                        "Erreur lors de l'envoi du code", Toast.LENGTH_SHORT).show());
            }
        }, "api/motDePasseOublie", body.toString(), "");
    }
}
