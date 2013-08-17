package com.ursarage.musicmunchers;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.ursarage.gameengine.ResourceManager;
import com.ursarage.gameengine.SceneManager;
import com.ursarage.toolkit.Vector2;

public class GameBoard implements MusicMuncherDefines {
	int mNumberOfColumns = 0;
	int mNumberOfRows = 0;
	
	String mScale;
	private int mTotalNoteCount = 0;

	BoardPiece[][] pieces;
	
	public int score_ = 0;
	
	GameScreen mLevel;
	private final Random random = new Random();
  private Vector2 mMuncherBoardLocation;

	public GameBoard(GameScreen level, String scale) {

		mLevel = level;
		mScale = scale;
    mMuncherBoardLocation = new Vector2(0,0);

    Vector2 allocatedBoardAreaSize = mLevel.allocatedBoardArea();
    mNumberOfColumns = (allocatedBoardAreaSize.X / CELL_WIDTH);
		mNumberOfRows = (allocatedBoardAreaSize.Y / CELL_HEIGHT);

    Gdx.app.log("touch", "Width: " + mNumberOfColumns +    "-    Height: " + mNumberOfRows);

    pieces = new BoardPiece[mNumberOfColumns][mNumberOfRows];
    for(int i = 0; i < mNumberOfColumns; i++) {
      for(int j = 0; j < mNumberOfRows; j++) {
        Gdx.app.log("touch", "Creating piece at " + i + "," + j);
        String note = chooseNote( scale );
        BoardPiece piece = new BoardPiece(ResourceManager.getInstance().boardCellImage, note, 0, 0, this, new Vector2(i, j));
        pieces[i][j] = piece;
      }
    }

    Vector2 actualBoardSize = new Vector2( mNumberOfColumns * BoardPiece.WIDTH_ADJUSTMENT,
                                           mNumberOfRows * BoardPiece.HEIGHT_ADJUSTMENT );

    final Vector2 scenePadding = mLevel.scenePadding();
    final int boardPositionX = ((allocatedBoardAreaSize.X - actualBoardSize.X)/2) + (int)(scenePadding.X *1.25);
    final int boardPositionY = ((allocatedBoardAreaSize.Y - actualBoardSize.Y)) + (int)(scenePadding.Y / 1.5);
    this.setBoardPosition(boardPositionX, boardPositionY);

    Gdx.app.log("touch", "made board pieces...");
    this.moveMuncher( randomBoardPiece() );

    Gdx.app.log("touch", "moved the muncher");
    calculateNumberOfCorrectNote();
	}

	public final int score() {
		return this.score_;
	}

  public void setBoardPosition( int bottomLeftX, int bottomLeftY) {
    int x = bottomLeftX;
    int y = bottomLeftY;
    for( int i = 0; i < this.mNumberOfColumns; i++ ) {
      y = bottomLeftY;
      for(int j = 0; j < mNumberOfRows; j++) {
          pieces[i][j].setPosition((float)x, (float)y);
          y += BoardPiece.HEIGHT_ADJUSTMENT;
      }
      x += BoardPiece.WIDTH_ADJUSTMENT;
    }
    this.moveMuncher( randomBoardPiece() );
  }
	
//	public void onTouchEvent( BoardPiece pieceTouched ) {
//		if( this.isLocationSame(mLevel.mMuncher, pieceTouched) && !pieceTouched.isClear() ) {
//             Gdx.app.log("touch", "Was same spot");
//            eatNote(pieceTouched);
//			if( isLevelComplete() )
//			{
//				 Gdx.app.log("touch", "GAME OVER!!!");
//				mLevel.levelComplete();
//			}
//		}
//
//		if( this.isAdjacent(mLevel.mMuncher, pieceTouched) )
//		{
//			this.moveMuncher(pieceTouched);
//		}
//	}

  public int calculateNumberOfCorrectNote() {
    for( int i = 0; i < this.mNumberOfColumns; i++ ) {
      for(int j = 0; j < mNumberOfRows; j++) {
        if( ScaleRepository.doesScaleContains(mScale, pieces[i][j].mNoteText.Text) )
          mTotalNoteCount +=1;
      }
    }

   return mTotalNoteCount;
  }

  private boolean isLevelComplete() {
    return  (mTotalNoteCount <= 0);
  }

  private void eatNote(BoardPiece piece) {
    Gdx.app.log("touch", "ScaleName" + mScale + " and board text " + piece.text() );
    if( ScaleRepository.doesScaleContains(mScale, piece.text()) )	{
      this.awardPoints();
      Gdx.app.log("touch", "awwarded points");
      mTotalNoteCount -= 1;
      Gdx.app.log("touch", "Notes Left: " + mTotalNoteCount);
    }
    else{
      Gdx.app.log("touch", "removed points");
      this.removePoints();
    }

    mLevel.updateScoreDisplay();
    piece.clear();
  }


//  public boolean onKeyDown(int keyCode, KeyEvent event) {
//      if( SceneManager.getInstance().getCurrentSceneType() != mLevel.getSceneType()) {
//          return false;
//      }
//      return false;
//  }

