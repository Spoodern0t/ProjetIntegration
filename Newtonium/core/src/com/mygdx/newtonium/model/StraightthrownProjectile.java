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
public final class StraightthrownProjectile extends Projectile{
//Extra module in case of need.
    //module and Related attributes
    TargettingModule aimer;
    float seekradius;

    Vector2 path;
//constructors
    public StraightthrownProjectile(double flatDamage, double decayTime, int maxHP, float speed, Vector2 position, Texture img){
        
        super(flatDamage, decayTime, maxHP, speed, position, img);
        this.seekradius = 175;//aimer's logical circle's range.
        this.aimer = new TargettingModule(Global.currentPlayer.position,seekradius);
        
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
        this.aimer.ScanCenterPos = Global.currentPlayer.position;
        //Projectile movement
        this.position.x += this.speed*path.x*deltaTime;
        this.position.y += this.speed*path.y*deltaTime;
        
        

    }

    protected double decidebearing(){//Targetting module implementation.
            if(!this.aimer.TargetList.isEmpty()){this.aimer.Refresh();}
            this.aimer.ScanEnemy(50);
            this.aimer.SortToNearest(Global.currentPlayer);
            if(!aimer.TargetList.isEmpty())
            {angle = Math.atan2((aimer.getNearest().position.y-Global.currentPlayer.position.y),
                                (aimer.getNearest().position.x-Global.currentPlayer.position.x));
            return angle;
            }else angle = MathUtils.random(0, 360); return angle;//if nobody's in sight, Random yeets occur, if ya wanna disable that, Do it this wednesday.-EY
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
