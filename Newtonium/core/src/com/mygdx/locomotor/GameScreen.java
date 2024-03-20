/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Yoruk Ekrem
 */

public class GameScreen implements Screen {

//screen
    public Camera Camera;
    
    
//Graphics 
    SpriteBatch batch;
    Texture idleTexture;
    Texture oofTexture;
    Texture evilTexture;
    
    
//background assets
    Texture mapTexture;
    public Sprite mapSprite;
    
    
//world parameters (things about how the measurements are treated)ask Ekrem for source
    final int WORLD_WIDTH = 1000;
    final int WORLD_HEIGHT = 1000; //could the level be the image's dimensions? ~AF

    
//timers (cooldowns and delays for game objects that occur in screen)
    public long lastSpawnTime = 0l;
    int wave;
    
    
//game objects
    public static Player player;
    Projectile projectile;
    
    //maybe combine the next two into an Entity list? ~AF
    public LinkedList<Projectile> projectileList; 
    public LinkedList<Enemy> enemyList;
    
    public LinkedList<Entity> despawnList;//clears dead entities after for loops ~AF
    
    
//equivalent to the create() method for this class.
    public GameScreen(final GameController game){
        
        Camera = new OrthographicCamera(800,400);
        
        
    //texture setup (all placeholder)
        idleTexture = new Texture("LilBoy.png");
        oofTexture = new Texture("sadge.png");
        evilTexture = new Texture("Evil.png");
        
        
    //background setup 
        mapTexture = new Texture("MapImg.jpg");
        mapSprite = new Sprite(mapTexture);
        mapSprite.setPosition(0,0);
        mapSprite.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        
        
    //gameobject setup and playercamera.
        player = new Player(); 
        projectile = new Projectile(0);//generic test projectile ~AF
        Camera.position.set(player.hitbox.x,player.hitbox.y,20);
        Camera.update();  
        
        
        enemyList = new LinkedList<>();
        projectileList = new LinkedList<>();
        despawnList = new LinkedList<>();
        batch = new SpriteBatch(); //images currently on-screen
    
       
    }
    //TODO: WE NEED TO REMAKE EVERY METHODS IN EVERY CLASS IN A WAY THAT ONLY METHODS THAT INVOLVE WHAT WE SEE HAPPEN IN A GAME HAPPEN HERE.
    //exceptions are: class descriptors and cooldown related things. I put alot of comments for earier organisation.

    
//Real-time game logic (called for each new frame)
    @Override
    public void render(float deltaTime){
        
    //old camera stuff
        Camera.position.set(player.sprite.getX(),player.sprite.getY(),0);
        Camera.update();
        batch.setProjectionMatrix((Camera.combined));
        
        batch.begin();

        
    //map and background
        ScreenUtils.clear(0,0,0,1);
        mapSprite.draw(batch);
        
        
    //player
        player.draw(batch);

        
    //projectile logic
        shootBullet(projectile); //TEMP: fire bullet if spacebar pressed ~AF
        
        for (Projectile p: projectileList){
            p.draw(batch);
            if (p.isDead){
                despawnList.add(p);
            }
        }
        
        projectileList.removeAll(despawnList);
        despawnList.clear();
        
        /* Flush all dead projectiles at once: Java raises an exception if a
           collection is modified while a thread uses it (like in for-loops).
        
           TODO all Entities should be handled in the same list, loop and
           flush regardless of their type. ~AF */
        
        
    //enemylist stuff (including collision methods.)
        ListIterator<Enemy> iter = enemyList.listIterator();
        
        for (Enemy e: enemyList) {
            
        //check for projectile collision with enemy
            for (Projectile p: projectileList){
                if (p.collide(e)){
                    e.sprite.setTexture(oofTexture);
                    e.currentHP = e.currentHP - (int)p.flatDamage;
                    p.currentHP = p.currentHP-1;
                }
                if(e.currentHP <=0){
                    e.die();
                }
                if(p.currentHP <= 0){
                    p.die();
                }
            }
            
        //set enemy's sprite to normal if they got hurt too long ago
            if (e.canGetHurt()) {
                e.sprite.setTexture(evilTexture);
                e.lastHurtTime = 0;
            }
            
            //use iterator to find closest enemy
            e.draw(batch);
                    
            if (player.collide(e)){
                player.sprite.setTexture(oofTexture);
            }
            
            if(e.isDead){
                despawnList.add(e);
                /*Ededsound.play();*/
            }
        }
        enemyList.removeAll(despawnList);
        despawnList.clear();
        
        if(TimeUtils.nanoTime() - this.lastSpawnTime > 3000000000L) {
            spawnEnemy();
        }
        if (player.canGetHurt()) {
            player.sprite.setTexture(idleTexture);
            player.lastHurtTime = 0;
        }
               
        batch.end();
    }
    
    //only temporary, essentially does what (item).trigger() does. ~AF
    private void shootBullet(Projectile projectile){ 
        
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            projectileList.add((Projectile)projectile.spawn());
        }
    }
    
    //Enemy spawn
    
    private void spawnEnemy(){
        Enemy mal = new Enemy(1,100);
        enemyList.add((Enemy)mal.spawn());
        this.lastSpawnTime = TimeUtils.nanoTime();
        
    }       
    
    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }
    @Override
    public void show() {
        
    }
    @Override
    public void dispose() {
                idleTexture.dispose();
                oofTexture.dispose();
                mapSprite.getTexture().dispose();
                evilTexture.dispose();
                batch.dispose();
    }
    
    
}
