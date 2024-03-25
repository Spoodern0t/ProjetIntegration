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
    static Texture idleTexture;
    static Texture oofTexture;
    static Texture evilTexture;
    
    
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
    Item item;
    Enemy enemy;
    
    public static LinkedList<Projectile> projectileList; 
    public static LinkedList<Enemy> enemyList;
    
    public static LinkedList<Entity> despawnList;//clears dead entities after for loops ~AF
    
    
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
        
        
    //gameobject setup (some temporary)
        player = new Player(); 
        
        item = new Item();//generic test item ~AF
        player.addItem(item);
        
        enemy = new Enemy(1,100);
        
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
        
    //move camera
        Camera.position.set(player.sprite.getX(),player.sprite.getY(),0);
        Camera.update();
        batch.setProjectionMatrix((Camera.combined));
        
        batch.begin();

        
    //render map background
        ScreenUtils.clear(0,0,0,1);
        mapSprite.draw(batch);
        
        
    //calculate player
        player.draw(batch); //as of now, this call triggers all the items. ~AF

        
    //calculate projectiles
        for (Projectile p: projectileList){
            p.draw(batch);
        }
             
    //calculate enemies      
        for (Enemy e: enemyList) {
            
        //check for projectile collision with enemy
            for (Projectile p: projectileList){
                if (p.collide(e)){
                    
                    //TODO: replace with hurtEnemy() method for Projectile.
                    if (e.canGetHurt()){
                        e.lastHurtTime = 0;
                        e.sprite.setTexture(oofTexture);
                        e.currentHP -= (int)p.flatDamage;
                        p.currentHP--;
                    }
                }
            }
            projectileList.removeAll(despawnList);
            
            e.draw(batch);
        
        //check for player collision with enemy
            if (player.collide(e)){
                if (player.canGetHurt()) {
                    player.lastHurtTime = 0;
                    player.sprite.setTexture(oofTexture);
                }
            }
            
        }
        /* 
        Flush all dead entities at once: Java raises an exception if a
        collection is modified while a thread uses it (like in for-loops).~AF
        */
        enemyList.removeAll(despawnList);
        despawnList.clear();
        
    //spawn enemies periodically
        if(TimeUtils.nanoTime() - lastSpawnTime > 3000000000L) {
            enemyList.add((Enemy)enemy.spawn());
            lastSpawnTime = TimeUtils.nanoTime();
        }
               
        batch.end();
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
    public void dispose() { //what does this do? ~AF
                idleTexture.dispose();
                oofTexture.dispose();
                mapSprite.getTexture().dispose();
                evilTexture.dispose();
                batch.dispose();
    }
    
    
}
