/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.newtonium.control.GameScreen;
import static com.mygdx.newtonium.control.GameScreen.enemyList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 *
 * @author Yoruk Ekrem (1676683)
 */

public class TargettingModule {//This is for anything that uses proximity based targetting.Nearest of ANYTHING goes first.
    
    public Circle LockonRange;
    public Vector2 ScanCenterPos;
    public LinkedList<Enemy> TargetList;
    
    public TargettingModule(Vector2 scannerposition,float ScanRange){
        this.ScanCenterPos = scannerposition;
        
        this.LockonRange =new Circle(scannerposition,ScanRange);
        
        
        TargetList = new LinkedList<>();
        
        
        
    }
        public void Refresh(){
            TargetList.removeAll(GameScreen.enemyList);
        }
        public void RemoveDead(){//To put near Dead disposal Area
            TargetList.removeAll(GameScreen.despawnList);
        }
        
        public void ScanEnemy(int Maxamount){ //to add Enemies to the entityList.How many of the nearest foes do you want to aim to.
            RemoveDead();
                for (Enemy e : GameScreen.enemyList) {
                if(LockonRange.overlaps(e.hitbox)&& TargetList.size() <= Maxamount){
                    TargetList.add(e);
                    
                }
            }
        }
        public float RangeFinder(Entity Start,Entity Target){
            //this is to find a distance between 2 Entities.Start is from center of
            if(!TargetList.isEmpty()){
            float Distance = ((float) Math.hypot(Target.hitbox.x - Target.hitboxRadius, Target.hitbox.y - Target.hitboxRadius))-(((float) Math.hypot(Start.position.x - Start.hitboxRadius, Start.position.y - Start.hitboxRadius)));
            return Distance;
            }return 0;
        }
        
        public void SortToNearest(Entity e){
            Collections.sort(TargetList,new Comparator<Enemy>(){
               @Override
                public int compare(Enemy Current, Enemy Next) {
                float distance1 = ((float) Math.hypot(Current.hitbox.x - Current.hitboxRadius, Current.hitbox.y - Current.hitboxRadius))-(((float) Math.hypot(e.position.x - e.hitboxRadius, e.position.y - e.hitboxRadius)));
                float distance2 = ((float) Math.hypot(Next.hitbox.x - Next.hitboxRadius, Next.hitbox.y - Next.hitboxRadius))-(((float) Math.hypot(e.position.x - e.hitboxRadius, e.position.y - e.hitboxRadius)));
                return Math.abs(distance1) < Math.abs(distance2) ? -1 : (distance1 == distance2 ? 0 : 1); 
                }
            });
        }
        public Enemy getNearest(){
            if(!TargetList.isEmpty()){
            return  TargetList.getFirst();
            }else return null;
            
        }
}
