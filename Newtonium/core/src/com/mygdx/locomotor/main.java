package com.mygdx.locomotor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
        Texture Map;
        public Sprite Mapsprite;
	
        
        @Override
	public void create () {
		
                
		img = new Texture("LilBoy.png");
                ooftexture = new Texture("sadge.png");
                BassTerd = new Texture("Evil.png");
                
                
                
                player = new Player();
                mal = new Enemie();
                
                
                
                Map = new Texture("MapImg.jpg");
                Mapsprite = new Sprite(Map);
                Mapsprite.setPosition(0,0);
                Mapsprite.setSize(1000,1000);
                
                
                
                
                
                Camera = new OrthographicCamera(800,400);
                Camera.position.set(player.HitBox.x,player.HitBox.y,20);
                Camera.update();
                batch = new SpriteBatch();
        }

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		Camera.position.set(player.sprite.getX(),player.sprite.getY(),0);
                Camera.update();
                batch.setProjectionMatrix((Camera.combined));
                
                batch.begin();
                /*if (player.sprite.getBoundingRectangle().overlaps(mal.sprite.getBoundingRectangle())){
                    player.alive = false;
                }
                */
                //collision system(Oof only.)
                Mapsprite.draw(batch);
		player.draw(batch);
                
                
                if (player.HitBox.overlaps(mal.HitBox)){
                    player.collide(true);
                }
                else player.collide(false);
                
                
            
                
                
                waves(1);
                batch.end();
                
                
               
	}
        
        public void waves(int vague){
            
            for (int i =0; i < vague; i++)
               mal.spawn(batch, player); 
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

