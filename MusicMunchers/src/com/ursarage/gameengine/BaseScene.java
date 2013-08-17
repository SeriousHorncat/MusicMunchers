package com.ursarage.gameengine;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.ursarage.musicmunchers.MusicMunchersGame;


public abstract class BaseScene implements Screen, InputProcessor {
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
  protected ResourceManager resourcesManager;
  protected MusicMunchersGame mGame;
  public OrthographicCamera camera;
    
    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------
    
    public BaseScene()
    {
      this.resourcesManager = ResourceManager.getInstance();
      this.mGame = this.resourcesManager.mGame;

      camera = new OrthographicCamera();
      camera.setToOrtho(false, 800, 480);

      createScene();
    }
    
    //---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------
    
    public abstract void createScene();
    
//    public abstract void onBackKeyPressed();
//
//    public abstract void onDPadPressed(int keyCode, KeyEvent event);
//
//    public abstract void onButtonPadPressed(int keyCode, KeyEvent event);
    
    public abstract SceneManager.SceneType getSceneType();
}
