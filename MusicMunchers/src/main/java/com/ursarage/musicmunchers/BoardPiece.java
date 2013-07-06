package com.ursarage.musicmunchers;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.FontUtils;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class BoardPiece extends Sprite implements MusicMuncherDefines {
	
	Text mText;
	String mNote;
	
	// The parent board which displays the board pieces.
	Gameboard mBoard;

    static int WIDTH_ADJUSTMENT = CELL_WIDTH - 2;
    static int HEIGHT_ADJUSTMENT = CELL_HEIGHT -2;
	
	public BoardPiece( String text, int x, int y, ITextureRegion texture, IFont font, Gameboard board, VertexBufferObjectManager manager ) {
		super(x, y, texture, manager);

		mNote = text;
        mBoard = board;

		float noteOffset =  CELL_WIDTH/2.0f;

        // Magic numbers below help tweek the position to seem more int he center
		mText = new Text( x + noteOffset - 1, y + noteOffset -2 , font, text, manager );

        this.attachChild(mText);
	}
	
	@Override
	public boolean onAreaTouched( final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		if ( pSceneTouchEvent.isActionDown() )
		{
			Log.w("touch", "from board piece ... touched the gameboard");
			mBoard.onTouchEvent(this);
		}
		return true;
	}
	
	public final String text() {
		return mNote;
	}
	
	public void clear() {
		Log.w("touch", "clearing the text supposidly");
		mNote = new String("");
		mText.setText(mNote);

	}
	
    public boolean isClear() {
    	return (mNote.length() == 0);
    }
}
