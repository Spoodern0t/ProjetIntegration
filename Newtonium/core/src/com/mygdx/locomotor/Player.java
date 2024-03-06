/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author 2249229
 */
public class Player extends Entity {
    
    int level, exp, levelTreshold;
    
<<<<<<< Updated upstream
    
    public Player(Texture img, Color color) {
        super(img, color);
=======
    public Player(Texture img,int maxHP){
         super(img,maxHP);
        this.maxHP=maxHP;
        this.currentHP = this.maxHP;
        
        sprite = new Sprite(img);
        sprite.setScale(4);
        position = new Vector2(Gdx.graphics.getWidth()/2,sprite.getScaleY()/**sprite.getHeight()/2)*/);   
    }

           
    public Player(Texture img, Color color,int maxHP) {
        super(img, color,maxHP);
        this.maxHP=maxHP;
        this.currentHP = this.maxHP;
        
        sprite = new Sprite(img);
        sprite.setScale(4);
        sprite.setColor(color);
        position = new Vector2(Gdx.graphics.getWidth()/2,sprite.getScaleY()/**sprite.getHeight()/2*/);   
>>>>>>> Stashed changes
    }
    
    public void levelUP(){
        
    }
    
<<<<<<< Updated upstream
=======
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
        sprite.draw(batch);
        
        
    }
>>>>>>> Stashed changes
    
    public void die(){
        
    }
    
    
    
    
}
