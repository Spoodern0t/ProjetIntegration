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

abstract class Entity {
    
//Attributes
    
    public int maxHP, currentHP, knockBack=0;
    double damageMod = 1;
    public Vector2 position;
    public Sprite sprite;
    public float speed, hitboxRadius = 24;
    public boolean alive =true; 
    public Circle HitBox;
    public Texture ooftexture =new Texture("sadge.png");
    public Texture img = new Texture("LilBoy.png");
    public Rectangle BoundingBox;
//Constructors
    public Entity(int maxHP, float speed, Vector2 position, Texture img, float hitBoxRadius){
        this.sprite = new Sprite(img);
        sprite.setScale(4); //can be set later
        this.position = position;
        this.hitboxRadius = hitBoxRadius;
        this.BoundingBox = new Rectangle(position.x,position.y,this.hitboxRadius*2*this.sprite.getScaleX(),this.hitboxRadius*2*this.sprite.getScaleY());
        hitBoxRadius = (sprite.getHeight()*sprite.getScaleY())/2;
        this.HitBox = new Circle(position,hitBoxRadius);
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.speed = Math.max(speed, 0); //so speed can't be negative
    }
    
    public Entity(int maxHP, float speed, Vector2 position){
        this(maxHP, speed, position, new Texture("LilBoy.png"),24);
    }
    
    public Entity(int maxHP, float speed){
        this(maxHP, speed, new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2));
    }
    
    public Entity(){
        this(100, 300);
    }

//methods
    public void update(float deltaTime)//anything that involves timing but isn't movement
    {

    }
    
    public void draw(SpriteBatch batch)//to put the textures here if they come in multiple components.
    {
        
    }
    
    public void die(){//disappearance trigger
        if(this.currentHP <= 0)
           this.alive = false;
    }
    
    public void spawn(){//Likely to be placed in GameScreen, Extended thought will be put on it at iteration 2
        
    }
    
    public boolean collide(Entity target){//hitboxCollide
        return(this.HitBox.overlaps(target.HitBox));//attempted both. Didn't work.
    }
    public boolean Scollide(Entity target){//SpriteCollide
        return(this.sprite.getBoundingRectangle().overlaps(target.sprite.getBoundingRectangle()));
    }
    
    
    
//getters/setters
    
}
