package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework;

import  org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework.Graphics.PixmapFormat;

public interface Pixmap {
    public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}
