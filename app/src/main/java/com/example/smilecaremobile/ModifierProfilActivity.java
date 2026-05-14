/****************************************
 Fichier : ModifierProfilActivity.java
 Auteur : Abdoulaye Dembele
 Fonctionnalité : MGC03 — Modifier le profil du client
 Date : 2026-05-10
 Vérification :
 Date        Nom         Approuvé
 =========================================================
 Historique de modifications :
 Date        Nom         Description
 =========================================================
 ****************************************/

package com.example.smilecaremobile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activité de modification du profil du client connecté.
 * Pré-remplit le formulaire avec les données actuelles depuis la BD locale.
 * Sauvegarde les modifications en BD locale et envoie à l'API Laravel.
 */
public class ModifierProfilActivity extends AppCompatActivity {

    private ImageView   ivPhoto;
    private EditText    etNom, etPrenom, etEmail, etAdresse, etTelephone, etNumAssurance;
    private Button      btnPhoto, btnSauvegarder;

    private static final int CODE_PERMISSION_CAMERA = 100;
    private static final int CODE_CAMERA            = 200;
    private Uri     photoUri;
    private Utilisateur clientActuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_profil);

        initVues();
        chargerProfil();

        btnPhoto.setOnClickListener(v -> lancerCamera());
        btnSauvegarder.setOnClickListener(v -> sauvegarder());
    }

    /**
     * Initialise les vues du formulaire.
     */
    private void initVues() {
        ivPhoto          = findViewById(R.id.iv_modifier_photo);
        btnPhoto         = findViewById(R.id.btn_modifier_photo);
        etNom            = findViewById(R.id.et_modifier_nom);
        etPrenom         = findViewById(R.id.et_modifier_prenom);
        etEmail          = findViewById(R.id.et_modifier_email);
        etAdresse        = findViewById(R.id.et_modifier_adresse);
        etTelephone      = findViewById(R.id.et_modifier_telephone);
        etNumAssurance   = findViewById(R.id.et_modifier_num_assurance);
        btnSauvegarder   = findViewById(R.id.btn_sauvegarder);
    }

    /**
     * Charge les données du client connecté depuis la BD locale
     * et pré-remplit le formulaire.
     */
    private void chargerProfil() {
        SessionManager session = new SessionManager(this);
        long id = session.getIdUtilisateur();

        SQLiteManager db = SQLiteManager.instanceOfDatabase(this);
        clientActuel = db.getUtilisateur(id);

        if (clientActuel != null) {
            etNom.setText(clientActuel.getNom());
            etPrenom.setText(clientActuel.getPrenom());
            etEmail.setText(clientActuel.getEmail());
            etAdresse.setText(clientActuel.getAdresse());
            etTelephone.setText(clientActuel.getTelephone());
            etNumAssurance.setText(clientActuel.getNum_assurance());

            if (clientActuel.getPhoto() != null && !clientActuel.getPhoto().isEmpty()) {
                ivPhoto.setImageURI(Uri.parse(clientActuel.getPhoto()));
            }
        }
    }

    /**
     * Valide les champs et sauvegarde les modifications
     * en BD locale et via l'API Laravel.
     */
    private void sauvegarder() {
        String nom    = etNom.getText().toString().trim();
        String prenom = etPrenom.getText().toString().trim();
        String email  = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(nom)) {
            etNom.setError(getString(R.string.error_champ_requis));
            etNom.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(prenom)) {
            etPrenom.setError(getString(R.string.error_champ_requis));
            etPrenom.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email) || !email.contains("@")) {
            etEmail.setError(getString(R.string.error_email_invalide));
            etEmail.requestFocus();
            return;
        }

        // Mettre à jour l'objet client
        clientActuel.setNom(nom);
        clientActuel.setPrenom(prenom);
        clientActuel.setEmail(email);
        clientActuel.setAdresse(etAdresse.getText().toString().trim());
        clientActuel.setTelephone(etTelephone.getText().toString().trim());
        clientActuel.setNum_assurance(etNumAssurance.getText().toString().trim());
        if (photoUri != null) {
            clientActuel.setPhoto(photoUri.toString());
        }

        SQLiteManager db = SQLiteManager.instanceOfDatabase(this);
        db.modifierUtilisateur(clientActuel);

        envoyerAPI();
    }

    /**
     * Envoie les modifications à l'API Laravel via PUT.
     */
    private void envoyerAPI() {
        API api = new API();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name",      clientActuel.getNom());
            jsonBody.put("prenom",    clientActuel.getPrenom());
            jsonBody.put("email",     clientActuel.getEmail());
            jsonBody.put("addresse",  clientActuel.getAdresse());
            jsonBody.put("telephone", clientActuel.getTelephone());
            jsonBody.put("id_role",   clientActuel.getId_role());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // TODO : Décommenter quand api.put() sera disponible dans API.java
        /*
        api.put(new API.ApiCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    Toast.makeText(ModifierProfilActivity.this,
                            getString(R.string.profil_modifie_succes),
                            Toast.LENGTH_SHORT).show();
                    finish();
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(ModilerProfilActivity.this,
                            getString(R.string.error_network),
                            Toast.LENGTH_SHORT).show();
                });
            }
        }, "api/utilisateurUpdate/" + clientActuel.getId_utilisateur(), jsonBody.toString(), token);
        */


        // Note : API.java ne supporte pas PUT pour l'instant
        // On affiche le succès de la BD locale
        Toast.makeText(this, getString(R.string.profil_modifie_succes), Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Vérifie la permission caméra et lance l'appareil photo.
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_PERMISSION_CAMERA &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intentCamera, CODE_CAMERA);
        }
    }

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
     * Convertit un Bitmap en URI.
     *
     * @param bitmap Image à convertir
     * @return URI de l'image
     */
    private Uri getImageUri(Bitmap bitmap) {
        java.io.ByteArrayOutputStream bytes = new java.io.ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(
                getContentResolver(), bitmap, "photo_profil", null);
        return Uri.parse(path);
    }
}