package com.example.sammx343.probepokemongo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PokemonBattle extends AppCompatActivity {

    TextView pokemon1Name, pokemon1Hp, pokemon2Name, pokemon2Hp , battleText, mainHPmax, wildHPmax;
    Pokemon pokemon1 , pokemon2;
    private CharSequence mText;
    private int mIndex;
    private long mDelay = 50;
    private boolean pokeballThrow = false;
    private long damage = 0;
    private boolean battleEnd = false;
    Button Attack;
    Button Pokeball;
    Button Flee;
    Button Potion;
    long wildHP = 0;
    long mainHP = 0;
    boolean buttonPress = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_battle);
        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());
        ArrayList<Pokemon> Pokemones = MainActivity.Pokemones;

        Attack = (Button) findViewById(R.id.buttonAttack);
        Pokeball = (Button) findViewById(R.id.buttonPokeball);
        Flee = (Button) findViewById(R.id.buttonFlee);
        Potion = (Button) findViewById(R.id.buttonPotion);

        Random xR = new Random();
        int max = 648;
        int min = 0;
        int x = xR.nextInt(max - min + 1) + min;

        pokemon1 = MapsActivity.pokemonWild;
        pokemon2 = (Pokemon) getIntent().getSerializableExtra("Pokemon2");

        wildHP = pokemon1.getHpLong();
        mainHP = pokemon2.getHpLong();

        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute(pokemon1.getImgFront());

        new DownloadImageTask((ImageView) findViewById(R.id.imageView2))
                .execute(pokemon2.getImgBack());

        battleText = (TextView) findViewById(R.id.battleText);
        pokemon1Name= (TextView) findViewById(R.id.pokemon1Name);
        pokemon1Hp = (TextView) findViewById(R.id.pokemon1Hp );
        pokemon2Name= (TextView) findViewById(R.id.pokemon2Name);
        pokemon2Hp  = (TextView) findViewById(R.id.pokemon2Hp);
        wildHPmax = (TextView) findViewById(R.id.wildHP);
        mainHPmax = (TextView) findViewById(R.id.mainHP);

        pokemon1Name.setText(pokemon1.getName());
        pokemon1Hp.setText(pokemon1.getHp());
        pokemon2Name.setText(pokemon2.getName());
        pokemon2Hp.setText(pokemon2.getHp());

        wildHPmax.setText("/ " + wildHP);
        mainHPmax.setText("/ " + mainHP);

        handler.postDelayed(r, 1000);

        animateText("Empieza la batalla Pokemón. ¿Que vas a hacer?", 30);
    }

    Handler handler=new Handler();
    Runnable r = new Runnable(){
        public void run() {
                if(!battleEnd){
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new   Runnable() {
                                        public void run() {

                                            if(pokemon2.getHpLong() == 0 && !battleEnd){
                                                animateText("Tu pokemón se ha debilidado. La batalla ha terminado", 30);
                                                battleEnd = true;
                                                new java.util.Timer().schedule(
                                                    new java.util.TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            runOnUiThread(new   Runnable() {
                                                                public void run() {
                                                                    finish();
                                                                }
                                                            });
                                                        }
                                                    },
                                                    6000
                                                );
                                            }

                                            if(pokemon1.getHpLong() == 0 && !battleEnd){
                                                System.out.println("Siiiii");
                                                animateText("El pokemón enemigo se ha debilidado. La batalla ha terminado", 30);
                                                battleEnd = true;
                                                new java.util.Timer().schedule(
                                                    new java.util.TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            runOnUiThread(new   Runnable() {
                                                                public void run() {
                                                                    Intent K = new Intent(getApplicationContext(), updatePokemon.class);
                                                                    K.putExtra("Pokemon", pokemon2);
                                                                    startActivityForResult(K,3);
                                                                    finish();
                                                                }
                                                            });
                                                        }
                                                    },
                                                    6000
                                                );
                                            }
                                        }
                                    });
                                }
                            },
                            5000
                    );
                }

                if(battleEnd) {
                    Attack.setEnabled(false);
                    Pokeball.setEnabled(false);
                    Potion.setEnabled(false);
                    Flee.setEnabled(false);
                }
                Attack.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {
                        buttonPress = true;
                        battle();
                    }
                });

                Pokeball.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonPress = true;
                        pokeball();
                    }
                });

                Flee.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        fleeDialog();
                    }
                });

                Potion.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        potion();
                        buttonPress = true;
                    }
                });

                if(buttonPress){
                    Attack.setEnabled(false);
                    Pokeball.setEnabled(false);
                    Potion.setEnabled(false);
                    System.out.println("Deberían DESAhabilitarse los botones ya");
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new   Runnable() {
                                        public void run() {
                                            System.out.println("Deberían habilitarse los botones ya");
                                            //animateText("¿Que vas a hacer ahora?",20);
                                            Attack.setEnabled(true);
                                            Pokeball.setEnabled(true);
                                            Potion.setEnabled(true);

                                        }
                                    });
                                }
                            },
                            10000
                    );
                //}
                buttonPress = false;
            }

            handler.postDelayed(this, 50);
        }
    };

    public void potion(){
        Intent K = new Intent(this, Potions.class);
        startActivityForResult(K,22);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 22){
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("Y");
                if(result == "100"){
                    pokemon2.setHp((mainHP)+"");
                    animateText("Tu pokemon se ha recuperado completamente", 50);
                    pokemon2Hp.setText(pokemon2.getHp());
                }else{
                    pokemon2.setHp((mainHP)+"");
                    animateText("Tu pokemon se ha recuperado", 50);
                    pokemon2Hp.setText(pokemon2.getHp());
                }
            }
        }
    }

    public void battle(){
        Attack.setEnabled(false);
        Pokeball.setEnabled(false);
        Potion.setEnabled(false);

        if(pokemon1.getSpeedLong() > pokemon2.getSpeedLong()){
            pokemon2 = attacking(pokemon2, pokemon1);
            pokemon2Hp.setText(pokemon2.getHp());
            animateText(pokemon1.getName() + " ha atacado y ha quitado "  + damage + " puntos de vida ", 40);
            if(pokemon2.getHpLong()>0){
                new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new   Runnable() {
                                public void run() {
                                    pokemon1 = attacking(pokemon1, pokemon2);
                                    pokemon1Hp.setText(pokemon1.getHp());
                                    animateText(pokemon2.getName() + " ha atacado y ha quitado "  + damage + " puntos de vida " , 40);
                                }
                            });
                        }
                    },
                    7000
                );
            }
        }else{
            pokemon1 = attacking(pokemon1, pokemon2);
            pokemon1Hp.setText(pokemon1.getHp());
            animateText(pokemon2.getName() + " ha atacado y ha quitado " + damage + " puntos de vida " , 40);
            if(pokemon1.getHpLong()>0){
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new   Runnable() {
                                    public void run() {
                                        pokemon2 = attacking(pokemon2, pokemon1);
                                        pokemon2Hp.setText(pokemon2.getHp());
                                        animateText(pokemon1.getName() + " ha atacado y ha quitado " + damage + " puntos de vida " , 40);
                                    }
                                });
                            }
                        },
                        7000
                );
            }
        }
    }


    public Pokemon attacking(Pokemon pok1, Pokemon pok2){
        damage = pok1.getDefenseLong() -  pok2.getAttackLong();
        long hp2 = pok1.getHpLong();

        System.out.println( pokemon1.getAttack() + " ATAQUE Y DEFENSA " + pokemon1.getDefense());
        System.out.println( pokemon2.getAttack() + " ATAQUE Y DEFENSA " + pokemon2.getDefense());

        if( damage >= - 1)
            damage = -1;

        hp2 += damage;

        if(hp2< 0)
            hp2 = 0;

        pok1.setHp(hp2+"");

        return pok1;
    }

    public void fleeBattle(View view) {
        fleeDialog();
    }


    public void pokeball(){
        animateText("... 1 ... 2 ... 3 ", 100);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.pokeball_icon);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new   Runnable() {
                            public void run() {
                                Random probability = new Random();
                                int max = 99;
                                int min = 0;
                                int prob = probability.nextInt(max - min + 1) + min;
                                System.out.println(wildHP + " - "+ pokemon1.getHpLong() );
                                System.out.println( ""+(pokemon1.getHpLong()) / (double)(wildHP));
                                double proby = ((1- (double)(pokemon1.getHpLong()) / (double)(wildHP) )*100);
                                System.out.println("Este es proby " + proby);
                                int tope = (int)  (proby + 50);
                                System.out.println("Este es tope " + tope);
                                if(prob <= tope){
                                    animateText("Felicidades nuevo pokemon atrapado! ", 40);
                                    pokemon2.setHp(mainHP+"");
                                    pokemon1.setHp(wildHP+"");
                                    pokemon1.save();
                                    new java.util.Timer().schedule(
                                            new java.util.TimerTask() {
                                                @Override
                                                public void run() {
                                                    finish();
                                                }
                                            },
                                            4000
                                    );
                                }else {
                                    animateText("Casi!. El pokemón se ha escapado de la pokeball ", 40);
                                    new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                                            .execute(pokemon1.getImgFront());
                                            new java.util.Timer().schedule(
                                                    new java.util.TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            runOnUiThread(new   Runnable() {
                                                                public void run() {
                                                                    pokemon2 = attacking(pokemon2, pokemon1);
                                                                    pokemon2Hp.setText(pokemon2.getHp());
                                                                    animateText(pokemon1.getName() + " ha atacado y ha quitado " + damage + " puntos de vida " , 40);
                                                                }
                                                            });
                                                        }
                                                    },
                                                    4000
                                            );

                                }

                            }
                        });
                    }
                },
                4000
        );
    }

    public void ThrowPokeball(View view) {
        pokeballThrow  = true;
    }

    public void onBackPressed() {
        //super.onBackPressed();
        fleeDialog();
    }

    private void fleeDialog(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.bottom_bar)
                .setTitle("Huir")
                .setMessage("Estás seguro de querer huir de la batalla actúal?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            battleText.setText(mText.subSequence(0, mIndex++));
            if(mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    public void animateText(CharSequence text, long delay) {
        mText = text;
        mIndex = 0;
        mDelay = delay;
        battleText.setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }
}
