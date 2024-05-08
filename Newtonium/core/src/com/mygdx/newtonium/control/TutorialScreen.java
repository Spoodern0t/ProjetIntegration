/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *
 * @author Thomas Cyr
 */
public class TutorialScreen implements Screen {
    
    final GameController game;
    Stage stage;
    Button backButton;
    Table mainTable;
    Viewport viewport;
    
    public TutorialScreen(final GameController game) {
        this.game = game;
        
        //Setting the stage
        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        
        //table setup
        mainTable = new Table();//The thing that will house buttons.
        mainTable.setFillParent(true);
        mainTable.center();
        mainTable.setDebug(false);
        
        //buttons go here, options selections will go here in a later version, just a back button for now
        backButton = new TextButton("Go Back", Global.skin);
        mainTable.add(backButton).fill();
        backButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor){
                        game.setScreen(new MainMenuScreen(game));//Button Functionnality
                    }
        });
        
        stage.addActor(mainTable);
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
