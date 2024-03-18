/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

/**
 * Eventual attribute of Player class. Controls projectile spawning logic.
 * @author Alexis Fecteau (2060238)
 * @Since 21/02/2024
 */
public class Item { //Could be an abstract superclass for specified items. ~AF

//attributes
    int level = 1;
    double cooldown; //seconds
    //Projectile projectile;
    
//constructors
    public Item(){ //unfinished
        this.level = 1;
        this.cooldown = 1;
        //this.projectile = 
    }
    
//methods
    
    //item activation
    public void trigger(){
        /*
        this.projectile.spawn()
        
        the idea is to handle projectile spawns in this method, based on
        parameters like angle, speed, etc
        
        this method will be on a timer and most likely have a switch-case
        based on item level. ~AF
        */
        
    }
       
}
