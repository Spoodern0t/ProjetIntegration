/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Yoruk Ekrem
 */
public class Entity {
    public int maxHP, currentHP, hitBoxRadius, knockBack;
    double damageMod;
    public Vector2 position;
    public Sprite sprite;
    public float speed = 300;
    public boolean alive =true;
    
    
    public Entity(Texture img,Color color, int maxHP)
    {
        this.maxHP=maxHP;
        this.currentHP = this.maxHP;
        sprite = new Sprite(img);
        sprite.setScale(4);
        sprite.setColor(color);
        position = new Vector2(Gdx.graphics.getWidth()/2,sprite.getScaleY()*sprite.getHeight()/2);    
    }
    
    public void Update(float deltaTime)
    {
        if(Gdx.input.isKeyPressed(Keys.A)) position.x -= deltaTime*speed;
        if(Gdx.input.isKeyPressed(Keys.D)) position.x += deltaTime*speed;
        if(Gdx.input.isKeyPressed(Keys.W)) position.y += deltaTime*speed;
        if(Gdx.input.isKeyPressed(Keys.S)) position.y -= deltaTime*speed;
        
        if(position.x-(sprite.getWidth()*sprite.getScaleX()/2)<=0) position.x=(sprite.getWidth()*sprite.getScaleX()/2);
         if(position.x+(sprite.getWidth()*sprite.getScaleX()/2)>=Gdx.graphics.getWidth()) position.x=Gdx.graphics.getWidth()-(sprite.getWidth()*sprite.getScaleX()/2);
        if(position.y-(sprite.getHeight()*sprite.getScaleY()/2)<=0) position.y=(sprite.getHeight()*sprite.getScaleY()/2);
         if(position.y+(sprite.getHeight()*sprite.getScaleY()/2)>=Gdx.graphics.getHeight()) position.y=Gdx.graphics.getHeight()-(sprite.getHeight()*sprite.getScaleY()/2);
         
         
         
    }
    public void Draw(SpriteBatch batch)
    {

        Update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x,position.y);
        sprite.draw(batch);
        
        
        
    }
    
    public boolean die(){
        return false;
        
    }
    
    public void spawn(){
        
    }
    
    
    
}
