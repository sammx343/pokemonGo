package com.example.sammx343.probepokemongo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.io.Serializable;
import java.util.List;

public class PokemonList extends AppCompatActivity implements Serializable {

    private static final String TAG = "Holi ";
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());
        //ArrayAdapter<Pokemon> adapter = new ArrayAdapter<Pokemon>(this, R.id.activity_pokemon_list, R.id.battleText, );
        final List<Pokemon> pokemons = new Select().from(Pokemon.class).queryList();

        CustomAdapter customAdapter = new CustomAdapter(this, pokemons);


        listView = (ListView) findViewById(R.id.pokemonList);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent K = new Intent(getApplicationContext(), MyPokemon.class);
                Pokemon pok = pokemons.get(i);
                K.putExtra("Pokemon", pok);
                startActivityForResult(K,3);
                finish();
            }
        });
    }

    public void Atras(View view) {
        finish();
    }
}
