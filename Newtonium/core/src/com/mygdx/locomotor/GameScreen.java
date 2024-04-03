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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

/**
 *
 * @author Ekrem Yoruk (1676683)
 * @author Alexis Fecteau (2060238)
 * 
 * @since 01/04/2024
 */

public class GameScreen implements Screen {

//screen
    static public Camera Camera;
    Viewport viewport;
    
//Graphics 
    

    //I migrated spritebatch and anything related to it to GameController so MainMenuScreen can also use it.~EY
    
    static Texture idleTexture;
    static Texture oofTexture;
    static Texture evilTexture;
    
    
//background assets
    Texture mapTexture;
    static public Sprite mapSprite;
    GameController game;
        
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
// Heads up Display    
    
    public int score = 0;
    float hudVertMargin, hudScorex, hudRightx, hudHealthx, hudRow1y,hudRow2y,hudSectionWidth, hudEXPx,hudEXPy;//Tableau 2x3 pour placer les objets hud.
    
//equivalent to the create() method for this class.
    public GameScreen(final GameController game){
        this.game = game;
        Camera = new OrthographicCamera(800,400);
        
        
    //texture setup (all placeholder)
        idleTexture = new Texture("LilBoy.png");
        oofTexture = new Texture("sadge.png");
        evilTexture = new Texture("Evil.png");
        
        
    //background setup 
        mapTexture = new Texture("MapImg.jpg");
        mapSprite = new Sprite(mapTexture);
        mapSprite.setPosition(0,0);
        
        //world boundaries
        final int WORLD_WIDTH = mapSprite.getRegionWidth();
        final int WORLD_HEIGHT = mapSprite.getRegionHeight(); //Lorsque la map sera faite on va pouvoir setter les limite de la map en remplacant 1000 par variable
        mapSprite.setSize(1000,1000);
        
       
        
        
    //gameobject setup (some temporary)
        player = new Player(); 
        
        item = new Item();//generic test item ~AF
        player.addItem(item);
        
        enemy = new Enemy(1,100);
        
        enemyList = new LinkedList<>();
        projectileList = new LinkedList<>();
        despawnList = new LinkedList<>();
        
        prepHud();//prepares Hud once.
       
    }
    float xmodifier = -200;
    float ymodifier = -200;
    
    public void prepHud(){
        //Make default bitmapfont customizable(size and all.)
        //FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(INSERT FONT FILE NAME HERE);
        //FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //example: fontParameter.size = 64;//to change letter size kinda like in word.
        
        
        //Scaling hud to fit game window.
        game.font.getData().setScale(1);
        //calculate Margins
        hudVertMargin = game.font.getCapHeight() /2;
        hudScorex = hudVertMargin + xmodifier ;
        hudRightx = ((Camera.viewportWidth * 2/3) - hudScorex );
        hudHealthx = (Camera.viewportWidth* 2/3 );
        hudRow1y = (Camera.viewportHeight - hudVertMargin );
        hudRow2y = (hudRow1y - hudVertMargin - game.font.getCapHeight()) ;
        hudSectionWidth = Camera.viewportWidth / 2;
    }
    
