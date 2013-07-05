package com.ursarage.musicmunchers;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.Color;
import android.util.Log;

import com.ursarage.ouyamediator.OuyaMediator;

import tv.ouya.console.api.OuyaFacade;

public class ResourceManager implements  MusicMuncherDefines {

	public static final ResourceManager INSTANCE = new ResourceManager();
	
	public Engine engine;
	public MunchersGameActivity activity;
	public Camera camera;
	public VertexBufferObjectManager vertexBufferObjectManager;
    public OuyaMediator ouya;
	
	/** 
	 * Splash Resources
	 */
	public ITextureRegion splashRegion;
	private BitmapTextureAtlas splashTextureAtlas;
	
	/**
	 * Main Menu Resources
	 */
	public Font titleFont;
	public Font menuItemFont;

    private BitmapTextureAtlas mMenuMuncherBitmapTextureAtlas;
    protected ITextureRegion mMenuMuncherTextureRegion;
	
	/**
	 * Game Level Resources
	 */
	public Font levelText;
	
	private BitmapTextureAtlas mBoardCellBitmapTextureAtlas;
	public ITextureRegion mBoardCell;
	
	private BitmapTextureAtlas mMuncherBitmapTextureAtlas;
	protected ITextureRegion mMuncherTextureRegion;
	
	
	public void loadMenuResources() {
		loadMenuGraphics();
		loadMenuAudio();
	}
	
	public void loadGameResources() {
		Log.w("touch", "Loading game resources");
		
		loadGameGraphics();
		loadGameFonts();
		loadGameAudio();
	}
	
	private void loadMenuGraphics() {
		FontFactory.setAssetBasePath("font/");
		titleFont = FontFactory.createFromAsset(activity.getFontManager(), activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR
												, activity.getAssets(), "VTSR.TTF", 48, true, Color.GREEN);
		titleFont.load();
		
		menuItemFont = FontFactory.createFromAsset(activity.getFontManager(), activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR
				, activity.getAssets(), "VTSR.TTF", 28, true, Color.GREEN);
		menuItemFont.load();

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        this.mMenuMuncherBitmapTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), CELL_WIDTH, CELL_HEIGHT, TextureOptions.BILINEAR);
        this.mMenuMuncherTextureRegion =
                BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuMuncherBitmapTextureAtlas, activity, "musicmuncher.png", 0, 0);
        this.mMenuMuncherBitmapTextureAtlas.load();

	}
	
	private void loadMenuAudio() {
		
	}
	
	private void loadGameGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBoardCellBitmapTextureAtlas =  new BitmapTextureAtlas(activity.getTextureManager(), 75, 75, TextureOptions.BILINEAR);
		this.mBoardCell =
		  BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBoardCellBitmapTextureAtlas, activity, "boardcell.png", 0, 0);
		this.mBoardCellBitmapTextureAtlas.load();
		
		this.mMuncherBitmapTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), CELL_WIDTH, CELL_HEIGHT, TextureOptions.BILINEAR);
		this.mMuncherTextureRegion = 
		  BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMuncherBitmapTextureAtlas, activity, "musicmuncher.png", 0, 0);
		this.mMuncherBitmapTextureAtlas.load();
	}
	
	private void loadGameFonts() {
		FontFactory.setAssetBasePath("font/");
		this.levelText = FontFactory.createFromAsset(activity.getFontManager(), activity.getTextureManager(), 
									512, 512, TextureOptions.BILINEAR, activity.getAssets(), "VTSR.TTF", 28, true, Color.WHITE);
		this.levelText.load();
	}
	
	private void loadGameAudio() {
		
	}
	
	public void loadSplashScreen() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "musicmuncher.png", 0, 0);
		splashTextureAtlas.load();
	}
	
	public void unloadSplashScreen() {
		splashTextureAtlas.unload();
		splashRegion = null;
	}
	
	
	
  public static void prepareManager(Engine engine, MunchersGameActivity activity, Camera camera, VertexBufferObjectManager vbom, OuyaMediator ouyaMediator)
    {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vertexBufferObjectManager = vbom;
        getInstance().ouya = ouyaMediator;

        Log.d("touch", "IS USING OUYA: " + getInstance().ouya.isRunningOnOUYAHardware());
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static ResourceManager getInstance()
    {
        return INSTANCE;
    }
}
