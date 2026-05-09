package com.example.smilecaremobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class UtilisateurDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public UtilisateurDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void insertUtilisateur(Utilisateur utilisateur) {
        ContentValues values = new ContentValues();

        values.put("nom", utilisateur.getNom());
        values.put("prenom", utilisateur.getPrenom());
        values.put("photo", utilisateur.getPhoto());
        if (utilisateur.getDate_naissance() != null) {
            values.put("date_naissance", utilisateur.getDate_naissance().getTime());
        }
        values.put("adresse", utilisateur.getAdresse());
        values.put("telephone", utilisateur.getTelephone());
        values.put("mdp", utilisateur.getMdp());
        values.put("num_assurance", utilisateur.getNum_assurance());
        values.put("id_role", utilisateur.getId_role());

        db.insert("Utilisateur", null, values);
    }
}
