package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.implement;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Pallina {
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    Canvas canvas;
    Paint paint;
    public Pallina(Context context) {
        paint=new Paint();
    }
    public void disegnaPallina(Canvas canvas,float x,float y){

        int radius;
        radius = 100;
        paint.setStyle(Paint.Style.FILL);
        // Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#CD5C5C"));
        canvas.drawCircle(x, y, radius, paint);
        Log.e("TEMPO","disegnato il cerchio");
    }
}
