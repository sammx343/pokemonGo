package com.example.sammx343.probepokemongo;

import java.util.Random;

/**
 * Created by Sammx343 on 04/11/2016.
 */

public class RandomPokemon extends Pokemon {

    private String number;
    private String nombre;
    private String tipo;
    private String imgFront;
    private String imgBack;
    private String Hp;
    private String Attack;
    private String Defense;
    private String Speed;
    private String ev_id;

    public RandomPokemon(String number, String nombre, String tipo, String imgFront, String imgBack, String maxHp, String maxAttack, String maxDefense, String maxSpeed, String ev_id){
        this.number = number;
        this.nombre = nombre;
        this.tipo = tipo;
        this.imgFront = imgFront;
        this.imgBack = imgBack;
        this.Hp = generateRandomAtribute(Integer.parseInt(maxHp));
        this.Attack = generateRandomAtribute(Integer.parseInt(maxAttack));
        this.Defense = generateRandomAtribute(Integer.parseInt(maxDefense));
        this.Speed = generateRandomAtribute(Integer.parseInt(maxSpeed));
        this.ev_id = ev_id;
    }

    private String generateRandomAtribute(int maxAtribute){
        Random xR = new Random();
        int minAtribute = 1;
        int x = xR.nextInt(maxAtribute - minAtribute + 1) + minAtribute;
        return x+"";
    }



    public String getNombre() {
        return nombre;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public String getImgFront() {
        return imgFront;
    }

    @Override
    public String getImgBack() {
        return imgBack;
    }

    @Override
    public String getHp() {
        return Hp;
    }

    @Override
    public String getAttack() {
        return Attack;
    }

    @Override
    public String getDefense() {
        return Defense;
    }

    @Override
    public String getSpeed() {
        return Speed;
    }

    @Override
    public String getEv_id() {
        return ev_id;
    }

    @Override
    public String getNumber() {
        return number;
    }
}
