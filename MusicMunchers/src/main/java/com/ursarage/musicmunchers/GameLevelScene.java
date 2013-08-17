package com.ursarage.musicmunchers;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.util.Log;
import android.view.KeyEvent;

import com.ursarage.musicmunchers.SceneManager.SceneType;
import com.ursarage.toolkit.Point;
import com.ursarage.toolkit.Vector2;

public class GameLevelScene extends BaseScene {

	private HUD mHud;
	
	private Text mScoreText;
	private Text mTitleText;
	
	private String mScaleName;
	
	private Gameboard mBoard;
	public Sprite mMuncher;

    final public int BOARD_PADDING_PERCENT = 10;

    public Vector2 mScenePaddingSize;
    public Vector2 mAllocatedBoardArea;

    @Override
	public void createScene() {
		
		Log.w("touch", "Creating game mLevel scene Game");

		mScaleName = new String("C");
		Log.w("touch", "Made Scale name");

		this.mHud = new HUD();
		Log.w("touch", "allocated HUD");

		this.camera.setHUD(this.mHud);
		Log.w("touch", "set HUD to the camera");

		mMuncher = new Sprite(0, 0, resourcesManager.mMuncherTextureRegion, this.vertexBufferObjectManager);
		Log.w("touch", "made muncher");

		mScenePaddingSize = new Vector2( (int)camera.getWidth() / BOARD_PADDING_PERCENT,
                                         (int)camera.getHeight() / BOARD_PADDING_PERCENT );

        final Point scoreLocation = new Point(mScenePaddingSize.X,
                    ((int)camera.getHeight()- mScenePaddingSize.X) );

        final Point titleCenterLocation = new Point( (int)camera.getWidth()/2,
                    scoreLocation.Y - (mScenePaddingSize.Y /2));

        this.mScoreText = new Text(scoreLocation.X, scoreLocation.Y, resourcesManager.levelText,
                "Score: " + 0, "Score: XXXX".length(), this.vertexBufferObjectManager);

        this.mTitleText = new Text(titleCenterLocation.X, titleCenterLocation.Y,
                resourcesManager.levelText, mScaleName + " Scale", "XX Scale".length(), this.vertexBufferObjectManager);
        this.mTitleText.setPosition( titleCenterLocation.X - (this.mTitleText.getWidth()/2) + 15,
                titleCenterLocation.Y - (this.mTitleText.getHeight()/2) );

        mAllocatedBoardArea = new Vector2( (int)camera.getWidth() -(mScenePaddingSize.X *2),
                                            titleCenterLocation.Y - (mScenePaddingSize.Y /2));

        Log.d("touch", "MaxBoardWidth: " + mAllocatedBoardArea.X +
                "-    MaxBoardHeight: " + mAllocatedBoardArea.Y);

        mBoard = new Gameboard( this, camera, mScaleName, mAllocatedBoardArea );
        Log.w("touch", "made gameboard");

		this.mHud.attachChild(this.mScoreText);
		this.mHud.attachChild(this.mTitleText);
		
		Log.w("touch", "Loaded HUD");


        attachChild(mBoard);
		attachChild(mMuncher);
        //resourcesManager.ouya.registerListener(mBoard);
    }
	
	public void levelComplete()
	{
		SceneManager.getInstance().loadWinScene(engine);
	}
	

	public void updateScoreDisplay() {
		this.mScoreText.setText("Score: " + this.mBoard.score());
	}
	

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

	}

    @Override
    public void onDPadPressed(int keyCode, KeyEvent event) {
        mBoard.onKeyUp(keyCode, event);
    }

    @Override
    public void onButtonPadPressed(int keyCode, KeyEvent event) {
        mBoard.onKeyUp(keyCode, event);
    }


    @Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		//resourcesManager.ouya.unregisterListener(mBoard);
	}

}
