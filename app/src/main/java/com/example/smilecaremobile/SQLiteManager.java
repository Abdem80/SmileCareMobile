/****************************************
 Fichier : SQLiteManager.java
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

/**
 * Gestionnaire de la base de données locale SQLite de SmileCare.
 * Étend SQLiteOpenHelper pour créer et gérer toutes les tables locales.
 * Utilisé pour stocker les données en mode hors ligne.
 */
public class SQLiteManager extends SQLiteOpenHelper {

    // -------------------------------------------------------
    // Infos de la base de données
    // -------------------------------------------------------
    private static final String NOM_BD  = "smilecare.db";
    private static final int    VERSION = 1;
    private static SQLiteManager sqLiteManager;

    // -------------------------------------------------------
    // Table : Role
    // -------------------------------------------------------
    public static final String TABLE_ROLE        = "Role";
    public static final String ROLE_ID           = "id_role";
    public static final String ROLE_NOM          = "nom";
    public static final String ROLE_DESCRIPTION  = "description";

    // -------------------------------------------------------
    // Table : Utilisateur
    // -------------------------------------------------------
    public static final String TABLE_UTILISATEUR      = "Utilisateur";
    public static final String UTIL_ID                = "id_utilisateur";
    public static final String UTIL_NOM               = "nom";
    public static final String UTIL_PRENOM            = "prenom";
    public static final String UTIL_PHOTO             = "photo";
    public static final String UTIL_DATE_NAISSANCE    = "date_naissance";
    public static final String UTIL_ADRESSE           = "adresse";
    public static final String UTIL_TELEPHONE         = "telephone";
    public static final String UTIL_MDP               = "mdp";
    public static final String UTIL_NUM_ASSURANCE     = "num_assurance";
    public static final String UTIL_ID_ROLE           = "id_role";

    // -------------------------------------------------------
    // Table : EtatRendezVous
    // -------------------------------------------------------
    public static final String TABLE_ETAT_RDV        = "EtatRendezVous";
    public static final String ETAT_ID               = "id_etat";
    public static final String ETAT_NOM              = "nom";
    public static final String ETAT_DESCRIPTION      = "description";

    // -------------------------------------------------------
    // Table : CategorieService
    // -------------------------------------------------------
    public static final String TABLE_CATEGORIE_SERVICE   = "CategorieService";
    public static final String CAT_ID                    = "id_categorie_service";
    public static final String CAT_NOM                   = "nom";
    public static final String CAT_DESCRIPTION           = "description";

    // -------------------------------------------------------
    // Table : Service
    // -------------------------------------------------------
    public static final String TABLE_SERVICE         = "Service";
    public static final String SERVICE_ID            = "id_service";
    public static final String SERVICE_NOM           = "nom";
    public static final String SERVICE_DESCRIPTION   = "description";
    public static final String SERVICE_DUREE         = "duree";
    public static final String SERVICE_ID_CATEGORIE  = "id_categorie";

    // -------------------------------------------------------
    // Table : RendezVous
    // -------------------------------------------------------
    public static final String TABLE_RDV             = "RendezVous";
    public static final String RDV_ID                = "id_rendezvous";
    public static final String RDV_COMMENTAIRE       = "commentaire";
    public static final String RDV_HEURE_DATE        = "heure_date";
    public static final String RDV_ID_ETAT           = "id_etat";
    public static final String RDV_ID_CLIENT         = "id_client";
    public static final String RDV_ID_EMPLOYEE       = "id_employee";
    public static final String RDV_ID_SERVICE        = "id_service";

    // -------------------------------------------------------
    // Constructeur
    // -------------------------------------------------------
    public SQLiteManager(Context context) {
        super(context, NOM_BD, null, VERSION);
    }

    /**
     * Retourne l'instance unique du SQLiteManager (pattern Singleton).
     * Crée l'instance si elle n'existe pas encore.
     *
     * @param context Le contexte Android
     * @return L'instance unique de SQLiteManager
     */
    public static SQLiteManager instanceOfDatabase(Context context) {
        if (sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);
        return sqLiteManager;
    }


    // -------------------------------------------------------
    // onCreate : création des tables
    // -------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {
        // --- Table Role ---
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(TABLE_ROLE + " (");
        sb.append(ROLE_ID + " INTEGER PRIMARY KEY, ");
        sb.append(ROLE_NOM + " VARCHAR(100) NOT NULL, ");
        sb.append(ROLE_DESCRIPTION + " VARCHAR(255)");
        sb.append(")");
        db.execSQL(sb.toString());

