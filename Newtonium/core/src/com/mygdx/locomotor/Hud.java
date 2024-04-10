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
//Game related parameters.
    int HealthValue;
    int ScoreValue;
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
    // These are placeholders because for some reason, Them numbers Ain't updatin.
    
    public Hud(SpriteBatch spriteBatch,GameScreen screen) {
        //Camera related stuff
        this.screen = screen;
        stageViewport = new FitViewport(800,400);
        //to bring in Stage related methods to the work.
        stage = new Stage(stageViewport,spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor
        
        Table root = new Table();//Root table is the one that takes the whole screen(Viewport).-Ey
        
        root.setDebug(true);
        
        root.setSize(800,400);

        //add the components(labels buttons etc) to the Root Table.   
        root.top();
        root.add(HealthLabel).expandX().height(screen.game.font.getCapHeight()).width(100).top().fillX().padTop(screen.game.font.getCapHeight());
        root.add(ScoreLabel).expandX().height(screen.game.font.getCapHeight()).width(100).top().fillX().padTop(screen.game.font.getCapHeight());
        
        stage.addActor(root);
    }
    public void updateText(float deltaTime){
     //this fetches the correct value from GameScreen.
     this.ScoreValue = screen.score;
     this.HealthValue = GameScreen.currentPlayer.currentHP;
     //This changes the text inside the labels to appropriate values fetched above.
     HealthLabel.setText("HP " + String.format("%03d",HealthValue));
     ScoreLabel.setText("Score "+ String.format(Locale.getDefault(), "%6d", ScoreValue));
    }

    public Stage getStage() { return stage; }

    public void dispose(){
        
        stage.dispose();
        
    }
}