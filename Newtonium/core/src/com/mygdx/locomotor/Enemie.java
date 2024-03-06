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

<<<<<<< Updated upstream
    public Enemie(Texture img, Color color) {
        super(img, color);
=======
    public Enemie(Texture img, Color color,int maxHP) {
        super(img, color,maxHP);
        this.maxHP=maxHP;
        this.currentHP = this.maxHP;
        sprite = new Sprite(img);
        sprite.setScale(4); // 
        sprite.setColor(color);
        this.position.x=0;
        this.position.y=0;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        
        
        public void Draw(SpriteBatch batch, Player p ){
        Update(Gdx.graphics.getDeltaTime(), p);
=======
        public void draw(SpriteBatch batch, Player p ){
            
            
        update(Gdx.graphics.getDeltaTime(), p);
>>>>>>> Stashed changes
        sprite.setPosition(this.position.x,this.position.y);
        sprite.draw(batch);
    }
        
        public void Spawn(SpriteBatch batch, Player p){
                int randx = (int) Math.random();
                int randy = (int) Math.random();
            this.Draw(batch, p);
            
        }
        
        
        
        
    }
    

