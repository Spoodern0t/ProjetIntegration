/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
/**
 *
 * @author Adam Tamine
 */
public class Projectile extends Entity{ //might become abstract superclass. ~AF
    
    double flatDamage, decayTime;
    double angle;
    
//constructors
    public Projectile(double flatDamage, double decayTime, double angle, int maxHP, float speed, Vector2 position, Texture img){
        
        super(maxHP, speed, position, img);
        this.flatDamage = flatDamage;
        this.decayTime = decayTime;
        this.angle = angle;
    }
    
    public Projectile(double angle){ //only for testing purposes. ~AF
        this(1, 3, angle, 1, 100,
                new Vector2(GameScreen.player.position.x,GameScreen.player.position.y),
                new Texture("Evil.png"));
        this.hitbox.radius = 12;
        this.sprite.setScale(1);
        this.sprite.setColor(Color.RED);
    }
    
//methods
    /**
     * Updates the projectile object for the current time and conditions.
     * Also handles object logic.
     * @param deltaTime Time since last call to render()
     * @author Adam Tamine
     * @since 06/03/2024
     */
    @Override
    public void update(float deltaTime){
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
     * @author Alexis Fecteau
     * @since 18/03/2024
     */
    @Override
    public Entity spawn(){ //currently simplified for testing purposes
        /*
        Vector2 pos = new Vector2(GameScreen.player.position.x,GameScreen.player.position.y);
        return new Projectile(
            this.flatDamage,
            this.decayTime,
            MathUtils.random(0,360),
            this.maxHP,
            this.speed,
            pos,
            this.img
        );
        */
        return new Projectile(MathUtils.random(0,360));
    }
}
