package com.example.sammx343.probepokemongo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Alguna Joda" ;
    private ProgressDialog pDialog;
    String pokemon = "";
    public static ArrayList<Pokemon> Pokemones;
    private SharedPreferences FIRST_TIME;
    BackgroundSound mBackgroundSound = new BackgroundSound();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(verificarRed()){
            new getData().execute();
        }

        FIRST_TIME = PreferenceManager.getDefaultSharedPreferences(this);

        //SharedPreferences.Editor editor = FIRST_TIME.edit();

        //if (FIRST_TIME.getBoolean("firstrun", true)) {



          //  FIRST_TIME.edit().putBoolean("firstrun", false).commit();
        //}

        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());
        mBackgroundSound.execute();
    }
/*
    public void onPause() {
        super.onPause();
        mBackgroundSound.cancel(true);
    }
*/
    public boolean verificarRed(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }else{
            Toast.makeText(this, "Necesitas Conexión a internet para jugar", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static String getData(){
        Log.d(TAG,"get");
        String response = null;
        try {
            URL ut1 = null;
            ut1 = new URL("http://190.144.171.172/proyectoMovil/pokemon.json");
            URLConnection yc = ut1.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream()
            ));

            StringBuilder sb = new StringBuilder();
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null ){
                sb.append(inputLine + "\n");
            }
            response = sb.toString();
            in.close();
            return response;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public void listPokemons(View view) {
        Intent i = new Intent(this, PokemonList.class);
        startActivityForResult(i,2);
    }

    public void InitBattle(View view) {
        /*Intent i = new Intent(this, ChoosePokemon.class);
        startActivityForResult(i,1);*/
        Intent i = new Intent(this, MapsActivity.class);
        startActivityForResult(i,1);
    }

    public void exit(View view) {
        this.finish();
    }



    private class getData extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("CARGANDO POKEDEX...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String response = getData();
            Pokemones = new ArrayList<Pokemon>();

            if(response != null){
                try {

                    JSONArray pokemons = new JSONArray(response);
                    //JSONObject poke = new JSONObject(response);
                    Log.d(TAG, pokemons.length()+"");
                    JSONObject poka = pokemons.getJSONObject(0);
                    pokemon =  poka.getString("ImgFront");
                    Log.d(TAG, poka.getString("ImgFront")+ "Esta deberia decir el nombre del pokemon");
                    for(int i=0;i<17;i++){
                        JSONObject pok = pokemons.getJSONObject(i);
                        Pokemon pikachu = new Pokemon(pok.getString("Id"), pok.getString("Name"),  pok.getString("Type") ,
                                                      pok.getString("ImgFront"), pok.getString("ImgBack"), pok.getString("HP"),
                                                      pok.getString("Attack"), pok.getString("Defense"), pok.getString("Speed"),
                                                      pok.getString("ev_id"));
                        Pokemones.add(pikachu);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                Log.d(TAG, "Nuloooooo");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
                if(new Select().from(Pokemon.class).count() < 1){
                    Intent i = new Intent(MainActivity.this, professorIntro.class);
                    startActivityForResult(i,1);
                }
            }
        }
    };

    public class BackgroundSound extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.route_song);
            player.setLooping(true); // Set looping
            player.setVolume(100,100);
            player.start();

            return null;
        }

    }


}
