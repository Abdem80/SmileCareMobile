/****************************************
 Fichier : SessionManager.java
 Auteur : Abdoulaye Dembele
 Fonctionnalité : MGC02 — Gestion de la session utilisateur
 Date : 2026-05-10
 Vérification :
 Date        Nom         Approuvé
 =========================================================
 Historique de modifications :
 Date        Nom         Description
 =========================================================
 ****************************************/

package com.example.smilecaremobile;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Gestionnaire de session utilisateur via fichier interne.
 * Sauvegarde, récupère et supprime la session du client connecté.
 * Basé sur le stockage de fichiers interne vu au cours 11.
 */
public class SessionManager {
    private static final String NOM_FICHIER = "session.txt";
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
    }


    /**
     * Sauvegarde l'ID de l'utilisateur connecté dans un fichier local.
     *
     * @param idUtilisateur ID de l'utilisateur connecté
     */
    public void sauvegarderSession(long idUtilisateur) {
        try {
            FileOutputStream fos = context.openFileOutput(NOM_FICHIER, Context.MODE_PRIVATE);
            fos.write(String.valueOf(idUtilisateur).getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère l'ID de l'utilisateur connecté depuis le fichier de session.
     *
     * @return L'ID de l'utilisateur connecté, ou -1 si aucune session
     */
    public long getIdUtilisateur() {
        try {
            FileInputStream fis = context.openFileInput(NOM_FICHIER);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String idStr = br.readLine();
            fis.close();
            return Long.parseLong(idStr);
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * Supprime le fichier de session lors de la déconnexion.
     */
    public void supprimerSession() {
        context.deleteFile(NOM_FICHIER);
    }

    /**
     * Vérifie si un utilisateur est connecté.
     *
     * @return {@code true} si une session existe, {@code false} sinon
     */
    public boolean estConnecte() {
        return context.getFileStreamPath(NOM_FICHIER).exists();
    }
}
