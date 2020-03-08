package com.iutlr.puissance4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iutlr.puissance4.exceptions.ColonneInvalideException;
import com.iutlr.puissance4.exceptions.ColonnePleineException;
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

    private LinearLayout plateauDeJeux;
    private List<Joueur> joueurs;
    private Plateau plateau;

    private int hauteur;
    private int largeur;
    private int nbJoueur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        Intent intent = getIntent();
        this.hauteur = Integer.parseInt(intent.getStringExtra("HAUTEUR"));
        this.largeur = Integer.parseInt(intent.getStringExtra("LARGEUR"));
        this.nbJoueur = intent.getIntExtra("NBJOUEUR",2);

        initGame();
    }

    private void nouvellePartie(){
        this.plateauDeJeux.removeAllViews();
        this.plateau = null;
        initGame();
    }
    private void initGame() {
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

        initPlateau();
    }

    private void initPlateau() {
        Drawable border = (Drawable) getDrawable(R.drawable.customborder);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1);
        for(int i = 0;i<plateau.getLargeur();i++){
            final LinearLayout colonne = new LinearLayout(this);
            colonne.setLayoutParams(params);
            colonne.setOrientation(LinearLayout.VERTICAL);
            colonne.setBackground(border);
            colonne.setGravity(Gravity.BOTTOM);
            final int fi = i;
            colonne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Joueur joueurCoutant = plateau.getJoueurCourant();
                        EtatPartie etatPartie = plateau.jouer(joueurCoutant,fi);
                        placementPion(joueurCoutant);
                        if (etatPartie.equals(EtatPartie.VICTOIRE)){
                            AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                            builder.setMessage("GG WP, "+plateau.getGagnant().getNom()+" tu as gagner,\n Voulez-vous rejouez avec les memes régalges");
                            builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent menuPrincipale = new Intent(StartActivity.this,ConfigurateActivity.class);
                                    startActivity(menuPrincipale);
                                    StartActivity.this.finish();
                                }
                            });
                            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    StartActivity.this.nouvellePartie();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        if (etatPartie.equals(EtatPartie.EGAL)){
                            AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                            builder.setMessage("Egalité, \n Voulez-vous rejouez avec les memes régalges");
                            builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent menuPrincipale = new Intent(StartActivity.this,ConfigurateActivity.class);
                                    startActivity(menuPrincipale);
                                    StartActivity.this.finish();
                                }
                            });
                            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    StartActivity.this.nouvellePartie();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    } catch (ColonneInvalideException e) {
                        Toast.makeText(StartActivity.this,"Vous ne pouvez pas jouer sur cette colonne",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    } catch (ColonnePleineException e) {
                        Toast.makeText(StartActivity.this,"Cette colonne est pleine",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    } catch (JoueurException e) {
                        Toast.makeText(StartActivity.this,"joueur invalide",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }

                private void placementPion(Joueur joueurCoutant) {
                    Drawable border = (Drawable) getDrawable(R.drawable.customborder);
                    ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, colonne.getHeight()/plateau.getHauteur());
                    ImageView img = new ImageView(StartActivity.this);
                    img.setLayoutParams(params);
                    img.setBackground(border);
                    switch (joueurCoutant.getImageResId()){
                        case R.id.img_joueur1:
                            img.setImageDrawable(imageJ1.getDrawable());
                            break;
                        case R.id.img_joueur2:
                            img.setImageDrawable(imageJ2.getDrawable());
                            break;
                        case R.id.img_joueur3:
                            img.setImageDrawable(imageJ3.getDrawable());
                            break;
                        case R.id.img_joueur4:
                            img.setImageDrawable(imageJ4.getDrawable());
                            break;
                        case R.id.img_joueur5:
                            img.setImageDrawable(imageJ5.getDrawable());
                            break;
                    }

                    colonne.addView(img,0);
                }
            });
            plateauDeJeux.addView(colonne);
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

        this.plateauDeJeux = findViewById(R.id.plateau);
    }
}
