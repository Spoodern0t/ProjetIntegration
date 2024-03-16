/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.LinkedList;

/**
 *
 * @author Yoruk Ekrem
 */
class GameScreen implements Screen {
    
    
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
    Projectile projectile;
    public LinkedList<Enemy> BaddieList;
    
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
        

        //gameobject setup 
        player = new Player(); 
        projectile = new Projectile(player.position.x,player.position.y,10,10,img);
          
        
        BaddieList = new LinkedList<>();      
        batch = new SpriteBatch();
        }

   

    @Override
    public void render(float deltaTime) {
                //old camera stuff
        Camera.position.set(player.HitBox.x,player.HitBox.y,20);
        Camera.update();
        batch.setProjectionMatrix((Camera.combined));
        
        batch.begin();
        
        //map and background
        ScreenUtils.clear(0,0,0,1);
        Mapsprite.draw(batch);
        
        
        projectile.update(deltaTime);
        //player
        player.draw(batch);
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
    
    public void SpawnEnemy(){
    
    }
    
    @Override
    public void resize(int width, int height) {
        
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
