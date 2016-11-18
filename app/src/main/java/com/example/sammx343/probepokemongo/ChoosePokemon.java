package com.example.sammx343.probepokemongo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

public class ChoosePokemon extends AppCompatActivity {
    Pokemon pokemon1;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pokemon);

        //pokemon1 = MapsActivity.pokemonWild;
        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());

        final List<Pokemon> pokemons = new Select().from(Pokemon.class).queryList();

        CustomAdapter customAdapter = new CustomAdapter(this, pokemons);


        listView = (ListView) findViewById(R.id.choosePokemonList);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent K = new Intent(getApplicationContext(), PokemonBattle.class);
                Pokemon pok = pokemons.get(i);
                K.putExtra("Pokemon2", pok);
          //      K.putExtra("Pokemon1", pokemon1);
                startActivityForResult(K,3);
                finish();
            }
        });
    }

    public void Atras(View view) {
        finish();
    }
}
