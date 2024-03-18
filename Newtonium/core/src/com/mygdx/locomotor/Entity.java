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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Yoruk Ekrem (1676683)
 * @author Alexis Fecteau (2060238)
 */

public abstract class Entity {
    
//Attributes
    
    public int maxHP, currentHP, knockBack=0;
    double damageMod = 1; //multiplier to apply on base stats in spawn()
    public Vector2 position;
    public Sprite sprite;
    public float speed, hitboxRadius = 24,lastHurtTime = 0f,timebetweenHurt = 1f;
    public boolean isDead = false; 
    public Circle hitBox;
    public Texture ooftexture =new Texture("sadge.png");
    public Texture img = new Texture("LilBoy.png");
    
    
//Constructors
    public Entity(int maxHP, float speed, Vector2 position, Texture img){
        
        this.sprite = new Sprite(img);
        this.sprite.setScale(4); //can be set later
        this.position = position;
        this.hitboxRadius = (sprite.getHeight()*sprite.getScaleY())/2;
        this.hitBox = new Circle(position,hitboxRadius);
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
     * @author Alexis Fecteau
     * @since 15/03/2024
     */
    public void update(float deltaTime){
        lastHurtTime += deltaTime;
        if (this.currentHP <= 0){
            this.die();
        }
        if (this.isDead){ //skips update early if object is dead
            return;
        }
    
    }
    public boolean canGetHurt(){//damage cooldown timer
        return(this.lastHurtTime - timebetweenHurt >= 0);//returns true when substraction result becomes higher than 0.
    }
    /**
     * Updates the object's position and moves its graphics and hit-box there.
     * @param batch This object's associated sprites
     * @author Nathan Latendresse, Ekrem Yoruk
     * @since 15/03/2024
     */
    public void draw(SpriteBatch batch){
        this.update(Gdx.graphics.getDeltaTime());
        this.sprite.setPosition(position.x,position.y);
        this.hitBox.setPosition(position.x,position.y);
        this.sprite.draw(batch);
    }
    
    /**
     * Marks object as dead. Used to prevent updates and remove it from lists.
     * @author Alexis Fecteau
     * @since 15/03/2024
     */
    public void die(){
        this.isDead = true;
        //may be overridden to add on-death interactions.
    }
    
    /**
     * Creates a copy of a final, hard-coded reference entity that shares only 
     * its fundamental attributes. This allows distinct entity types to have
     * varied starting conditions (e.g. coordinates, angle, stat multipliers)
     * without defining them individually.
     * @author Alexis Fecteau
     * @since 15/03/2024
     */
    public abstract Entity spawn();
    
    /**
     * Checks for collision between this object and another Entity.
     * @param target Entity to check for collision with. 
     * @return true if there's a collision, false otherwise.
     * @author Alexis Fecteau
     * @since 15/03/2024
     */
    public boolean collide(Entity target){
        if (this != target){
            return(this.hitBox.overlaps(target.hitBox));
        } else return false;
    }
    public boolean Scollide(Entity target){//SpriteCollide
        return(this.sprite.getBoundingRectangle().overlaps(target.sprite.getBoundingRectangle()));
    }
    
    
//getters/setters
    
}
