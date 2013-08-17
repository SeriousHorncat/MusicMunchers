package com.ursarage.gameengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.ursarage.musicmunchers.MusicMuncherDefines;
import com.ursarage.musicmunchers.MusicMunchersGame;

public class ResourceManager implements MusicMuncherDefines {

  public static final ResourceManager INSTANCE = new ResourceManager();

  public MusicMunchersGame mGame;

  /**
  * Shared Game Resources
  */
 // public Music rainMusic;
 // public Sound dropSound;
  public Texture musicMuncherImage;


  /**
  * Splash Resources
  */
  public Texture ursaRageLogoimage;

  /**
  * Main Menu Resources
  */

  /**
  * Game Level Resources
  */
  //public Texture mBoardCellImage;
  //public Texture dropImage;
  //public Texture bucketImage;
  public Texture boardCellImage;

  public void loadSharedResouces() {
    // load the drop sound effect and the rain background "music"
    //rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
    //dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
    musicMuncherImage = new Texture(Gdx.files.internal("gfx/musicmuncher.png"));
  }

  public void disposeSharedresources() {
    musicMuncherImage.dispose();
    //dropSound.dispose();
    //rainMusic.dispose();
  }


  public void loadMenuResources() {
  }

  public void loadGameResources() {
    Gdx.app.log("touch", "Loading game resources");

    // load the images for the droplet and the bucket, 64x64 pixels each
    //dropImage = new Texture(Gdx.files.internal("droplet.png"));
    //bucketImage = new Texture(Gdx.files.internal("bucket.png"));
    boardCellImage = new Texture(Gdx.files.internal("gfx/boardcell.png"));
  }

  public void unloadGameResources() {
    //dropImage.dispose();
    // bucketImage.dispose();
  }


  public void loadSplashScreen() {
    ursaRageLogoimage = new Texture(Gdx.files.internal("logo.png"));
  }

  public void unloadSplashScreen() {
    ursaRageLogoimage.dispose();
  }

  public static void prepareManager( MusicMunchersGame game ) {
    getInstance().mGame = game;
  }

  //---------------------------------------------
  // GETTERS AND SETTERS
  //---------------------------------------------
  public static ResourceManager getInstance() {
      return INSTANCE;
  }

  public void prepareSharedResources() {
    loadSharedResouces();
  }
}
