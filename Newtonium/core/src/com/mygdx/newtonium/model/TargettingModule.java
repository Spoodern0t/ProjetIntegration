/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.newtonium.control.GameScreen;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Component used by ProximityItem and HomingProjectile. Handles enemy position
 * scans within a circular range.
 * 
 * @author Yoruk Ekrem (1676683)
 */

public class TargettingModule {//This is for anything that uses proximity based targetting.Nearest of ANYTHING goes first.
    
    public Circle lockonRange;
    public Vector2 scanCenterPos;
    public LinkedList<Enemy> targetList;
    
    public TargettingModule(Vector2 scannerPosition,float scanRange){
        this.scanCenterPos = scannerPosition;
        
        this.lockonRange =new Circle(scannerPosition,scanRange);
        
        
        targetList = new LinkedList<>();
        
        
        
    }

    /**Empties the TargetList to prevent accumulation in list.
     *
     */
    public void refresh(){
            if(!targetList.isEmpty()){targetList.removeAll(GameScreen.enemyList);}
            
        }

    /**Removes every Entity that is within the despawnList 
     * to prevent crashes and accumulation of non-interactable entities
     *
     */
    public void removeDead(){//To put near Dead disposal Area
            if(!targetList.isEmpty()){targetList.removeAll(GameScreen.despawnList);}
            
        }
        
    /**Adds to targetList every Every Instances of Enemy class Entities's hitboxxes that is overlapping the lockonRange circle.
     *
     * @param maxAmount
     */
    public void scanEnemy(int maxAmount){ //to add Enemies to the entityList.How many of the nearest foes do you want to aim to.
                removeDead();
                for (Enemy e : GameScreen.enemyList) {
                if(lockonRange.overlaps(e.hitbox)&& targetList.size() <= maxAmount){
                    targetList.add(e);
                }
            }
        }
    
    // Is to take the distance between Entity start and Target
    public float RangeFinder(Entity start, Entity target){
            //this is to find a distance between 2 Entities.Start is from center of
            if(!targetList.isEmpty()){
            float distance = ((float) Math.hypot(target.hitbox.x - target.hitboxRadius, target.hitbox.y - target.hitboxRadius))-(((float) Math.hypot(start.position.x - start.hitboxRadius, start.position.y - start.hitboxRadius)));
            return distance;
            }return 0;
    }
        
    /**Sorts the targetList where the index of of the nearest enemy is the nearest
     *to the chosen Entity.
     * @param e
     */
    public void SortToNearest(Entity e){
            Collections.sort(targetList, new Comparator<Enemy>(){
               @Override
                public int compare(Enemy current, Enemy next) {
                float distance1 = ((float) Math.hypot(current.hitbox.x - current.hitboxRadius, current.hitbox.y - current.hitboxRadius))-(((float) Math.hypot(e.position.x - e.hitboxRadius, e.position.y - e.hitboxRadius)));
                float distance2 = ((float) Math.hypot(next.hitbox.x - next.hitboxRadius, next.hitbox.y - next.hitboxRadius))-(((float) Math.hypot(e.position.x - e.hitboxRadius, e.position.y - e.hitboxRadius)));
                return Math.abs(distance1) < Math.abs(distance2) ? -1 : (distance1 == distance2 ? 0 : 1); 
                }
            });
        }
    
    /**
     *  
     * @return return the nearest Entity in the targetList that happens to be the first.
     */
    public Enemy getNearest(){
            if(!targetList.isEmpty()){
            return  targetList.getFirst();
            }else return null;
            
        }
    //Optionnal Boolean to see If there is anything present within the viscinity of the LockonRange. Can be used to toggle proximity based methods
        public Boolean pollPresence(){ //this is to access the GameScreen without importing it as this class already does it.
        for (Enemy e : GameScreen.enemyList) {
            if(lockonRange.overlaps(e.hitbox)){return true;}
        }return false;
        }
}
