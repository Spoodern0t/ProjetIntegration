package com.mygdx.locomotor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        Texture ooftexture;
        Texture BassTerd;
        Player player;
        Enemie mal;
        int vague;
        OrthographicCamera Camera;
	
        
        @Override
	public void create () {
		
                batch = new SpriteBatch();
		img = new Texture("LilBoy.png");
                ooftexture = new Texture("sadge.png");
                BassTerd = new Texture("Evil.png");
                mal = new Enemie();
                player = new Player();
                
                
                Camera = new OrthographicCamera(800,400);
                
                
        }

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
                /*if (player.sprite.getBoundingRectangle().overlaps(mal.sprite.getBoundingRectangle())){
                    player.alive = false;
                }
                */
                
                if (player.HitBox.overlaps(mal.HitBox)){
                    player.collide(true);
                }
                else player.collide(false);
                
                
            
                
                if (player.alive){
		player.draw(batch);
                }
                waves(1);
                batch.end();
                
                
               
	}
        
        public void waves(int vague){
            
            for (int i =0; i < vague; i++)
               mal.spawn(batch, player); 
        }
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

    
}

