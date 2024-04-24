/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;
import com.mygdx.newtonium.control.Global;
import com.mygdx.newtonium.control.GameScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
/**
 *
 * @author Adam Tamine
 * @author Alexis Fecteau (2060238)
 * 
 * @since 23/04/2024
 */
public class Projectile extends Entity{ //might become abstract superclass. ~AF
    
    public double flatDamage, decayTime;
    double angle;
    
//constructors
    public Projectile(double flatDamage, double decayTime, int maxHP, float speed, Vector2 position, Texture img){
        
        super(maxHP, speed, position, img);
        this.flatDamage = flatDamage;
        this.decayTime = decayTime;
        this.angle = MathUtils.random(0,360);
    }
    
//methods
    /**
     * Updates the projectile object for the current time and conditions.
     * Also handles object logic.
     * @param deltaTime Time since last call to render()
     */
    @Override
    protected void update(float deltaTime) throws DeadEntityException{
        this.decayTime -= deltaTime;
        if (this.decayTime <= 0){
            this.die();
        }
        super.update(deltaTime);
        this.position.add(this.speed *(float)Math.cos(this.angle) * deltaTime, this.speed * (float)Math.sin(this.angle) * deltaTime);
    }
    
    /**
     * Creates a copy of a Projectile object that spawns at the player's
     * position with a random angle.
     * @return new Projectile object similar to calling instance
     */
    @Override
    public Entity spawn(){
        
        Vector2 pos = new Vector2(GameScreen.currentPlayer.position.x,GameScreen.currentPlayer.position.y);
        return new Projectile(
            this.flatDamage,
            this.decayTime,
            this.maxHP,
            this.speed,
            pos,
            this.sprite.getTexture()
        );
        
    }
}
