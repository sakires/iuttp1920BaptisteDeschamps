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
        while(this.plateau[colonne][i] != -1 ){
            if(i >= haureur){
                throw new ColonnePleineException();
            }
            i++;
        }
        this.plateau[colonne][i] = j.getImageResId();
        if (plateauPlein()){
            this.etatPartie = EtatPartie.EGAL;
        }
        if (victoire(j,colonne,i)){
            this.etatPartie = EtatPartie.VICTOIRE;
            this.gagnant = j;
        }
        joueurSuivant();
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

    private boolean victoire(Joueur j,int posX,int posY){

        if(victoireHorizontal(j, posX, posY)) return true;
        if(victoireVertical(j, posX, posY)) return true;
        if (victoireDiagonaleHaut(j, posX, posY)) return true;
        if (victoireDiagonaleBas(j, posX, posY)) return true;
        return false;
    }
    private boolean victoireHorizontal(Joueur j, int posX, int posY){
        int compteur = 0;
        int x = posX;
        // test victoire horizontal
        while (x<largeur && this.plateau[x][posY] == j.getImageResId()){
            compteur++;
            x++;
        }
        x = posX;
        while (x>=0 && this.plateau[x][posY] == j.getImageResId()){
            compteur++;
            x--;
        }
        return compteur>=4;
    }
    private boolean victoireVertical(Joueur j, int posX, int posY){
        int compteur = 0;
        int y = posY;
        //test victoire vertical
        while (y<haureur && this.plateau[posX][y] == j.getImageResId()){
            compteur++;
            y++;
        }
        y = posY;
        while (y>=0 && this.plateau[posX][y] == j.getImageResId()){
            compteur++;
            y--;
        }
        return compteur>=4;
    }
    private boolean victoireDiagonaleBas(Joueur j, int posX, int posY) {
        int compteur;
        int x = posX;
        int y = posY;
        compteur = 0;
        //testdiagonal bas
        while(x<largeur && y>=0 && plateau[x][y] == j.getImageResId()){
            compteur++;
            x++;
            y--;
        }
        x = posX;
        y = posY;
        while(x>=0 && y<haureur && plateau[x][y] == j.getImageResId()){
            compteur++;
            x--;
            y++;
        }
        return compteur>=4;
    }

    private boolean victoireDiagonaleHaut(Joueur j,int posX,int posY){
        int compteur = 0;
        int x = posX;
        int y = posY;
        //test diagonal haut
        while(x<largeur && y<haureur && plateau[x][y] == j.getImageResId()){
            compteur++;
            x++;
            y++;
        }
        x = posX;
        y = posY;
        while(x>=0 && y>=0 && plateau[x][y] == j.getImageResId()){
            compteur++;
            x--;
            y--;
        }
        return compteur>=4;
    }

    private boolean plateauPlein(){
        int x = 0;
        int y = 0;
        while(x < largeur){
            while (y < haureur){
                if(plateau[x][y] != -1) return false;
                y++;
            }
            x++;
            y = 0;
        }
        return true;
    }
    private void joueurSuivant(){
        this.posJoueurCourant = (posJoueurCourant + 1) % joueurs.size();
    }
}
