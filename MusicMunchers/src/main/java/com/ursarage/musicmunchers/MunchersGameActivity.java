package com.ursarage.musicmunchers;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ursarage.ouyamediator.OuyaMediator;

import tv.ouya.console.api.OuyaController;

/**
 * (c) 2013 Ursa Rage
 *
 * @author SeriousHorncat
 */
public class MunchersGameActivity extends BaseGameActivity implements  MusicMuncherDefines {
    // ===========================================================
    // Constants
    // ===========================================================

    protected static final int CAMERA_WIDTH = 960;
    protected static final int CAMERA_HEIGHT = 540;

    // ===========================================================
    // Fields
    // ===========================================================

    private Camera mCamera;

    private ScaleRepository repository = new ScaleRepository();
    private ResourceManager resourcesManager;
    private OuyaMediator mOuyaMediator;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mOuyaMediator = new OuyaMediator(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public Engine onCreateEngine(EngineOptions pEngineOptions) {
        return new LimitedFPSEngine(pEngineOptions, 60);
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        Toast.makeText(this, "Touch the adjacent squares to move muncher.", Toast.LENGTH_LONG).show();

        this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback  pOnCreateResourcesCallback) throws IOException {
        ResourceManager.prepareManager(mEngine, this,mCamera, getVertexBufferObjectManager(), mOuyaMediator);
        resourcesManager = ResourceManager.getInstance();
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);

        //this.mEngine.registerUpdateHandler(new FPSLogger());
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback ) throws IOException {
        mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback()
        {
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                SceneManager.getInstance().createMenuScene();
            }
        }));
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public void onResumeGame() {
        super.onResumeGame();
    }

    @Override
    public void onPauseGame() {
        super.onPauseGame();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (this.isGameLoaded())
        {
            System.exit(0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
       // mOuyaMediator.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
        }
        else if( OuyaMediator.isOuyaControllerDPad(keyCode) )
        {
            SceneManager.getInstance().getCurrentScene().onDPadPressed(keyCode, event);
        }
        else if( OuyaMediator.isOuyaControllerButtonPad(keyCode) ) {
            SceneManager.getInstance().getCurrentScene().onButtonPadPressed(keyCode, event);
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
       // mOuyaMediator.onKeyUp(keyCode, event);
        return false;
    }


}