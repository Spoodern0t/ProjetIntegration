/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Eventual attribute of Player class. Controls projectile spawning logic.
 * @author Alexis Fecteau (2060238)
 * @Since 21/02/2024
 */
public class Item { //Will become a superclass for diversified items. ~AF

//attributes
    int level = 1;
    protected float cooldown; //in seconds
    protected float lastTriggerTime = 0f;
    Projectile projectile;
    
//constructors
    public Item(){ //unfinished
        this.level = 1;
        this.cooldown = 0.25f;
        this.projectile = new Projectile(0); //temporary
    }
    
//methods
    
    public void update(float deltaTime){
        
        this.lastTriggerTime += deltaTime;
        if (this.canTrigger()){
            this.trigger();
        }
    }
    
    /**
     * Checks whether this item meets the conditions to trigger. Used in update().
     * @author Alexis Fecteau
     * @since 25/03/2024
     */
    private boolean canTrigger(){
        
        //input check is temporary. The final items fire automatically. ~AF
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){ 
            
            if (this.lastTriggerTime >= this.cooldown){
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Activates this item's effect and handles its logic.
     * @author Alexis Fecteau
     * @since 25/03/2024
     */
    private void trigger(){
        
    //reset trigger cooldown
        this.lastTriggerTime = 0;
        
    //fire projectiles
        GameScreen.projectileList.add((Projectile)this.projectile.spawn());
        
        /*
        Projectiles should be spawned here, not in GameScreen.java
        
        The idea is to calculate projectile spawn parameters (angle, speed, etc)
        in this method. This can be overridden for every functionally unique
        projectile that requires new spawning arguments.
        
        This method's body will most likely include a switch-case block for
        the item's level if we implement a level cap. Otherwise attributes
        should upgraded via another method and then used here. 
        
        ~AF
        */
        
    }
    
       
}
