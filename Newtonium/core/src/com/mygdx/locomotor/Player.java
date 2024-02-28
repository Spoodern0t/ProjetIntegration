/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author 2249229
 */
public class Player extends Entity {
    
    int level, exp, levelTreshold;
    
    
    public Player(Texture img, Color color,int maxHP) {
        super(img, color,maxHP);
    }
    
    public void levelUP(){
        
    }
    
    
    @Override
    public boolean die(){
        return false;
    }
    
    
    
    
}
