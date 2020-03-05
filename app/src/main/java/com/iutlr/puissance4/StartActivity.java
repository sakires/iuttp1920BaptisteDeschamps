package com.iutlr.puissance4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iutlr.puissance4.exceptions.JoueurException;
import com.iutlr.puissance4.exceptions.PlateauInvalideException;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    private TextView textJ1;
    private TextView textJ2;
    private TextView textJ3;
    private TextView textJ4;
    private TextView textJ5;

    private ImageView imageJ1;
    private ImageView imageJ2;
    private ImageView imageJ3;
    private ImageView imageJ4;
    private ImageView imageJ5;

    private List<Joueur> joueurs;
    private Plateau plateau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        Intent intent = getIntent();
        int hauteur = Integer.parseInt(intent.getStringExtra("HAUTEUR"));
        int largeur = Integer.parseInt(intent.getStringExtra("LARGEUR"));
        int nbJoueur = intent.getIntExtra("NBJOUEUR",2);

        initField();
        this.joueurs = new ArrayList<Joueur>();
        initJoueur(nbJoueur);

        try {
            this.plateau = new Plateau(largeur,hauteur,this.joueurs);
        } catch (PlateauInvalideException e) {
            e.printStackTrace();
        } catch (JoueurException e) {
            e.printStackTrace();
        }
    }

    private void initJoueur(int nbJoueur) {
        if(nbJoueur >= 2){
            this.textJ1.setVisibility(View.VISIBLE);
            this.imageJ1.setVisibility(View.VISIBLE);
            this.joueurs.add(new Joueur("Joueur 1",this.imageJ1.getId()));

            this.imageJ2.setVisibility(View.VISIBLE);
            this.textJ2.setVisibility(View.VISIBLE);
            this.joueurs.add(new Joueur("Joueur 2",this.imageJ2.getId()));
        }
        if (nbJoueur >= 3){
            this.textJ3.setVisibility(View.VISIBLE);
            this.imageJ3.setVisibility(View.VISIBLE);
            this.joueurs.add(new Joueur("Joueur 3",this.imageJ3.getId()));
        }
        if (nbJoueur >= 4){
            this.textJ4.setVisibility(View.VISIBLE);
            this.imageJ4.setVisibility(View.VISIBLE);
            this.joueurs.add(new Joueur("Joueur 4",this.imageJ4.getId()));
        }
        if (nbJoueur >= 5){
            this.textJ5.setVisibility(View.VISIBLE);
            this.imageJ5.setVisibility(View.VISIBLE);
            this.joueurs.add(new Joueur("Joueur 5",this.imageJ5.getId()));
        }
    }

    private void initField(){
        this.textJ1 = findViewById(R.id.textView_joueur1);
        this.textJ2 = findViewById(R.id.textView_joueur2);
        this.textJ3 = findViewById(R.id.textView_joueur3);
        this.textJ4 = findViewById(R.id.textView_joueur4);
        this.textJ5 = findViewById(R.id.textView_joueur5);

        this.imageJ1 = findViewById(R.id.img_joueur1);
        this.imageJ2 = findViewById(R.id.img_joueur2);
        this.imageJ3 = findViewById(R.id.img_joueur3);
        this.imageJ4 = findViewById(R.id.img_joueur4);
        this.imageJ5 = findViewById(R.id.img_joueur5);
    }
}
