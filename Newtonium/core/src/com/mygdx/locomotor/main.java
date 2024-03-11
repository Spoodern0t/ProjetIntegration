package com.mygdx.locomotor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
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

public class main extends Game {
        //this class will likely be reserved for main menu.
        //this version has not much in ways of change but the change will be arriving strongly VERY SOON.
        //TODO: Simple Main menu with only button start, ideally here.
        GameScreen gameScreen;
        @Override
	public void create () {
		gameScreen = new GameScreen();
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

