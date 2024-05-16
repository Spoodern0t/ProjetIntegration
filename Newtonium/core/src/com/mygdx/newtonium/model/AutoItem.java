/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

/**
 * Item type that fires projectiles at a constant rate.
 * 
 * @author Alexis Fecteau
 * @since 03/05/2024
 */
public class AutoItem extends Item {
    
//constructors
    public AutoItem(int level, float cooldown, Projectile projectile){
        super(level, cooldown, projectile);
    }

//methods
    @Override
    protected boolean canTrigger() {
        return (this.lastTriggerTime >= this.cooldown);
    }
    
}
