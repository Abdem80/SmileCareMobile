/****************************************
 Fichier : InscriptionActivity.java
 Auteur : Abdoulaye Dembele
 Fonctionnalité : MGC01 — Créer compte client
 Date : 2026-05-09
 Vérification :
 Date        Nom         Approuvé
 =========================================================
 Historique de modifications :
 Date        Nom         Description
 =========================================================
 ****************************************/

package com.example.smilecaremobile.activites;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smilecaremobile.R;
import com.example.smilecaremobile.api.API;
import com.example.smilecaremobile.database.SQLiteManager;
import com.example.smilecaremobile.modeles.Utilisateur;
import com.example.smilecaremobile.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Activité d'inscription d'un nouveau client dans SmileCare.
 * Gère la validation du formulaire, la sauvegarde en BD locale
 * et l'envoi des données à l'API Laravel via POST /api/utilisateurAdd.
 */
public class InscriptionActivity extends AppCompatActivity {
    private ImageView ivPhoto;
    private EditText etNom, etPrenom, etEmail, etMdp,
            etAdresse, etTelephone, etDateNaissance, etNumAssurance;
    private Button btnPhoto, btnInscrire;

    private static final int CODE_PERMISSION_CAMERA = 100;
    private static final int CODE_CAMERA = 200;
    private Uri photoUri;

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

        btnPhoto.setOnClickListener(v -> lancerCamera());

        btnInscrire.setOnClickListener(v -> {
            // Convertir String -> Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date dateNaissance = null;
            try {
                dateNaissance = sdf.parse(etDateNaissance.getText().toString().trim());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String cheminPhoto = photoUri != null ? photoUri.toString() : "";

            if (validerFormulaire()) {
                Utilisateur nouvelUtilisateur = new Utilisateur(
                        0,
                        etNom.getText().toString().trim(),
                        etPrenom.getText().toString().trim(),
                        etEmail.getText().toString().trim(),
                        cheminPhoto,
                        dateNaissance,
                        etAdresse.getText().toString().trim(),
                        etTelephone.getText().toString().trim(),
                        etMdp.getText().toString().trim(),
                        etNumAssurance.getText().toString().trim(),
                        4
                );
                SQLiteManager db = SQLiteManager.instanceOfDatabase(this);

                long idInsere = db.insertUtilisateur(nouvelUtilisateur);
                SessionManager sessionManager = new SessionManager(this);
                sessionManager.sauvegarderSession(idInsere);

                API api = new API();

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("name", nouvelUtilisateur.getNom());
                    jsonBody.put("prenom", nouvelUtilisateur.getPrenom());
                    jsonBody.put("email", nouvelUtilisateur.getEmail());
                    jsonBody.put("password", nouvelUtilisateur.getMdp());
                    jsonBody.put("id_role", 4);
                    jsonBody.put("dateNaissance", etDateNaissance.getText().toString().trim());
                    jsonBody.put("addresse", nouvelUtilisateur.getAdresse());
                    jsonBody.put("telephone", nouvelUtilisateur.getTelephone());
                    jsonBody.put("num_assurance", nouvelUtilisateur.getNum_assurance());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                api.post(new API.ApiCallback() {
                    @Override
                    public void onSuccess(String response) throws JSONException {
                        JSONObject json = new JSONObject(response);
                        long idCentral = json.getLong("id");
                        sessionManager.sauvegarderIdCentral(idCentral);
                        runOnUiThread(() -> {
                            Toast.makeText(InscriptionActivity.this,
                                    getString(R.string.inscription_succes),
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(InscriptionActivity.this, ProfilActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        runOnUiThread(() -> {
                            Toast.makeText(InscriptionActivity.this,
                                    getString(R.string.error_network),
                                    Toast.LENGTH_SHORT).show();
                        });
                    }
                }, "api/utilisateurAdd", jsonBody.toString(), "");
            }
        });
    }

    /**
     * Valide les champs du formulaire d'inscription.
     * Vérifie que les champs obligatoires sont remplis et que
     * le format du courriel et de la date de naissance sont corrects.
     *
     * @return {@code true} si tous les champs sont valides, {@code false} sinon
     */
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

    /**
     * Vérifie si la permission caméra est accordée.
     * Si oui, lance l'appareil photo. Sinon, demande la permission.
     */
    private void lancerCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intentCamera, CODE_CAMERA);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CODE_PERMISSION_CAMERA);
        }
    }

    /**
     * Gère la réponse de l'utilisateur après la demande de permission caméra.
     *
     * @param requestCode  Code de la permission demandée
     * @param permissions  Liste des permissions demandées
     * @param grantResults Résultats des permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CODE_PERMISSION_CAMERA) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, CODE_CAMERA);
            } else {
                Toast.makeText(this,
                        getString(R.string.camera_permission_denied),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Récupère la photo prise par la caméra et l'affiche dans l'ImageView.
     *
     * @param requestCode Code de la requête
     * @param resultCode  Résultat de l'activité
     * @param data        Données retournées par la caméra
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivPhoto.setImageBitmap(photo);
            photoUri = getImageUri(photo);
        }
    }

    /**
     * Convertit un Bitmap en URI pour pouvoir l'envoyer à l'API.
     *
     * @param bitmap Image à convertir
     * @return URI de l'image
     */
    private Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(
                getContentResolver(), bitmap, "photo_profil", null);
        return Uri.parse(path);
    }
}