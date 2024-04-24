/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static com.badlogic.gdx.utils.Align.center;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.Locale;

/**
 *
 * @author Yoruk Ekrem(1676683)
 */
public class Hud {
//Game related parameters.
    int HealthValue;
    int ScoreValue;
    int levelValue = Global.currentPlayer.level;
    long StartTime = System.currentTimeMillis();
    float minutes = 0f;
    float seconds = 0f;
    float GameTime = 0l;
//Stage related methods turned out to be the more viable option for me this time.-EY
    private Stage stage;
    private FitViewport stageViewport;
//Aesthetics.
    Skin skin = new Skin(Gdx.files.internal("HudUIstuffPH/uiskin.json"));//Called in a Text uiskinpack from Libgdx Wiki. Can change anytime.Ask when wanting to change looks.
//Calling GameScreen to take in game attributes.    
    final GameScreen screen;
    
//Creating Labels
    Label HealthLabel = new Label("HP " + String.format("%03d",HealthValue),skin);
    Label ScoreLabel = new Label("Score "+ String.format(Locale.getDefault(), "%6d", ScoreValue),skin);
    Label LevelLabel = new Label("Level" + String.format(Locale.getDefault(), "%2d",levelValue),skin);
    Label TimeLabel = new Label(String.format("%.0fm%.0fs", minutes, seconds),skin);
//Hp and EXP bar
    ProgressBar Hpbar = new ProgressBar(0,Global.currentPlayer.maxHP,1,false,skin);
    ProgressBar Expbar = new ProgressBar(0,Global.currentPlayer.levelThreshold,1,false,skin);
    // These are placeholders because for some reason, Them numbers Ain't updatin.
    
    public Hud(SpriteBatch spriteBatch,GameScreen screen) {
        //Camera related stuff
        this.screen = screen;
        stageViewport = new FitViewport(800,400);
        //to bring in Stage related methods to the work.
        stage = new Stage(stageViewport,spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor
        
        Table root = new Table();//Root table is the one that takes the whole screen(Viewport).-Ey
        
        root.setDebug(false);//Cause of the thin red lines.
        
        root.setSize(800,395);

        //add the components(labels buttons etc) to the Root Table.   
        root.top();
        root.add(Hpbar).left();
        root.add(HealthLabel).left().height(screen.game.font.getCapHeight()).top();
        root.add(TimeLabel).center().expandX().height(screen.game.font.getCapHeight()).width(100).align(center).top().left().padLeft(50);
        root.add(ScoreLabel).right().expandX().height(screen.game.font.getCapHeight()).width(100).top();
        root.row();
        root.add(Expbar).left();
        root.add(LevelLabel).left().expandX().height(screen.game.font.getCapHeight()).width(100);
        stage.addActor(root);
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
     //Time Related things.
     
     GameTime += deltaTime;
     minutes = (float)Math.floor(GameTime / 60.0f);
     seconds = GameTime - minutes * 60.0f;
     
     
     TimeLabel.setText(String.format("%.00f:%.00f", minutes, seconds));
    }

    public Stage getStage() { return stage; }

    public void dispose(){
        
        stage.dispose();
        
    }
}