/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Nathan Latendresse (2249229)
 * @author Alexis Fecteau (2060238)
 * 
 * @since 03/04/2024
 */
public class Enemy extends Entity {
    
//attributes
    public double levelScaling; //boosts enemy stats depending on player level

//constructors
    public Enemy(double levelScaling, int maxHP, float speed, Vector2 position, Texture img) {
        super(maxHP, speed, position, img);
        this.levelScaling = levelScaling;
        this.sprite.setColor(Color.BLUE); //for testing purposes

    }
    
 //methods
    /**
     * Updates the enemy object for the current time and conditions.
     * Also handles object logic.
     * @param deltaTime Time since last call to render()
     */
    @Override
    public void update (float deltaTime) throws DeadEntityException{
        
        Player p = GameScreen.currentPlayer;
        super.update(deltaTime);
        
        if (this.canGetHurt()) {
            this.sprite.setTexture(GameScreen.evilTexture);
        }
        
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
     */
    @Override
    public Entity spawn(){
        
        Vector2 pos;
        float posX;
        float posY;
              
    //randomized spawning point around the screen's border
        float camW = GameScreen.Camera.viewportWidth/2;
        float camH = GameScreen.Camera.viewportHeight/2;
        float camX = GameScreen.Camera.position.x;
        float camY = GameScreen.Camera.position.y;
        
        if (MathUtils.randomBoolean()){
            posX = MathUtils.random(camX-camW-sprite.getWidth(), camX+camW);
            posY = (MathUtils.randomBoolean()) ? camY-camH-sprite.getHeight() : camY+camH;
        } else {
            posX = (MathUtils.randomBoolean()) ? camX-camW-sprite.getWidth() : camX+camW;
            posY = MathUtils.random(camY-camH-sprite.getHeight(), camY+camH);
        }
        pos = new Vector2(posX, posY);
        
        return new Enemy(
            this.levelScaling,
            this.maxHP,
            this.speed,
            pos,
            this.sprite.getTexture()
        );
    }
      
}
    

