package com.ursarage.musicmunchers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ursarage.gameengine.ResourceManager;
import com.ursarage.gameengine.SceneManager;

public class MusicMunchersGame extends Game implements InputProcessor {
  SpriteBatch batch;
  BitmapFont font;

  ResourceManager resourceManager;

	@Override
	public void create() {
    font = new BitmapFont(Gdx.files.internal("font/dosgreen.txt"), false);
    batch = new SpriteBatch();

    resourceManager = resourceManager.getInstance();
    resourceManager.prepareManager(this);
    resourceManager.prepareSharedResources();

    ScaleRepository repository = new ScaleRepository();

    SceneManager.getInstance().setScene(SceneManager.SceneType.SCENE_SPLASH);
    Gdx.input.setInputProcessor(this);
  }


	@Override
	public void dispose() {
    batch.dispose();
    font.dispose();
    ResourceManager.getInstance().disposeSharedresources();
	}

	@Override
	public void render() {		
		super.render();
  }


	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

  @Override
  public boolean keyDown(int i) {
    return false;
  }

  @Override
  public boolean keyUp(int i) {
    return SceneManager.getInstance().getCurrentScene().keyUp(i);
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
