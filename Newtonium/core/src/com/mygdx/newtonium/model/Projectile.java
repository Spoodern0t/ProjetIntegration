/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
/**
 * Entity type summoned by Item objects and meant to damage Enemy objects.
 * 
 * @author Alexis Fecteau (2060238)
 * 
 * @since 13/05/2024
 */
public abstract class Projectile extends Entity{ //might become abstract superclass. ~AF
    
    public double flatDamage, decayTime;
    double angle = 0;
    float mass; //in kilograms
    
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
    
    /**
     * Calculates the relative velocity (in meters/second) between this
     * projectile and a target Entity.
     * @param target Entity to calculate relative velocity with.
     * @param deltaTime Time since last game logic update.
     * @return Speed of this object relative to target
     */
    protected float relativeVelocity(Entity target, float deltaTime){
        
    //get this and target's pixel displacements vectors since last render
        Vector2 deltaX = new Vector2(this.position.x - this.lastPosition.x, this.lastPosition.y - this.lastPosition.y);
        Vector2 targetDeltaX =  new Vector2(target.position.x - target.lastPosition.x, target.lastPosition.y - target.lastPosition.y);
        
    //get a relative pixel displacement length between this and target
        float relativeDeltaX = deltaX.dst(targetDeltaX);
        
    //convert length from pixels to meters
        relativeDeltaX /= 25; // 1 meter = 25 pixels
        
    //calculate relative velocity magnitude using deltaTime 
        float RVMagnitude = relativeDeltaX / deltaTime;
        return Math.abs(RVMagnitude);
    }
    
    /**
     * Calculates the force (in Newtons) this projectile would exert on a
     * target Entity, based on the relative velocity between them.
     * @param target Entity to calculate exerted force on.
     * @param deltaTime Time since last game logic update.
     * @return 
     */
    protected float exertedForce(Entity target, float deltaTime){
        //Exerted force = (projectile mass * relative velocity between objects) / collision duration
        
        float collisionTime = 1;
        float force = (this.mass * relativeVelocity(target, deltaTime))/collisionTime; //a collision of 1 second gives nicely-balanced numbers. ~AF
        
        return force; //in Newtons
    }
    
    /**
     * Checks for collision with target Entity and calculates contact damage.
     * @param target Entity to check for collision and damage with. 
     * @return true if there's a collision, false otherwise.
     */
    @Override
    public boolean collide(Entity target){
        if (this.hitbox.overlaps(target.hitbox))
            this.flatDamage = exertedForce(target, Gdx.graphics.getDeltaTime());
        return this.hitbox.overlaps(target.hitbox);
    }
    
}
