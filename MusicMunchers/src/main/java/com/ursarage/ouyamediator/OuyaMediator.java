package com.ursarage.ouyamediator;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tv.ouya.console.api.OuyaFacade;

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
        controllerLisetners = new ArrayList<IOuyaControllerListener>();
    }

    public boolean isRunningOnOUYAHardware() {
        return mOuya.isRunningOnOUYAHardware();
    }

    public boolean registerListener( IOuyaControllerListener listener ) {
         return controllerLisetners.add(listener);
    }

    public boolean unregisterListener( IOuyaControllerListener listener ) {
        controllerLisetners.remove(listener);
        return true;
    }

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event)
    {
        Log.d("touch", "onkeydown in controller thingy");
        for(Iterator<IOuyaControllerListener> i = controllerLisetners.iterator(); i.hasNext(); ) {
            IOuyaControllerListener item = i.next();
            item.onKeyDown(keyCode, event);
        }

        return true;
    }

}
