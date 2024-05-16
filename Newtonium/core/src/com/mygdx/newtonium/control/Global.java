/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.newtonium.control;

import com.mygdx.newtonium.model.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.Input.Keys;


/**
 *
 * @author Thomas Cyr (2289144)
 * @author Alexis Fecteau (2060238)
 * 
 * @since 15/05/2024
 */
public class Global {
    
    //preset coordinates
    private static final Vector2 nullPosition = new Vector2(0,0); //for when calling instance position doesnt matter
    private static final Vector2 centerScreen = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
    private static final Vector2 topScreen = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight());
    //gameplay textures
    public static final Texture playerHurtPlaceholder = new Texture("NewtonHurt.png");
    public static final Texture playerPlaceholder = new Texture("Newton.png");
    
    public static final Texture evilPlaceholder = new Texture("NormalEnemy.png");
    public static final Texture evilFast = new Texture("FastEnemy.png");
    public static final Texture evilStrong = new Texture("StrongEnemy.png");
    public static final Texture evilTanky = new Texture("TankEnemy.png");
            
    public static final Texture BackgroundPlaceHolder = new Texture("border_gamescreen.png");
    public static final Texture MenuBackgroundPlaceHolder = new Texture("MenuBackgroundPlaceholder.png");
    public static final Texture mapPlaceholder = new Texture("mapCenter.png");
    public static final Texture scaleMapPlaceholder = new Texture("MapImgScale.png");
    
    public static final Texture bulletPlaceholder = new Texture("apple_projectile.png");
    public static final Texture orbitPlaceholder = new Texture("satellite_projectile.png");
    public static final Texture harmonicPlaceholder = new Texture("MetalWeight.png");
    
    //ui textures
    public static Texture cogwheel = new Texture("NewtoniumCogwheel.png");
    public static Texture info = new Texture("NewtoniumHelp.png");
    public static Texture playButton = new Texture("NewtoniumLaunchSymbol.png");
    public static Texture playButtonDown = new Texture("NewtoniumLaunchSymbolDown.png");
    public static Texture SelectionBox = new Texture("NewtoniumSelectionBox.png");
    public static Texture SelectionRectangle = new Texture("NewtoniumSelectionRectangle.png");
    public static Texture title = new Texture("NewtTitle.png");
    
    public static Skin skin = new Skin(Gdx.files.internal("HudUIstuffPH/uiskin.json"));
    
    //game objects
    public static Player currentPlayer = Global.Players.testPlayer;
     
    //player types
    public static class Players{
        
        private static final int DEFAULT_PLAYER_HP = 10;
        private static final float DEFAULT_PLAYER_SPEED = 300;
        
        //template objects for spawn() copies
        static Player testPlayer = new Player(1, DEFAULT_PLAYER_HP, DEFAULT_PLAYER_SPEED, centerScreen, playerPlaceholder);
        
    }
    
    //enemy types
    public static class Enemies{
        private static final double DEFAULT_LEVEL_SCALING = 1;
        private static final int DEFAULT_ENEMY_HP = 200;
        private static final float DEFAULT_ENEMY_SPEED = 100;
        private static final int DEFAULT_ENEMY_STRENGTH = 1;
        private static final int DEFAULT_XP_VALUE = 10;
        
        //template objects for spawn() copies
        public static final Enemy defaultEnemy = new Enemy(
                                    DEFAULT_LEVEL_SCALING, 
                                    DEFAULT_ENEMY_HP, 
                                    DEFAULT_ENEMY_SPEED, 
                                    DEFAULT_ENEMY_STRENGTH, 
                                    DEFAULT_XP_VALUE, 
                                    nullPosition,
                                    evilPlaceholder);
        
        public static final Enemy fastEnemy = new Enemy(
                                    DEFAULT_LEVEL_SCALING, 
                                    DEFAULT_ENEMY_HP, 
                                    DEFAULT_ENEMY_SPEED*1.5f, 
                                    DEFAULT_ENEMY_STRENGTH, 
                                    DEFAULT_XP_VALUE, 
                                    nullPosition,
                                    evilFast);
        
        public static final Enemy strongEnemy = new Enemy(
                                    DEFAULT_LEVEL_SCALING, 
                                    DEFAULT_ENEMY_HP, 
                                    DEFAULT_ENEMY_SPEED, 
                                    DEFAULT_ENEMY_STRENGTH*2, 
                                    DEFAULT_XP_VALUE*2, 
                                    nullPosition,
                                    evilStrong);
        
        public static final Enemy tankyEnemy = new Enemy(
                                    DEFAULT_LEVEL_SCALING, 
                                    DEFAULT_ENEMY_HP*5, 
                                    DEFAULT_ENEMY_SPEED/2, 
                                    DEFAULT_ENEMY_STRENGTH, 
                                    DEFAULT_XP_VALUE*3, 
                                    nullPosition,
                                    evilTanky);
        
        //Array of enemy types that can appear in-game
        public static Enemy[] enemyRotation = new Enemy[]{defaultEnemy, fastEnemy, strongEnemy, tankyEnemy};
    }
    
    //projectile types
    public static class Projectiles {
        
        private static final double DEFAULT_PROJ_DECAYTIME = 3; //seconds
        private static final int DEFAULT_PROJ_PIERCE = 1; //enemy contacts necessary for early despawn
        private static final float DEFAULT_PROJ_SPEED = 150; //pixels per second
        
        //template objects for spawn() copies
        public static Projectile homingApple = new HomingProjectile(2,0.5f,15, 1, DEFAULT_PROJ_DECAYTIME, DEFAULT_PROJ_PIERCE, DEFAULT_PROJ_SPEED, centerScreen, bulletPlaceholder);
        public static Projectile satellite = new OrbitProjectile(10, 4, 3, 10, orbitPlaceholder);
        public static Projectile springBlock = new HarmonicProjectile(10, 10, 3.5f, 10, harmonicPlaceholder);
        public static Projectile fallingApple = new FallingProjectile(3,15,bulletPlaceholder);
    }
    
    //prebuilt Item objects (NOT templates, these get used as themselves)
    public static class Items {
        
        //debug Items
        public static Item homingBulletTester = new TestItem( Keys.E, 1, 0.25f, Projectiles.homingApple);
        public static Item satelliteTester = new TestItem(Keys.SPACE, 1, 0.25f, Projectiles.satellite);
        public static Item springBlockTester = new TestItem(Keys.Q, 1, 0.25f, Projectiles.springBlock);
        public static Item fallTester = new TestItem(Keys.X,1,0.2f,Projectiles.fallingApple);
        //gameplay Items
        public static Item appleFling = new ProximityItem(1, 0.35f, Projectiles.homingApple);
        public static Item appleBurst = new BurstItem(1, 8, Projectiles.homingApple, 6);
        
        public static Item satelliteFling = new AutoItem(1, 3.5f, Projectiles.satellite);
        public static Item satelliteBurst = new BurstItem(1, 9, Projectiles.satellite, 3);
        
        public static Item springBlockFling = new AutoItem(1, 11, Projectiles.springBlock);
        
        //public static Item meteorShower = new BurstItem(1,1f,Projectiles.meteor,25); //unfinished
    }
    
 
    
}
