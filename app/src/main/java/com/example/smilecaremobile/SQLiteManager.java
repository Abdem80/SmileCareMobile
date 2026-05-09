/****************************************
 Fichier : DatabaseHelper.java
 Auteur : Abdoulaye Dembele
 Fonctionnalité : Base de données locale SQLite
 Date : 2026-05-09
 Vérification :
 Date        Nom         Approuvé
 =========================================================
 Historique de modifications :
 Date        Nom         Description
 =========================================================
 ****************************************/

package com.example.smilecaremobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteManager extends SQLiteOpenHelper {
    private static final String NOM_BD  = "smilecare.db";
    private static final int    VERSION = 1;

    public SQLiteManager(Context context) {
        super(context, NOM_BD, null, VERSION);
    }

    private static final String CREATE_TABLE_ROLE =
            "CREATE TABLE Role (" +
                    "id_role INTEGER PRIMARY KEY, " +
                    "nom VARCHAR(100) NOT NULL, " +
                    "description VARCHAR(255)" +
                    ")";

    private static final String CREATE_TABLE_UTILISATEUR =
            "CREATE TABLE Utilisateur (" +
                    "id_utilisateur INTEGER PRIMARY KEY, " +
                    "nom VARCHAR(100) NOT NULL, " +
                    "prenom VARCHAR(100) NOT NULL, " +
                    "photo VARCHAR(255), " +
                    "date_naissance DATE, " +
                    "adresse VARCHAR(255) NOT NULL, " +
                    "telephone VARCHAR(20), " +
                    "mdp VARCHAR(255) NOT NULL, " +
                    "num_assurance VARCHAR(30), " +
                    "id_role INTEGER NOT NULL, " +
                    "FOREIGN KEY (id_role) REFERENCES Role(id_role)" +
                    ")";

    private static final String CREATE_TABLE_ETAT_RENDEZVOUS =
            "CREATE TABLE EtatRendezVous (" +
                    "id_etat INTEGER PRIMARY KEY, " +
                    "nom VARCHAR(50) NOT NULL, " +
                    "description VARCHAR(255)" +
                    ")";

    private static final String CREATE_TABLE_CATEGORIE_SERVICE =
            "CREATE TABLE CategorieService (" +
                    "id_categorie_service INTEGER PRIMARY KEY, " +
                    "nom VARCHAR(100) NOT NULL, " +
                    "description VARCHAR(255)" +
                    ")";

    private static final String CREATE_TABLE_SERVICE =
            "CREATE TABLE Service (" +
                    "id_service INTEGER PRIMARY KEY, " +
                    "nom VARCHAR(100) NOT NULL, " +
                    "description VARCHAR(255), " +
                    "duree INTEGER NOT NULL, " +
                    "id_categorie INTEGER NOT NULL, " +
                    "FOREIGN KEY (id_categorie) REFERENCES CategorieService(id_categorie_service)" +
                    ")";

    private static final String CREATE_TABLE_RENDEZVOUS =
            "CREATE TABLE RendezVous (" +
                    "id_rendezvous INTEGER PRIMARY KEY, " +
                    "commentaire VARCHAR(255), " +
                    "heure_date TIMESTAMP NOT NULL, " +
                    "id_etat INTEGER NOT NULL, " +
                    "id_client INTEGER NOT NULL, " +
                    "id_employee INTEGER NOT NULL, " +
                    "id_service INTEGER NOT NULL, " +
                    "FOREIGN KEY (id_etat) REFERENCES EtatRendezVous(id_etat), " +
                    "FOREIGN KEY (id_client) REFERENCES Utilisateur(id_utilisateur), " +
                    "FOREIGN KEY (id_service) REFERENCES Service(id_service)" +
                    ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ROLE);
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(CREATE_TABLE_ETAT_RENDEZVOUS);
        db.execSQL(CREATE_TABLE_CATEGORIE_SERVICE);
        db.execSQL(CREATE_TABLE_SERVICE);
        db.execSQL(CREATE_TABLE_RENDEZVOUS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS RendezVous");
        db.execSQL("DROP TABLE IF EXISTS Service");
        db.execSQL("DROP TABLE IF EXISTS CategorieService");
        db.execSQL("DROP TABLE IF EXISTS EtatRendezVous");
        db.execSQL("DROP TABLE IF EXISTS Utilisateur");
        db.execSQL("DROP TABLE IF EXISTS Role");
        onCreate(db);
    }

    public void insertUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase db = this.getWritableDatabase();
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
        db.close();
    }
}