  public boolean onKeyUp(int keyCode) {

    Gdx.app.log("touch", "in keydown event in gameboard ...");
    if(keyCode == Input.Keys.RIGHT ) {
        if( (mMuncherBoardLocation.X + 1) >= pieces.length)
            return false;
        mMuncherBoardLocation.X += 1;
        moveMuncher(pieces[mMuncherBoardLocation.X][mMuncherBoardLocation.Y]);
        return true;
    } else if (keyCode == Input.Keys.LEFT  ) {
        if( (mMuncherBoardLocation.X - 1) < 0)
            return false;
        mMuncherBoardLocation.X -= 1;
        moveMuncher(pieces[mMuncherBoardLocation.X][mMuncherBoardLocation.Y]);
        return true;
    } else if (keyCode == Input.Keys.DOWN  ) {
        if( (mMuncherBoardLocation.Y - 1) < 0)
            return false;
        mMuncherBoardLocation.Y -= 1;
        moveMuncher(pieces[mMuncherBoardLocation.X][mMuncherBoardLocation.Y]);
        return true;
    }
    else if ( keyCode == Input.Keys.UP ) {
        if( (mMuncherBoardLocation.Y + 1) >=  pieces[0].length)
            return false;
        mMuncherBoardLocation.Y += 1;
        moveMuncher(pieces[mMuncherBoardLocation.X][mMuncherBoardLocation.Y]);
        return true;
    }
    else if ( keyCode == Input.Keys.SPACE ) {
        eatNote(pieces[mMuncherBoardLocation.X][mMuncherBoardLocation.Y]);

        if( isLevelComplete() )
        {
             Gdx.app.log("touch", "GAME OVER!!!");
            mLevel.levelComplete();
        }
        return true;
    }

    Gdx.app.log("touch", "moving the board piece...");
    return false;
  }
	
	private boolean isAdjacent(Sprite firstSprite, Sprite secondSprite) {
		final float xLocation = Math.abs(firstSprite.getX() - secondSprite.getX());
		final float yLocation = Math.abs(firstSprite.getY() - secondSprite.getY()) ;
		if( xLocation == yLocation )
			return false;

		Gdx.app.log("touch", "X Size("+  xLocation + ") Y Size(" + yLocation + ")");
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
    Gdx.app.log("touch", "moving muncher to board index from piece touched " + pieceTouched.boardIndexLocation().X + "," + pieceTouched.boardIndexLocation().Y);
    mMuncherBoardLocation = pieceTouched.boardIndexLocation();

//    Vector3 piecePosition = new Vector3();
//    piecePosition.set(pieceTouched.getX(), pieceTouched.getY(), 0);
//    mLevel.camera.unproject(piecePosition);

    Gdx.app.log("touch", "moving muncher to board piece note " + pieceTouched.mNoteText.Text + " @ board index " + mMuncherBoardLocation.X + "," + mMuncherBoardLocation.Y);
    Gdx.app.log("touch", "Pixel PIece Location (" +pieceTouched.getX() +  "," +pieceTouched.getY() + " )");
    //mLevel.mMuncher.setPosition(piecePosition.x, piecePosition.y);
    mLevel.mMuncher.setPosition(pieceTouched.getX(), pieceTouched.getY());
    Gdx.app.log("touch", "Pixel Muncher Location (" +mLevel.mMuncher.getX() +  "," +mLevel.mMuncher.getY() + " )");
	}
	
	private String chooseNote( final String scale ) {
		if( random.nextInt(1) == 1 ){
      return ScaleRepository.RandomNote(scale);
    }

		return ScaleRepository.RandomNote();
	}
	
	private boolean isLocationSame( final Sprite firstSprite, final Sprite secondSprite ) {
		Gdx.app.log("touch", "SpriteOne(" +firstSprite.getX() + "," +firstSprite.getY() + ") SpriteTwo(" +secondSprite.getX() +"," + secondSprite.getY()+ ")");
		return ( firstSprite.getX()==secondSprite.getX() && firstSprite.getY()==secondSprite.getY() );
	}

  private final BoardPiece randomBoardPiece() {
    Gdx.app.log("touch", "pieces column length: " + pieces.length + "   row length" + pieces[0].length);
    Vector2 cellIndex = new Vector2( random.nextInt(pieces.length), random.nextInt(pieces[0].length));
    Gdx.app.log("touch", "random index column: " + cellIndex.X + "   row " + cellIndex.Y);

    return pieces[cellIndex.X][cellIndex.Y];
  }

  public void draw( SpriteBatch batch, BitmapFont font ) {
    for( int i = 0; i < this.mNumberOfColumns; i++ )  {
      for(int j = 0; j < mNumberOfRows; j++) {
        pieces[i][j].draw(batch, font);
      }
    }
  }
}
