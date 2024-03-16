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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Nathan Latendresse (2249229)
 */
public class Enemy extends Entity {
    
//attributes
    public double levelScaling; //boosts enemy stats depending on player level
    //public float vitesse = 50; //what does enemyhitboxradius do?

//constructors
    public Enemy(double levelScaling, int maxHP, float speed, Vector2 position, Texture img) {
        super(maxHP, speed, position, img);
        this.levelScaling = levelScaling;
        //sprite.setColor(color);

    }
    
    public Enemy(int maxHP, float speed) {
        this(1, maxHP, speed, new Vector2(0, 0), new Texture("Evil.png"));
        this.sprite.setColor(Color.BLUE); //for testing purposes

    }
    
    public Enemy() {
        this(100, 300);

    }
    
 //methods

    /**
     * Updates the enemy object for the current time and conditions.
     * Also handles object logic.
     * @param deltaTime Time since last call to render()
     * @author Nathan Latendresse
     * @since 21/02/2024
     */
    @Override
    public void update (float deltaTime){
        
        Player p = GameScreen.player;
        super.update(deltaTime);
        
    //moves enemy towards player
        if(p.position.x <= this.position.x){
              position.x -= speed*deltaTime;
        } 
        if(p.position.x >= this.position.x){
              position.x += speed*deltaTime;
        }
        if(p.position.y <= this.position.y){
              position.y -= speed*deltaTime;
        } 
        if(p.position.y >= this.position.y){
              position.y += speed*deltaTime;
        }    
    }
    
    /**
     * Creates a copy of an Enemy object that spawns at a random location.
     * @return new Enemy object similar to calling instance
     * @author Alexis Fecteau
     * @since 15/03/2024
     */
    @Override
    public Entity spawn(){
        
    //random spawning position
        float posx = MathUtils.random(0,1000);
        float posy = MathUtils.random(0,1000);
        Vector2 pos = new Vector2(posx, posy);
        //TODO: make enemies spawn at edge of screen instead of random stage coordinates
        
        return new Enemy(
            this.levelScaling,
            this.maxHP,
            this.speed,
            pos,
            this.img
        );
    }
      
}
    

