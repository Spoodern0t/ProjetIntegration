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
 */

//IMPORTANT!! La classe Entity est censée être abstraite! ~AF
abstract class Entity {
    public int maxHP, currentHP, hitBoxRadius, knockBack;
    double damageMod;
    public Vector2 position;
    public Sprite sprite;
    public float speed = 300;
    public boolean alive =true;
    
    
    public Entity(Texture img,Color color, int maxHP)
    {

    position = new Vector2();    
    }
    
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
    
    
    
}
