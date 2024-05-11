/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.mygdx.newtonium.control.Global;

/**
 * Item type that includes Aiming functionalities.If aiming is put here, Projectiles will aim first then go second, Meaning they won't home like a heat seeking missile
 * 
 * 
 * @author Yoruk Ekrem
 */
public class ProximityItem extends Item {
    //aiming related things
    TargettingModule seekZone;
    float seekRadius = 15 * 25;//25 pixels to 1 meter radius
    
    public ProximityItem(int level, float cooldown, Projectile projectile) {
        super(level, cooldown, projectile);
        
        if (projectile instanceof HomingProjectile){
            this.seekRadius = ((HomingProjectile)projectile).seekRadius;
        }
        
        this.seekZone = new TargettingModule(Global.currentPlayer.position,this.seekRadius);
        //Migrate this to an Item(Gun) 
    }
    @Override
        protected void update(float deltaTime){
        this.seekZone.refresh();
        this.seekZone.scanCenterPos = Global.currentPlayer.position;
        this.seekZone.scanEnemy(50);
        this.lastTriggerTime += deltaTime; 
            if (canTrigger() && seekZone.pollPresence()){
                this.trigger();
            }
        
        //this is to remove everything in the scanner so it stops shooting.
    }

        
    @Override
    protected boolean canTrigger(){
        return (this.lastTriggerTime >= this.cooldown);
    }
    
}
