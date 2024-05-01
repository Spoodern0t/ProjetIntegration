/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.mygdx.newtonium.control.Global;
import com.mygdx.newtonium.control.GameScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Nathan Latendresse (2249229)
 * @author Alexis Fecteau (2060238)
 * @author Thomas Cyr (2289144)
 * 
 * @since 03/04/2024
 */
public class Enemy extends Entity {
    
//attributes
    private Texture initTexture;
    public double levelScaling; //boosts enemy stats depending on player level
    public int strength; //damage dealth by the enemy
    public int exp; //xp gained when the enemy is killed

//constructors
    public Enemy(double levelScaling, int maxHP, float speed, int strength, int exp, Vector2 position, Texture img) {
        super(maxHP, speed, position, img);
        this.initTexture = img;
        this.levelScaling = levelScaling;
        //this.sprite.setColor(color); //for testing purposes
        this.strength = strength;
        this.exp = exp;

    }
    
 //methods
    /**
     * Updates the enemy object for the current time and conditions.
     * Also handles object logic.
     * @param deltaTime Time since last call to render()
     */
    @Override
    protected void update (float deltaTime) throws DeadEntityException{
        
        Player p = Global.currentPlayer; //todo: Move currentplayer and enemylist to Global
        super.update(deltaTime);
        
        if (this.canGetHurt()) {
            this.sprite.setTexture(this.initTexture);
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
        //int spawningID;
              
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
                this.strength,
                this.exp,
                pos,
                this.sprite.getTexture()
        );
        /*
        //getting a random enemy type depending on the amount of options (4 is the amount of possible enemy types, up it if you want to add another)
        spawningID = (int) Math.floor(Math.random()*(4) + 1);
        
        
        switch (spawningID) { //you could've prebuilt all of these in Global and spawned them at random ~AF
            case 1 :
                //default enemy (find a name maybe)
                return new Enemy(
            this.levelScaling,
            this.maxHP,
            this.speed,
            this.strength,
            this.exp,
            pos,
            this.sprite.getTexture(),
            this.sprite.getColor()
        );
            case 2 :
                //faster enemy (find a name maybe)
                return new Enemy(
            this.levelScaling,
            this.maxHP,
            this.speed*2,
            this.strength,
            this.exp,
            pos,
            this.sprite.getTexture(),
            Color.CHARTREUSE
        );
            case 3 :
                //stronger enemy (find a name maybe)
                return new Enemy(
            this.levelScaling,
            this.maxHP,
            this.speed,
            this.strength*2,
            this.exp*2,
            pos,
            this.sprite.getTexture(),
            Color.SCARLET
        );
            case 4 :
                //more tanky enemy (find a name maybe
                return new Enemy(
            this.levelScaling,
            this.maxHP*10,
            this.speed/2,
            this.strength,
            this.exp*3,
            pos,
            this.sprite.getTexture(),
            Color.LIGHT_GRAY
        );
            //default enemy (find a name maybe)
            default :
                return new Enemy(
            this.levelScaling,
            this.maxHP,
            this.speed,
            this.strength,
            this.exp,
            pos,
            this.sprite.getTexture(),
            this.sprite.getColor()
        );
            
                
        }*/
        
    }
      
}
    

