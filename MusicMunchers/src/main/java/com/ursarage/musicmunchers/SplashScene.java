package com.ursarage.musicmunchers;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.ursarage.musicmunchers.SceneManager.SceneType;

public class SplashScene extends BaseScene {
	
	private Sprite slpashSprite;

	@Override
	public void createScene() {
		slpashSprite = new Sprite(0,0, resourcesManager.splashRegion, vertexBufferObjectManager)
		{
		    @Override
		    protected void preDraw(GLState pGLState, Camera camera) 
		    {
		       super.preDraw(pGLState, camera);
		       pGLState.enableDither();
		    }
		};
		
		slpashSprite.setScale(1.5f);
		slpashSprite.setPosition(400, 240);
		attachChild(slpashSprite);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
		slpashSprite.detachSelf();
		slpashSprite.dispose();
	    this.detachSelf();
	    this.dispose();
	}

}
