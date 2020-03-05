package com.iutlr.puissance4;

import com.iutlr.puissance4.exceptions.JoueurException;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test(expected = JoueurException.class)
    public void notEnoughPlayersTest() throws Exception {
        List<Joueur> joueurs = new ArrayList<>();
        Plateau p = new Plateau(10, 10, joueurs);
    }


    @Test
    public void gameVictoryTestVertical() throws Exception {
        Joueur j1 = new Joueur("J1", 0);
        Joueur j2 = new Joueur("J2", 1);

        List<Joueur> joueurs = new ArrayList<>();
        joueurs.add(j1);
        joueurs.add(j2);

        Plateau p = new Plateau(10, 10, joueurs);

        p.jouer(j1,0);
        p.jouer(j2,1);
        p.jouer(j1,0);
        p.jouer(j2,1);
        p.jouer(j1,0);
        p.jouer(j2,1);
        p.jouer(j1,0);

        assertEquals(j1, p.getGagnant());
    }

    @Test
    public void gameVictoryTestHorizontal() throws Exception {
        Joueur j1 = new Joueur("J1", 0);
        Joueur j2 = new Joueur("J2", 1);

        List<Joueur> joueurs = new ArrayList<>();
        joueurs.add(j1);
        joueurs.add(j2);

        Plateau p = new Plateau(10, 10, joueurs);

        p.jouer(j1,0);
        p.jouer(j2,0);
        p.jouer(j1,1);
        p.jouer(j2,0);
        p.jouer(j1,2);
        p.jouer(j2,0);
        p.jouer(j1,3);

        assertEquals(j1, p.getGagnant());
    }

    @Test
    public void gameVictoryTestDiagonalHaut() throws Exception {
        Joueur j1 = new Joueur("J1", 0);
        Joueur j2 = new Joueur("J2", 1);

        List<Joueur> joueurs = new ArrayList<>();
        joueurs.add(j1);
        joueurs.add(j2);

        Plateau p = new Plateau(10, 10, joueurs);

        p.jouer(j1,0);
        p.jouer(j2,1);
        p.jouer(j1,1);
        p.jouer(j2,2);
        p.jouer(j1,2);
        p.jouer(j2,3);
        p.jouer(j1,2);
        p.jouer(j2,3);
        p.jouer(j1,3);
        p.jouer(j2,0);
        p.jouer(j1,3);

        assertEquals(j1, p.getGagnant());
    }

    @Test
    public void gameVictoryTestDiagonalBas() throws Exception {
        Joueur j1 = new Joueur("J1", 0);
        Joueur j2 = new Joueur("J2", 1);

        List<Joueur> joueurs = new ArrayList<>();
        joueurs.add(j1);
        joueurs.add(j2);

        Plateau p = new Plateau(10, 10, joueurs);

        p.jouer(j1,4);
        p.jouer(j2,3);

        p.jouer(j1,3);
        p.jouer(j2,2);

        p.jouer(j1,2);
        p.jouer(j2,1);

        p.jouer(j1,2);
        p.jouer(j2,1);

        p.jouer(j1,1);
        p.jouer(j2,5);

        p.jouer(j1,1);

        assertEquals(j1, p.getGagnant());
    }

    @Test
    public void gameEqual() throws Exception{
        Joueur j1 = new Joueur("J1", 0);
        Joueur j2 = new Joueur("J2", 1);

        List<Joueur> joueurs = new ArrayList<>();
        joueurs.add(j1);
        joueurs.add(j2);

        Plateau p = new Plateau(4, 5, joueurs);

        p.jouer(j1,0);
        p.jouer(j2,1);

        p.jouer(j1,0);
        p.jouer(j2,1);

        p.jouer(j1,0);
        p.jouer(j2,1);

        p.jouer(j1,1);
        p.jouer(j2,0);

        p.jouer(j1,0);
        p.jouer(j2,1);

        //
        p.jouer(j1,2);
        p.jouer(j2,3);

        p.jouer(j1,2);
        p.jouer(j2,3);

        p.jouer(j1,2);
        p.jouer(j2,3);

        p.jouer(j1,3);
        p.jouer(j2,2);

        assertEquals(EtatPartie.EN_COURS,p.jouer(j1,2));
        assertEquals(EtatPartie.EGAL,p.jouer(j2,3));

        assertNull(p.getGagnant());
    }
}