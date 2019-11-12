package com.iutlr.puissance4;

import com.iutlr.puissance4.exceptions.ColonneInvalideException;
import com.iutlr.puissance4.exceptions.ColonnePleineException;
import com.iutlr.puissance4.exceptions.JoueurException;
import com.iutlr.puissance4.exceptions.PlateauInvalideException;

import java.util.List;

public class Plateau {


    private int largeur;
    private int haureur;
    private List<Joueur> joueurs;
    private EtatPartie etatPartie;
    private int posJoueurCourant;
    // [largeur][hauteur]
    private int[][] plateau;
    private Joueur gagnant;
    /**
     * Construit un nouveau plateau de jeu vide
     * @param largeur la largeur du plateau
     * @param hauteur la hauteur du plateau
     * @param joueurs la liste des joueurs
     * @throws PlateauInvalideException si la taille du plateau est incorrecte (mini 4x4)
     * @throws JoueurException si le nombre de joueurs est invalide (5 max)
     */
    public Plateau(int largeur, int hauteur, List<Joueur> joueurs) throws PlateauInvalideException, JoueurException {
        if (joueurs.size()> 5  || joueurs.size() < 2) {
            throw new JoueurException();
        }
        if (largeur < 4 || hauteur < 4 ){
            throw new PlateauInvalideException();
        }
        this.etatPartie = EtatPartie.EN_COURS;
        this.joueurs = joueurs;
        this.largeur = largeur;
        this.haureur = hauteur;
        this.plateau = new int[largeur][hauteur];
        this.posJoueurCourant = 0;

        initPlateau();
    }

    /**
     * Retourne la largeur du plateau
     * @return la largeur du plateau
     */
    public int getLargeur() {
        // TODO
        return this.largeur;
    }

    /**
     * Retourne la hauteur du plateau
     * @return la hauteur du plateau
     */
    public int getHauteur() {
        // TODO
        return this.haureur;
    }

    /**
     * Retourne le joueur qui doit jouer
     * @return le joueur qui doit jouer
     * @throws JoueurException si personne ne doit jouer (fin de partie...)
     */
    public Joueur getJoueurCourant() throws JoueurException {
       if (this.posJoueurCourant < 0 || this.posJoueurCourant > this.joueurs.size()){
           throw new JoueurException();
       }else {
           return this.joueurs.get(this.posJoueurCourant);
       }

    }

    /**
     * Joue une position
     * @param j le joueur jouant la position
     * @param colonne l'indexe de la colonne jouée
     * @throws ColonneInvalideException si l'index de la colonne n'existe pas
     * @throws ColonnePleineException si la colonne jouée est déjà pleine
     * @throws JoueurException si ce n'est pas a ce joueur de jouer
     */
    public EtatPartie jouer(Joueur j, int colonne) throws ColonneInvalideException, ColonnePleineException, JoueurException {
        if (!j.equals(this.joueurs.get(this.posJoueurCourant))){
            throw new JoueurException();
        }
        if(this.largeur < colonne){
            throw new ColonneInvalideException();
        }
        int i = 0;
        while(this.plateau[colonne][i] == -1 ){
            i++;
            if(i >= haureur){
                throw new ColonnePleineException();
            }
        }
        this.plateau[colonne][i] = j.getImageResId();

        return this.etatPartie;
    }

    /**
     * Indique le gagnant d'une partie
     * @return le gagnant si un joueur a déjà gagné, null sinon
     */
    public Joueur getGagnant() {
        if (this.etatPartie == EtatPartie.VICTOIRE){
            return this.gagnant;
        }else {
            return null;
        }
    }


    /**
     * permet d'initialisé le plateau
     */
    private void initPlateau(){
        for (int i =0;i<largeur;i++){
            for(int j=0;j<haureur;j++){
                plateau[i][j] = -1;
;            }
        }
    }
}
