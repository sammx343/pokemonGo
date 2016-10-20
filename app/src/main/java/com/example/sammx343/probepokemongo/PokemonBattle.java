package com.example.sammx343.probepokemongo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class PokemonBattle extends AppCompatActivity {

    TextView pokemon1Name, pokemon1Hp, pokemon2Name, pokemon2Hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_battle);

        ArrayList<Pokemon> Pokemones = MainActivity.Pokemones;

        Random xR = new Random();
        int max = 648;
        int min = 0;
        int x = xR.nextInt(max - min + 1) + min;
        int y = xR.nextInt(max - min + 1) + min;

        Pokemon pokemon1 = Pokemones.get(x);
        Pokemon pokemon2 = Pokemones.get(y);

        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute(pokemon1.getImgFront());

        new DownloadImageTask((ImageView) findViewById(R.id.imageView2))
                .execute(pokemon2.getImgBack());


        pokemon1Hp = (TextView) findViewById(R.id.pokemon1Hp );
        pokemon2Name= (TextView) findViewById(R.id.pokemon2Name);
        pokemon2Hp  = (TextView) findViewById(R.id.pokemon2Hp);

        pokemon1Name.setText(pokemon1.getNombre());
        pokemon1Hp.setText("Hp: " + pokemon1.getHp());
        pokemon2Name.setText(pokemon2.getNombre());
        pokemon2Hp.setText("Hp: " +pokemon2.getHp());

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
