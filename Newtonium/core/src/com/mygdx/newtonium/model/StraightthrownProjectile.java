/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

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
public class StraightthrownProjectile extends Projectile{
//Extra module in case of need.
    //module and Related attributes
    TargettingModule aimer;
    float seekradius;
//constructors
    public StraightthrownProjectile(double flatDamage, double decayTime, int maxHP, float speed, Vector2 position, Texture img){
        
        super(flatDamage, decayTime, maxHP, speed, position, img);
        
        this.seekradius = 175;//aimer's logical circle's range.
        this.aimer = new TargettingModule(Global.currentPlayer.position,seekradius);
        
            
           
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
        this.position.add(this.speed *(float)Math.cos(this.angle) * deltaTime, this.speed * (float)Math.sin(this.angle) * deltaTime);
        
        this.aimer.CenterPos = Global.currentPlayer.position;
        this.aimer.ScanEnemy(50);
        this.aimer.SortToNearest(Global.currentPlayer);
        if(!aimer.TargetList.isEmpty()){
            this.angle=Math.atan((aimer.getNearest().position.y-Global.currentPlayer.position.y)/(aimer.getNearest().position.x-Global.currentPlayer.position.x));
            }
    }
    
    
    /**
     * Creates a copy of this projectile at the player's current position.
     * @return new TestProjectile object similar to calling instance
     */
    @Override
    public Entity spawn(){
        
        Vector2 pos = new Vector2(Global.currentPlayer.position);
        return new StraightthrownProjectile(
            this.flatDamage,
            this.decayTime,
            this.maxHP,
            this.speed,
            pos,
            this.sprite.getTexture()
        );
        
    }
    
}
