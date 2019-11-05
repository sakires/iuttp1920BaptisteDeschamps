package com.iutlr.puissance4;

import android.support.annotation.Nullable;

class Joueur {

    private String nom;
    private int imageResId;
    /**
     * Crée un joueur
     * @param nom le nom du joueur
     * @param imageResId l'id de resource de l'image representant le joueur
     */
    public Joueur(String nom, int imageResId) {
        this.nom = nom;
        this.imageResId = imageResId;
    }

    /**
     * Retourne le nom du joueur
     * @return le nom du joueur
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Retourne l'id de resource de l'image representant le joueur
     * @return l'id de resource de l'image representant le joueur
     */
    public int getImageResId() {
        return this.imageResId;
    }


    public boolean equals(Joueur j) {
        return this.imageResId == j.imageResId;
    }
}
