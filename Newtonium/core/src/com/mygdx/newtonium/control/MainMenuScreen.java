/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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
    
    //Initialising Stage related methods
    Image GameLogo;
    Drawable Logo;
    Stage stage;
    Button playButton;
    Button HowtoButton;
    Button QuitButton;
    Button SettingsButton;
    //Tables
    Table Center;
    ScreenViewport viewport;
    public MainMenuScreen(final GameController game) {
        this.game = game;

        //Stage related Setups
        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        
        Gdx.input.setInputProcessor(stage);//This is for Any input related things in Stage related crap.
        
        
       
        //Table setup.
            
 
            Center = new Table();//The thing that will house buttons.
            Center.setFillParent(true);
            Center.center();
            Center.setDebug(false);
                //creating and adding Relevant buttons to Center Table 9logic included.
                //GameLogo
                GameLogo = new Image();
                Logo = new TextureRegionDrawable(new TextureRegion(Global.title));
                GameLogo.setDrawable(Logo);
                Center.add(GameLogo).top();
                Center.row();
                //PlayButton
                playButton = new TextButton("play",Global.skin);
                Center.add(playButton).fill();
                playButton.addListener(new ChangeListener(){
                    @Override
                    public void changed(ChangeEvent event, Actor actor){
                        game.setScreen(new GameScreen(game));//Button Functionnality
                        if (GameScreen.isPaused){
                            GameScreen.isPaused = !GameScreen.isPaused;
                            }
                        
                    }
                });
                Center.row();//Changes Row, Adds to Below.
                //HowtoplayButton
                HowtoButton = new TextButton("How to play",Global.skin);
                Center.add(HowtoButton).fill().padTop(10);
                HowtoButton.addListener(new ChangeListener(){
                    @Override
                    public void changed(ChangeEvent event, Actor actor){
                        game.setScreen(new TutorialScreen(game));//Button Functionnality
                    }
                });
                Center.row();
                
                
                
                QuitButton = new TextButton("Quit",Global.skin);
                Center.add(QuitButton).fill().padTop(10);
                QuitButton.addListener(new ChangeListener(){
                   @Override
                   public void changed(ChangeEvent event, Actor actor){
                     Gdx.app.exit();//Button Functionallity
                   }
                });
                stage.addActor(Center);

    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float deltaTime) {
            ScreenUtils.clear(0,0,0,1);
        
            game.batch.setProjectionMatrix(stage.getCamera().combined);
            stage.act(deltaTime);
            stage.draw();         	
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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

        stage.dispose();
        Global.skin.dispose();
    }
    
}
