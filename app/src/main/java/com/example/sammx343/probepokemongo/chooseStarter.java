package com.example.sammx343.probepokemongo;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class chooseStarter extends AppCompatActivity {

    ImageView pokeball1, pokeball2, pokeball3;
    TextView name1, name2, name3;
    ImageView starter1, starter2, starter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_starter2);

        pokeball1 = (ImageView) findViewById(R.id.pokeball1);
        pokeball2 = (ImageView) findViewById(R.id.pokeball2);
        pokeball3 = (ImageView) findViewById(R.id.pokeball3);

        name1 = (TextView) findViewById(R.id.starterName1);
        name2 = (TextView) findViewById(R.id.starterName2);
        name3 = (TextView) findViewById(R.id.starterName3);

        starter1 = (ImageView) findViewById(R.id.starter1);
        starter2 = (ImageView) findViewById(R.id.starter2);
        starter3 = (ImageView) findViewById(R.id.starter3);

        final Pokemon starter1 = MainActivity.Pokemones.get(0);
        final Pokemon starter2 = MainActivity.Pokemones.get(3);
        final Pokemon starter3 = MainActivity.Pokemones.get(6);

        name1.setText(starter1.getName());
        name2.setText(starter2.getName());
        name3.setText(starter3.getName());

        new DownloadImageTask((ImageView) findViewById(R.id.starter1))
                .execute(starter1.getImgFront());

        new DownloadImageTask((ImageView) findViewById(R.id.starter2))
                .execute(starter2.getImgFront());

        new DownloadImageTask((ImageView) findViewById(R.id.starter3))
                .execute(starter3.getImgFront());


        pokeball1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                choosePokemon(starter1, "hierba");
            }
        });

        pokeball2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                choosePokemon(starter2, "fuego");
            }
        });

        pokeball3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                choosePokemon(starter3, "agua");
            }
        });

    }

    void choosePokemon(final Pokemon starter , String tipo){
        new AlertDialog.Builder(chooseStarter.this)
                .setIcon(getResources().getDrawable(R.drawable.pokeball_icon))
            .setTitle("Escoger a " + starter.getName())
            .setMessage("Estás seguro de querer escoger a " + starter.getName() + " el pokemon de " + tipo + "?")
            .setPositiveButton("Si", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new AlertDialog.Builder(chooseStarter.this)
                            .setIcon(getResources().getDrawable(R.drawable.pokeball_icon))
                            .setTitle("Nuevo Pokemon " + starter.getName())
                            .setMessage("Pokemón atrapado")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }

                            })
                            .show();


                    starter.save();

                }

            })
            .setNegativeButton("No", null)
            .show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


}
