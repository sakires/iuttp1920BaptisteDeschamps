package com.iutlr.puissance4;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iutlr.puissance4.exceptions.JoueurException;
import com.iutlr.puissance4.exceptions.PlateauInvalideException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ConfigurateActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private Button btn_joueur;
    private EditText largeur,hauteur;
    private LinearLayout parametrageLayout;
    private ArrayList<EditText> listeParametreJoueur;
    private ArrayList<ImageView> listImageView;
    private ArrayList<String> listPathImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurate);
        listeParametreJoueur = new ArrayList<EditText>();
        listImageView = new ArrayList<ImageView>();
        listPathImage = new ArrayList<String>();
        init();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                parametrageLayout.removeAllViews();
                listeParametreJoueur.clear();
                listImageView.clear();
                listPathImage.clear();
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
                for(int i=0 ; i<nbJoueur ; i++){
                    LinearLayout paramJoueurs = new LinearLayout(ConfigurateActivity.this);
                    paramJoueurs.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
                    paramJoueurs.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView imgjoueur = new ImageView(ConfigurateActivity.this);
                    imgjoueur.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250,4));
                    imgjoueur.setBackgroundColor(getColorBase(i));
                    final int finalI = i;
                    imgjoueur.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), finalI);
                        }
                    });

                    EditText joueurName = new EditText(ConfigurateActivity.this);
                    joueurName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
                    joueurName.setHint("Joueur "+(i+1));
                    listeParametreJoueur.add(joueurName);
                    listImageView.add(imgjoueur);
                    paramJoueurs.addView(imgjoueur);
                    paramJoueurs.addView(joueurName);
                    parametrageLayout.addView(paramJoueurs);
                }
            }

            private int getColorBase(int numeroJoueur){
                switch (numeroJoueur){
                    case 0:
                        listPathImage.add("RED");
                        return Color.RED;
                    case 1:
                        listPathImage.add("BLUE");
                        return Color.BLUE;
                    case 2:
                        listPathImage.add("GREEN");
                        return Color.GREEN;
                    case 3:
                        listPathImage.add("YELLOW");
                        return Color.YELLOW;
                    case 4:
                        listPathImage.add("GRAY");
                        return Color.GRAY;
                    default:
                        listPathImage.add("BLACK");
                        return Color.BLACK;
                }
            }
        });
        btn_joueur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> listeNom = new ArrayList<String>();
                ArrayList<Drawable> listImage = new ArrayList<>();
                int nbJoueur = nbJoueur();
                if(nbJoueur == -1 || nbJoueur > Plateau.NOMBRE_JOUEUR_MAX  || nbJoueur < Plateau.NOMBRE_JOUEUR_MIN ){
                    Toast.makeText(ConfigurateActivity.this,ConfigurateActivity.this.getString(R.string.nbJoueurError),Toast.LENGTH_LONG).show();
                }else if(hauteur.getText().toString().equals("") || Integer.parseInt(hauteur.getText().toString()) < Plateau.HAUTEUR_MIN){
                    Toast.makeText(ConfigurateActivity.this,ConfigurateActivity.this.getString(R.string.hauteurPlateauError)+Plateau.HAUTEUR_MIN,Toast.LENGTH_LONG).show();
                }else if(largeur.getText().toString().equals("") || Integer.parseInt(largeur.getText().toString()) < Plateau.LARGEUR_MIN){
                    Toast.makeText(ConfigurateActivity.this,ConfigurateActivity.this.getString(R.string.LargeurPlateauError)+Plateau.LARGEUR_MIN,Toast.LENGTH_LONG).show();
                } else{
                    Intent startPartie = new Intent(ConfigurateActivity.this,StartActivity.class);
                    startPartie.putExtra(ConstanteIntent.NBJOUEUR,nbJoueur);
                    startPartie.putExtra(ConstanteIntent.HAUTEUR,hauteur.getText().toString());
                    startPartie.putExtra(ConstanteIntent.LARGEUR,largeur.getText().toString());
                    for(int i = 0;i<listeParametreJoueur.size();i++){
                        if (listeParametreJoueur.get(i).getText().toString().equals("")){
                            listeNom.add(listeParametreJoueur.get(i).getHint().toString());
                        }else{
                            listeNom.add(listeParametreJoueur.get(i).getText().toString());
                        }
                    }

                    startPartie.putExtra(ConstanteIntent.NOMJOUEUR,listeNom.toArray(new String[0]));
                    startPartie.putExtra(ConstanteIntent.IMAGEJOUEUR,listPathImage.toArray(new String[0]));

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode >= 0 && requestCode < 5 && resultCode == RESULT_OK && null != data) {
            try {
                Uri selectedImage = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                Drawable imageSelect = Drawable.createFromStream(inputStream, selectedImage.toString() );
                listImageView.get(requestCode).setImageDrawable(imageSelect);
                listImageView.get(requestCode).setBackgroundColor(Color.TRANSPARENT);
                listPathImage.set(requestCode,selectedImage.toString());
            } catch (FileNotFoundException e) {
            }
        }
    }
}
