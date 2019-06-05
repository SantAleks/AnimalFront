package com.cherentsov.AnimalFront.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

public class GTWClient implements EntryPoint {

    /** This is the entry point method.*/
    public void onModuleLoad() {
        RootPanel.get().add(new UIPanel());
    }
}
