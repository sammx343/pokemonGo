package com.example.sammx343.probepokemongo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDataBase.class)
public class Pokemon extends BaseModel {

    @PrimaryKey
    private String id;
    @Column
    private String nombre;
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

    public Pokemon(){

    }

    public Pokemon(String id, String nombre, String tipo, String imgFront, String imgBack, String hp, String attack, String defense, String speed, String ev_id) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.imgFront = imgFront;
        this.imgBack = imgBack;
        this.Hp = hp;
        this.Attack = attack;
        this.Defense = defense;
        this.Speed = speed;
        this.ev_id = ev_id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
