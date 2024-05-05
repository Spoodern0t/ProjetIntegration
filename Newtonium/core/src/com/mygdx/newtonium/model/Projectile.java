/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
/**
 *
 * @author Adam Tamine
 * @author Alexis Fecteau (2060238)
 * 
 * @since 29/04/2024
 */
public abstract class Projectile extends Entity{ //might become abstract superclass. ~AF
    
    public double flatDamage, decayTime;
    double angle = 0;
    
//constructors
    public Projectile(double flatDamage, double decayTime, int pierceAmount, float speed, Vector2 position, Texture img){
        
        super(pierceAmount, speed, position, img);
        this.flatDamage = flatDamage;
        this.decayTime = decayTime;
    }
    
//methods
    /**
     * Updates the projectile object for the current time and conditions.
     * Also handles object logic.
     * @param deltaTime Time since last call to render()
     */
    @Override
    protected void update(float deltaTime) throws DeadEntityException{
        super.update(deltaTime);
        this.decayTime -= deltaTime;
        if (this.decayTime <= 0){
            this.die();
        }
    }
    //In case I direly Need it i guess idk-EY
    public void setangle(double angle){
        this.angle = angle;
    }
    
    /**
     * Creates a copy of this projectile at the player's current position.
     * @return new Projectile object similar to calling instance
     */
    /*
    @Override
    public Entity spawn(){
        
        Vector2 pos = new Vector2(Global.currentPlayer.position.x,Global.currentPlayer.position.y);
        return new Projectile(
            this.flatDamage,
            this.decayTime,
            this.maxHP,
            this.speed,
            pos,
            this.sprite.getTexture()
        );
        
    }*/
}
