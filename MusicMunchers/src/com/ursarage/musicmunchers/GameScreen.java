package com.ursarage.musicmunchers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.ursarage.gameengine.BaseScene;
import com.ursarage.gameengine.Label;
import com.ursarage.gameengine.SceneManager;
import com.ursarage.toolkit.Point;
import com.ursarage.toolkit.Vector2;

/**
 * Created by Angelina on 7/18/13.
 */
public class GameScreen extends BaseScene {
  Vector3 touchPosition;
  Rectangle bucket;

  Array<Rectangle> raindrops;
  long lastDropTime;

  private Label mTitleText;
  private Label mScoreText;

  private String mScaleName;

  private GameBoard mBoard;
  public Sprite mMuncher;

  final public int BOARD_PADDING_PERCENT = 10;

  public GameScreen() {
    Gdx.app.log("touch", "Creating game mLevel scene Game");

    mScaleName = new String("C");

    mMuncher = new Sprite(resourcesManager.musicMuncherImage, 64  , 64);

    final Vector2 padding = scenePadding();
    mScoreText = new Label( "Score: " + 0, new Point( padding.X, (int)camera.viewportHeight - (padding.Y/2)) );

    final Point headerCenterLocation =  headerCenterLocation();
    mTitleText = new Label( mScaleName + " Scale ", new Point( headerCenterLocation.X -40, headerCenterLocation.Y-15 ));

    this.mBoard = new GameBoard( this, mScaleName );
    this.mBoard.setBoardPosition( padding.X, padding.Y );

    Gdx.app.log("touch", "made gameboard");
  }

  public Vector2 scenePadding() {
    return new Vector2( (int)camera.viewportWidth / BOARD_PADDING_PERCENT,
        (int)camera.viewportHeight / BOARD_PADDING_PERCENT );
  }

  public void levelComplete() {
//    SceneManager.getInstance().loadWinScene(engine);
  }

  public void updateScoreDisplay() {
    this.mScoreText.Text = "Score: " + this.mBoard.score();
  }

  private Point headerCenterLocation() {
    return new Point( (int)camera.viewportWidth/2, (int)camera.viewportHeight - (scenePadding().Y /2) );
  }

  public Vector2 allocatedBoardArea() {
    Vector2 scenePadding = scenePadding();
    Point headerCenterPoint = headerCenterLocation();
    return new Vector2( (int)camera.viewportWidth -(scenePadding.X *2),
        headerCenterPoint.Y - (scenePadding.Y /2));
  }

  @Override
  public void createScene() {
  }

  @Override
  public SceneManager.SceneType getSceneType() {
    return SceneManager.SceneType.SCENE_GAME;
  }

  @Override
  public void render(float v) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    camera.update();
    mGame.batch.setProjectionMatrix(camera.combined);

    mGame.batch.begin();
    this.mBoard.draw(mGame.batch, mGame.font);
    this.mMuncher.draw(mGame.batch);
    this.mTitleText.draw(mGame.batch, mGame.font);
    this.mScoreText.draw(mGame.batch, mGame.font);
    mGame.batch.end();
//    if(Gdx.input.isTouched()) {
//      Vector3 touchPos = new Vector3();
//      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//      camera.unproject(touchPos);
////      bucket.x = touchPos.x - 64 / 2;
//    }
//    if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
//    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
  }

  @Override
  public void resize(int i, int i2) {

  }

  @Override
  public void show() {
   // resourcesManager.rainMusic.play();
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
    resourcesManager.unloadGameResources();
  }

  @Override
  public boolean keyDown(int i) {
    return false;
  }

  @Override
  public boolean keyUp(int i) {
    return mBoard.onKeyUp(i);
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
