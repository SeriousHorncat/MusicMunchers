package com.ursarage.musicmunchers;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.animator.AlphaMenuSceneAnimator;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import com.ursarage.musicmunchers.SceneManager.SceneType;

import android.util.Log;

public class MainMenuScene extends BaseScene  implements IOnMenuItemClickListener {

	private Text titleText;
	
	private MenuScene menuScene;

    public Sprite mMuncher;
	static final int MENU_PLAY = 0;
	
	@Override
	public void createScene() {
		titleText = new Text(310, 400, resourcesManager.titleFont, "MUSIC MUNCHERS", "MUSIC MUNCHERS".length(), resourcesManager.vertexBufferObjectManager);
		attachChild(titleText);
		
		menuScene = new MenuScene(camera, new AlphaMenuSceneAnimator());
		
		final IMenuItem playMenuItem = new ColorMenuItemDecorator( new TextMenuItem(MENU_PLAY, resourcesManager.menuItemFont, "PLAY", this.vertexBufferObjectManager), new Color(0,0,1), new Color(0,1,0));
		menuScene.addMenuItem(playMenuItem);
		menuScene.buildAnimations();
		menuScene.setBackgroundEnabled(false);
		menuScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuScene, false, true, true);

        mMuncher = new Sprite(315, 260, resourcesManager.mMenuMuncherTextureRegion, this.vertexBufferObjectManager);

        attachChild(mMuncher);
        if( resourcesManager.ouya.isRunningOnOUYAHardware())
        {
            // TOdo something here
        }
        Log.d("touch", "is it crashing here?");
     }

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {

		switch(pMenuItem.getID()) {
			case MENU_PLAY:
				SceneManager.getInstance().loadGameScene(engine);
				return true;
			default: 
				return false;
		}
	}

    /**@Override
    public boolean onGenericMotionEvent(final MotionEvent event)
    {
        int playerNum = OuyaController.getPlayerNumByDeviceId(event.getDeviceId());
        return true;
    }**/
}
