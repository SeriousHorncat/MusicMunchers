package com.ursarage.ouyamediator;

import android.content.Context;

import tv.ouya.console.api.OuyaFacade;

/**
 * Created by Angelina on 7/4/13.
 */
public class OuyaMediator {

    public static final String DEVELOPER_ID="";
    OuyaFacade mOuya;
    public OuyaMediator(Context gameContext) {
        mOuya = OuyaFacade.getInstance();
        mOuya.init(gameContext,DEVELOPER_ID );

    }
}
