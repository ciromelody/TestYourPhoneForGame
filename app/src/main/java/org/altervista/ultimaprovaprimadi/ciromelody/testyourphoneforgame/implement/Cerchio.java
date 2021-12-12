package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.implement;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class Cerchio extends View {
       private int centroCerchio_X;
       private int centroCerchio_Y;
       Canvas canvas;

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    Paint paint = null;
            public Cerchio(Context context)
            {
                super(context);
                paint = new Paint();
            }

            @Override
            protected void onDraw(Canvas canvas)
            {
                super.onDraw(canvas);
                int x = getWidth();
                int y = getHeight();
                int radius;
                radius = 100;
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.BLACK);
                canvas.drawPaint(paint);
                // Use Color.parseColor to define HTML colors
                paint.setColor(Color.parseColor("#CD5C5C"));
                canvas.drawCircle(x/2, y/2, radius, paint);
                Log.e("TEMPO","disegnato il cerchio");
                //invalidate();
            }

    public void setCentroCerchio_X(int centroCerchio_X) {
        this.centroCerchio_X = centroCerchio_X;
    }

    public void setCentroCerchio_Y(int centroCerchio_Y) {
        this.centroCerchio_Y = centroCerchio_Y;
    }

    public int getCentroCerchio_X() {
        return centroCerchio_X;
    }

    public int getCentroCerchio_Y() {
        return centroCerchio_Y;
    }
}

