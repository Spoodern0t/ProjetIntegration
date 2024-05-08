/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.control;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * here we make overlays to add on top of the Hud.-Ey
 * @author Yoruk Ekrem(1676683) there are nested classes or simply the sake of organisation,This CAN BE DONE BETTER put I prefer it done at the moment.
 */
public class GameOverlay {
    public static TextButton Pbutton = new TextButton("Pause",Global.skin);// pauseButton Convert To an image button with NewtoniumLaunchSymbol.png When applicable.
    public static TextButton Mbutton = new TextButton("Back to mainmenu",Global.skin);//menuButton
    public static TextButton Cbutton = new TextButton("Continue",Global.skin);//ContinueButton
    public static ImageButton weaponSelectButton;//This is for when you want to select a weapon to upgrade for the updrage window.Make an arraylist of it idk.
    
    public static Label FinalScoreLabel = new Label("",Global.skin);
    public static Label FinalTimeLabel = new Label("",Global.skin);
    
    public static class G_E_Overlay{//Game End Overlay
        public Window GameEndWindow;
        private final Hud hud;
        private final GameController game;
        private final GameScreen screen;
        public G_E_Overlay(Hud hud,GameScreen screen,GameController game){
            this.hud = hud;
            this.game = game;
            this.screen = screen;
            //GameEndWindow Initial Configurations
            GameEndWindow = new Window("Game Over",Global.skin);
            GameEndWindow.setPosition(hud.getStage().getWidth()/2-(GameEndWindow.getWidth()/2),hud.getStage().getHeight()/2-(GameEndWindow.getHeight()/2));//To centralise position of the screen.
            GameEndWindow.setMovable(false);
            GameEndWindow.setVisible(false);
            
            //Window content Setup
                //MainMenu Button Setup
            Mbutton = new TextButton("Back to MainMenu.",Global.skin);
            Mbutton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor){
                        game.setScreen(new MainMenuScreen(game));//Button Functionnality
                        screen.dispose();
                        GameEndWindow.remove();
                    }
            });
                //FinalScoreLabel setup. It will take info from hud or GameScreen when player dies.
            FinalScoreLabel.setText("Final Score: "+hud.ScoreLabel.getText());
                //Final time Label to see what time game ended
            FinalTimeLabel.setText("Final Time: "+hud.minutes+":"+hud.seconds);
            //Content Organisation
            GameEndWindow.add(Mbutton).center().fillX();
            GameEndWindow.row();
            GameEndWindow.add(FinalScoreLabel);
            GameEndWindow.row();
            GameEndWindow.add(FinalTimeLabel);
            
        }
        public void GendOccur(){
            if(GameScreen.isOver){
            GoverUp();  
            }
        }
        public void GoverUp(){
            GameEndWindow.setVisible(true);
            TakeEndInfo();//So it can update the info One last time.
        }
        public void TakeEndInfo(){
            //this is in case It takes the info too late.
            FinalScoreLabel.setText("Final Score: "+hud.ScoreValue);
            FinalTimeLabel.setText("Final Time: "+String.format("%.00f:%.00f", hud.minutes, hud.seconds));
        }
        
    }
    
    
    public static class P_Menu{//PauseMenu
        
        private final GameScreen screen;
        private final GameController game;
        private final Hud hud;
        public Table pausebuttontable;
        public Window PauseMWindow = new Window("Paused!",Global.skin);
        
        public P_Menu(Hud hud,GameController game,GameScreen screen){
            this.game = screen.game;
            this.hud = hud;
            this.screen = screen;
            //Initial Window Configuration
            PauseMWindow.setPosition(hud.getStage().getWidth()/2-(PauseMWindow.getWidth()/2),hud.getStage().getHeight()/2-(PauseMWindow.getHeight()/2));
            PauseMWindow.setVisible(false);
            //Set up Window content
                //MainMenu Button
            Mbutton = new TextButton("Back to MainMenu.",Global.skin);
            Mbutton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor){
                        game.setScreen(new MainMenuScreen(game));//Button Functionnality
                        screen.dispose();
                        PauseMWindow.remove();
                        //GameScreen.despawnList.add(Global.currentPlayer); A regler plus tard
                    }
            });
                //Continue Button
            Cbutton = new TextButton("Continue",Global.skin);
            Cbutton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor){
                        GameScreen.isPaused = false;
                        Unpause();
                    }
            });
            Pbutton = new TextButton("Pause",Global.skin);
            Pbutton.addListener(new ChangeListener(){//PauseButton
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor){
                        GameScreen.isPaused = true;
                        Pause();//Button Functionnality
                    }
            });
            
            //Organising the content in the Window.
            PauseMWindow.add(Cbutton);
            PauseMWindow.row();
            PauseMWindow.add(Mbutton);
            
            pausebuttontable = new Table();
            pausebuttontable.setDebug(true);
            pausebuttontable.add(Pbutton).bottom();
            pausebuttontable.setSize(Pbutton.getWidth(),Pbutton.getHeight());
            pausebuttontable.setPosition(800-(pausebuttontable.getWidth()),0);
        } 
        public void Pause(){//These can be optimised further but Im keeping it like this for Reliability's sake. -EY
            if(GameScreen.isPaused ){PauseMWindow.setVisible(true);}
            
            
        }
        public void Unpause(){
            if(GameScreen.isPaused == false){PauseMWindow.setVisible(false);}
        }
    }
    
    
    public static class U_MENU{//UpgradeMenu
        public Window LevelUpMenu = new Window("Level up!",Global.skin);
        private final Hud hud;
        private final GameController game;
        private final GameScreen screen;
        public U_MENU(Hud hud,GameController game,GameScreen screen){
            this.hud = hud;
            this.game = game;
            this.screen = screen;
            
            
            
                //Content Organisation
                LevelUpMenu.add();
            
        }
    }
    
}
