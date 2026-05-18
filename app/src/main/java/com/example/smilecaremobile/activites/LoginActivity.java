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
import com.example.smilecaremobile.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //charger les fields
        EditText email = findViewById(R.id.email_login);
        EditText password = findViewById(R.id.password_login);

        //charger les boutons
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnCreateAccount = findViewById(R.id.btn_create_account);
        Button btnForgotPassword = findViewById(R.id.btn_forgot_password);

        //setter les onclicklistener
        //bouton login
        btnLogin.setOnClickListener(v -> {
            String emailStr = email.getText().toString().trim();
            String passwordStr = password.getText().toString().trim();

            if (emailStr.isEmpty() || !emailStr.contains("@")) {
                email.setError("Courriel invalide");
                email.requestFocus();
                return;
            }
            if (passwordStr.isEmpty()) {
                password.setError("Mot de passe requis");
                password.requestFocus();
                return;
            }

            JSONObject body = new JSONObject();
            try {
                body.put("email", emailStr);
                body.put("password", passwordStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            new API().post(new API.ApiCallback() {
                @Override
                public void onSuccess(String response) throws JSONException {
                    JSONObject json = new JSONObject(response);
                    long userId = json.getLong("id");
                    SessionManager session = new SessionManager(LoginActivity.this);
                    session.sauvegarderSession(userId);
                    session.sauvegarderIdCentral(userId);
                    runOnUiThread(() -> {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    });
                }

                @Override
                public void onFailure(String error) {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this,
                            "Courriel ou mot de passe incorrect", Toast.LENGTH_SHORT).show());
                }
            }, "api/login", body.toString(), "");
        });

        //bouton creer un compte
        btnCreateAccount.setOnClickListener(v ->
                startActivity(new Intent(this, InscriptionActivity.class)));

        //bouton mot de passe oublie
        //btnForgotPassword.setOnClickListener(v -> startActivity(new Intent(this, ForgotPasswordActivity.class)));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}