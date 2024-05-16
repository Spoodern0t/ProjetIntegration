/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.newtonium.control.GameScreen;

/**
 * Projectile type that recreates Uniformly Accelerated Rectilinear Motion in a
 * downwards trajectory. Also contains a small horizontal URM component.
 * 
 * @author Alexis Fecteau (2060238)
 * @author Yoruk Ekrem
 * 
 * @since 15/05/2024
 */
public class FallingProjectile extends Projectile {

    final float gravity = -9.81f;
    final float xspeed = MathUtils.random(-3,3); //in meters per second
    float timeSinceSpawn = 0;
    float initialHeight; //in meters
    float currentHeight; //in meters
    
    float landingPoint; //Y-axis landing coordinate since this "falls" on a separate axis
    
    private final Sprite cosmeticShadow = new Sprite(new Texture("plh_dropshadow.png"));
    private final float cosmeticSpinAngle;
    
    //float range;
    //float spawnXdeviation;
    
    public FallingProjectile(float mass, float initialHeight, Texture img) {
        super(1,100, 10,0, new Vector2(0, 0), img);
        this.mass = mass;
        this.initialHeight = initialHeight;
        this.currentHeight = initialHeight;
        
    //randomized landing point within the screen's border
        float camX = GameScreen.Camera.position.x;
        float camY = GameScreen.Camera.position.y;
        float spawnRadius = MathUtils.random(0, 3) * 25;
        float spawnAngle = MathUtils.random(0, (float)Math.PI*2);
        
        float posX = camX + spawnRadius * MathUtils.cos(spawnAngle);
        float posY = camY + spawnRadius * MathUtils.sin(spawnAngle);
        this.landingPoint = posY;
        this.position = new Vector2(posX, posY + (this.currentHeight * 25)); //25 pixels to 1 meter ratio
        
    //cosmetic drop-shadow
        this.cosmeticShadow.setCenterY(this.landingPoint);
        this.cosmeticShadow.setCenterX(this.position.x);
        
    //cosmetic projectile rotation
        this.sprite.rotate(MathUtils.random(360f));
        cosmeticSpinAngle = MathUtils.random(-10, 10);
        
    }
    
    
    @Override
    protected void update(float deltaTime) throws DeadEntityException{
        super.update(deltaTime);

        if(this.position.y <= this.landingPoint){ //extra kill condition
            this.die();
        }
        
        this.timeSinceSpawn += deltaTime;
        this.sprite.rotate(this.cosmeticSpinAngle);
        
    //current height = initial height + (initial vertical speed * time) + (vertical acceleration * time^2)/2
        this.currentHeight = this.initialHeight + (this.gravity * timeSinceSpawn * timeSinceSpawn)/2; //initial vertical speed is 0
        this.position.y = this.landingPoint+ this.currentHeight*25;
        
    //constant horizontal speed
        this.position.x += (this.xspeed*25) * deltaTime;
        this.cosmeticShadow.setCenterX(this.position.x);
        
    //kill condition
        if(this.position.y <= this.landingPoint){
            this.die();
        }
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
            this.cosmeticShadow.draw(batch);
            this.sprite.draw(batch);
        }
    }
    
    /**
     * Calculates the relative velocity (in meters/second) between this
     * projectile and a target Entity.
     * @param target Entity to calculate relative velocity with.
     * @param deltaTime Time since last game logic update.
     * @return Speed of this object relative to target
     */
    @Override
    protected float relativeVelocity(Entity target, float deltaTime){
        
    //get this and target's pixel displacements vectors since last render
        Vector2 deltaX = new Vector2(this.position.x - this.lastPosition.x, this.lastPosition.y - this.lastPosition.y);
        Vector2 targetDeltaX =  new Vector2(target.position.x - target.lastPosition.x, 0); //enemies are moving on a "separate" y axis ~AF
        
    //get a relative pixel displacement length between this and target
        float relativeDeltaX = deltaX.dst(targetDeltaX);
        
    //convert length from pixels to meters
        relativeDeltaX /= 25; // 1 meter = 25 pixels
        
    //calculate relative velocity magnitude using deltaTime 
        float RVMagnitude = relativeDeltaX / deltaTime;
        return Math.abs(RVMagnitude);
    }
    
    /**
     * Checks for collision with target Entity and calculates contact damage.
     * @param target Entity to check for collision and damage with. 
     * @return true if there's a collision, false otherwise.
     */
    @Override
    public boolean collide(Entity target){
        
    //render the projectile's image on top of the enemies (purely visual)
        this.sprite.draw(GameScreen.game.batch);
        
    //check for projectile being ground-level before doing damage
        boolean isReachable = (this.position.y <= this.landingPoint + target.sprite.getHeight());
        return (super.collide(target) && isReachable);
    }
    
    @Override
    public Entity spawn() {
        return new FallingProjectile(
            this.mass,
            this.initialHeight,
            this.sprite.getTexture()
        );
    }
 
}