        // --- Table Utilisateur ---
        sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(TABLE_UTILISATEUR + " (");
        sb.append(UTIL_ID + " INTEGER PRIMARY KEY, ");
        sb.append(UTIL_NOM + " VARCHAR(100) NOT NULL, ");
        sb.append(UTIL_PRENOM + " VARCHAR(100) NOT NULL, ");
        sb.append(UTIL_PHOTO + " VARCHAR(255), ");
        sb.append(UTIL_DATE_NAISSANCE + " DATE, ");
        sb.append(UTIL_ADRESSE + " VARCHAR(255) NOT NULL, ");
        sb.append(UTIL_TELEPHONE + " VARCHAR(20), ");
        sb.append(UTIL_MDP + " VARCHAR(255) NOT NULL, ");
        sb.append(UTIL_NUM_ASSURANCE + " VARCHAR(30), ");
        sb.append(UTIL_ID_ROLE + " INTEGER NOT NULL, ");
        sb.append("FOREIGN KEY (" + UTIL_ID_ROLE + ") REFERENCES " + TABLE_ROLE + "(" + ROLE_ID + ")");
        sb.append(")");
        db.execSQL(sb.toString());

        // --- Table EtatRendezVous ---
        sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(TABLE_ETAT_RDV + " (");
        sb.append(ETAT_ID + " INTEGER PRIMARY KEY, ");
        sb.append(ETAT_NOM + " VARCHAR(50) NOT NULL, ");
        sb.append(ETAT_DESCRIPTION + " VARCHAR(255)");
        sb.append(")");
        db.execSQL(sb.toString());

        // --- Table CategorieService ---
        sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(TABLE_CATEGORIE_SERVICE + " (");
        sb.append(CAT_ID + " INTEGER PRIMARY KEY, ");
        sb.append(CAT_NOM + " VARCHAR(100) NOT NULL, ");
        sb.append(CAT_DESCRIPTION + " VARCHAR(255)");
        sb.append(")");
        db.execSQL(sb.toString());

        // --- Table Service ---
        sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(TABLE_SERVICE + " (");
        sb.append(SERVICE_ID + " INTEGER PRIMARY KEY, ");
        sb.append(SERVICE_NOM + " VARCHAR(100) NOT NULL, ");
        sb.append(SERVICE_DESCRIPTION + " VARCHAR(255), ");
        sb.append(SERVICE_DUREE + " INTEGER NOT NULL, ");
        sb.append(SERVICE_ID_CATEGORIE + " INTEGER NOT NULL, ");
        sb.append("FOREIGN KEY (" + SERVICE_ID_CATEGORIE + ") REFERENCES " + TABLE_CATEGORIE_SERVICE + "(" + CAT_ID + ")");
        sb.append(")");
        db.execSQL(sb.toString());

        // --- Table RendezVous ---
        sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(TABLE_RDV + " (");
        sb.append(RDV_ID + " INTEGER PRIMARY KEY, ");
        sb.append(RDV_COMMENTAIRE + " VARCHAR(255), ");
        sb.append(RDV_HEURE_DATE + " TIMESTAMP NOT NULL, ");
        sb.append(RDV_ID_ETAT + " INTEGER NOT NULL, ");
        sb.append(RDV_ID_CLIENT + " INTEGER NOT NULL, ");
        sb.append(RDV_ID_EMPLOYEE + " INTEGER NOT NULL, ");
        sb.append(RDV_ID_SERVICE + " INTEGER NOT NULL, ");
        sb.append("FOREIGN KEY (" + RDV_ID_ETAT + ") REFERENCES " + TABLE_ETAT_RDV + "(" + ETAT_ID + "), ");
        sb.append("FOREIGN KEY (" + RDV_ID_CLIENT + ") REFERENCES " + TABLE_UTILISATEUR + "(" + UTIL_ID + "), ");
        sb.append("FOREIGN KEY (" + RDV_ID_SERVICE + ") REFERENCES " + TABLE_SERVICE + "(" + SERVICE_ID + ")");
        sb.append(")");
        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // if (oldVersion < 2) {
        //     db.execSQL("ALTER TABLE " + TABLE_UTILISATEUR + " ADD COLUMN courriel VARCHAR(255)");
        // }
        // if (oldVersion < 3) {
        //     db.execSQL("ALTER TABLE " + TABLE_RDV + " ADD COLUMN note INTEGER");
        // }
    }

    // -------------------------------------------------------
    // Méthode : insertUtilisateur
    // -------------------------------------------------------
    /**
     * Insère un nouvel utilisateur dans la base de données locale.
     *
     * @param utilisateur L'objet Utilisateur à insérer
     */
    public void insertUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UTIL_NOM, utilisateur.getNom());
        values.put(UTIL_PRENOM, utilisateur.getPrenom());
        values.put(UTIL_PHOTO, utilisateur.getPhoto());
        if (utilisateur.getDate_naissance() != null) {
            values.put(UTIL_DATE_NAISSANCE, utilisateur.getDate_naissance().getTime());
        }
        values.put(UTIL_ADRESSE, utilisateur.getAdresse());
        values.put(UTIL_TELEPHONE, utilisateur.getTelephone());
        values.put(UTIL_MDP, utilisateur.getMdp());
        values.put(UTIL_NUM_ASSURANCE, utilisateur.getNum_assurance());
        values.put(UTIL_ID_ROLE, utilisateur.getId_role());

        db.insert(TABLE_UTILISATEUR, null, values);
        db.close();
    }
}