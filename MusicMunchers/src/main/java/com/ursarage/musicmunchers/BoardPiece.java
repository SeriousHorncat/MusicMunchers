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
	
	public BoardPiece( String text, int x, int y, ITextureRegion texture, IFont font, Gameboard board, VertexBufferObjectManager manager )
	{
		super(x, y, texture, manager);
		mNote = text;
		int noteOffset =  CELL_WIDTH/2;
		mText = new Text( 1+ noteOffset - (FontUtils.measureText(font, text)/2), 2 + noteOffset - (font.getLineHeight()/2), font, text, manager );
		
		mBoard = board;
		
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
