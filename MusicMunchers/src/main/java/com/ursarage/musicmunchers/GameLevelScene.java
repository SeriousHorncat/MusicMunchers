package com.ursarage.musicmunchers;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.util.Log;

import com.ursarage.musicmunchers.SceneManager.SceneType;

public class GameLevelScene extends BaseScene {

	private HUD mHud;
	
	private Text mScoreText;
	private Text mTitleText;
	
	private String mScaleName;
	
	private Gameboard board;
	public Sprite mMuncher;

	
	@Override
	public void createScene() {
		
		Log.w("touch", "Creating game leve scene Game");
		
		mScaleName = new String("C");
		
		Log.w("touch", "Made Scale name");

		this.mHud = new HUD();
		
		Log.w("touch", "allocated HUD");
		this.camera.setHUD(this.mHud);
		Log.w("touch", "set HUD to the camera");

		mMuncher = new Sprite(0, 0, resourcesManager.mMuncherTextureRegion, this.vertexBufferObjectManager);
		Log.w("touch", "made muncher");

		board = new Gameboard( this, camera, mScaleName );
		Log.w("touch", "made gameboard");

		this.mScoreText = new Text(70, 455, resourcesManager.levelText, "Score: " + board.score(), "Score: XXXX".length(), this.vertexBufferObjectManager);
		
		Log.w("touch", "Created SCORE TEXT");
		this.mTitleText = new Text(310, 400, resourcesManager.levelText, mScaleName + " Scale", "XX Scale".length(), this.vertexBufferObjectManager);

		
		this.mHud.attachChild(this.mScoreText);
		this.mHud.attachChild(this.mTitleText);
		
		Log.w("touch", "Loaded HUD");

		attachChild(board);
		attachChild(mMuncher);
	}
	
	public void levelComplete()
	{
		SceneManager.getInstance().loadWinScene(engine);
	}
	

	public void updateScoreDisplay() {
		this.mScoreText.setText("Score: " + this.board.score());
	}
	

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}

}
