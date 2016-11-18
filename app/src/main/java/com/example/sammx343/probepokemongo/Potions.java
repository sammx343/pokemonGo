package com.example.sammx343.probepokemongo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Potions extends AppCompatActivity {
    int potion50 = 5;
    int potion100 = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potions);
    }

    public void potion100(View view) {
        String resultado = "100";
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Y", resultado);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void potion50(View view) {
        String resultado = "50";
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Y", resultado);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
