/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.locomotor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import static com.badlogic.gdx.utils.Align.center;
import static com.badlogic.gdx.utils.Align.left;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.Locale;

/**
 *
 * @author Yoruk Ekrem(1676683)
 */
public class Hud {

    private Stage stage;
    private FitViewport stageViewport;
    Skin skin = new Skin(Gdx.files.internal("HudUIstuffPH/uiskin.json"));
    final GameScreen screen;
    
    int HealthValue = 0;
    int ScoreValue = 0;// These are placeholders because for some reason, Them numbers Ain't updatin.
    
    public Hud(SpriteBatch spriteBatch,GameScreen screen) {
        this.screen = screen;
        stageViewport = new FitViewport(800,400);
        stage = new Stage(stageViewport,spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor
        
      
       
        
        
        Table root = new Table();
        
        root.setDebug(true);
        
        root.setSize(800,400);

        //add the Buttons etc.
        
        Label HealthLabel = new Label( String.format("%03d",HealthValue),skin);
        Label ScoreLabel = new Label( String.format(Locale.getDefault(), "%6d", ScoreValue),skin);
        
        root.top();
        root.add(HealthLabel).expandX().height(screen.game.font.getCapHeight()).width(100).top().fillX().padTop(screen.game.font.getCapHeight());
        root.add(ScoreLabel).expandX().height(screen.game.font.getCapHeight()).width(100).top().fillX().padTop(screen.game.font.getCapHeight());
        
        stage.addActor(root);
    }
    public void update(float deltaTime){
     this.HealthValue = GameScreen.currentPlayer.currentHP;
     this.ScoreValue = screen.score;
    }

    public Stage getStage() { return stage; }

    public void dispose(){
        
        stage.dispose();
        
    }
}