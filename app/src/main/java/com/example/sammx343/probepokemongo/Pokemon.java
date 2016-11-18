package com.example.sammx343.probepokemongo;

import android.graphics.Bitmap;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.Random;

@Table(database = PokemonTable.class)
public class Pokemon extends BaseModel implements Serializable{

    @PrimaryKey(autoincrement = true)
    private long id;
    @Column
    private String number;
    @Column
    private String name;
    @Column
    private String tipo;
    @Column
    private String imgFront;
    @Column
    private String imgBack;
    @Column
    private String Hp;
    @Column
    private String Attack;
    @Column
    private String Defense;
    @Column
    private String Speed;
    @Column
    private String ev_id;

    private String maxHp;
    private String maxAttack;
    private String maxDefense;
    private String maxSpeed;
    private Bitmap bitmap;

    public Pokemon(){
    }

    public Pokemon(String number, String name, String tipo, String imgFront, String imgBack, String maxHp, String maxAttack, String maxDefense, String maxSpeed, String ev_id) {
        this.number = number;
        this.name = name;
        this.tipo = tipo;
        this.imgFront = imgFront;
        this.imgBack = imgBack;
        this.Hp = generateRandomAtribute(Integer.parseInt(maxHp));
        this.Attack = generateRandomAtribute(Integer.parseInt(maxAttack));
        this.Defense = generateRandomAtribute(Integer.parseInt(maxDefense));
        this.Speed = generateRandomAtribute(Integer.parseInt(maxSpeed));
        this.ev_id = ev_id;
        this.maxHp = maxHp;
        this.maxAttack = maxAttack;
        this.maxDefense = maxDefense;
        this.maxSpeed = maxSpeed;
    }

    private String generateRandomAtribute(int maxAtribute){
        Random xR = new Random();
        int minAtribute = 1;
        int x = xR.nextInt(maxAtribute - minAtribute + 1) + minAtribute;
        return x+"";
    }

    public String getAttack() {
        return Attack;
    }

    public void setAttack(String attack) {
        Attack = attack;
    }

    public String getDefense() {
        return Defense;
    }

    public void setDefense(String defense) {
        Defense = defense;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public String getEv_id() {
        return ev_id;
    }

    public void setEv_id(String ev_id) {
        this.ev_id = ev_id;
    }

    public String getHp() {
        return Hp;
    }

    public void setHp(String hp) {
        Hp = hp;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImgFront() {
        return imgFront;
    }

    public void setImgFront(String imgFront) {
        this.imgFront = imgFront;
    }

    public String getImgBack() {
        return imgBack;
    }

    public void setImgBack(String imgBack) {
        this.imgBack = imgBack;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHpLong(){
        return Long.parseLong(this.Hp);
    }

    public long getAttackLong(){
        return Long.parseLong(this.Attack);
    }

    public long getDefenseLong(){
        return Long.parseLong(this.Defense);
    }

    public long getSpeedLong(){
        return Long.parseLong(this.Speed);
    }

    public String getMaxAttack() {
        return maxAttack;
    }

    public String getMaxDefense() {
        return maxDefense;
    }

    public String getMaxSpeed() {
        return maxSpeed;
    }

    public String getMaxHp() {
        return maxHp;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
