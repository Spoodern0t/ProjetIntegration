/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.newtonium.control.Global;

/**
 *
 * @author Alexis Fecteau (2060238)
 * 
 * @since 25/04/2024
 */
public class TestProjectile extends Projectile{
    
//constructors
    public TestProjectile(double flatDamage, double decayTime, int maxHP, float speed, Vector2 position, Texture img){
        
        super(flatDamage, decayTime, maxHP, speed, position, img);
        this.sprite.setColor(Color.RED);
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
        
        Vector2 pos = new Vector2(Global.currentPlayer.position.x,Global.currentPlayer.position.y);
        return new TestProjectile(
            this.flatDamage,
            this.decayTime,
            this.maxHP,
            this.speed,
            pos,
            this.sprite.getTexture()
        );
        
    }
    
}
