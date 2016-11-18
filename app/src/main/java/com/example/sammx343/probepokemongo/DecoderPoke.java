package com.example.sammx343.probepokemongo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 26/10/16.
 */

public class DecoderPoke extends AsyncTask<Void,Void,List<Pokemon>> {
    private ProgressDialog pDialog;
    MapsActivity mapa;
    private  static String TAG = "Json2Running";
    List<Pokemon> pokemon;

    public DecoderPoke(ProgressDialog pDialog, List<Pokemon> pokemon) {
        this.pDialog = pDialog;
        this.pokemon = pokemon;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.setMessage("Wait a minute, please....");
        pDialog.setCancelable(false);
        pDialog.show();

    }
    @Override
    protected List<Pokemon> doInBackground(Void... voids) {
        String response = getData("http://190.144.171.172/proyectoMovil/pokemon.json");
        ArrayList<Pokemon> pokemonI = new ArrayList<Pokemon>();
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray poke = jsonObject.getJSONArray("result");
                for (int i = 0; i < poke.length(); i++) {
                    JSONObject pok = poke.getJSONObject(i);

                    pokemonI.add(i, new Pokemon(pok.getString("Id"), pok.getString("Name"),  pok.getString("Type") ,
                            pok.getString("ImgFront"), pok.getString("ImgBack"), pok.getString("HP"),
                            pok.getString("Attack"), pok.getString("Defense"), pok.getString("Speed"),
                            pok.getString("ev_id")));;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return pokemonI;
    }

    @Override
    protected void onPostExecute(List<Pokemon> aVoid) {
        super.onPostExecute(aVoid);
        pokemon.addAll(aVoid);
        System.out.println("Los pokemones son:"+ pokemon.size() +pokemon.toString());
        Log.d(TAG, pokemon.toString());
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }


    }

    protected static String getData(String Url){
        String response = "";
        try {
            URL url = null;
            url = new URL(Url);
            URLConnection uConnect = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uConnect.getInputStream()));
            String Line;
            while ((Line = in.readLine()) != null){
                response += Line;
            }
            response ="{'result':"+response+"}";
            in.close();
            return response;

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
