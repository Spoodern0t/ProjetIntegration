/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium;

import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Yoruk Ekrem
 */
public class Player extends Entity{
    
    public Player(float CenterX, float CenterY,
            float width, float height,
            float MoveSpeed,
            Texture Mchar, Texture oofMchar) {
            super(CenterX, CenterY, width, height, MoveSpeed, Mchar, oofMchar);
            
    }
    
}
