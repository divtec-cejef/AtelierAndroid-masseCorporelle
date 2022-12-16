package com.example.massecorporelle_mathy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.math.BigDecimal;
//Récupération des composants importants edits text et bt


public class MainActivity extends AppCompatActivity {
    //Déclaration variable global
    private EditText ET_saisieTaille;
    private EditText ET_saisiePoids;
    private Button BT_effacer;
    private Button BT_calculer;

    @Override
    //Fait appel à la méthode du parent
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //On ne peut pas ajouter des choses / manipuler
        //On va récupérer l'id dans l'identifiant des resources interne
        ET_saisieTaille = findViewById(R.id.main_taille_et);
        ET_saisiePoids = findViewById(R.id.main_poid_et);
        BT_calculer = findViewById(R.id.main_calcul_bt);
        BT_effacer = findViewById(R.id.main_cancel_bt);

        //Permet empecher utilisateur d'utiliser ce composant
        BT_calculer.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Comportement dès le moment ou l'user saisi du text dedans
        ET_saisieTaille.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Test si les deux zone de text ne sont pas vide
                //si oui active le bouton calculer, sinon l'eteind
                if (!ET_saisieTaille.getText().toString().isEmpty() && !ET_saisiePoids.getText().toString().isEmpty()) {
                    // set text to Not typing
                    BT_calculer.setEnabled(false);
                } else {
                    BT_calculer.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //ne se declenchera uniquement quen si l'users appuie sur le bouton
        BT_effacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFields();
            }
        });

        BT_calculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent resultActivity = new Intent(MainActivity.this, ResultActivity.class);
                resultActivity.putExtra("valeurIMC", calculIMC(Double.parseDouble(ET_saisieTaille.getText().toString()), Double.parseDouble(ET_saisiePoids.getText().toString())));
                resultActivity.putExtra("valeurIMCText", indiceIMCText(calculIMC(Double.parseDouble(ET_saisieTaille.getText().toString()), Double.parseDouble(ET_saisiePoids.getText().toString()))));
                startActivity(resultActivity);


            }


            private void resetFields() {
                //doit vider champ saisie et poid
                //Reveilloullier le bt start

                ET_saisiePoids.setText("");
                ET_saisiePoids.setText("");
                BT_calculer.setEnabled(false);
                ET_saisieTaille.requestFocus();

            }

            private Double calculIMC(Double taille, Double poids) {
                if (taille > 3) {
                    //permet de ramener en mettre
                    taille = taille / 100;
                }

                Double imc = (poids / Math.pow(taille, 2));
                //Ramene calcul a décimal
                BigDecimal roundIMC = new BigDecimal(imc);
                roundIMC.setScale(2, BigDecimal.ROUND_UP);

                return roundIMC.doubleValue();

            }

            private String indiceIMCText(Double indiceIMC) {
                if (indiceIMC <= 16) {
                    return getText(R.string.imc_text_anorexie).toString();
                } else if (indiceIMC > 16 && indiceIMC <= 18.5) {
                    return getText(R.string.imc_text_maigreur).toString();
                } else if (indiceIMC > 18.5 && indiceIMC <= 25) {
                    return getText(R.string.imc_text_normal).toString();
                } else if (indiceIMC > 25 && indiceIMC <= 30) {
                    return getText(R.string.imc_text_surpoids).toString();
                } else if (indiceIMC > 30 && indiceIMC <= 35) {
                    return getText(R.string.imc_text_ob1).toString();
                } else if (indiceIMC > 35 && indiceIMC <= 40) {
                    return getText(R.string.imc_text_ob2).toString();
                } else if (indiceIMC > 40) {
                    return getText(R.string.imc_text_ob3).toString();
                } else {
                    return getText(R.string.imc_text_error).toString();
                }
            }

        });

    }
}