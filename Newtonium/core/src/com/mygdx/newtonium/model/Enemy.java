/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.mygdx.newtonium.control.Global;
import com.mygdx.newtonium.control.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.newtonium.control.DamageDisplayer;

/**
 * Entities meant to harm the Player on contact and react to Projectile objects.
 * 
 * @author Nathan Latendresse (2249229)
 * @author Alexis Fecteau (2060238)
 * @author Thomas Cyr (2289144)
 * @author Ekrem Yoruk (1676683)
 * @since 03/04/2024
 */
public class Enemy extends Entity {
    
//attributes
    public double levelScaling; //boosts enemy stats depending on player level
    public int strength; //damage dealth by the enemy
    public int exp; //xp gained when the enemy is killed
    
//pushback attribute    
    protected double pAngle;
    protected double pBackForce;//to make pushback more gradual.
    protected Vector2 vecDif;
    protected Vector2 pushback;
    protected float pushdirection = 0;
    protected float pushlenght = 0;
    
//damage displayer module
    public DamageDisplayer paindisplayer;
    
//constructors
    public Enemy(double levelScaling, int maxHP, float speed, int strength, int exp, Vector2 position, Texture img) {
        super(maxHP, speed, position, img);
        this.levelScaling = levelScaling;
        //this.sprite.setColor(color); //for testing purposes
        this.strength = strength;
        this.exp = exp;
        this.pushback = new Vector2();
        this.vecDif = new Vector2();
        
        paindisplayer = new DamageDisplayer(10f);
    }
    
 //methods
    /**
     * Updates the enemy object for the current time and conditions.
     * Also handles object logic.
     * @param deltaTime Time since last call to render()
     */
    @Override
    protected void update (float deltaTime) throws DeadEntityException{
        
        Player p = Global.currentPlayer; //todo: Move currentplayer and enemylist to Global
        super.update(deltaTime);
        
        if (this.canGetHurt()) {
            this.damageDisplayable = true;
            this.sprite.setTexture(this.initTexture);
        }
        //Major Tweak to the way we calculate ENEMY navigation.
        
        float direction = (float) Math.atan2(p.position.y-position.y,p.position.x-position.x);
           
        //enemycolliderImplementation MiscTODO: add random temporary direction to make them swarm more.
        for (Enemy e:GameScreen.enemyList){//softcollider addition attempt
            if(collide(e)){
                pushdirection = (float) Math.atan2(position.y-e.position.y,position.x-e.position.x);
                direction = direction - pushdirection;
            }
            }
        //Current Enemynavigation method.
        position.x += (deltaTime*speed*Math.cos(direction));
        position.y += (deltaTime*speed*Math.sin(direction));    
        
    }
    
    /**
     * Creates a copy of an Enemy object that spawns at a random location.
     * @return new Enemy object similar to calling instance
     */
    @Override
    public Entity spawn(){
        
        Vector2 pos;
        float posX;
        float posY;
        //int spawningID;
              
    //randomized spawning point around the screen's border
        float camW = GameScreen.Camera.viewportWidth/2;
        float camH = GameScreen.Camera.viewportHeight/2;
        float camX = GameScreen.Camera.position.x;
        float camY = GameScreen.Camera.position.y;
        
        if (MathUtils.randomBoolean()){
            posX = MathUtils.random(camX-camW-sprite.getWidth(), camX+camW);
            posY = (MathUtils.randomBoolean()) ? camY-camH-sprite.getHeight() : camY+camH;
        } else {
            posX = (MathUtils.randomBoolean()) ? camX-camW-sprite.getWidth() : camX+camW;
            posY = MathUtils.random(camY-camH-sprite.getHeight(), camY+camH);
        }
        pos = new Vector2(posX, posY);
        
        return new Enemy(
                this.levelScaling,
                this.maxHP,
                this.speed,
                this.strength,
                this.exp,
                pos,
                this.sprite.getTexture()
        );
        
    }

}
    

