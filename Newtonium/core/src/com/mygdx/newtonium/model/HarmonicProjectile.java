/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.newtonium.control.Global;

/**
 *
 * @author Alexis Fecteau
 * @since 08/05/2024
 */
public class HarmonicProjectile extends Projectile {
    
//attributes
    float mass; //in kilograms
    double angularFrequence; //in radians
    double phaseConstant = -(Math.PI / 2);
    float amplitude; //in meters
    float acceleration;
    float harmonicPeriod; //in seconds
    float timeSinceSpawn = 0;
    
    
//constructors
    public HarmonicProjectile(float mass, float amplitude, float harmonicPeriod, int pierceAmount, Texture img) {
        super(1, 0, pierceAmount, 0, new Vector2(Global.currentPlayer.position), img);
        this.mass = mass;
        this.amplitude = amplitude;
        this.harmonicPeriod = harmonicPeriod;
        
        //w = 2*PI/T
        this.angularFrequence = (2 * Math.PI)/this.harmonicPeriod;
        
        this.decayTime = this.harmonicPeriod * 3;
        
        //initial offset to avoid crazy high damage on spawning frame
        this.position.x = Global.currentPlayer.position.x;
        this.position.y = Global.currentPlayer.position.y;
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
        
        float playerOffset = Global.currentPlayer.position.x;
        
        this.position.y = Global.currentPlayer.position.y;
        this.timeSinceSpawn += deltaTime;
        
    //calculate new position
        //current position = amplitude * cos(angular velocity * time + phase constant)
        this.position.x = (float) ((amplitude*25) * Math.cos(angularFrequence * timeSinceSpawn + phaseConstant)); //25 pixels to 1 meter ratio
        this.position.x += playerOffset;
    }
    
    /**
     * Calculates the relative velocity (in meters/second) between this
     * projectile and a target Entity.
     * @param target Entity to calculate relative velocity with.
     * @param deltaTime Time since last game logic update.
     * @return Speed of this object relative to target
     */
    private float relativeVelocity(Entity target, float deltaTime){
        
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
    private float exertedForce(Entity target, float deltaTime){
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
    
    /**
     * Creates a copy of this projectile at the player's current position.
     * @return new HarmonicProjectile object similar to calling instance
     */
    @Override
    public Entity spawn(){

        return new HarmonicProjectile( //TODO: adapt this once this class is complete
            this.mass,
            this.amplitude,
            this.harmonicPeriod,
            this.maxHP,
            this.sprite.getTexture()
        );
        
    }
    
}
