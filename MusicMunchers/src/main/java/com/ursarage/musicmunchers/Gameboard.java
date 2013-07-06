package com.ursarage.musicmunchers;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;
import android.view.KeyEvent;

import com.ursarage.ouyamediator.IOuyaControllerListener;
import com.ursarage.toolkit.Point;
import com.ursarage.toolkit.Vector2;

import tv.ouya.console.api.OuyaController;

public class Gameboard extends Rectangle implements MusicMuncherDefines, IOuyaControllerListener {


	int mNumberOfColumns = 0;
	int mNumberOfRows = 0;
	
	String mScale;
	private int mTotalNoteCount = 0;
	
	BoardPiece[][] pieces;
	
	public int score_ = 0;
	
	GameLevelScene mLevel;
	private final Random random = new Random();
    private Point mBoardPosition;
	
	public Gameboard(GameLevelScene level, Camera camera, String scale, Vector2 allocatedBoardAreaSize) {
		super(0,0, camera.getSurfaceX(), camera.getSurfaceY(), level.vertexBufferObjectManager);
		
		mLevel = level;
		mScale = scale;

        mNumberOfColumns = (allocatedBoardAreaSize.X / CELL_WIDTH) - 2;
		mNumberOfRows = (allocatedBoardAreaSize.Y / CELL_HEIGHT) -1;
        Log.d("touch", "Width: " + mNumberOfColumns +    "-    Height: " + mNumberOfRows);

        pieces = new BoardPiece[mNumberOfColumns][mNumberOfRows];
        for(int i = 0; i < mNumberOfColumns; i++)
        {
            for(int j = 0; j < mNumberOfRows; j++)
            {
              String note = chooseNote( scale );
              BoardPiece piece = new BoardPiece(note, 0, 0, mLevel.resourcesManager.mBoardCell,
                      mLevel.resourcesManager.levelText, this, mLevel.vertexBufferObjectManager);
              pieces[i][j] = piece;
              this.attachChild(piece);
              level.registerTouchArea(piece);
            }
        }

        Vector2 actualBoardSize = new Vector2( mNumberOfColumns * BoardPiece.WIDTH_ADJUSTMENT,
                                               mNumberOfRows * BoardPiece.HEIGHT_ADJUSTMENT );

        final int boardPositionX = ((allocatedBoardAreaSize.X - actualBoardSize.X)/2) +
                (int)(level.mScenePaddingSize.X *1.25);

        final int boardPositionY = ((allocatedBoardAreaSize.Y - actualBoardSize.Y)) +
                            (int)(level.mScenePaddingSize.Y / 1.5);

        mBoardPosition = new Point( boardPositionX, boardPositionY);
        this.setBoardPosition(mBoardPosition.X, mBoardPosition.Y);
        Log.d("touch", "made board pieces...");

		this.moveMuncher( randomBoardPiece() );
        Log.d("touch", "moved the muncher");
	}

	public final int score() {
		return this.score_;
	}

    public void setBoardPosition( int bottomLeftX, int bottomLeftY) {
        int x = bottomLeftX;
        int y = bottomLeftY;
        for( int i = 0; i < this.mNumberOfColumns; i++ )
        {
            y = bottomLeftY;
            for(int j = 0; j < mNumberOfRows; j++)
            {
                pieces[i][j].setPosition((float)x, (float)y);
                y += BoardPiece.HEIGHT_ADJUSTMENT;
            }
            x += BoardPiece.WIDTH_ADJUSTMENT;
        }
    }
	
	public void onTouchEvent( BoardPiece pieceTouched ) {
		if( this.isLocationSame(mLevel.mMuncher, pieceTouched) && !pieceTouched.isClear() ) {
			Log.w("touch", "Was same spot");
			Log.w("touch", "ScaleName" + mScale + " and board text " + pieceTouched.text() );
			if( ScaleRepository.doesScaleContains(mScale, pieceTouched.text()) )	{
				this.awardPoints();
				Log.w("touch", "awwarded points");
				mTotalNoteCount -= 1;
				Log.w("touch", "Notes Left: " + mTotalNoteCount);
			}			
			else{
				Log.w("touch", "removed points");
				this.removePoints();
			}
			
			mLevel.updateScoreDisplay();
			
			pieceTouched.clear();
			if( mTotalNoteCount <= 0 )
			{
				Log.w("touch", "GAME OVER!!!");
				mLevel.levelComplete();
			}
				
		}

		if( this.isAdjacent(mLevel.mMuncher, pieceTouched) )
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
		mLevel.mMuncher.setPosition(pieceTouched.getX(), pieceTouched.getY());
	}
	
	private String chooseNote( final String scale ) {
		if( random.nextInt(1) == 1 ){
            mTotalNoteCount += 1;
            return ScaleRepository.RandomNote(scale);
        }

		return ScaleRepository.RandomNote();
	}
	
	private boolean isLocationSame( final Sprite firstSprite, final Sprite secondSprite ) {
		
		Log.w("touch", "SpriteOne(" +firstSprite.getX() + "," +firstSprite.getY() + ") SpriteTwo(" +secondSprite.getX() +"," + secondSprite.getY()+ ")");
		return ( firstSprite.getX()==secondSprite.getX() && 
				firstSprite.getY()==secondSprite.getY() );
	}

    private final BoardPiece randomBoardPiece() {
        Point cellIndex = new Point( random.nextInt(pieces.length), random.nextInt(pieces[0].length));
        return pieces[cellIndex.X][cellIndex.Y];
    }

}
