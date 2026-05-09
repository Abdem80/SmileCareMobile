package com.example.smilecaremobile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InscriptionActivity extends AppCompatActivity {
    private ImageView ivPhoto;
    private EditText etNom, etPrenom, etEmail, etMdp,
            etAdresse, etTelephone, etDateNaissance, etNumAssurance;
    private Button btnPhoto, btnInscrire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // Initialisation des vues
        ivPhoto          = findViewById(R.id.iv_photo);
        btnPhoto         = findViewById(R.id.btn_photo);
        etNom            = findViewById(R.id.et_nom);
        etPrenom         = findViewById(R.id.et_prenom);
        etEmail          = findViewById(R.id.et_email);
        etMdp            = findViewById(R.id.et_mdp);
        etAdresse        = findViewById(R.id.et_adresse);
        etTelephone      = findViewById(R.id.et_telephone);
        etDateNaissance  = findViewById(R.id.et_date_naissance);
        etNumAssurance   = findViewById(R.id.et_num_assurance);
        btnInscrire      = findViewById(R.id.btn_inscrire);

        // Gestion des événements
        btnInscrire.setOnClickListener(v -> {
            // Convertir String → Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date dateNaissance = null;
            try {
                dateNaissance = sdf.parse(etDateNaissance.getText().toString().trim());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (validerFormulaire()) {
                Utilisateur nouvelUtilisateur = new Utilisateur(
                        0,
                        etNom.getText().toString().trim(),
                        etPrenom.getText().toString().trim(),
                        "",
                        dateNaissance,
                        etAdresse.getText().toString().trim(),
                        etTelephone.getText().toString().trim(),
                        etMdp.getText().toString().trim(),
                        etNumAssurance.getText().toString().trim(),
                        4
                );
                SQLiteManager db = new SQLiteManager(this);
                db.insertUtilisateur(nouvelUtilisateur);

                // On va appeler l'API ici plus tard
            }
        });
    }

    private boolean validerFormulaire() {

        if (etNom.getText().toString().isEmpty()) {
            etNom.setError(getString(R.string.error_champ_requis));
            etNom.requestFocus();
            return false;
        }
        if (etPrenom.getText().toString().isEmpty()) {
            etPrenom.setError(getString(R.string.error_champ_requis));
            etPrenom.requestFocus();
            return false;
        }
        if (etEmail.getText().toString().isEmpty() || !etEmail.getText().toString().contains("@")) {
            etEmail.setError(getString(R.string.error_email_invalide));
            etEmail.requestFocus();
            return false;
        }
        if (etMdp.getText().toString().isEmpty()) {
            etMdp.setError(getString(R.string.error_champ_requis));
            etMdp.requestFocus();
            return false;
        }
        if (etAdresse.getText().toString().isEmpty()) {
            etAdresse.setError(getString(R.string.error_champ_requis));
            etAdresse.requestFocus();
            return false;
        }
        if (etDateNaissance.getText().toString().isEmpty() ||
                !etDateNaissance.getText().toString().matches("\\d{4}-\\d{2}-\\d{2}")) {
            etDateNaissance.setError(getString(R.string.error_date_invalide));
            etDateNaissance.requestFocus();
            return false;
        }

        return true;
    }
}