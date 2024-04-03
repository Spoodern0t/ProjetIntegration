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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    Stage stage;//PlaceHolder in case shit goes down REAAAAL bad.
    //Textures
    Texture playActive;
    Texture playPassive;
    Texture quitActive;
    Texture quitPassive;
    //Sprites to facilitate texture Change.
    Sprite playButton;
    Sprite quitButton;
    
    
    
    public MainMenuScreen(final GameController game) {
        this.game = game;
        //camera setup
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);//Reevaluate yDown = false and find Functionnality
        //playTexture Setup
        playActive = new Texture("NewtoniumSelectionBox.png");//Active is when pictture changes when you mouse over it.
        playPassive = new Texture("NewtoniumLaunchSymbol.png");//Passive is when mouse is NOT over the Texture
        //Quit texture setup
        quitActive = new Texture("NewtoniumSelectionBox.png");
        quitPassive = new Texture("NewtoniumTitle.png");
        //Playbutton sprite setup
        playButton = new Sprite(playPassive);
        playButton.setSize(600,60);
        playButton.setX(360);
        playButton.setY(camera.viewportHeight/2);
        //quitButton sprite setup
        quitButton = new Sprite(quitPassive);
        quitButton.setSize(600,60);
        quitButton.setX(playButton.getX());
        quitButton.setY(playButton.getY()- playButton.getHeight());        
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
	
        

        playButton.draw(game.batch);
        
        
        quitButton.draw(game.batch);
        
        
        
        //Corrects Mouse input to desired parameters.
        int targetX = Gdx.input.getX();
        int targetY = Gdx.input.getY();
        Vector3 MouseInTranslator = new Vector3(targetX,targetY,0);
        camera.unproject(MouseInTranslator);
        
        // Button Logic.
        //Play Button
	if (MouseInTranslator.x < playButton.getX() + playButton.getWidth() && MouseInTranslator.x > playButton.getX() && MouseInTranslator.y < playButton.getY() + playButton.getHeight() && MouseInTranslator.y > playButton.getY() ) {
        playButton.setTexture(playActive);//Button Detection and margins
        if(Gdx.input.isButtonJustPressed(LEFT)){
           game.setScreen(new GameScreen(game));//Button Functionnality
        }
        }
        else playButton.setTexture(playPassive);
        
        //Quit Button
        if (MouseInTranslator.x < quitButton.getX() + quitButton.getWidth() && MouseInTranslator.x > quitButton.getX() && MouseInTranslator.y < quitButton.getY() + quitButton.getHeight() && MouseInTranslator.y > quitButton.getY() ) {
        quitButton.setTexture(quitActive);//Button Detection and margins
        if(Gdx.input.isButtonPressed(LEFT)){
            Gdx.app.exit();//Button Functionallity
        }
        }
        else {quitButton.setTexture(quitPassive);
        }
            
        game.batch.end(); 
        
//        game.setScreen(new GameScreen(game));//ScreenTransition prompter. Condition it as you see fit.

       
		
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
        playPassive.dispose();
        playActive.dispose();
        quitPassive.dispose();
        quitActive.dispose();
    }
    
}
