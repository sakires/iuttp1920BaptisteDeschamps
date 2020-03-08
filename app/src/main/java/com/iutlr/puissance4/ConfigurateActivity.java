package com.iutlr.puissance4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.iutlr.puissance4.exceptions.JoueurException;
import com.iutlr.puissance4.exceptions.PlateauInvalideException;

import java.util.ArrayList;
import java.util.List;

public class ConfigurateActivity extends AppCompatActivity {


    private RadioGroup radioGroup;
    private Button btn_joueur;
    private EditText largeur,hauteur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurate);

        init();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case 0:
                        Toast.makeText(ConfigurateActivity.this,"2 j",Toast.LENGTH_LONG).show();
                    case 1:
                        Toast.makeText(ConfigurateActivity.this,"3 j",Toast.LENGTH_LONG).show();
                    case 2:
                        Toast.makeText(ConfigurateActivity.this,"4 j",Toast.LENGTH_LONG).show();
                    case 3:
                        Toast.makeText(ConfigurateActivity.this,"5 j",Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_joueur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nbJoueur = nbJoueur();
                if(nbJoueur == -1 || nbJoueur > Plateau.NOMBRE_JOUEUR_MAX  || nbJoueur < Plateau.NOMBRE_JOUEUR_MIN ){
                    Toast.makeText(ConfigurateActivity.this,ConfigurateActivity.this.getString(R.string.nbJoueurError),Toast.LENGTH_LONG).show();
                }else if(Integer.parseInt(hauteur.getText().toString()) < Plateau.HAUTEUR_MIN){
                    Toast.makeText(ConfigurateActivity.this,ConfigurateActivity.this.getString(R.string.hauteurPlateauError)+Plateau.HAUTEUR_MIN,Toast.LENGTH_LONG).show();
                }else if(Integer.parseInt(largeur.getText().toString()) < Plateau.LARGEUR_MIN){
                    Toast.makeText(ConfigurateActivity.this,ConfigurateActivity.this.getString(R.string.LargeurPlateauError)+Plateau.LARGEUR_MIN,Toast.LENGTH_LONG).show();
                } else{
                    Intent startPartie = new Intent(ConfigurateActivity.this,StartActivity.class);
                    // Rajouter les extra
                    startPartie.putExtra("NBJOUEUR",nbJoueur);
                    startPartie.putExtra("HAUTEUR",hauteur.getText().toString());
                    startPartie.putExtra("LARGEUR",largeur.getText().toString());
                    startActivity(startPartie);
                    finish();
                }
            }
        });
    }

    private void init(){
        btn_joueur  = (Button)      findViewById(R.id.play_Btn);
        radioGroup  = (RadioGroup)  findViewById(R.id.radioGroup);
        largeur     = (EditText)    findViewById(R.id.largeur);
        hauteur     = (EditText)    findViewById(R.id.hauteur);
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
