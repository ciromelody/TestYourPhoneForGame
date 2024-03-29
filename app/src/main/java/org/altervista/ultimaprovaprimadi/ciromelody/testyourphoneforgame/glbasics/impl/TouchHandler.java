package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.impl;

import java.util.List;

import android.view.View.OnTouchListener;

import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework.Input.TouchEvent;

import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework.Input;

public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<Input.TouchEvent> getTouchEvents();
}
