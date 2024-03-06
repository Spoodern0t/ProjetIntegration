/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Yoruk Ekrem
 * @author Alexis Fecteau (2060238)
 */

abstract class Entity {
    
//Attributes
    
    public int maxHP, currentHP, hitBoxRadius, knockBack=0;
    double damageMod = 1;
    public Vector2 position;
    public Sprite sprite;
    public float speed;
    public boolean alive =true; //is this necessary? ~AF
    
//Constructors
    //TODO: add hitboxradius in these
    public Entity(int maxHP, float speed, Vector2 position, Texture img){
        this.sprite = new Sprite(img);
        sprite.setScale(4); //can be set later
        this.position = position;
        //this.hitBoxRadius = hitboxradius;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.speed = Math.max(speed, 0); //so speed can't be negative
    }
    
    public Entity(int maxHP, float speed, Vector2 position){
        this(maxHP, speed, position, new Texture("LilBoy.png"));
    }
    
    public Entity(int maxHP, float speed){
        this(maxHP, speed, new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2));
    }
    
    public Entity(){
        this(100, 300);
    }
    
    


//methods
    public void update(float deltaTime)
    {

    }
    
    public void draw(SpriteBatch batch)
    {
        
    }
    
    public boolean die(){ //Pourquoi boolean? le diagramme dit classe void... ~AF
        return false;
        
    }
    
    public void spawn(){
        
    }
    
//getters/setters
    
}
