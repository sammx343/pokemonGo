package com.example.sammx343.probepokemongo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sammx343 on 23/08/2016.
 */
public class CustomAdapter extends BaseAdapter {

    private static final String TAG = "llEGA";
    private Context context;

    List<Pokemon> Pokemons = new ArrayList<Pokemon>();

    public CustomAdapter(Context context, List<Pokemon> Contactos){
        this.context = context;
        this.Pokemons = Contactos;
    }

    @Override
    public int getCount() {
        return Pokemons.size();
    }

    @Override
    public Object getItem(int i) {
        return Pokemons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d(TAG, " LLega hasta Aqui");

        String pokemonName = Pokemons.get(i).getName();
        String pokemonImage = Pokemons.get(i).getImgFront();
        String attack = Pokemons.get(i).getAttack();
        String defense = Pokemons.get(i).getDefense();
        String speed = Pokemons.get(i).getSpeed();
        String hp = Pokemons.get(i).getHp();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row, null);
        }

        TextView tv1 = (TextView) view.findViewById(R.id.pokemonListName);
        TextView attck = (TextView) view.findViewById(R.id.attackText);
        TextView dfs = (TextView) view.findViewById(R.id.defenseText);
        TextView spd = (TextView) view.findViewById(R.id.speedText);
        TextView hp1 = (TextView) view.findViewById(R.id.hpText);



        tv1.setText(pokemonName);
        attck.setText("Atk : " + attack);
        dfs.setText("Def : " + defense);
        spd.setText("Vel : " + speed);
        hp1.setText("Hp : " + hp);

        new DownloadImageTask((ImageView) view.findViewById(R.id.pokemonListImage))
                .execute(pokemonImage);


        return view;
    }
}
