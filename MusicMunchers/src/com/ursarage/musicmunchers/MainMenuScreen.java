package com.ursarage.musicmunchers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.ursarage.gameengine.BaseScene;
import com.ursarage.gameengine.SceneManager;

/**
 * Created by Angelina on 7/18/13.
 */
public class MainMenuScreen extends BaseScene {

  public MainMenuScreen() {
  }

  @Override
  public void createScene() {
  }

  @Override
  public void render(float v) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    camera.update();
    mGame.batch.setProjectionMatrix(camera.combined);

    mGame.batch.begin();
    mGame.font.draw(mGame.batch, "MUSIC MUNCHERS ", 100, 150);
    mGame.font.draw(mGame.batch, "PLAY", 100, 100);
    mGame.batch.end();

    if (Gdx.input.isTouched()) {
        SceneManager.getInstance().loadGameScene();
        dispose();
    }
  }

  @Override
  public void resize(int i, int i2) {

  }

  @Override
  public void show() {

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

  }

  @Override
  public SceneManager.SceneType getSceneType() {
    return SceneManager.SceneType.SCENE_MENU;
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
