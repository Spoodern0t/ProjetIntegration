/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

/**
 *
 * @author Nathan Latendresse (2249229)
 * @author Alexis Fecteau (2060238)
 * 
 * @since 02/04/2024
 *
 */
public class Player extends Entity {
    
//Attributes
    static int DEFAULT_MAX_HP = 10;
    static float DEFAULT_SPEED = 300;
    int level, exp, levelThreshold = 100;
    static float Default_PlayerHitBox = 24;
    
    static final int MAXIMUM_ITEM_CAPACITY = 6;
    private ArrayList<Item> items = new ArrayList<>();
    
//Constructors     
    public Player(int level, int maxHP, float speed, Vector2 position, Texture img) {
        super(maxHP, speed, position, img);
        this.level = Math.max(level, 1); //so level never goes under 0
        this.timeBetweenHurt = 3f;
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
     * @since 15/03/2024
     */
    public void levelUp(){
        this.level++; //maybe add an int parameter later for multiple level-ups?
    }
    
    /**
     * Updates the player object for the current time and conditions.
     * Also handles object logic.
     * @param deltaTime Time since last call to render()
     * @since 02/04/2024
     */
    @Override  
    public void update(float deltaTime){
        
        super.update(deltaTime); //checks for death, skips update if dead
        
    //player movement logic
        if (Gdx.input.isKeyPressed(Input.Keys.A)) position.x -= deltaTime*speed;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) position.x += deltaTime*speed;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) position.y += deltaTime*speed;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) position.y -= deltaTime*speed;
        
        //keep player witin the boundaries
        float offsetX = (sprite.getWidth()*sprite.getScaleX() - sprite.getWidth())/2;
        float offsetY = (sprite.getHeight()*sprite.getScaleY() - sprite.getHeight())/2;
        if (position.x - offsetX < 0)
            position.x = offsetX;
        if (position.x + sprite.getWidth() + offsetX > GameScreen.mapSprite.getWidth())
            position.x = GameScreen.mapSprite.getWidth() - (sprite.getWidth() + offsetX);
        if (position.y - offsetY < 0)
            position.y = offsetY;
        if (position.y + sprite.getHeight() + offsetY > GameScreen.mapSprite.getHeight())
            position.y = GameScreen.mapSprite.getHeight() - (sprite.getHeight() + offsetY);
         
    //damage taken logic
        if (this.canGetHurt()) {
            this.sprite.setTexture(GameScreen.idleTexture);
        }
        
    //item trigger logic
        for (Item i: items){
            i.update(deltaTime);
        }
        
    }
    
    /**
     * Marks player as dead and prints test message.
     * TODO: show game over screen upon player death
     * @since 05/03/2024
     */
    @Override
    public void die(){
        super.die();
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
     * @since 15/03/2024
     */
    @Override
    public Entity spawn(){
        return new Player(
            1, 
            this.maxHP,
            this.speed,
            new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2),
            this.idleTexture
        );
    }
    
//getters/setters
    
    /**
     * Adds an item to the player's inventory. Restricted by item capacity.
     * @param item Item to add to the player's inventory 
     * @since 25/03/2024
     */
    public void addItem(Item item){
        if (items.size() < MAXIMUM_ITEM_CAPACITY){
            items.add(item);
        }
    }
    
    
}
