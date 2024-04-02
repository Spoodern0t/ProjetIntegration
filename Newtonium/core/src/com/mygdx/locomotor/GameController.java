package com.mygdx.locomotor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameController extends Game {
        //this class will likely be reserved for main menu.
        //this version has not much in ways of change but the change will be arriving strongly VERY SOON.
        //TODO: Simple Main menu with only button start, ideally here.
        
        public MainMenuScreen menuScreen;
        public SpriteBatch batch;//So I can call it from more than 1 class while still utilising a "game class" rather than a "screen" class.
        public BitmapFont font;
        
        @Override
	public void create () {
            
            
            batch = new SpriteBatch(); //images currently on-screen
            font = new BitmapFont();
            menuScreen = new MainMenuScreen(this);
            this.setScreen(menuScreen);

        }
             
	@Override
	public void render () {
		super.render();
	}
        
            @Override
            public void resize(int width, int height) {
                menuScreen.resize(width, height);
            }
        
	
            @Override
            public void dispose () {
                batch.dispose();
                menuScreen.dispose();
                font.dispose();
	}

    

    
}

