/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;
import com.mygdx.newtonium.control.GameScreen;
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
        /* IF WE USE THIS IT WIL AIM (CAN'T WORK, TO MUCH CLASS)
        for(Enemy e: GameScreen.enemyList){
            this.angle=Math.atan((e.position.y-GameScreen.player.position.y)/(e.position.x-GameScreen.player.position.x));
    }
        */
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
     * Creates a copy of this projectile at the player's current position.
     * @return new TestProjectile object similar to calling instance
     */
    @Override
    public Entity spawn(){
        
        Vector2 pos = new Vector2(Global.currentPlayer.position);
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
