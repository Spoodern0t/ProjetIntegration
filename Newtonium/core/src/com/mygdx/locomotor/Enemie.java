/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

/**Testing Testing Gitbuh Testing/
/**
 *
 * @author 2249229
 */
public class Enemie extends Entity {
    
    public double levelScaling;
    public float vitesse = 50;
   

    public Enemie(Texture img, Color color,int maxHP) {
        super(img, color,maxHP);
        this.position.x=0;
        this.position.y=0;
    }
    
    
   
    public void Update (float deltaTime, Player p){
        
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
        
        
        public void Draw(SpriteBatch batch, Player p ){
            
            
        Update(Gdx.graphics.getDeltaTime(), p);
        sprite.setPosition(this.position.x,this.position.y);
        sprite.draw(batch);
    }
        
        @Override
        public void Spawn(SpriteBatch batch, Player p){
            int randx = (int) Math.random();
            int randy = (int) Math.random();
            this.Draw(batch, p);
            
        }
        
        
        
        
    }
    

