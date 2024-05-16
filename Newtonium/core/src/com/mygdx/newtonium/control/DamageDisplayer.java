/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.newtonium.model.Enemy;
import com.mygdx.newtonium.model.Projectile;
import java.util.LinkedList;
import java.util.Locale;

/**
 *
 * @author Yoruk Ekrem
 */
public class DamageDisplayer{
        
        float displayingtime = 0;
        float xoffset;
        float yspeed = 10f;
        float ydecel = 5f;
        DamageLabel dlabel;
        public LinkedList<DamageLabel> displayList;
        public LinkedList<DamageLabel> disposeList;
        public DamageDisplayer(float AllowedTime) {//item equivalent

            displayList = new LinkedList<>();
            disposeList = new LinkedList<>();
   
        }
        
        public void adddamageLabel(Projectile p){
              displayList.add(new DamageLabel("",Global.skin,p));
        } 
        
           
        
    public class DamageLabel extends Label{
            float AllowedTime = 1f;
            float displayingtime = 0;
            float xoffset;
            float yoffset;
            float yspeed = 1000f;
            float ydecel = 5f;
            
        public DamageLabel(CharSequence text, Skin skin,Projectile p) {
            super(text, skin);
            xoffset = MathUtils.random(-50, 50);
            yoffset = MathUtils.random(-50, 50);
            
            
            setText(String.format(Locale.getDefault(),"%.0f", p.flatDamage) + " Newtons!");
                    
        }
        
            public void updatepos(Vector2 initpos){
            float deltaTime = Gdx.graphics.getDeltaTime();
            
            displayingtime += deltaTime;
            if(displayingtime>=AllowedTime){
                disposeList.add(this);
                
                displayingtime = 0;
            }
            
            this.setPosition(initpos.x + xoffset,initpos.y + yoffset);
                        
        }
        
    }       
}
