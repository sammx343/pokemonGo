package com.example.sammx343.probepokemongo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class updatePokemon extends AppCompatActivity {
    Pokemon pok;
    ImageView PokemonImage;
    Button attack, defense, speed, hp, atras;
    boolean updated = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pokemon);

        pok = (Pokemon) getIntent().getSerializableExtra("Pokemon");

        new DownloadImageTask((ImageView) findViewById(R.id.pokemonUpdateImg))
                .execute(pok.getImgFront());

        attack = (Button) findViewById(R.id.attackUpdate);
        defense = (Button) findViewById(R.id.defenseUpdate);
        speed = (Button) findViewById(R.id.speedUpdate);
        hp = (Button) findViewById(R.id.hpUpdate);
        atras = (Button) findViewById(R.id.atrasUpdate);

        attack.setText("Ataque: " + pok.getAttack());
        defense.setText("Defensa: " + pok.getDefense());
        speed.setText("Velocidad: " + pok.getSpeed());
        hp.setText("HP: " + pok.getHp());

        atras.setEnabled(false);
        handler.postDelayed(r, 1000);
    }

    Handler handler=new Handler();
    Runnable r = new Runnable(){
        public void run() {



            attack.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    pok.setAttack((pok.getAttackLong()+1)+"");
                    pok.save();
                    updated = true;
                }
            });

            defense.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    pok.setDefense((pok.getDefenseLong()+1)+"");
                    pok.save();
                    updated = true;
                }
            });

            speed.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    pok.setSpeed((pok.getSpeedLong()+1)+"");
                    pok.save();
                    updated = true;
                }
            });

            hp.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    pok.setHp((pok.getHpLong()+1)+"");
                    pok.save();
                    updated = true;
                }
            });

            attack.setText("Ataque: " + pok.getAttack());
            defense.setText("Defensa: " + pok.getDefense());
            speed.setText("Velocidad: " + pok.getSpeed());
            hp.setText("HP: " + pok.getHp());

            if(updated){
                attack.setEnabled(false);
                defense.setEnabled(false);
                speed.setEnabled(false);
                hp.setEnabled(false);
                atras.setEnabled(true);
            }
            handler.postDelayed(this, 50);
        }
    };

    public void atras(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
       if(!updated){
           Toast.makeText(this, "Debes subir una categoría a tu Pokemon primero" , Toast.LENGTH_SHORT).show();
       }else{
           finish();
       }
    }
}
