/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Nathan Latendresse (2249229)
 *
 */
public class Player extends Entity {
    
//Attributes
    static int DEFAULT_MAX_HP = 100;
    static float DEFAULT_SPEED = 300;
    int level, exp, levelTreshold = 100;
    static float Default_PlayerHitBox = 24;
    
//Constructors     
    public Player(int level, int maxHP, float speed, Vector2 position, Texture img) {
        super(maxHP, speed, position, img);
        this.level = Math.max(level, 1); //so level never goes under 0
        //ITERATION 2: this.exp calculated with level input and levelUp() method            
        
    }
    
    public Player(int level) {
        this(level, DEFAULT_MAX_HP, DEFAULT_SPEED, new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2), new Texture("LilBoy.png"));
        this.sprite.setColor(Color.GREEN);
        this.hitboxRadius = Player.Default_PlayerHitBox;
    }
    
    public Player() {
        this(1);
    }
//Methods
    
    /**
     * Increases the player object's level attribute
     * @author Alexis Fecteau
     * @since 15/03/2024
     */
    public void levelUp(){
        this.level++; //maybe add an int parameter later for multiple level-ups?
    }
    
    /**
     * Updates the player object for the current time and conditions.
     * Also handles object logic.
     * @param deltaTime Time since last call to render()
     * @author Nathan Latendresse
     * @since 21/02/2024
     */
    @Override  
    public void update(float deltaTime){
        
        super.update(deltaTime); //checks for death, skips update if dead
        
    //moves player when movement keys are pressed
        if(Gdx.input.isKeyPressed(Input.Keys.A)) position.x -= deltaTime*speed;
        if(Gdx.input.isKeyPressed(Input.Keys.D)) position.x += deltaTime*speed;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) position.y += deltaTime*speed;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) position.y -= deltaTime*speed;
        
        if(position.x-(sprite.getWidth()*sprite.getScaleX()/2)<=0) position.x=(sprite.getWidth()*sprite.getScaleX()/2);
        if(position.x+(sprite.getWidth()*sprite.getScaleX()/2)>=Gdx.graphics.getWidth()) position.x=Gdx.graphics.getWidth()-(sprite.getWidth()*sprite.getScaleX()/2);
        if(position.y-(sprite.getHeight()*sprite.getScaleY()/2)<=0) position.y=(sprite.getHeight()*sprite.getScaleY()/2);
        if(position.y+(sprite.getHeight()*sprite.getScaleY()/2)>=Gdx.graphics.getHeight()) position.y=Gdx.graphics.getHeight()-(sprite.getHeight()*sprite.getScaleY()/2);
        
        //TODO: damage taken logic
    }
    
    /**
     * Marks player as dead and prints test message.
     * TODO: show game over screen upon player death
     * @author Ekrem Yoruk
     * @since 05/03/2024
     */
    @Override
    public void die(){
        super.die();
        /*endgamemenuopeninator(); to, you know open the darn game over screen.*/
        System.out.println("Playur is ded! Not big souprice.");  /*endgamemenuopen();*/
    }
    
    /**
     * Creates a copy of a Player object that's level 1 and spawns at the
     * middle of the screen.
     * @return new Player object similar to calling instance
     * @author Alexis Fecteau
     * @since 15/03/2024
     */
    @Override
    public Entity spawn(){
        return new Player(
            1, 
            this.maxHP,
            this.speed,
            new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2),
            this.img
        );
    }
    
    
    
}
