/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.newtonium.control.Global;

/**
 *
 * @author Alexis Fecteau
 * @since 08/05/2024
 */
public class HarmonicProjectile extends Projectile {
    
//attributes
    private Sprite springCosmetic = new Sprite(new Texture("plh_harmonicspring2.png"));
    private final int springHeight = springCosmetic.getTexture().getHeight();
    
    final double phaseConstant = -(Math.PI / 2); //in radians
    
    double angularFrequence; //in radians
    float amplitude; //in meters
    float harmonicPeriod; //in seconds
    float timeSinceSpawn = 0; //in seconds
    
    
//constructors
    public HarmonicProjectile(float mass, float amplitude, float harmonicPeriod, int pierceAmount, Texture img) {
        super(1, 0, pierceAmount, 0, new Vector2(Global.currentPlayer.position), img);
        this.mass = mass;
        this.amplitude = amplitude;
        this.harmonicPeriod = harmonicPeriod;
        
        //w = 2*PI/T
        this.angularFrequence = (2 * Math.PI)/this.harmonicPeriod;
        
        this.decayTime = this.harmonicPeriod * 3;
        
        //initial offset to avoid crazy high damage on spawning frame
        this.position.x = Global.currentPlayer.position.x;
        this.position.y = Global.currentPlayer.position.y;
    }

//methods 
    /**
     * Updates the projectile object for the current time and conditions.
     * Also handles object logic.
     * @param deltaTime Time since last call to render()
     */
    @Override
    protected void update(float deltaTime) throws DeadEntityException{
        super.update(deltaTime);
        
    //calculate new position
        this.position.y = Global.currentPlayer.position.y;
        this.timeSinceSpawn += deltaTime;
        
        //current x position = amplitude * cos(angular velocity * time + phase constant)
        this.position.x = (float) ((amplitude*25) * Math.cos(angularFrequence * timeSinceSpawn + phaseConstant)); //25 pixels to 1 meter ratio
        this.position.x += Global.currentPlayer.position.x;
        
    //calculate cosmetic spring texture position and width
        float springWidth = this.position.x - Global.currentPlayer.position.x;
        springCosmetic.setSize(springWidth, springHeight);
        springCosmetic.setPosition(Global.currentPlayer.position.x, 0);
        springCosmetic.setCenterY(this.position.y);
        
    }
    
    /**
     * Calls this object's update() method and draws its associated graphics to
     * the screen.
     * @param batch drawn SpriteBatch to add this object's sprites to.
     * @param deltaTime Time since last render()
     */
    @Override
    public void draw(SpriteBatch batch, float deltaTime){
        try{
            this.update(deltaTime);
            this.sprite.setCenter(this.position.x,this.position.y);
            this.hitbox.setPosition(this.position);
        } catch (DeadEntityException e){
            
        }finally{
            this.springCosmetic.draw(batch);
            this.sprite.draw(batch);
        }
    }
    
    /**
     * Creates a copy of this projectile at the player's current position.
     * @return new HarmonicProjectile object similar to calling instance
     */
    @Override
    public Entity spawn(){

        return new HarmonicProjectile( //TODO: adapt this once this class is complete
            this.mass,
            this.amplitude,
            this.harmonicPeriod,
            this.maxHP,
            this.sprite.getTexture()
        );
        
    }
    
}
