/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Buttons.LEFT;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    
    Texture playActive;
    Texture playPassive;
    Texture quitActive;
    Texture quitPassive;
    
    Sprite playButton;
    Sprite quitButton;
    
    
    
    public MainMenuScreen(final GameController game) {
        this.game = game;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        
        playActive = new Texture("NewtoniumSelectionBox.png");//Active is when pictture changes when you mouse over it.
        playPassive = new Texture("NewtoniumLaunchSymbol.png");//Passive is when mouse is NOT over the Texture
        quitActive = new Texture("NewtoniumSelectionBox.png");
        quitPassive = new Texture("NewtoniumTitle.png");
        
        playButton = new Sprite(playPassive);
        quitButton = new Sprite(quitPassive);
                
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
	
        
        playButton.setScale(1);
        playButton.setX(camera.viewportWidth/3);
        playButton.draw(game.batch);
        
        quitButton.setScale(1/4);
        quitButton.setX(playButton.getX());
        quitButton.setY(playButton.getY()-playButton.getHeight());
        quitButton.draw(game.batch);
        
	
        
	if (Gdx.input.getX() < playButton.getX() + playButton.getWidth() 
         && Gdx.input.getX() > playButton.getX()
         && Gdx.input.getY() < playButton.getY() + playButton.getHeight() 
         && Gdx.input.getY() > playButton.getY() ) {
        playButton.setTexture(playActive);
        if(Gdx.input.isButtonJustPressed(LEFT)){
           game.setScreen(new GameScreen(game));
        }
        }
        else playButton.setTexture(playPassive);
        
        if (Gdx.input.getX() < quitButton.getX() + quitButton.getWidth() 
         && Gdx.input.getX() > quitButton.getX()
         && Gdx.input.getY() < quitButton.getY() + quitButton.getHeight() 
         && Gdx.input.getY() > quitButton.getY() ) {
        quitButton.setTexture(quitActive);
        }
        else {quitButton.setTexture(quitPassive);
        }
            
       game.batch.end(); 
        
//        game.setScreen(new GameScreen(game));//ScreenTransition prompter. Condition it as you see fit.

        dispose();
		
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