    //TODO: WE NEED TO REMAKE EVERY METHODS IN EVERY CLASS IN A WAY THAT ONLY METHODS THAT INVOLVE WHAT WE SEE HAPPEN IN A GAME HAPPEN HERE.
    //exceptions are: class descriptors and cooldown related things. I put alot of comments for earier organisation.
    
//Real-time game logic (called for each new frame)
    @Override
    public void render(float deltaTime){
        
    //move camera
        Camera.position.set(player.sprite.getX(),player.sprite.getY(),0);
        Camera.update();
        game.batch.setProjectionMatrix((Camera.combined));
        
        game.batch.begin();

        
    //render map background
        ScreenUtils.clear(0,0,0,1);
        mapSprite.draw(game.batch);
        
        
    //calculate player
        player.draw(game.batch); //as of now, this call triggers all the items. ~AF

        
    //calculate projectiles
        for (Projectile p: projectileList){
            p.draw(game.batch);
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
                        e.currentHP -= (int)p.flatDamage*p.speed*player.damageMod;
                        p.currentHP--;
                    }
                }
            }
            projectileList.removeAll(despawnList);
            
            e.draw(game.batch);
        
        //check for player collision with enemy
            if (player.collide(e)){
                if (player.canGetHurt()) {
                    player.currentHP -= e.damageMod*e.speed;
                    player.lastHurtTime = 0;
                    player.sprite.setTexture(oofTexture);
                    e.currentHP --;
                }
            }
            if(e.currentHP<=0){
                score+=100;//PlaceHolder Method That I didn't know the appropriate method to implement.Feel free to move at as long as Functionnality remains the same -EY
                player.exp = 50;
                player.currentEXP += player.exp;
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
            lastSpawnTime = TimeUtils.nanoTime();
        }
    // Hud related things(PLACEHOLDER)-EY           
        updateHud();
        
        game.batch.end();
    }
    private void updateHud(){
        /*//Testing equipment FOR (CO ORDINATES)
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) { 
                                                       ymodifier += Gdx.graphics.getDeltaTime()*(hudVertMargin*100);}
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) { 
                                                       ymodifier -= Gdx.graphics.getDeltaTime()*(hudVertMargin*100);}
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) { 
                                                       xmodifier -= Gdx.graphics.getDeltaTime()*(hudVertMargin*100);}
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { 
                                                        xmodifier += Gdx.graphics.getDeltaTime()*(hudVertMargin*100);}
        
        
        System.out.println(String.valueOf(hudRow1y)+" scorey");
        System.out.println(String.valueOf(hudScorex)+" scorex");
        System.out.println(String.valueOf(xmodifier)+" modifierscorex");
        System.out.println(String.valueOf(ymodifier)+" modifierscorey");
        */
        
        //HP and Score
        //Rendering top row
        game.font.draw(game.batch, "Score",Camera.position.x + hudScorex + xmodifier,Camera.position.y + hudRow1y +ymodifier, hudSectionWidth,Align.left,false);
        game.font.draw(game.batch,"HP",Camera.position.x + hudHealthx + xmodifier ,Camera.position.y + hudRow1y +ymodifier,hudSectionWidth,Align.left,false);
        //2nd row
        game.font.draw(game.batch, String.format(Locale.getDefault(), "%6d", score),Camera.position.x + hudScorex + xmodifier, Camera.position.y + hudRow2y + ymodifier,hudSectionWidth,Align.left,false);
        game.font.draw(game.batch, String.format(Locale.getDefault(), "%3d", player.currentHP),Camera.position.x + hudHealthx + xmodifier,Camera.position.y + hudRow2y + ymodifier,hudSectionWidth,Align.left,false);
        
        //EXP and Level
        
        game.font.draw(game.batch,"EXP",Camera.position.x+player.sprite.getWidth() ,Camera.position.y - hudEXPy -ymodifier,hudSectionWidth,Align.left,false);
        game.font.draw(game.batch, String.format(Locale.getDefault(), "%3d", player.currentEXP),Camera.position.x+player.sprite.getWidth()*3 ,Camera.position.y - hudEXPy- ymodifier,hudSectionWidth,Align.left,false);
        
        game.font.draw(game.batch,"Level",Camera.position.x-player.sprite.getWidth()*3 ,Camera.position.y - hudEXPy -ymodifier,hudSectionWidth,Align.left,false);
        game.font.draw(game.batch, String.format(Locale.getDefault(), "%3d", player.level),Camera.position.x-player.sprite.getWidth() ,Camera.position.y - hudEXPy- ymodifier,hudSectionWidth,Align.left,false);
        
        //HP Enemy
        for(Enemy e: enemyList){
            float HpPosX = e.position.x,HpPosY = e.position.y+e.sprite.getHeight()*2 ;
            
            game.font.draw(game.batch,"HP",HpPosX,HpPosY ,hudSectionWidth,Align.left,false);
            game.font.draw(game.batch, String.format(Locale.getDefault(), "%3d", e.currentHP),HpPosX ,HpPosY-e.sprite.getHeight()/2,hudSectionWidth,Align.left,false);
        }
        
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
                
    }
    
    
}
