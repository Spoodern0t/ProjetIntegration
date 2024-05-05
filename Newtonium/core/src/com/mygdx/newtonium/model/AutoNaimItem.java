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
        
        this.seekradius = 175;//aimer's logical circle's range.
        this.aimer = new TargettingModule(Global.currentPlayer.position,seekradius);
        
        if(!aimer.TargetList.isEmpty()){
           this.aimangle = Math.atan2((fetchTarget().position.y - Global.currentPlayer.position.y),(fetchTarget().position.x - Global.currentPlayer.position.x));
            }//Migrate this to an Item(Gun) 
    }
    

    
    public Enemy fetchTarget(){//To put it in Item. Item = gun Projectile = bullet. If bullet aims then its like homing missile If item aims then gun aims and BOOLET goes straight.
            this.aimer.ScanCenterPos = Global.currentPlayer.position;
            this.aimer.ScanEnemy(50);
            this.aimer.SortToNearest(Global.currentPlayer);
            return aimer.getNearest();
    }
    
        
    @Override
    protected boolean canTrigger() {return (this.lastTriggerTime >= this.cooldown);}
    
}
