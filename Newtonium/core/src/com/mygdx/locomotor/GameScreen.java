/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

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
    
    //world parameters(things about how the meausrements are treated)ask Ekrem for source
    final int WORLD_WIDTH = 1000;
    final int WORLD_HEIGHT = 1000;

    //timings(cooldowns and delays for game objects)
    public long lastspawnTime = 0L;
    int vague;
    
    //game objects
    Player player;
    
    public LinkedList<Enemie> listmals;
    
    //equivalent to the create() method for this class.
    GameScreen(){
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
        Camera.position.set(player.HitBox.x,player.HitBox.y,20);
        Camera.update();  
        
        listmals = new LinkedList<>();      
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
        // enemylist stuff(including collision methods.)
                ListIterator<Enemie> iter = listmals.listIterator();
                while(iter.hasNext()) {
                Enemie mal = iter.next();
                mal.moveEnemy(batch,player);
                
                if (player.collide(mal)== true){
                player.sprite.setTexture(ooftexture);    
                }else player.sprite.setTexture(img);
                if(mal.alive == false){
                        iter.remove();
                        /*Ededsound.play();*/
                }
                }
                if(TimeUtils.nanoTime() - this.lastspawnTime > 3000000000L) spawnEnemy();
                batch.end();
    }
    private void spawnEnemy() {
            Enemie mal = new Enemie();
            mal.position.x = MathUtils.random(0,100 - mal.sprite.getTexture().getWidth());
            mal.position.y = MathUtils.random(0,100 - mal.sprite.getTexture().getHeight());
            mal.hitboxRadius = (mal.sprite.getHeight()*mal.sprite.getScaleY()/2);
            mal.HitBox.x = mal.position.x;mal.HitBox.y = mal.position.y;
            listmals.add(mal);
            this.lastspawnTime = TimeUtils.nanoTime();
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
