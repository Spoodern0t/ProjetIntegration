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
import com.badlogic.gdx.graphics.Color;
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
    
//Ingame Graphical User Interface(GUI) 
    //damagenumbers Related object.
    public float displayingpositionx;
    public float displayingpositiony;
    
//background assets
    static public Sprite mapSprite;
    GameController game;
        
//timers (cooldowns and delays for game objects that occur in screen)
    public float lastSpawnTime = 0f;
    public float displayingtime = 0f;
    boolean displayable = false;
    int wave;
    
//game objects
    public Player player = Global.currentPlayer;
    Enemy[] enemyTypes = Global.Enemies.enemyRotation;
    
    public static LinkedList<Projectile> projectileList; 
    public static LinkedList<Enemy> enemyList;
    
    public static LinkedList<Entity> despawnList;//clears dead entities after for loops ~AF
    
// Heads up Display    
    public int score = 0;
    float hudSectionWidth;//Attribut pour Des labels sur l'ennemie
    public Hud hud;
    
//Popup and Pause related Attributes
    //Game Over Related Attributes
     public static boolean isOver = false;
    //Pause Related Attributes
     public static boolean isPaused = false;
    
     
     
//Constructor
    public GameScreen(final GameController game){
        
        this.game = game;
        Camera = new OrthographicCamera(800,400);
        
        hud = new Hud(game.batch,this,this.game);
        
    //background setup 
        mapSprite = new Sprite(Global.mapPlaceholder);
        mapSprite.setPosition(0,0);
    
    //world boundaries
    /*
        final int WORLD_WIDTH = mapSprite.getRegionWidth();
        final int WORLD_HEIGHT = mapSprite.getRegionHeight(); //Lorsque la map sera faite on va pouvoir setter les limite de la map en remplacant 1000 par variable
        mapSprite.setSize(1000,1000);
    */
        
    //gameobject setup
        
        //Initialize entity lists
        enemyList = new LinkedList<>();
        projectileList = new LinkedList<>();
        despawnList = new LinkedList<>();
        
        //Initialize player's item inventory (temporary until levelups are implemented)
        player.addItem(Global.Items.appleFling);
        player.addItem(Global.Items.appleBurst);
        //player.addItem(Global.Items.satelliteFling);
        player.addItem(Global.Items.satelliteBurst);
        player.addItem(Global.Items.springBlockFling);
        
        //Test items for debugging - comment out before submitting project
        /*
        player.addItem(Global.Items.homingBulletTester);
        player.addItem(Global.Items.satelliteTester);
        player.addItem(Global.Items.springBlockTester);
        */
        
       
    }
    //Real-time game logic (called for each new frame)
    @Override
    public void render(float deltaTime){
        
        if(Gdx.input.isKeyJustPressed(P)){//fixed Since 2024/05/01
           isPaused = !isPaused;
           hud.pmenu.Pause();
           hud.pmenu.Unpause();
        }
        if(isOver || isPaused){
            deltaTime = 0;
            if(isOver){hud.goverlay.GendOccur();}
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
            e.sprite.setColor(Color.WHITE);
        //check for projectile collision with enemy
            for (Projectile p: projectileList){
                if (p.collide(e)){
                    if (e.canGetHurt()){
                        e.sprite.setColor(Color.RED);
                        e.damageDisplayable = true;
                        e.lastHurtTime = 0;
                        e.currentHP -= (int)p.flatDamage; //Do NOT multiply this by anything. It will screw up our physics calculations. ~AF
                        System.out.println(p.flatDamage + " Newtons!"); //for testing
                        p.currentHP--;
                    }
                }
            }
            
            e.draw(game.batch,deltaTime);
            if(!e.paindisplayer.displayList.isEmpty()){
            for(DamageDisplayer.damageLabel l: e.paindisplayer.displayList){
             l.draw(game.batch,1);
             l.updatepos(deltaTime, e);
            }
            }
        //check for player collision with enemy
            if (player.collide(e)){
                if (player.canGetHurt()) {
                    player.currentHP = player.currentHP - e.strength;
                    player.lastHurtTime = 0;
                    player.sprite.setTexture(Global.playerHurtPlaceholder);
                    e.currentHP--; //obsolete as soon as we give enemies proper HP! ~AF
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
            e.paindisplayer.displayList.removeAll(e.paindisplayer.disposeList);
            e.paindisplayer.disposeList.clear();
        
        }
        /* 
        Flush all dead entities at once: Java raises an exception if a
        collection is modified while a thread uses it (like in for-loops).~AF
        
        */
        enemyList.removeAll(despawnList);
        despawnList.clear();
        
        
    //spawn enemies periodically
        lastSpawnTime += deltaTime;
        if(lastSpawnTime > 3f) { //spawn delay
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
    private void displayDamage(Projectile p,Enemy e,float allowedTime,float deltaTime){
           //GameOverlay.damageDisplayer.adddisplay(e,p);
    }
    @Override
    public void resize(int width, int height) {
        hud.getStage().getViewport().update(width,height);

    }

    @Override
    public void pause() {//Add Hud.Pause() Method composition here.
        
    }

    @Override
    public void resume() {//Add Hud.UnPause() Method composition here
    }
    
    @Override
    public void hide() {
        
    }
    @Override
    public void show() {
        
    }
    
    public void reset(){//hardcoding some values to reset Hud related ones.
        player = (Player) Global.currentPlayer.spawn();
        
        hud.Expbar.setValue(0);
        hud.GameTime = 0;
        
    }
    
    @Override
    public void dispose() {
          hud.dispose();
          hud.getOverlayStage().dispose();
          GameScreen.enemyList.removeAll(enemyList);
          reset();
    }
    
    
}
