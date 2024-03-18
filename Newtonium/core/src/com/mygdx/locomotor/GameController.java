package com.mygdx.locomotor;

import com.badlogic.gdx.Game;

public class GameController extends Game {
        //this class will likely be reserved for main menu.
        //this version has not much in ways of change but the change will be arriving strongly VERY SOON.
        //TODO: Simple Main menu with only button start, ideally here.
        GameScreen gameScreen;
        @Override
	public void create () {
		
            gameScreen = new GameScreen(this);
            setScreen(gameScreen);

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

                gameScreen.dispose();
	}

    

    
}

