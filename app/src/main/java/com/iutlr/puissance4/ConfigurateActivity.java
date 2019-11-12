package com.iutlr.puissance4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ConfigurateActivity extends AppCompatActivity {


    private RadioGroup radioGroup;
    private Button btn_joueur;
    private EditText largeur,hauteur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurate);

        init();

        btn_joueur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nbJoueur = nbJoueur();
                //verifier aussi la hauteur et la largeur
                if(nbJoueur == -1 ){
                    // faire boite de dialog
                }else{
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
