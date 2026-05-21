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

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        EditText etCourriel = findViewById(R.id.et_courriel);
        Button btnSuivant = findViewById(R.id.btn_suivant);

        btnSuivant.setOnClickListener(v -> {
            String courriel = etCourriel.getText().toString().trim();

            if (courriel.isEmpty() || !courriel.contains("@")) {
                etCourriel.setError("Courriel invalide");
                etCourriel.requestFocus();
                return;
            }

            JSONObject body = new JSONObject();
            try {
                body.put("courriel", courriel);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            new API().post(new API.ApiCallback() {
                @Override
                public void onSuccess(String response) {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(ForgotPasswordActivity.this, VerificationCodeActivity.class);
                        intent.putExtra("courriel", courriel);
                        startActivity(intent);
                    });
                }

                @Override
                public void onFailure(String error) {
                    runOnUiThread(() -> Toast.makeText(ForgotPasswordActivity.this,
                            "Courriel introuvable", Toast.LENGTH_SHORT).show());
                    Intent intent = new Intent(ForgotPasswordActivity.this, VerificationCodeActivity.class);
                    intent.putExtra("courriel", courriel);
                    startActivity(intent);
                }
            }, "api/motDePasseOublie", body.toString(), "");
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
