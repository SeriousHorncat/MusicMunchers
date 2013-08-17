package com.ursarage.musicmunchers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.ursarage.gameengine.BaseScene;
import com.ursarage.gameengine.SceneManager;

/**
 * Created by Angelina on 7/20/13.
 */
public class SplashScreen extends BaseScene {

  private Rectangle ursaRageLogo;

  @Override
  public void createScene() {
    ursaRageLogo = new Rectangle();
    ursaRageLogo.x = 800 / 2 - 512 / 2;
    ursaRageLogo.y = (400 / 2 - 512 / 2) + 20 ;
  }

  @Override
  public SceneManager.SceneType getSceneType() {
    return SceneManager.SceneType.SCENE_SPLASH;
  }

  @Override
  public void render(float v) {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    camera.update();

    mGame.batch.setProjectionMatrix(camera.combined);

    mGame.batch.begin();
    mGame.batch.draw(resourcesManager.ursaRageLogoimage, ursaRageLogo.x, ursaRageLogo.y);
    mGame.batch.end();
  }

  @Override
  public void resize(int i, int i2) {
  }

  @Override
  public void show() {
    Timer.schedule(new Timer.Task() {
    @Override
    public void run() {
        SceneManager.getInstance().loadMenuScene();
      }
    }, 2);
  }

  @Override
  public void hide() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void dispose() {
    resourcesManager.unloadSplashScreen();
  }

  @Override
  public boolean keyDown(int i) {
    return false;
  }

  @Override
  public boolean keyUp(int i) {
    return false;
  }

  @Override
  public boolean keyTyped(char c) {
    return false;
  }

  @Override
  public boolean touchDown(int i, int i2, int i3, int i4) {
    return false;
  }

  @Override
  public boolean touchUp(int i, int i2, int i3, int i4) {
    return false;
  }

  @Override
  public boolean touchDragged(int i, int i2, int i3) {
    return false;
  }

  @Override
  public boolean mouseMoved(int i, int i2) {
    return false;
  }

  @Override
  public boolean scrolled(int i) {
    return false;
  }
}
