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
 * @author 2249229
 *
 */
public class Player extends Entity {
    
//Attributes
    static int DEFAULT_MAX_HP = 100;
    static float DEFAULT_SPEED = 300;
    int level, exp, levelTreshold = 100;
    static float Default_PlayerHitBox = 24;
    /* these can be declared in Entity.java and inherited
    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }*/
    
//Constructors     
    public Player(int level, int maxHP, float speed, Vector2 position, Texture img) {
        super(maxHP, speed, position, img,24);
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
    public void levelUp(){
        
    }
    
      @Override  
      public void update(float deltaTime)
    {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) position.x -= deltaTime*speed;
        if(Gdx.input.isKeyPressed(Input.Keys.D)) position.x += deltaTime*speed;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) position.y += deltaTime*speed;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) position.y -= deltaTime*speed;
        
        if(position.x-(sprite.getWidth()*sprite.getScaleX()/2)<=0) position.x=(sprite.getWidth()*sprite.getScaleX()/2);
        if(position.x+(sprite.getWidth()*sprite.getScaleX()/2)>=Gdx.graphics.getWidth()) position.x=Gdx.graphics.getWidth()-(sprite.getWidth()*sprite.getScaleX()/2);
        if(position.y-(sprite.getHeight()*sprite.getScaleY()/2)<=0) position.y=(sprite.getHeight()*sprite.getScaleY()/2);
        if(position.y+(sprite.getHeight()*sprite.getScaleY()/2)>=Gdx.graphics.getHeight()) position.y=Gdx.graphics.getHeight()-(sprite.getHeight()*sprite.getScaleY()/2);
        
        
        
    }
      @Override
        public void draw(SpriteBatch batch)
    {

        update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x,position.y);
        Player.super.HitBox.setPosition(position.x,position.y);
        sprite.draw(batch);
        
        
    }
    
    @Override
    public void die(){
        if(this.currentHP<=0){
           this.alive = false; 
        }else{
           this.alive = true;
        }if(this.alive == false){
            /*endgamemenuopeninator(); to, you know open the darn game over screen.*/
            System.out.println("Playur is ded! Not big souprice.");  /*endgamemenuopen();*/
        }
    }
    
    
    
    
}
