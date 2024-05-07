/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.badlogic.gdx.Gdx;
import com.mygdx.newtonium.control.Global;

/**
 * Item type that includes Aiming functionalities.If aiming is put here, Projectiles will aim first then go second, Meaning they won't home like a heat seeking missile
 * 
 * 
 * @author Yoruk Ekrem
 */
public class AutoNaimItem extends Item {
    //aiming related things
    TargettingModule aimer;
    float seekradius;
    double aimangle;
    
    public AutoNaimItem(int level, float cooldown, Projectile projectile) {
        super(level, cooldown, projectile);
        
        this.seekradius = 250;//aimer's logical circle's range.
        this.aimer = new TargettingModule(Global.currentPlayer.position,seekradius);
        //Migrate this to an Item(Gun) 
    }
    @Override
        protected void update(float deltaTime){
        this.aimer.ScanCenterPos = Global.currentPlayer.position;
        this.aimer.ScanEnemy(50);
        this.lastTriggerTime += deltaTime;
        if (canTrigger()){
            this.trigger();
        }
        this.aimer.Refresh();
        //this is to remove everything in the scanner so it stops shooting.
    }

        
    @Override
    protected boolean canTrigger(){return (this.lastTriggerTime >= this.cooldown);}
    
}
