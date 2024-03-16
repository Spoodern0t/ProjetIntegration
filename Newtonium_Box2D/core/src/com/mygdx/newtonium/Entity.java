/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Yoruk Ekrem
 */
abstract class Entity {
    //Characteristics
    public int maxHP,HP;
    double damageModifier;
    float movespeed;
    //position and size
    Rectangle HitBox;//Box style hitbox Will default to this if I have to
    Circle Contacter;//Will reattempt this first.
    
    //graphics
    Texture Mchar,oofMchar;
    
        public Entity(float CenterX,float CenterY,
                float width,float height,
                float MoveSpeed,
                Texture Mchar,Texture oofMchar){
            this.movespeed = MoveSpeed;
            this.HitBox = new Rectangle(CenterX-width/2,CenterY-height/2,width,height);
        }
        
    
        public void update(float deltaTime){//If ya got any cooldowns time will pass through that, so try using this for those.
            
        }
        
        public void move(float xNext,float yNext){//only thing here used to make movements. which is used to make stuff happen.
            HitBox.setPosition(HitBox.x+xNext,HitBox.y+yNext);
        }
               
    
        public void draw(Batch batch){
            batch.draw(Mchar,HitBox.x, HitBox.y,
                       HitBox.width,HitBox.height);
            //here is where you add your conditionnal sprites Like auras, shields n stuff.
        }
        
        
}
