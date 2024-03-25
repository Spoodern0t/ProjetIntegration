package com.mygdx.locomotor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameController extends Game {
        //this class will likely be reserved for main menu.
        //this version has not much in ways of change but the change will be arriving strongly VERY SOON.
        //TODO: Simple Main menu with only button start, ideally here.
        
        GameScreen gameScreen;
        SpriteBatch batch;
        @Override
	public void create () {
            
            batch = new SpriteBatch(); //images currently on-screen
            gameScreen = new GameScreen(this);
            this.setScreen(gameScreen);

        }
             
	@Override
	public void render () {
		super.render();
	}
        
            @Override
            public void resize(int width, int height) {
                gameScreen.resize(width, height);
            }
        
	
            @Override
            public void dispose () {
                batch.dispose();
                gameScreen.dispose();
	}

    

    
}

