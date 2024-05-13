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
 * 
 * @author Nathan LaTendresse
 * @author Alexis Fecteau (2060238)
 * @author Ekrem Yoruk (1676683)
 * @since 03/05/2024
 */
public final class HomingProjectile extends Projectile{//Celui-ci est un MRU.
//Extra module in case of need.
    //module and Related attributes
    TargettingModule seekZone;
    float seekRadius; //range in meters 25px = 1m
    Vector2 path;
    
    //physics related values.
        float mass;//in kilograms
        float drag;
        //throwing randomness.disable if you want to.
        int minvelo = 17,maxvelo = 20;
        
        float initvelocity = MathUtils.random(minvelo,maxvelo);//in m/s
        //misc
        float sprorientation=MathUtils.random(0,360);//makes the apple spin like its thrown by a very intelligent man.
//constructors
    public HomingProjectile(float mass,float drag,float seekRadius, double flatDamage, double decayTime, int maxHP, float speed, Vector2 position, Texture img){
        super(flatDamage, decayTime, maxHP, speed, position, img);
        
        this.seekRadius = seekRadius * 25; //25 pixels to 1 meter ratio
        this.speed = (initvelocity * 25); //25 pixels to 1 meter conversion.
        this.drag = drag;
        this.seekZone = new TargettingModule(Global.currentPlayer.position,this.seekRadius);
        this.mass = mass;
        this.path = new Vector2();
        decidebearing();
        this.path.x = (1*(float)Math.cos(angle));
        this.path.y = (1*(float)Math.sin(angle));
           
    }
    
//methods
    /**
     * Updates the projectile object for the current time and conditions.
     * Also handles object logic.
     * @param deltaTime Time since last call to render()
     * @throws com.mygdx.newtonium.model.Entity.DeadEntityException
     */
    @Override
    protected void update(float deltaTime) throws DeadEntityException{
        super.update(deltaTime);
        //Scanner Movement
        this.seekZone.scanCenterPos = Global.currentPlayer.position;
        //Projectile movement
        this.position.x += this.speed*path.x*deltaTime;
        this.position.y += this.speed*path.y*deltaTime;
        
        this.sprite.setRotation(this.sprorientation++);
        this.speed -= (drag*deltaTime);

    }

    protected double decidebearing(){//Targetting module implementation.
            if(!this.seekZone.targetList.isEmpty()){this.seekZone.refresh();}
            this.seekZone.scanEnemy(50);
            this.seekZone.SortToNearest(Global.currentPlayer);
            if(!seekZone.targetList.isEmpty())
            {angle = Math.atan2((seekZone.getNearest().position.y-Global.currentPlayer.position.y),
                                (seekZone.getNearest().position.x-Global.currentPlayer.position.x));
            return angle;
            }else angle = MathUtils.random(0, 360); return angle;//if nobody's in sight, Random yeets occur, if ya wanna disable that, Do it this wednesday.-EY
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
     * @return new TestProjectile object similar to calling instance
     */
    @Override
    public Entity spawn(){
        Vector2 pos = new Vector2(Global.currentPlayer.position);
        
        return new HomingProjectile(
            this.mass,
            this.drag,
            this.seekRadius,
            this.flatDamage,
            this.decayTime,
            this.maxHP,
            this.speed,
            pos,
            this.sprite.getTexture()
        );
        
    }
    
}
