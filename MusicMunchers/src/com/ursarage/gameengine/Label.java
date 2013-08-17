package com.ursarage.gameengine;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ursarage.toolkit.Point;

/**
 * Created by Angelina on 8/3/13.
 */
public class Label {
  public String Text = new String();
  private Point mPosition = new Point(0,0);

  public Label( String text, Point position ) {
    Text = text;
    mPosition = position;
  }

  public void setPosition( float x, float y ) {
    mPosition = new Point( (int)x, (int)y );
  }

  public void draw( SpriteBatch batch, BitmapFont font ) {
    font.draw( batch, Text, mPosition.X, mPosition.Y );
  }
}
