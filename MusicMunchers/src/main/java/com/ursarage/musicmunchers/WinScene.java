package com.ursarage.musicmunchers;

import org.andengine.entity.text.Text;

import android.util.Log;

import com.ursarage.musicmunchers.SceneManager.SceneType;

public class WinScene extends BaseScene {

	private Text winText;
	
	@Override
	public void createScene() {
		this.camera.setHUD(null);
		Log.w("touch", "Game crashed here...");
		winText =  new Text(310, 400, resourcesManager.titleFont, "YOU WIN!!!!1!!","YOU WIN!!!!1!!".length(), this.vertexBufferObjectManager);
		Log.w("touch", "Game crashed here...not");
		attachChild(winText);
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);

	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_WIN;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}

}
