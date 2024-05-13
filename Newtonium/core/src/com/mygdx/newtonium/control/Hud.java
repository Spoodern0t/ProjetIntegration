/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static com.badlogic.gdx.utils.Align.center;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.newtonium.control.GameOverlay;
import java.util.Locale;

/**
 *
 * @author Yoruk Ekrem(1676683)
 */
public  class Hud {
//Game related parameters.
    int HealthValue;
    int ScoreValue;
    int levelValue = Global.currentPlayer.level;
    
    float minutes = 0f;
    float seconds = 0f;
    float GameTime = 0l;
//Stage related methods turned out to be the more viable option for me this time.-EY
    private Stage hudstage;
    private FitViewport stageViewport;
    private Stage overlaystage;
    private Stage PbuttonStage;//This is created Solely for the pause Button,
    
//Calling additionnal components for the Hud.
    GameOverlay.G_E_Overlay goverlay;
    GameOverlay.P_Menu pmenu;
//Calling GameScreen to take in game attributes.    
    final GameScreen screen;
    final GameController game;
//Creating Labels
    Label HealthLabel = new Label("HP " + String.format("%03d",HealthValue),Global.skin);
    Label ScoreLabel = new Label("Score "+ String.format(Locale.getDefault(), "%6d", ScoreValue),Global.skin);
    Label LevelLabel = new Label("Level" + String.format(Locale.getDefault(), "%2d",levelValue),Global.skin);
    Label TimeLabel = new Label(String.format("%.0fm%.0fs", minutes, seconds),Global.skin);
//Hp and EXP bar
    ProgressBar Hpbar = new ProgressBar(0,Global.currentPlayer.maxHP,1,false,Global.skin);
    ProgressBar Expbar = new ProgressBar(0,Global.currentPlayer.levelThreshold,1,false,Global.skin);
    // These are placeholders because for some reason, Them numbers Ain't updatin.
    
    public Hud(SpriteBatch spriteBatch,GameScreen screen,GameController game) {
        //Camera related stuff
        this.game = game;
        this.screen = screen;
        
        stageViewport = new FitViewport(800,400);
        //to bring in Stage related methods to the work.
        hudstage = new Stage(stageViewport,spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor
        
        
        
        Table root = new Table();//Root table is the one that takes the whole screen(Viewport).-Ey
        
        root.setDebug(false);//Cause of the thin red lines.
        
        root.setSize(800,395);

        //add the components(labels buttons etc) to the Root Table.   
        root.top();
        root.add(Hpbar).left();
        root.add(HealthLabel).left().height(game.font.getCapHeight()).top();
        root.add(TimeLabel).center().expandX().height(game.font.getCapHeight()).width(100).align(center).top().left().padLeft(50);
        root.add(ScoreLabel).right().expandX().height(game.font.getCapHeight()).width(100).top();
        root.row();
        root.add(Expbar).left();
        root.add(LevelLabel).left().expandX().height(screen.game.font.getCapHeight()).width(100);
        hudstage.addActor(root);
        
        //Overlay related things(Pause,upgrade and gameover related things) Input enabling included
        overlaystage = new Stage(hudstage.getViewport(),hudstage.getBatch());
        
            //Game end Overlay Related Setup
            goverlay = new GameOverlay.G_E_Overlay(this,screen,game);
            //Game Pause Overlay Related Setup
            pmenu = new GameOverlay.P_Menu(this,game,screen);
        overlaystage.addActor(goverlay.GameEndWindow);
        
        overlaystage.addActor(pmenu.PauseMWindow);
        //pauseButtonStage
        PbuttonStage = new Stage(stageViewport,spriteBatch);//Another Stage just for the sake of an extra button -EY
        
        PbuttonStage.addActor(pmenu.pausebuttontable);
        
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(overlaystage);//primary input processor
        multiplexer.addProcessor(PbuttonStage);//Secondary input processor
        Gdx.input.setInputProcessor(multiplexer);//in case you ask for reasons, its because of differing time systems(pause time and regular time). Optimise further if you want to.
    }
    public void updateHud(float deltaTime){
     //this fetches the correct value from GameScreen.
     this.ScoreValue = screen.score;
     this.HealthValue = Global.currentPlayer.currentHP;
     this.levelValue = Global.currentPlayer.level;
     //This changes the text inside the labels to appropriate values fetched above.
     HealthLabel.setText("HP " + String.format("%03d",HealthValue));
     ScoreLabel.setText("Score "+ String.format(Locale.getDefault(), "%6d", ScoreValue));
     LevelLabel.setText("Level "+ levelValue);
     
     //This is to update the values in the Bars.
     Hpbar.setValue(HealthValue);
     Expbar.setValue(Global.currentPlayer.currentExp);
     Expbar.setRange(0,Global.currentPlayer.levelThreshold );
     //Time Related things.
     
     GameTime += deltaTime;
     minutes = (float)Math.floor(GameTime / 59.0f);
     seconds = GameTime - minutes * 59.0f;
     
     
     TimeLabel.setText(String.format("%.00f:%.00f", minutes, seconds));
    }
    
    public Stage getStage() { return hudstage; }//Just the regular Hud. only Displays.
    public Stage getOverlayStage() { return overlaystage; }//This one is an input processor.
    public Stage getPbuttonStage() { return PbuttonStage; }//This one too.

    
    public void dispose(){
        getStage().dispose();
        getOverlayStage().dispose();
        getPbuttonStage().dispose();
    }
}