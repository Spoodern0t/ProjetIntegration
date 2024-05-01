/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.control;

import com.mygdx.newtonium.model.*;
import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys.P;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Random;

/**
 *
 * @author Ekrem Yoruk (1676683)
 * @author Alexis Fecteau (2060238)
 * @author Thomas Cyr (2289144)
 * 
 * @since 29/04/2024
 */

public class GameScreen implements Screen {

    Random rand = new Random();
    
//screen
    static public Camera Camera;
    Viewport viewport;
    
//Graphics 
    public Hud hud;
    //I migrated spritebatch and anything related to it to GameController so MainMenuScreen can also use it.~EY
    
    
//background assets
    static public Sprite mapSprite;
    GameController game;
        
//timers (cooldowns and delays for game objects that occur in screen)
    public float lastSpawnTime = 0f;
    int wave;
    
    
//game objects
    Player player = Global.currentPlayer;
    Enemy[] enemyTypes = Global.Enemies.enemyRotation;
    
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
        mapSprite = new Sprite(Global.scaleMapPlaceholder);
        mapSprite.setPosition(0,0);
    
    /*
    //world boundaries
        final int WORLD_WIDTH = mapSprite.getRegionWidth();
        final int WORLD_HEIGHT = mapSprite.getRegionHeight(); //Lorsque la map sera faite on va pouvoir setter les limite de la map en remplacant 1000 par variable
        mapSprite.setSize(1000,1000);
    */
        
    //gameobject setup (some temporary)
        //player.addItem(Global.Items.testItem);
        player.addItem(Global.Items.satelliteTester);
        
        enemyList = new LinkedList<>();
        projectileList = new LinkedList<>();
        despawnList = new LinkedList<>();
        
        //Used the screen to fetch The hp and score and Likely other relevant Data.
        hud = new Hud(game.batch,this,this.game);
        
       
    }
    //Erased PrepHud Method and migrated related parameters to Hud class.
    //Real-time game logic (called for each new frame)
    @Override
    public void render(float deltaTime){
        if(Gdx.input.isKeyJustPressed(P)){//Pause Actin funky, Needs Fix.Only does 1 thing at a Time, Might be me with a severe lapse in logic. To any that can solve it, thank you in advance -EY
           hud.pmenu.Pause();
        }
        if(isOver || isPaused){
            deltaTime = 0;
            hud.goverlay.GendOccur();
            /*if(isPaused){
               hud.pmenu.Unpause();   
            }*/
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
                        e.currentHP -= (int)p.flatDamage*p.damageMod;
                        System.out.println(p.flatDamage + "Newtons!"); //for testing
                        p.currentHP--;
                    }
                }
            }
            
            e.draw(game.batch,deltaTime);
            
        //check for player collision with enemy
            if (player.collide(e)){
                if (player.canGetHurt()) {
                    player.currentHP = player.currentHP - e.strength;
                    player.lastHurtTime = 0;
                    player.sprite.setTexture(Global.hurtPlaceholder);
                    e.currentHP--;
                }
            }
            
        //TODO: rewrite this part to use exp and levelThreshold like they're meant to
            if(e.currentHP<=0){
                score+=100;//PlaceHolder Method That I didn't know the appropriate method to implement.Feel free to move at as long as Functionnality remains the same -EY
                player.exp = e.exp;
                player.currentExp += player.exp;
                
            }
            //Player can level up
            if (player.currentExp >= player.levelThreshold){
                player.levelUp();
                System.out.println("level up");
            }
            //System.out.println("Player xp : "+player.currentExp+ " Player level : "+player.level+" Level Treshold : "+player.levelThreshold);
        }
        /* 
        Flush all dead entities at once: Java raises an exception if a
        collection is modified while a thread uses it (like in for-loops).~AF
        */
        enemyList.removeAll(despawnList);
        despawnList.clear();
        
    //spawn enemies periodically
        lastSpawnTime += deltaTime;
        if(lastSpawnTime > 1f) {
            enemyList.add((Enemy)enemyTypes[rand.nextInt(enemyTypes.length)].spawn());
            lastSpawnTime = 0;
        }
        
    // Hud related things(PLACEHOLDER)-EY           
        updateEnemyHp();
        
        
        
        game.batch.end();
        //Combining Camera View with other Windows In case.
        game.batch.setProjectionMatrix(hud.getStage().getCamera().combined);
        
        //Updating hud
        hud.getStage().draw();//Drawing hud
        hud.getStage().act(deltaTime);
        
        hud.getOverlayStage().draw();
        hud.getOverlayStage().act(Gdx.graphics.getDeltaTime());
        
        hud.getPbuttonStage().draw();
        hud.getPbuttonStage().act(deltaTime);
        
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

    }
    
    
}
