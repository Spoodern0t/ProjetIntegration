/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
public class GoverOverlay {
    final GameScreen screen;
    final GameController game;
    //Stage related boilerplate
    FitViewport GoverView;
    Stage GoverStage;
    //Components
    Table GoverTable;
    TextButton Mbutton;
    Label ScoreLabel;
    Window goverW;
    public GoverOverlay(SpriteBatch batch,GameScreen screen,GameController game){
        //Calling Likely necessary Classes or Entities.
        this.screen = screen;
        this.game = game;
        //Stage related.
        GoverView = new FitViewport(600,300);
        GoverView.setScreenSize(600, 300);//To make it a window that is smaller than The GameScreen and hud.
        GoverStage = new Stage(GoverView,batch);
        
        
        GoverTable = new Table().center();
        GoverTable.setFillParent(true);
        Mbutton= new TextButton("back to main menu",Global.skin);
        GoverTable.add(Mbutton).expandY();
        Mbutton.center();
        Mbutton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor){
                        game.setScreen(new MainMenuScreen(game));//Button Functionnality
                    }
        });
        GoverTable.row();
        ScoreLabel =  new Label(String.valueOf(screen.hud.ScoreValue),Global.skin);
        
        GoverTable.add(ScoreLabel).center();
        GoverStage.addActor(GoverTable);
        
    }
    
    public void dispose(){
        GoverStage.dispose();
    }    
}
