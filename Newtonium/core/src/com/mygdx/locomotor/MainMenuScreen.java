/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Warning: This is a very bare bones edition of a main menu. 
 * Direct any ideas for improvement to the authors by way of messages.
 * 
 * @author Ekrem Yoruk (1676683)
 * @author Thomas Cyr
 * 
 * @since 25/03/2024
 */
public class MainMenuScreen implements Screen {
    
    final GameController game;
    OrthographicCamera camera;
    
    
    
    public MainMenuScreen(final GameController game) {
        this.game = game;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(0,0,0,1);
        
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
	game.batch.begin();
	game.font.draw(game.batch, "Welcome to Newtonium! ", (Gdx.graphics.getWidth()/2-50), (Gdx.graphics.getHeight()/2)+10);
	game.font.draw(game.batch, "click anywhere to start !",(Gdx.graphics.getWidth()/2)-50 , (Gdx.graphics.getHeight()/2)-10);
	game.batch.end();

	if (Gdx.input.isTouched()) {
        game.setScreen(new GameScreen(game));
        dispose();
		}
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
    public void dispose() {
        
    }
    
}
