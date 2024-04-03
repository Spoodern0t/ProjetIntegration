/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Ekrem Yoruk (1676683)
 * @author Alexis Fecteau (2060238)
 * 
 * @since 03/04/2024
 */

public abstract class Entity {
    
//Attributes
    public int maxHP, currentHP, knockBack=0;
    double damageMod = 1; //multiplier to apply on base stats in spawn()
    public Vector2 position;
    public Sprite sprite;
    public float speed, hitboxRadius = 24;
    public float lastHurtTime = 0f, timeBetweenHurt = 1f;
    public boolean isDead = false; 
    public Circle hitbox;
    
    
//Constructors
    public Entity(int maxHP, float speed, Vector2 position, Texture img){
        
        this.sprite = new Sprite(img);
        //DO NOT USE SETSCALE()!
        //Either use setSize() or manually change the texture file's dimensions.
        //A sprite's position is its bottom left corner, and it doesn't adjust when scaling! ~AF
        this.position = position;
        this.hitboxRadius = sprite.getHeight()/2;
        this.hitbox = new Circle(position,hitboxRadius);
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.speed = Math.max(speed, 0); //so speed can't be negative
    }
    
    public Entity(int maxHP, float speed, Vector2 position){
        this(maxHP, speed, position, new Texture("LilBoy.png"));
    }
    
    public Entity(int maxHP, float speed){
        this(maxHP, speed, new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2));
    }
    
    public Entity(){
        this(100, 300);
    }

//methods
    /**
     * Handles the object's logic, updates its state and attributes for the 
     * current time/conditions.
     * @param deltaTime Time since last call to render(). Usually gotten from 
     * Gdx.graphics.getDeltaTime()
     * @throws com.mygdx.locomotor.Entity.DeadEntityException
     */
    public void update(float deltaTime) throws DeadEntityException{
        
        if (this.isDead){ //skips update early if object is dead
            throw new DeadEntityException();
        }
        
        lastHurtTime += deltaTime;
        
        if (this.currentHP <= 0){
            this.die();
        }
    
    }
    
    /**
     * Tells whether this object can currently take damage.
     * @return true if the damage cool-down is over.
     */
    public boolean canGetHurt(){//damage cooldown timer
        return(this.lastHurtTime >= timeBetweenHurt);//returns true when substraction result becomes higher than 0.
    }
    
    /**
     * Updates the object's position and moves its graphics and hit-box there.
     * @param batch This object's associated sprites
     * @since 15/03/2024
     */
    public void draw(SpriteBatch batch){
        try{
            this.update(Gdx.graphics.getDeltaTime());
            this.sprite.setPosition(this.position.x,this.position.y); //TODO: change to sprite.setCenter() and deal with consequences
            this.hitbox.setPosition(this.position);
        } catch (DeadEntityException e){
            
        }finally{
            this.sprite.draw(batch);
        }

    }
    
    /**
     * Marks object as dead and prepares its removal via depawnList.
     */
    public void die(){
        this.isDead = true;
        GameScreen.despawnList.add(this);
        //may be overridden to add on-death interactions.
    }
    
    /**
     * Creates a copy of a final, hard-coded reference entity that shares only 
     * its fundamental attributes. This allows distinct entity types to have
     * varied starting conditions (e.g. coordinates, angle, stat multipliers)
     * without defining them individually.
     * @return new Entity similar to calling object
     */
    public abstract Entity spawn();
    
    /**
     * Checks for collision between this object and another Entity.
     * @param target Entity to check for collision with. 
     * @return true if there's a collision, false otherwise.
     */
    public boolean collide(Entity target){
        return (this.hitbox.overlaps(target.hitbox));
    }
    
//custom exception type
    protected class DeadEntityException extends Exception{
        
        public DeadEntityException(){
            
        }
        public DeadEntityException(String message){
            super(message);
        }
        
    }

//getters/setters
    
}
