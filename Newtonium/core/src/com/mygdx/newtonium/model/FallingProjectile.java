/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.newtonium.control.Global;

/**
 *
 * @author Yoruk Ekrem
 */
public class FallingProjectile extends Projectile {//MRUA gravitationnelle Assisted

    Vector2 gravity ;
    Vector2 speedV ; 
    float xspeed = MathUtils.random(-25f*5,25f*5);//random constant speed
    float time;
    float initialheight;
    float bumpbuffer = 3*25;//impact buffer so that it hits when it should be close to ground.
    
    Vector2 initspawnground;//we select random point here, then add the height to the y co ordinates.
    
    float range;
    
    public FallingProjectile(float range,float mass,float initialHeight,Vector2 position, Texture img) {
        super(1,5, 10,0, position, img);
        this.mass = mass;
        //Meteor landing point chooser implementation
        this.range = range;
        this.range = this.range*25;//conversion from pixel to meter.
        this.initialheight = initialheight*25;//conversion from pixel to meter.
        this.initspawnground = new Vector2();
        //setting up MRUA related factors        
        this.time = 0;
        gravity = new Vector2(0,(-9.81f*25));
        speedV = new Vector2(xspeed,0);
        
        //setting up initial spawnpoint
        float distancechoice = MathUtils.random(this.range);
        this.position.setToRandomDirection();
        this.position.setLength(distancechoice);
        this.position.y += Gdx.graphics.getHeight()+initialheight;
           
    }
    
    
    @Override
    protected void update(float deltaTime) throws DeadEntityException{
        super.update(deltaTime);
        
        this.time += 2*deltaTime;
        this.speedV.set(speedV.x,gravity.y*time);
        this.position.mulAdd(speedV,deltaTime);
        this.speed = this.position.dst(this.lastPosition);
        //System.out.println(speed);
        
        if(((Gdx.graphics.getHeight()+this.initialheight)-position.y) <= 0){
        this.die();
        }
    }
    
        /**
     * Checks for collision with target Entity and calculates contact damage.
     * @param target Entity to check for collision and damage with. 
     * @return true if there's a collision, false otherwise.
     */
    @Override
    public boolean collide(Entity target){
        
        if (this.hitbox.overlaps(target.hitbox))
            this.flatDamage = exertedForce(target, Gdx.graphics.getDeltaTime());
        return this.hitbox.overlaps(target.hitbox);
    }

    @Override
    public Entity spawn() {
        Vector2 Ipos = new Vector2();
        Ipos.x = MathUtils.random(range);
        Ipos.y += Gdx.graphics.getHeight()+initialheight;//Hauteur de l'ecran + hauteur initiale.
        
        return new FallingProjectile(
        this.range,
        this.mass,
        this.initialheight,
        Ipos,
        this.sprite.getTexture());
    }
 
}