package com.ursarage.musicmunchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.ursarage.ouyamediator.IOuyaControllerListener;

import tv.ouya.console.api.OuyaController;

public class Gameboard extends Rectangle implements MusicMuncherDefines, IOuyaControllerListener {
	

	int numberOfCellsWidth;
	int numberOfCellsHeight;
	
	String scaleName;
	private int totalNoteCount = 0;
	
	List<BoardPiece> pieces;
	
	public int score_ = 0;
	
	GameLevelScene level;
	private Random random = new Random();
	
	public Gameboard(GameLevelScene levelScene, Camera camera, String scale )
	{
		super(0,0, camera.getSurfaceX(), camera.getSurfaceY(), levelScene.vertexBufferObjectManager);
		
		level = levelScene;
		scaleName = scale;

		numberOfCellsWidth = ((int)camera.getWidth() - (levelScene.BOARD_PADDING_PERCENT*2)) / CELL_WIDTH;
		numberOfCellsHeight = ((int)camera.getHeight() - (levelScene.BOARD_PADDING_PERCENT*2)) / CELL_HEIGHT;

        Log.d("touch", "Width: " + numberOfCellsWidth  +    "-    Height: " + numberOfCellsHeight);
		
		int gameboardWidth = levelScene.maxGameboardWidth - (levelScene.BOARD_PADDING_PERCENT*2);
		int gameboardHeight = levelScene.maxGameboardHeight - (levelScene.BOARD_PADDING_PERCENT*2);
		
	    int initialBoardPositionX = levelScene.levelPaddingWidth;
	    int initialBoardPositiony = levelScene.levelPaddingHeight;
	      
	    int pieceX = initialBoardPositionX;
	    int pieceY = initialBoardPositiony;

	    pieces = new ArrayList<BoardPiece>();
		while( pieceX < gameboardWidth )
		{
			pieceY = initialBoardPositiony;
			while(pieceY < gameboardHeight )
			{
			  String note = chooseNote( scale );
			  BoardPiece piece = new BoardPiece(note, pieceX, pieceY, level.resourcesManager.mBoardCell,
					  level.resourcesManager.levelText, this, level.vertexBufferObjectManager);
			  pieces.add(piece);
			  this.attachChild(piece);
			  levelScene.registerTouchArea(piece);
			  pieceY += CELL_HEIGHT - 2;
			}
			pieceX += CELL_WIDTH -2;
		}

		this.moveMuncher( randomBoardPiece() );
	}


	public final int score() {
		return this.score_;
	}
	
	public void onTouchEvent( BoardPiece pieceTouched ) {
		if( this.isLocationSame(level.mMuncher, pieceTouched) && !pieceTouched.isClear() ) {
			Log.w("touch", "Was same spot");
			Log.w("touch", "ScaleName" + scaleName + " and board text " + pieceTouched.text() );
			if( ScaleRepository.doesScaleContains(scaleName, pieceTouched.text()) )	{
				this.awardPoints();
				Log.w("touch", "awwarded points");
				totalNoteCount -= 1;
				Log.w("touch", "Notes Left: " + totalNoteCount);
			}			
			else{
				Log.w("touch", "removed points");
				this.removePoints();
			}
			
			level.updateScoreDisplay();
			
			pieceTouched.clear();
			if( totalNoteCount <= 0 )
			{
				Log.w("touch", "GAME OVER!!!");
				level.levelComplete();
			}
				
		}

		if( this.isAdjacent(level.mMuncher, pieceTouched) )
		{
			this.moveMuncher(pieceTouched);
		}
	}


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == OuyaController.BUTTON_DPAD_RIGHT ) {
            return true;
        } else if (keyCode == OuyaController.BUTTON_DPAD_LEFT ) {
            return true;
        } else if (keyCode == OuyaController.BUTTON_DPAD_DOWN ) {

        }
        else if ( keyCode == OuyaController.BUTTON_DPAD_UP ) {

        }

        return false;
    }
	
	private boolean isAdjacent(Sprite firstSprite, Sprite secondSprite) {
		
		final float xLocation = Math.abs(firstSprite.getX() - secondSprite.getX());
		final float yLocation = Math.abs(firstSprite.getY() - secondSprite.getY()) ;
		if( xLocation == yLocation )
			return false;
		Log.w("touch", "X Size("+  xLocation + ") Y Size(" + yLocation + ")");
		if(  xLocation <= CELL_WIDTH && yLocation <= CELL_HEIGHT )
			return true;
		
		return false;
	}
	
	private void awardPoints() {
		score_ += 10;
	}
	
	private void removePoints() {
		score_ -= 10;
		
		if( score_ < 0 )
			score_ = 0;
	}
	
	private void moveMuncher( final BoardPiece pieceTouched ) {
		level.mMuncher.setPosition(pieceTouched.getX(), pieceTouched.getY());	
	}
	
	private String chooseNote( String scale ) {
		if( random.nextInt(1) == 1 ){
            totalNoteCount += 1;
            return ScaleRepository.RandomNote(scale);
        }

		return ScaleRepository.RandomNote();
	}
	
	private boolean isLocationSame( Sprite firstSprite, Sprite secondSprite ) {
		
		Log.w("touch", "SpriteOne(" +firstSprite.getX() + "," +firstSprite.getY() + ") SpriteTwo(" +secondSprite.getX() +"," + secondSprite.getY()+ ")");
		return ( firstSprite.getX()==secondSprite.getX() && 
				firstSprite.getY()==secondSprite.getY() );
	}

    private final BoardPiece  randomBoardPiece() {
        int randomCellIndex = random.nextInt(pieces.size() + 1);
        return pieces.get(randomCellIndex);
    }

}
