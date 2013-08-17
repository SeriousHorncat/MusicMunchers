package com.ursarage.musicmunchers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ursarage.gameengine.Label;
import com.ursarage.toolkit.Point;
import com.ursarage.toolkit.Vector2;

public class BoardPiece extends Sprite implements MusicMuncherDefines {
	Label mNoteText;
  Vector2 mBoardIndex;

	// The parent board which displays the board pieces.
	GameBoard mBoard;

  static int WIDTH_ADJUSTMENT = CELL_WIDTH - 2;
  static int HEIGHT_ADJUSTMENT = CELL_HEIGHT -2;
	
	public BoardPiece(Texture cellTexture, String text, int x, int y, GameBoard board, Vector2 boardIndex) {
    super( cellTexture );

		mNoteText = new Label(text, new Point(0, 0));
    mBoard = board;
    mBoardIndex = boardIndex;
    this.setPosition(x, y);
	}

  public final Vector2 boardIndexLocation() {
    return mBoardIndex;
  }
//
//	@Override
//	public boolean onAreaTouched( final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,
//                                final float pTouchAreaLocalY) {
//		if ( pSceneTouchEvent.isActionDown() )
//		{
//      Gdx.app.log("touch", "from board piece ... touched the gameboard");
//			mBoard.onTouchEvent(this);
//		}
//		return true;
//	}
//
	public final String text() {
		return mNoteText.Text;
	}
	
	public void clear() {
    mNoteText.Text = new String("");
	}
	
  public boolean isClear() {
    return (mNoteText.Text.length() == 0);
  }

  public void draw( SpriteBatch batch, BitmapFont font) {
    super.draw(batch);
    mNoteText.draw(batch, font);
  }

  @Override
  public void setPosition(float x, float y) {
    super.setPosition(x, y);
    float noteOffset =  CELL_WIDTH/2.00f;
    mNoteText.setPosition( (x + noteOffset -7), (y + noteOffset + 5) );
  }
}
