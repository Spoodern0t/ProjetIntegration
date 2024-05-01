/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.newtonium.control.Global;

/**
 *
 * @author Alexis Fecteau (2060238)
 * 
 * @since 29/04/2024
 */
public class OrbitProjectile extends Projectile{
    
//attributes
    //NOTE: These units are on a Scale of 1 meter per 25 pixels (or Vector2D position units)
    
    float mass; //kilograms
    float orbitRadius; //meters 
    float orbitPeriod; //seconds
    float collisionTime = 1; //in seconds (may get rid of this)
    
    
//constructors
    public OrbitProjectile(float mass, float orbitRadius, int pierceAmount, float instantVelocity, Texture img){
        //flatDamage updates automatically over this object's lifespan. 
        super(1, 0, pierceAmount, instantVelocity, new Vector2(Global.currentPlayer.position), img);
        this.mass = mass;
        this.orbitRadius = orbitRadius;
        this.orbitPeriod = (2 * (float)Math.PI * orbitRadius) / instantVelocity;
        this.decayTime = this.orbitPeriod * 3;
        //this.angle could be set to a random number here, right now it's 0. ~AF
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
        
    //calculate new position
        //ds = v*dt     ds = r*d(angle)
        float deltaAngle = (speed * deltaTime)/orbitRadius;
        angle += deltaAngle;
        
        this.position.x = Global.currentPlayer.position.x + (float)Math.cos(angle) * orbitRadius * 25; //nb. meters * 25 pixels a meter
        this.position.y = Global.currentPlayer.position.y + (float)Math.sin(angle) * orbitRadius * 25;
        
    }
    
    /**
     * Calculates the relative velocity (in meters/second) between this
     * projectile and a target Entity.
     * @param target Entity to calculate relative velocity with.
     * @param deltaTime Time since last game logic update.
     * @return 
     */
    private float relativeVelocity(Entity target, float deltaTime){
        
    //get this and target's pixel displacements vectors since last render
        Vector2 deltaX = new Vector2(this.position.x - this.lastPosition.x, this.lastPosition.y - this.lastPosition.y);
        Vector2 targetDeltaX =  new Vector2(target.position.x - target.lastPosition.x, target.lastPosition.y - target.lastPosition.y);
        
    //get a relative pixel displacement length between this and target
        float relativeDeltaX = deltaX.sub(targetDeltaX).len();
        
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
    private float exertedForce(Entity target, float deltaTime){
        //Exerted force = (projectile mass * relative velocity between objects) / collision duration
        
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
    
    /**
     * Creates a copy of this projectile at the player's current position.
     * @return new OrbitProjectile object similar to calling instance
     */
    @Override
    public Entity spawn(){

        return new OrbitProjectile( //TODO: adapt this once this class is complete
            this.mass,
            this.orbitRadius,
            this.maxHP,
            this.speed,
            this.sprite.getTexture()
        );
        
    }
    
    
}
