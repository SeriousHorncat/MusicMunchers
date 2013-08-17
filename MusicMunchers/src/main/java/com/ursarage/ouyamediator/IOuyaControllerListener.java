package com.ursarage.ouyamediator;

import android.view.KeyEvent;


/**
 * Created by Angelina on 7/5/13.
 */
public interface IOuyaControllerListener {
        public boolean onKeyDown(int keyCode, android.view.KeyEvent event);
        public boolean onKeyUp(int keyCode, KeyEvent event);
}
