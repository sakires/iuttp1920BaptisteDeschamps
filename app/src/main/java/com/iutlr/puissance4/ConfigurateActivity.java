package com.iutlr.puissance4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iutlr.puissance4.exceptions.JoueurException;
import com.iutlr.puissance4.exceptions.PlateauInvalideException;

import java.util.ArrayList;
import java.util.List;

public class ConfigurateActivity extends AppCompatActivity {


    private RadioGroup radioGroup;
    private Button btn_joueur;
    private EditText largeur,hauteur;
    private LinearLayout parametrageLayout;
    private ArrayList<EditText> listeParametreJoueur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurate);
        listeParametreJoueur = new ArrayList<EditText>();
        init();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                parametrageLayout.removeAllViews();
                listeParametreJoueur.clear();
                switch (checkedId){
                    case R.id.r2 :
                        createJoueurParametre(2);
                        break;
                    case R.id.r3 :
                        createJoueurParametre(3);
                        break;
                    case R.id.r4 :
                        createJoueurParametre(4);
                        break;
                    case R.id.r5 :
                        createJoueurParametre(5);
                        break;
                    default:
                        break;
                }
            }

            private void createJoueurParametre(int nbJoueur){
                for(int i=1 ; i<=nbJoueur ; i++){
                    EditText joueurName = new EditText(ConfigurateActivity.this);
                    ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1);
                    joueurName.setLayoutParams(params);
                    joueurName.setHint("Joueur "+i);
                    listeParametreJoueur.add(joueurName);
                    parametrageLayout.addView(joueurName);
                }
            }
        });
        btn_joueur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> listeNom = new ArrayList<String>();
                int nbJoueur = nbJoueur();
                if(nbJoueur == -1 || nbJoueur > Plateau.NOMBRE_JOUEUR_MAX  || nbJoueur < Plateau.NOMBRE_JOUEUR_MIN ){
                    Toast.makeText(ConfigurateActivity.this,ConfigurateActivity.this.getString(R.string.nbJoueurError),Toast.LENGTH_LONG).show();
                }else if(hauteur.getText().toString().equals("") || Integer.parseInt(hauteur.getText().toString()) < Plateau.HAUTEUR_MIN){
                    Toast.makeText(ConfigurateActivity.this,ConfigurateActivity.this.getString(R.string.hauteurPlateauError)+Plateau.HAUTEUR_MIN,Toast.LENGTH_LONG).show();
                }else if(largeur.getText().toString().equals("") || Integer.parseInt(largeur.getText().toString()) < Plateau.LARGEUR_MIN){
                    Toast.makeText(ConfigurateActivity.this,ConfigurateActivity.this.getString(R.string.LargeurPlateauError)+Plateau.LARGEUR_MIN,Toast.LENGTH_LONG).show();
                } else{
                    Intent startPartie = new Intent(ConfigurateActivity.this,StartActivity.class);
                    // Rajouter les extra
                    startPartie.putExtra("NBJOUEUR",nbJoueur);
                    startPartie.putExtra("HAUTEUR",hauteur.getText().toString());
                    startPartie.putExtra("LARGEUR",largeur.getText().toString());
                    for(int i = 0;i<listeParametreJoueur.size();i++){
                        if (listeParametreJoueur.get(i).getText().toString().equals("")){
                            listeNom.add(listeParametreJoueur.get(i).getHint().toString());
                        }else{
                            listeNom.add(listeParametreJoueur.get(i).getText().toString());
                        }

                    }
                    startPartie.putExtra("NOMJOUEUR",listeNom.toArray(new String[0]));
                    startActivity(startPartie);
                    finish();
                }
            }
        });
    }

    private void init(){
        btn_joueur          = (Button)      findViewById(R.id.play_Btn);
        radioGroup          = (RadioGroup)  findViewById(R.id.radioGroup);
        largeur             = (EditText)    findViewById(R.id.largeur);
        hauteur             = (EditText)    findViewById(R.id.hauteur);
        parametrageLayout   = (LinearLayout) findViewById(R.id.parametreJoueur);
    }
    private int nbJoueur(){
        int idRadioChecked = radioGroup.getCheckedRadioButtonId();
        int nbJoueur;
        switch (idRadioChecked){
            case R.id.r2 :
                nbJoueur = 2;
                break;
            case R.id.r3 :
                nbJoueur = 3;
                break;
            case R.id.r4 :
                nbJoueur = 4;
                break;
            case R.id.r5 :
                nbJoueur = 5;
                break;
            default:
                nbJoueur = -1;
                break;
        }
        return nbJoueur;
    }
}
