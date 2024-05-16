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
 * @author Alexis Fecteau (2060238)
 * @author Yoruk Ekrem
 */
public class FallingProjectile extends Projectile {//MRUA movement

    final float gravity = -9.81f;
    final float xspeed = MathUtils.random(-5,5); //in meters per second
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
        float camW = GameScreen.Camera.viewportWidth/2;
        float camH = GameScreen.Camera.viewportHeight/2;
        float camX = GameScreen.Camera.position.x;
        float camY = GameScreen.Camera.position.y;
        
        float posX = MathUtils.random(camX-camW, camX+camW);
        float posY = MathUtils.random(camY-camH, camY+camH);
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
