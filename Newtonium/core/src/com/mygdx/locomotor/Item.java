/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

/**
 *
 * @author Alexis Fecteau (2060238)
 */
public class Item { //pourrait être superclasse abstraite pour armes spécifiques. ~AF

//attributs 
    int level = 1;
    double cooldown; //en secondes
    //Projectile projectile;
    
//constructeurs
    public Item(){
        this.level = 1;
        this.cooldown = 1;
        //this.projectile = some default projectile
    }
    
//methodes
    
    //activation de l'item
    public void trigger(){
        /*
        this.projectile.spawn()
        
        l'idee c'est de spawner le projectile avec des coordonnees et
        possiblement des variables comme angle ou vitesse.
        
        cela depend du fait que Projectile herite de Entity. il faudra reparer
        la superclasse Entity.
        
        ~AF
        */
        
    }
    
    
}
