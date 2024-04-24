/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.control;

import com.mygdx.newtonium.model.*;
import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys.E;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.LinkedList;
import java.util.Locale;

/**
 *
 * @author Ekrem Yoruk (1676683)
 * @author Alexis Fecteau (2060238)
 * 
 * @since 17/04/2024
 */

public class GameScreen implements Screen {

//screen
    static public Camera Camera;
    Viewport viewport;
    
//Graphics 
    public Hud hud;
    public GoverOverlay GoverLay;
    //I migrated spritebatch and anything related to it to GameController so MainMenuScreen can also use it.~EY
    
    
//background assets
    Texture mapTexture;
    static public Sprite mapSprite;
    GameController game;
        
//timers (cooldowns and delays for game objects that occur in screen)
    public long lastSpawnTime = 0l;
    int wave;
    
    
//game objects
    Player player = Global.currentPlayer;
    Enemy enemy = Global.Enemies.testEnemy;
    
    
    public static LinkedList<Projectile> projectileList; 
    public static LinkedList<Enemy> enemyList;
    
    public static LinkedList<Entity> despawnList;//clears dead entities after for loops ~AF
// Heads up Display    
    
    public int score = 0;
    float hudSectionWidth;//Attribut pour Des labels sur l'ennemie
    
//Popup and Pause related Attributes
    //Game Over Related Attributes
     public static boolean isOver = false;
    //Pause Related Attributes
     public static boolean isPaused = false;
    
//equivalent to the create() method for this class.
    public GameScreen(final GameController game){
        this.game = game;
        Camera = new OrthographicCamera(800,400);
        
    //background setup 
        mapTexture = new Texture("MapImg.jpg");
        mapSprite = new Sprite(mapTexture);
        mapSprite.setPosition(0,0);
        
    //world boundaries
        final int WORLD_WIDTH = mapSprite.getRegionWidth();
        final int WORLD_HEIGHT = mapSprite.getRegionHeight(); //Lorsque la map sera faite on va pouvoir setter les limite de la map en remplacant 1000 par variable
        mapSprite.setSize(1000,1000);
        
    //gameobject setup (some temporary)
        player.addItem(Global.Items.testItem);
        
        enemyList = new LinkedList<>();
        projectileList = new LinkedList<>();
        despawnList = new LinkedList<>();
        
        //Used the screen to fetch The hp and score and Likely other relevant Data.
        hud = new Hud(game.batch,this);
        GoverLay = new GoverOverlay(game.batch,this,this.game);
       
    }
    //Erased PrepHud Method and migrated related parameters to Hud class.
    //Real-time game logic (called for each new frame)
    @Override
    public void render(float deltaTime){
        if(Gdx.input.isKeyJustPressed(E)){
            isPaused = !isPaused;
        }
        if(isOver || isPaused){
            deltaTime = 0;
            game.batch.setProjectionMatrix(GoverLay.GoverStage.getCamera().combined);
            if(isOver){
               GoverLay.GoverStage.draw();
               GoverLay.GoverStage.act(Gdx.graphics.getDeltaTime());
            }
            if(isPaused){
               
            }
        }
    //move camera
        Camera.position.set(player.sprite.getX(),player.sprite.getY(),0);
        Camera.update();
        game.batch.setProjectionMatrix((Camera.combined));
        
        game.batch.begin();

        
    //render map background
        ScreenUtils.clear(0,0,0,1);
        mapSprite.draw(game.batch);
        
        
    //calculate player
        player.draw(game.batch,deltaTime); //as of now, this call triggers all the items. ~AF

        
    //calculate projectiles
        for (Projectile p: projectileList){
            p.draw(game.batch,deltaTime);
        }
        projectileList.removeAll(despawnList);
        
    //calculate enemies      
        for (Enemy e: enemyList) {
            
        //check for projectile collision with enemy
            for (Projectile p: projectileList){
                if (p.collide(e)){
                    
                    //TODO: replace with hurtEnemy() method for Projectile.
                    if (e.canGetHurt()){
                        e.lastHurtTime = 0;
                        e.sprite.setTexture(Global.evilPlaceholder);
                        e.currentHP -= (int)p.flatDamage*p.speed*player.damageMod;
                        p.currentHP--;
                    }
                }
            }
            
            e.draw(game.batch,deltaTime);
            
        //check for player collision with enemy
            if (player.collide(e)){
                if (player.canGetHurt()) {
                    player.currentHP -= e.damageMod; //that's not what damageMod is for! ~AF
                    player.lastHurtTime = 0;
                    player.sprite.setTexture(Global.hurtPlaceholder);
                    e.currentHP --;
                }
            }
            
        //TODO: rewrite this part to use exp and levelThreshold like they're meant to
            if(e.currentHP<=0){
                score+=100;//PlaceHolder Method That I didn't know the appropriate method to implement.Feel free to move at as long as Functionnality remains the same -EY
                player.exp = 10;
                player.currentExp += player.exp;
                player.levelThreshold -= player.exp;
            }
            //Player can level up
            if (player.levelThreshold <=0){
                player.levelUp();
                player.levelThreshold = player.DEFAULT_LEVELTHRESHOLD * player.level;
                //System.out.println("level up");
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
            if(isOver || isPaused){
            lastSpawnTime = 999999999999999999l;//Temporary fix To stop Enemies from spawning while Game is paused or over.
            }else
            lastSpawnTime = TimeUtils.nanoTime();
        }
    // Hud related things(PLACEHOLDER)-EY           
        updateEnemyHp();
        
        game.batch.end();
        //Combining Camera View with other Windows In case.
        game.batch.setProjectionMatrix(hud.getStage().getCamera().combined);
        
        //Updating hud
        hud.getStage().draw();//Drawing hud
        hud.getStage().act(deltaTime);
        hud.updateHud(deltaTime);
    }
    
    private void updateEnemyHp(){//for now Im thinking of keeping this for Debug mode., Eventually, I might add damage numbers AND maybe attribute modifiers(Newton,Volts, Amps etc.)

        //HP Enemy
        for(Enemy e: enemyList){//This is debugging things.
            float HpPosX = e.position.x,HpPosY = e.position.y+e.sprite.getHeight()*2 ;
            
            game.font.draw(game.batch,"HP",HpPosX,HpPosY ,hudSectionWidth,Align.left,false);
            game.font.draw(game.batch, String.format(Locale.getDefault(), "%3d", e.currentHP),HpPosX ,HpPosY-e.sprite.getHeight()/2,hudSectionWidth,Align.left,false);
        }
        
    }
    
    @Override
    public void resize(int width, int height) {
        hud.getStage().getViewport().update(width,height);
        GoverLay.GoverStage.getViewport().update(width, height);
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
          hud.dispose();
          GoverLay.dispose();
    }
    
    
}
