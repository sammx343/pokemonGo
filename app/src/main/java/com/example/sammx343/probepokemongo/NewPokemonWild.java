package com.example.sammx343.probepokemongo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.io.InputStream;

public class NewPokemonWild extends AppCompatActivity {

    TextView name, attack, defense, speed, Hp;
    Image pokemon;
    Pokemon pok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pokemon_wild);
        pok = MapsActivity.pokemonWild;

        name = (TextView) findViewById(R.id.myPokemonName);
        attack = (TextView) findViewById(R.id.myPokemonAttack);
        defense = (TextView) findViewById(R.id.myPokemonDefense);
        speed = (TextView) findViewById(R.id.myPokemonSpeed);
        Hp = (TextView) findViewById(R.id.myPokemonHP);

        name.setText(pok.getName());
        attack.setText("Ataque : " + pok.getAttack());
        defense.setText("Defensa : " + pok.getDefense());
        speed.setText("Velocidad : " + pok.getSpeed());
        Hp.setText("HP : " + pok.getHp());

        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());

        new DownloadImageTask((ImageView) findViewById(R.id.myPokemonImage))
                .execute(pok.getImgFront());
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent(NewPokemonWild.this, PokemonList.class);
        startActivityForResult(i,2);
        finish();
    }

    public void Atras(View view) {
        Intent i = new Intent(NewPokemonWild.this, PokemonList.class);
        startActivityForResult(i,2);
        finish();
    }

    public void borrarPokemon(){
        new AlertDialog.Builder(this)
                .setIcon(getResources().getDrawable(R.drawable.open_pokeball))
                .setTitle("Liberar Pokemón")
                .setMessage("Estás seguro de querer liberar este Pokemón?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pok.delete();
                        Intent i = new Intent(NewPokemonWild.this, PokemonList.class);
                        startActivityForResult(i,2);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void liberatePokemon(View view) {
        System.out.println(new Select().from(Pokemon.class).count() + " esta es la cantidad de pokemones" );
        if(new Select().from(Pokemon.class).count() < 2){
            System.out.println("Esto debería entrar a sacar el pokemon");
            new AlertDialog.Builder(this)
                    .setIcon(getResources().getDrawable(R.drawable.open_pokeball))
                    .setTitle("Ultimo Pokemón")
                    .setMessage("Este es tu ultimo Pokemon. Deberás entrar de nuevo para pedir un Pokemon Inicial")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            borrarPokemon();
                        }

                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }else
            borrarPokemon();

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
