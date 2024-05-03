/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 *
 * @author Alexis Fecteau
 * @since 03/05/2024
 */
public class TestItem extends Item {
    
    private final int triggerKey; //field value imported from com.badlogic.gdx.Input.Keys
    
//constructors
    public TestItem(int triggerKey, int level, float cooldown, Projectile projectile){
        super(level, cooldown, projectile);
        this.triggerKey = triggerKey;
    }
    
//methods
    /**
     * Checks whether this item meets the conditions to trigger. Used in Item.update().
     * @return true if the item can fire at time of update.
     */
    @Override
    protected boolean canTrigger(){
        
        if(Gdx.input.isKeyPressed(triggerKey))            
            return super.canTrigger();
        
        return false;
    }
}
