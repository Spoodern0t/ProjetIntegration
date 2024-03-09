package com.mygdx.locomotor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        Texture ooftexture;
        Texture BassTerd;
        Player player;
        public Array<Enemie> mals;
        int vague;
        OrthographicCamera Camera;
        Texture Map;
        public Sprite Mapsprite;
	public long lastspawnTime;
        
        @Override
	public void create () {
		
                
		img = new Texture("LilBoy.png");
                ooftexture = new Texture("sadge.png");
                BassTerd = new Texture("Evil.png");
                
                
                
                player = new Player();
                
                
                Map = new Texture("MapImg.jpg");
                Mapsprite = new Sprite(Map);
                Mapsprite.setPosition(0,0);
                Mapsprite.setSize(1000,1000);
                
                
                
                
                
                Camera = new OrthographicCamera(800,400);
                Camera.position.set(player.HitBox.x,player.HitBox.y,20);
                Camera.update();
                batch = new SpriteBatch();
                //*Attempt at making Enemy spawner*//
                mals = new Array<Enemie>();
                spawnEnemy();
                
        }
             public void spawnEnemy() {
             Enemie mal = new Enemie();
             mal.position.x = MathUtils.random(0,1000 - mal.sprite.getTexture().getWidth());
             mal.position.y = MathUtils.random(0,1000 - mal.sprite.getTexture().getHeight());
             mals.add(mal);
             this.lastspawnTime = TimeUtils.nanoTime();
            }
	@Override
	public void render () {
		
                //camera stuff
                ScreenUtils.clear(0, 0, 0, 1);
		Camera.position.set(player.sprite.getX(),player.sprite.getY(),0);
                Camera.update();
                batch.setProjectionMatrix((Camera.combined));
                
                batch.begin();
      
                //drawing the stuff(map,player and enemy
                Mapsprite.draw(batch);
		player.draw(batch);
                
                
                for(Iterator<Enemie> iter = mals.iterator(); iter.hasNext();){
                Enemie mal = iter.next();
                mal.draw(batch,player);
                if (player.HitBox.overlaps(mal.HitBox)){
                    player.collide(true);
                }
                else player.collide(false);
                if(mal.alive == false){
                        iter.remove();
                        /*Ededsound.play();*/
                }
                if(TimeUtils.nanoTime() - this.lastspawnTime > 3000000000L) spawnEnemy();
                }
                batch.end();
                
                
                
                
               
	}
      
        
	
	@Override
	public void dispose () {
		img.dispose();
                ooftexture.dispose();
                Mapsprite.getTexture().dispose();
                BassTerd.dispose();
                batch.dispose();
	}

    

    
}

