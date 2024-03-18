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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Iterator;
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
    Texture img;
    Texture ooftexture;
    Texture BassTerd;
    
    // background things
    Texture Map;
    public Sprite Mapsprite;
    
    //world parameters(things about how the measurements are treated)ask Ekrem for source
    final int WORLD_WIDTH = 1000;
    final int WORLD_HEIGHT = 1000;

    //timings(cooldowns and delays for game objects that occur in screen)
    public long lastspawnTime = 0l;
    int vague;
    
    
    //game objects
    public static Player player;
    Projectile projectile;
    
    //maybe combine into Entity list? ~AF
    public LinkedList<Projectile> projectileList; //maybe combine into Entity list? ~AF
    public LinkedList<Enemy> enemieList;
    
    public LinkedList<Entity> despawnList;//clears dead entities after loops
    
    //equivalent to the create() method for this class.
    public GameScreen(final GameController game){
        Camera = new OrthographicCamera(800,400);
                
        
        
        //texture setup
        img = new Texture("LilBoy.png");
        ooftexture = new Texture("sadge.png");
        BassTerd = new Texture("Evil.png");
                
        
        //background setup 
        Map = new Texture("MapImg.jpg");
        Mapsprite = new Sprite(Map);
        Mapsprite.setPosition(0,0);
        Mapsprite.setSize(1000,1000);
        

        //gameobject setup and playercamera.
        player = new Player(); 
        projectile = new Projectile(0);//generic test projectile
        Camera.position.set(player.hitBox.x,player.hitBox.y,20);
        Camera.update();  
        
        
        enemieList = new LinkedList<>();
        projectileList = new LinkedList<>();
        despawnList = new LinkedList<>();
        batch = new SpriteBatch();
    
    
    
    }
    //TODO: WE NEED TO REMAKE EVERY METHODS IN EVERY CLASS IN A WAY THAT ONLY METHODS THAT INVOLVE WHAT WE SEE HAPPEN IN A GAME HAPPEN HERE.
    //exceptions are: class descriptors and cooldown related things. I put alot of comments for earier organisation.
    
    @Override
    public void render(float deltaTime) {
    //old camera stuff
        Camera.position.set(player.sprite.getX(),player.sprite.getY(),0);
        Camera.update();
        batch.setProjectionMatrix((Camera.combined));
        
        batch.begin();
        
    //map and background
        ScreenUtils.clear(0,0,0,1);
        Mapsprite.draw(batch);
        
        
    //player
        player.draw(batch);
        
    //projectiles
        fireboolet(projectile); //checks for spacebar input
        for (Projectile p: projectileList){
            p.draw(batch);
            if (p.isDead){
                despawnList.add(p);
            }
        }
        projectileList.removeAll(despawnList);
        despawnList.clear();
        
    // enemylist stuff(including collision methods.)
            ListIterator<Enemy> iter = enemieList.listIterator();
            
            for(Enemy e: enemieList) {
                    //use iterator to find closest enemy
                    //while (iter.hasNext(){
                    //Enemie mal = iter.next();
                    e.draw(batch);
                    
                    if (player.collide(e)== true){
                        player.sprite.setTexture(ooftexture);System.out.println("True Box");
                    }else System.out.println("Box False");
                    if(e.isDead){
                        enemieList.remove(e);
                        //iter.remove();
                        /*Ededsound.play();*/
                    }
                }
                if(TimeUtils.nanoTime() - this.lastspawnTime > 3000000000L) {
                    spawnEnemy();
                }
                if (player.canGetHurt() == true) {player.sprite.setTexture(img);player.lastHurtTime = 0;}
               
                batch.end();
    }
    
    private void fireboolet(Projectile projectile){
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            System.out.println("Space pressed");
            projectileList.add((Projectile)projectile.spawn());
        }

        
        
    }
    
    private void spawnEnemy() {
            Enemy mal = new Enemy();
            enemieList.add((Enemy)mal.spawn());
            this.lastspawnTime = TimeUtils.nanoTime();
    }
    
    public void EnemyAttack() {
                ListIterator<Enemy> iter = enemieList.listIterator();
                while(iter.hasNext()) {
                    Enemy mal = iter.next();
                    if (player.canGetHurt()==true)
                    if (player.collide(mal)== true){
                        player.sprite.setTexture(ooftexture);    
                    } 
                    if(mal.isDead){
                        iter.remove(); //this removes from iterator, not main list!
                        /*Ededsound.play();*/
                    }
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
    public void dispose() {
                img.dispose();
                ooftexture.dispose();
                Mapsprite.getTexture().dispose();
                BassTerd.dispose();
                batch.dispose();
    }
    
    
}
