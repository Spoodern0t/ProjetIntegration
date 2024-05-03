/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.mygdx.newtonium.control.GameScreen;

/**
 * Attribute of Player class. Controls projectile spawning logic.
 * 
 * @author Alexis Fecteau (2060238)
 * @Since 03/05/2024
 */
public abstract class Item {

//attributes
    int level;
    protected float cooldown; //in seconds
    protected float lastTriggerTime;
    protected Projectile projectile;
    
//constructors
    public Item(int level, float cooldown, Projectile projectile){
        this.level = Math.max(1, level);
        this.cooldown = cooldown;
        this.lastTriggerTime = cooldown;
        this.projectile = projectile;
    }
    
//methods
    /**
     * Updates this object's cool-downs and triggers it if it can.
     * @param deltaTime time since last call to render() in GameScreen
     */
    protected void update(float deltaTime){
        
        this.lastTriggerTime += deltaTime;
        if (this.canTrigger()){
            this.trigger();
        }
    }
    
    /**
     * Checks whether this item meets the conditions to trigger. Used in update().
     * @return true if the item can fire at time of update.
     */
    protected boolean canTrigger(){
        return (this.lastTriggerTime >= this.cooldown);
    }
    
    /**
     * Activates this item's effect and handles its logic.
     */
    protected void trigger(){
        
    //reset trigger cooldown
        this.lastTriggerTime = 0;
        
    //fire projectiles
        GameScreen.projectileList.add((Projectile)this.projectile.spawn());
        
        /*
        Projectiles should be spawned here, not in GameScreen.java
        
        The idea is to calculate what to do with the projectiles in this method.
        This can be overridden for every Item subclass that has a functionally
        unique way of firing projectiles.
        
        This method's body will most likely include a switch-case block for
        the item's level if we implement a level cap. Otherwise the level
        attribute can be used as a variable in spawning logic written here.
        
        ~AF
        */
        
    }
    
       
}
