package com.example.sammx343.probepokemongo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class professorIntro extends AppCompatActivity {

    TextView professorText;
    ImageView starter1, starter2, starter3;
    int textType = 0;
    boolean t7 = false;

    private CharSequence mText;
    private int mIndex;
    private long mDelay = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter_pokemon);

        professorText = (TextView) findViewById(R.id.professorText);
        animateText("Hola! Bienvenido al maravilloso mundo de los Pokemon ...");

        RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.activity_starter_pokemon);
        rlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                textType++;
                switch (textType){
                    case 1:
                        animateText("Mi nombre es Elizabeth, y te doy la bienvenida...");
                        //professorText.setText("Mi nombre es Elizabeth, y te doy la bienvenida...");
                        break;

                    case 2:
                        animateText("Este mundo está compuesto por criaturas llamadas Pokemon...");
                        //professorText.setText("Este mundo está compuesto por criaturas llamadas Pokemon...");
                        break;

                    case 3:
                        animateText("Para algunos, los Pokemon son mascotas, otros los usan para batallas...");
                        break;

                    case 4:
                        animateText("Y yo? Yo estudio a los Pokemon. Estos maravillosos seres...");
                        break;

                    case 5:
                        animateText("Para entrar a este mundo, lo primero que necesitarás es un pokemon inicial...");
                        break;

                    case 6:
                        animateText("Deberás elegir uno...");
                        break;

                    case 7:
                        new AlertDialog.Builder(professorIntro.this)
                                .setIcon(getResources().getDrawable(R.drawable.pokeball_icon))
                                .setTitle("Elegir Pokemon")
                                .setMessage("Has entendido?")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(professorIntro.this, chooseStarter.class);
                                        startActivityForResult(i,2);
                                        t7 = true ;
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        textType = 0;
                                    }

                                }).show();
                        break;

                    case 8:
                        if(t7 == true) {
                            animateText("Que bonito Pokemón has elegido. Tu aventura va a comenzar ... ");
                        }
                        else {
                            animateText("Mi nombre es Elizabeth, y te doy la bienvenida...");
                            textType = 1;
                        }
                        break;
                    case 9:
                        finish();
                        break;
                    default:
                        professorText.setText(" ");
                        break;
                }
            }
        });
    }

    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            professorText.setText(mText.subSequence(0, mIndex++));
            if(mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    public void animateText(CharSequence text) {
        mText = text;
        mIndex = 0;

        professorText.setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                //User is moving around on the screen
                break;

            case MotionEvent.ACTION_UP:


        }

        return super.onTouchEvent(event);
    }
}
