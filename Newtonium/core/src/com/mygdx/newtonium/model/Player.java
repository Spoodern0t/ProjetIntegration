/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.mygdx.newtonium.control.Global;
import com.mygdx.newtonium.control.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

/**
 *
 * @author Nathan Latendresse (2249229)
 * @author Alexis Fecteau (2060238)
 * 
 * @since 29/04/2024
 *
 */
public class Player extends Entity {
    
//Attributes
    public int DEFAULT_LEVELTHRESHOLD = 100;
    public int currentExp, level, exp, levelThreshold = 100;
    
    static final int MAXIMUM_ITEM_CAPACITY = 6;
    private ArrayList<Item> items = new ArrayList<>();
    
//Constructors     
    public Player(int level, int maxHP, float speed, Vector2 position, Texture img) {
        super(maxHP, speed, position, img);
        this.level = Math.max(level, 1); //so level never goes under 0
        this.timeBetweenHurt = 0.5f;
        //ITERATION 2: this.exp calculated with level input and levelUp() method            
        
    }
    
//Methods
    
    /**
     * Increases the player object's level attribute
     */
    public void levelUp(){
        this.level++; //maybe add an int parameter later for multiple level-ups?
        this.currentExp = 0;
        this.levelThreshold = DEFAULT_LEVELTHRESHOLD * this.level;
    }
    
    /**
     * Updates the player object for the current time and conditions.
     * Also handles object logic.
     * @param deltaTime Time since last call to render()
     */
    @Override  
    protected void update(float deltaTime) throws DeadEntityException{
        
        super.update(deltaTime); //checks for death, skips update if dead
        
    //player movement logic
        if (Gdx.input.isKeyPressed(Input.Keys.A)) position.x -= deltaTime*speed;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) position.x += deltaTime*speed;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) position.y += deltaTime*speed;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) position.y -= deltaTime*speed;
        
        //keep player within the boundaries
        if (position.x < sprite.getWidth()/2)
            position.x = sprite.getWidth()/2;
        if (position.x + sprite.getWidth()/2 > GameScreen.mapSprite.getWidth())
            position.x = GameScreen.mapSprite.getWidth() - sprite.getWidth()/2;
        if (position.y < sprite.getHeight()/2)
            position.y = sprite.getHeight()/2;
        if (position.y + sprite.getHeight()/2 > GameScreen.mapSprite.getHeight())
            position.y = GameScreen.mapSprite.getHeight() - sprite.getHeight()/2;
         
    //damage taken logic
        if (this.canGetHurt()) {
            this.sprite.setTexture(this.initTexture);
        }
        
    //item trigger logic
        for (Item i: items){
            i.update(deltaTime);
        }
        
    }
    
    /**
     * Marks player as dead and prints test message.
     */
    @Override
    public void die(){
        super.die();
        items.removeAll(items);
        GameScreen.isOver = true;
        
        /*
        //player death prompts game to end, etc
        endgameMenuOpen();
        System.out.println("Playur is ded! Not big souprice.");
        */
    }
    
    /**
     * Creates a copy of a Player object that's level 1 and spawns at the
     * middle of the screen.
     * @return new Player object similar to calling instance
     */
    @Override
    public Entity spawn(){
        return new Player(
            1, 
            this.maxHP,
            this.speed,
            new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2),
            this.sprite.getTexture()
        );
    }
    
//getters/setters
    
    /**
     * Adds an item to the player's inventory. Restricted by item capacity.
     * @param item Item to add to the player's inventory 
     */
    public void addItem(Item item){
        if (items.size() < MAXIMUM_ITEM_CAPACITY){
            items.add(item);
        }
    }
    
    
}
