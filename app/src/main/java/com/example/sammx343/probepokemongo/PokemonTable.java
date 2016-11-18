package com.example.sammx343.probepokemongo;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = PokemonTable.NAME, version = PokemonTable.VERSION)

public class PokemonTable {

    public static final String NAME = "PokemonTable";
    public static final int VERSION = 1;
}
