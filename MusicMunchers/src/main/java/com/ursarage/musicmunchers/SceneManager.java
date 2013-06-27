package com.ursarage.musicmunchers;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import android.util.Log;

public class SceneManager {
	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene gameLevelScene;
	private BaseScene loadingScene;
	private BaseScene winScene;
	
	private static final SceneManager INSTANCE = new SceneManager();
	
	private SceneType currentSceneType = SceneType.SCENE_SPLASH;
	private BaseScene currentScene;
	
	private Engine engine = ResourceManager.getInstance().engine;
	
	public enum SceneType
	{
		SCENE_SPLASH,
		SCENE_MENU,
		SCENE_GAME,
		SCENE_LOADING,
		SCENE_WIN
	}
	
	public void setScene(BaseScene scene)
    {
        engine.setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
    }
	
	public void setScene(SceneType sceneType)
    {
        switch (sceneType)
        {
            case SCENE_MENU:
                setScene(menuScene);
                break;
            case SCENE_GAME:
                setScene(gameLevelScene);
                break;
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_LOADING:
                setScene(loadingScene);
                break;
            case SCENE_WIN:
            	setScene(winScene);
            	break;
            default:
                break;
        }
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static SceneManager getInstance()
    {
        return INSTANCE;
    }
    
    public SceneType getCurrentSceneType()
    {
        return currentSceneType;
    }
    
    public BaseScene getCurrentScene()
    {
        return currentScene;
    }
    
    public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
    {
        ResourceManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }
    
    private void disposeSplashScene()
    {
        ResourceManager.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }
    
    public void createMenuScene()
    {
        ResourceManager.getInstance().loadMenuResources();
        menuScene = new MainMenuScene();
        setScene(menuScene);
        disposeSplashScene();
    }
    
    public void loadGameScene(final Engine mEngine)
    {
		Log.w("touch", "menuItemClicked Loading Game");
		
		ResourceManager.getInstance().loadGameResources();
        gameLevelScene = new GameLevelScene();
        setScene(gameLevelScene);
    }
    
    public void loadWinScene(final Engine mEngine)
    {
    	Log.w("touch", "WIN SCENE Game");
    	winScene = new WinScene();
    	setScene(winScene);
    }
    
}
