/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
/**
 *
 * @author Alexis Fecteau (2060238)
 */
public class Projectile extends Entity{
    
    double flatDamage, decayTime;
    public float vitesse_projectile;
    public Sprite sprite_projectile;
    
    public Projectile(float x, float y, float speed, float angle, Texture texture){
        this.maxHP = maxHP;
        position= new Vector2(0,1000);
        this.position = new Vector2(x, y);
        this.position = new Vector2(x, y);
        this.speed = speed;
        this.position = new Vector2(speed * (float)Math.cos(angle), speed * (float)Math.sin(angle));
        this.sprite = new Sprite(texture); 
        this.sprite.setPosition(x, y);
        this.sprite.scale(20);
        
    }
    
    public void update(float deltaTime){
         position.add(position.x * deltaTime, position.y * deltaTime);
        sprite.setPosition(position.x, position.y);
    }
    public void draw(SpriteBatch batch) {
        
        sprite.draw(batch);
    }
    
    /*
    
    */
}
