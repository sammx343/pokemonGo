package com.example.sammx343.probepokemongo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Alguna Joda" ;
    private ProgressDialog pDialog;
    String pokemon = "";
    public static ArrayList<Pokemon> Pokemones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());

        if(verificarRed()){
            new getData().execute();
        }
        //new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
        //        .execute(pokemon);

        Pokemon arceus;

    }

    public boolean verificarRed(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(this, "Hay Red", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(this, "NO Hay Red", Toast.LENGTH_SHORT).show();
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

    public void VerificarRed(View view) {

    }

    public void InitBattle(View view) {

        Intent i = new Intent(this, PokemonBattle.class);
        startActivityForResult(i,1);
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

            Log.d(TAG, " response " + response.length() + " este es el response en esa vuelta");
            if(response != null){
                try {

                    JSONArray pokemons = new JSONArray(response);
                    //JSONObject poke = new JSONObject(response);
                    Log.d(TAG, pokemons.length()+"");
                    JSONObject poka = pokemons.getJSONObject(0);
                    pokemon =  poka.getString("ImgFront");
                    Log.d(TAG, poka.getString("ImgFront")+ "Esta deberia decir el nombre del pokemon");
                    for(int i=0;i<pokemons.length();i++){
                        JSONObject pok = pokemons.getJSONObject(i);
                        Pokemon pikachu = new Pokemon(pok.getString("Id"), pok.getString("Name"),  pok.getString("Type") ,
                                                      pok.getString("ImgFront"), pok.getString("ImgBack"), pok.getString("HP"),
                                                      pok.getString("Attack"), pok.getString("Defense"), pok.getString("Speed"),
                                                      pok.getString("ev_id"));
                        Pokemones.add(pikachu);

                        Log.d(TAG, Pokemones.get(i).getNombre() + " id : " + Pokemones.get(i).getId()  );

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
            if (pDialog.isShowing())
                pDialog.dismiss();


        }
    };


}
