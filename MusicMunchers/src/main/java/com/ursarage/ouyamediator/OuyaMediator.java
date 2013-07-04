package com.ursarage.ouyamediator;

import android.content.Context;

import tv.ouya.console.api.OuyaFacade;
import IOuyaControllerListener;

/**
 * Created by Angelina on 7/4/13.
 */
public class OuyaMediator {

    public static final String DEVELOPER_ID="";
    OuyaFacade mOuya;

    List<IOuyaControllerListener> controllerLisetners;
    public OuyaMediator(Context gameContext) {
        mOuya = OuyaFacade.getInstance();
        mOuya.init(gameContext,DEVELOPER_ID );
        controllerLisetners = new List<IOuyaControllerListener>();
    }

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event)
    {
        for(Iterator<IOuyaControllerListener> i = controllerLisetners.iterator(); i.hasNext(); ) {
            IOuyaControllerListener item = i.next();
            item.onKeyEvent(keyCode, event);
        }
    }

}
