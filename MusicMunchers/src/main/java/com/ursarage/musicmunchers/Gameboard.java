package com.ursarage.musicmunchers;

import android.util.Log;
import android.view.KeyEvent;

import com.ursarage.toolkit.Point;
import com.ursarage.toolkit.Vector2;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;

import java.util.Random;

import tv.ouya.console.api.OuyaController;

public class Gameboard extends Rectangle implements MusicMuncherDefines {


	int mNumberOfColumns = 0;
	int mNumberOfRows = 0;
	
	String mScale;
	private int mTotalNoteCount = 0;


	BoardPiece[][] pieces;
	
	public int score_ = 0;
	
	GameLevelScene mLevel;
	private final Random random = new Random();
    private Point mBoardPosition;
    private Vector2 mMuncherBoardLocation;
    private boolean isReady = false;
	
	public Gameboard(GameLevelScene level, Camera camera, String scale, Vector2 allocatedBoardAreaSize) {
		super(0,0, camera.getSurfaceX(), camera.getSurfaceY(), level.vertexBufferObjectManager);

		mLevel = level;
		mScale = scale;
        mMuncherBoardLocation = new Vector2(0,0);

    mNumberOfColumns = (allocatedBoardAreaSize.X / CELL_WIDTH) - 2;
		mNumberOfRows = (allocatedBoardAreaSize.Y / CELL_HEIGHT) -1;
        Log.d("touch", "Width: " + mNumberOfColumns +    "-    Height: " + mNumberOfRows);

        pieces = new BoardPiece[mNumberOfColumns][mNumberOfRows];
        for(int i = 0; i < mNumberOfColumns; i++)
        {
            for(int j = 0; j < mNumberOfRows; j++)
            {
              Log.d("touch", "Creating piece at " + i + "," + j );
              String note = chooseNote( scale );
              BoardPiece piece = new BoardPiece(note, 0, 0, mLevel.resourcesManager.mBoardCell,
                      mLevel.resourcesManager.levelText, this, mLevel.vertexBufferObjectManager,
                      new Vector2(i, j));
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

        calculateNumberOfCorrectNote();
        isReady = true;
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
            eatNote(pieceTouched);
			if( isLevelComplete() )
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

    public int calculateNumberOfCorrectNote() {
        for( int i = 0; i < this.mNumberOfColumns; i++ )
        {
            for(int j = 0; j < mNumberOfRows; j++)
            {
                if( ScaleRepository.doesScaleContains(mScale, pieces[i][j].mNote) )
                    mTotalNoteCount +=1;
            }
        }

       return mTotalNoteCount;
    }

    private boolean isLevelComplete() {
        return  (mTotalNoteCount <= 0);
    }

    private void eatNote(BoardPiece piece) {
        Log.w("touch", "ScaleName" + mScale + " and board text " + piece.text() );
        if( ScaleRepository.doesScaleContains(mScale, piece.text()) )	{
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
        piece.clear();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( SceneManager.getInstance().getCurrentSceneType() != mLevel.getSceneType()) {
            return false;
        }
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if( SceneManager.getInstance().getCurrentSceneType() != mLevel.getSceneType()) {
            return false;
        }

        if( !isReady )
            return false;

        Log.d("touch", "in keydown event in gameboard ...");
        if(keyCode == OuyaController.BUTTON_DPAD_RIGHT ) {
            if( (mMuncherBoardLocation.X + 1) >= pieces.length)
                return false;
            mMuncherBoardLocation.X += 1;
            moveMuncher(pieces[mMuncherBoardLocation.X][mMuncherBoardLocation.Y]);
            return true;
        } else if (keyCode == OuyaController.BUTTON_DPAD_LEFT ) {
            if( (mMuncherBoardLocation.X - 1) < 0)
                return false;
            mMuncherBoardLocation.X -= 1;
            moveMuncher(pieces[mMuncherBoardLocation.X][mMuncherBoardLocation.Y]);
            return true;
        } else if (keyCode == OuyaController.BUTTON_DPAD_DOWN ) {
            if( (mMuncherBoardLocation.Y - 1) < 0)
                return false;
            mMuncherBoardLocation.Y -= 1;
            moveMuncher(pieces[mMuncherBoardLocation.X][mMuncherBoardLocation.Y]);
            return true;
        }
        else if ( keyCode == OuyaController.BUTTON_DPAD_UP ) {
            if( (mMuncherBoardLocation.Y + 1) >=  pieces[0].length)
                return false;
            mMuncherBoardLocation.Y += 1;
            moveMuncher(pieces[mMuncherBoardLocation.X][mMuncherBoardLocation.Y]);
            return true;
        }
        else if ( keyCode == OuyaController.BUTTON_O) {
            eatNote(pieces[mMuncherBoardLocation.X][mMuncherBoardLocation.Y]);

            if( isLevelComplete() )
            {
                Log.w("touch", "GAME OVER!!!");
                mLevel.levelComplete();
            }
            return true;
        }

        Log.d("touch", "moving the board piece...");
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
        Log.d("touch", "moving muncher to board index from piece touched " + pieceTouched.boardIndexLocation().X + "," + pieceTouched.boardIndexLocation().Y);
        mMuncherBoardLocation = pieceTouched.boardIndexLocation();
        Log.d("touch", "moving muncher to board index " + mMuncherBoardLocation.X + "," + mMuncherBoardLocation.Y);
		mLevel.mMuncher.setPosition(pieceTouched.getX(), pieceTouched.getY());
	}
	
	private String chooseNote( final String scale ) {
		if( random.nextInt(1) == 1 ){
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
        Log.d("touch", "pieces column length: " + pieces.length + "   row length"  + pieces[0].length);
        Vector2 cellIndex = new Vector2( random.nextInt(pieces.length), random.nextInt(pieces[0].length));

        Log.d("touch", "random index column: " + cellIndex.X + "   row "  + cellIndex.Y);

        return pieces[cellIndex.X][cellIndex.Y];
    }

}
