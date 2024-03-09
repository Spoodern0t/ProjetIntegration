/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

/**
 *
 * @author 2249229
 */
public class Enemie extends Entity {
    
    public double levelScaling; //boost enemy stats depending on player level
    public float vitesse = 50,EnemyhitboxRadius = 24;
    
   

    public Enemie(int maxHP, float speed, Vector2 position, Texture img,float hitBoxRadius) {
        super(maxHP, speed, position, img,hitBoxRadius);
        this.EnemyhitboxRadius = hitBoxRadius;
        
        //sprite.setColor(color);

    }
    
    public Enemie(int maxHP, float speed) {
        this(maxHP, speed, new Vector2(0, 0), new Texture("Evil.png"),24);
        this.sprite.setColor(Color.BLUE); //for testing purposes

    }
    
    public Enemie() {
        this(100, 300);

    }
    
    
   
    public void update (float deltaTime, Player p){
        
        if(p.position.x <= this.position.x){
              position.x -= vitesse*deltaTime;
        } 
        if(p.position.x >= this.position.x){
              position.x += vitesse*deltaTime;
        }
        if(p.position.y <= this.position.y){
              position.y -= vitesse*deltaTime;
        } 
        if(p.position.y >= this.position.y){
              position.y += vitesse*deltaTime;
        }    
    }
        
    public void draw(SpriteBatch batch, Player p ){
       
        update(Gdx.graphics.getDeltaTime(), p);
        sprite.setPosition(this.position.x,this.position.y);
        Enemie.super.HitBox.setPosition(this.position.x,this.position.y);
        sprite.draw(batch);
    }   
      
    }
    

