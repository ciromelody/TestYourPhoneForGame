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
    float centroX,centroY;
    int        larghezzaCanvas,altezzaCanvas;
    boolean scendi;
    boolean vaiAdestra;
    public Pallina(Context context,int larghezzaCanvas,int altezzaCanvas) {
        paint=new Paint();
        this.larghezzaCanvas=larghezzaCanvas;
        this.altezzaCanvas=altezzaCanvas;

    }
    public void aggiorna(){
        if(vaiAdestra){centroX=centroX+3;
                     if (centroX>larghezzaCanvas-100){vaiAdestra=false;}
         }else {centroX=centroX-3;
                if(centroX<100){vaiAdestra=true;}}

        if(scendi){ centroY=centroY+10;
                    if (centroY>larghezzaCanvas-100){scendi=false;}
        }else {centroY=centroY-10;
                      if (centroY<100){scendi=true;}
                                   }


    }
    public void disegnaPallina(Canvas canvas){

        int radius;
        radius = 100;
        paint.setStyle(Paint.Style.FILL);
        // Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#CD5C5C"));
        canvas.drawCircle(centroX, centroY, radius, paint);
       // Log.e("TEMPO","disegnato il cerchio");
    }
}
