/****************************************
 Fichier : ProfilActivity.java
 Auteur : Abdoulaye Dembele
 Fonctionnalité : MGC02 — Afficher le profil du client connecté
 Date : 2026-05-10
 Vérification :
 Date        Nom         Approuvé
 =========================================================
 Historique de modifications :
 Date        Nom         Description
 =========================================================
 ****************************************/

package com.example.smilecaremobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activité d'affichage du profil du client connecté.
 * Récupère les données depuis la BD locale via SQLiteManager
 * en utilisant l'ID sauvegardé dans le fichier de session.
 */
public class ProfilActivity extends AppCompatActivity {

    private ImageView ivPhoto;
    private TextView tvNom, tvPrenom, tvEmail, tvAdresse, tvTelephone, tvAssurance;
    private Button btnDeconnexion;
    private Button btnModifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // Initialisation des vues
        ivPhoto      = findViewById(R.id.iv_profil_photo);
        tvNom        = findViewById(R.id.tv_profil_nom);
        tvPrenom     = findViewById(R.id.tv_profil_prenom);
        tvEmail      = findViewById(R.id.tv_profil_email);
        tvAdresse    = findViewById(R.id.tv_profil_adresse);
        tvTelephone  = findViewById(R.id.tv_profil_telephone);
        tvAssurance  = findViewById(R.id.tv_profil_assurance);
        btnDeconnexion = findViewById(R.id.btn_deconnexion);
        btnModifier = findViewById(R.id.btn_modifier);


        afficherProfil();

        btnDeconnexion.setOnClickListener(v -> deconnecter());

        btnModifier.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilActivity.this, ModifierProfilActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Récupère l'utilisateur connecté depuis la BD locale
     * et affiche ses informations dans les vues.
     */
    private void afficherProfil() {
        SessionManager session = new SessionManager(this);
        long idUtilisateur = session.getIdUtilisateur();

        if (idUtilisateur == -1) {
            Toast.makeText(this, getString(R.string.error_session), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        SQLiteManager db = SQLiteManager.instanceOfDatabase(this);
        Utilisateur client = db.getUtilisateur(idUtilisateur);

        if (client != null) {
            tvNom.setText(client.getNom());
            tvPrenom.setText(client.getPrenom());
            tvEmail.setText(client.getEmail());
            tvAdresse.setText(client.getAdresse());
            tvTelephone.setText(client.getTelephone());
            tvAssurance.setText(client.getNum_assurance());

            // Afficher la photo si elle existe
            if (client.getPhoto() != null && !client.getPhoto().isEmpty()) {
                ivPhoto.setImageURI(Uri.parse(client.getPhoto()));
            }
        } else {
            Toast.makeText(this, getString(R.string.error_charger_client), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Déconnecte l'utilisateur en supprimant le fichier de session
     * et redirige vers MainActivity.
     */
    private void deconnecter() {
        SessionManager session = new SessionManager(this);
        session.supprimerSession();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}