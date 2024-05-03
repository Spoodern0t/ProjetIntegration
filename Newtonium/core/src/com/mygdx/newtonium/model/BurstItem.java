/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.mygdx.newtonium.control.GameScreen;

/**
 *
 * @author Alexis Fecteau
 * @since 03/05/2024
 */
public class BurstItem extends Item {
    
//attributes
    int amount;
    private static int triggerCounter = 0;
    
//constructors
    public BurstItem(int level, float cooldown, Projectile projectile, int amount){
        super(level, cooldown, projectile);
        this.amount = Math.max(0, amount);
    }

//methods
    @Override
    protected void update(float deltaTime){
        this.lastTriggerTime += deltaTime;
        if (this.canTrigger()){
            this.trigger();
            triggerCounter++;
        }
        
        if (this.lastTriggerTime >= this.cooldown){
            this.lastTriggerTime = 0;
            this.triggerCounter = 0;
        }
    }
    
    @Override
    protected boolean canTrigger() {
        float burstLength = 1f; //first half of cooldown
        
        if (triggerCounter < amount){
            return (this.lastTriggerTime >= ((triggerCounter)*(burstLength/this.amount)));
        } else return false;
    }
    
    @Override
    protected void trigger(){
        GameScreen.projectileList.add((Projectile)this.projectile.spawn());
        
    }
    
}
