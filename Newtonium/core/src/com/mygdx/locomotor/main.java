package com.mygdx.locomotor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        Player player;
        Enemie mal;
        int vague;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("LilBoy.png");
                player = new Player(img,Color.GREEN);
                mal = new Enemie(img, Color.BLUE);
        }

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		player.Draw(batch);
                Waves(1);
                batch.end();
                
                
               
	}

        @Override
        public void resume() {
            super.resume(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void pause() {
            super.pause(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void resize(int width, int height) {
            super.resize(width, height); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        
<<<<<<< Updated upstream
        public void Waves(int vague){
=======
        //Methodes propres au jeux
        public void waves(int vague){
>>>>>>> Stashed changes
            
            for (int i =0; i < vague; i++)
               mal.Spawn(batch, player); 
        }
    	
        @Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}

