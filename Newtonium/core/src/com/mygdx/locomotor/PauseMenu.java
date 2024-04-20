/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 *
 * @author Yoruk Ekrem
 */
public class PauseMenu {
     final GameScreen screen;
    final GameController game;
    //Stage related boilerplate
    FitViewport PauseViewport;
    Stage PauseStage;
    
    Skin skin;
    //Components
    Table PauseMtable;
    TextButton Mbutton;
    Label ScoreLabel;
    Window PauseW;
    public PauseMenu(SpriteBatch batch,GameScreen screen,GameController game){
        //Calling Likely necessary Classes or Entities.
        this.screen = screen;
        this.game = game;
        //Stage related.
        skin = new Skin(Gdx.files.internal("HudUIstuffPH/uiskin.json"));
        PauseViewport = new FitViewport(600,300);
        PauseViewport.setScreenSize(600, 300);//To make it a window that is smaller than The GameScreen and hud.
        PauseStage = new Stage(PauseViewport,batch);
        
        
        PauseMtable = new Table().center();
        PauseMtable.setFillParent(true);
        Mbutton= new TextButton("back to main menu",skin);
        PauseMtable.add(Mbutton).expandY();
        Mbutton.center();
        Mbutton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor){
                        game.setScreen(new MainMenuScreen(game));//Button Functionnality
                    }
        });
        PauseMtable.row();
        ScoreLabel =  new Label(String.valueOf(screen.hud.ScoreValue),skin);
        PauseMtable.add(ScoreLabel);
        PauseStage.addActor(PauseMtable);
    }
}
