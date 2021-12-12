package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameActivityTest extends AppCompatActivity {

    FinestraDiGioco viewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewGame=new FinestraDiGioco(this);
        //setContentView(R.layout.activity_game);
        setContentView( viewGame);

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewGame.resume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        viewGame.pause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public  class  FinestraDiGioco extends SurfaceView implements Runnable{
        boolean stogiocando=true;
        Thread cominciaAcorrere ;
        SurfaceHolder holder;
        Long tempoMillisPrima;
        Long tempoMillisDopo;
        Long tempoRimasto;
        String durataFrame;
        Paint paint;
        boolean giocoFinito;

        int x=0;
        int rigaDrawText=10;
            public FinestraDiGioco(Context context) {
                super(context);
                holder = getHolder();
               paint=new Paint();
            }

            @Override
            public void run() {
               // Log.e("TEMPO","x:"+x);
                while (stogiocando){
                    tempoMillisPrima=System.currentTimeMillis();
                    aggiornaGioco();
                    disegnaGioco();
                    controllaGioco();

                }
            }
        private void aggiornaGioco() {
                if(x>255){x=0;}
                x++;

        }
        private void disegnaGioco() {
            if (!holder.getSurface().isValid()) {
               return;
            }
            Log.e("TEMPO","x:"+x);
            Canvas canvas = holder.lockCanvas();
                   paint.setTextSize(55);
                   canvas.drawRGB(161, 161, 161);
                   paint.setColor(Color.argb(255, 255, 0, 0));
                if((tempoRimasto)>77)
                            {  rigaDrawText=89;
                                paint.setTextSize(81);
                                paint.setColor(Color.argb(255, 255, 0, 0));
                                canvas.drawText("Tempo Frame "+ tempoRimasto+" millisecondi" , 10, rigaDrawText, paint);}
                      else {
                              paint.setColor(Color.argb(255, 0, 255, 0));
                              canvas.drawText("Tempo Frame "+ tempoRimasto+" millisecondi", 10, 50, paint);}
            holder.unlockCanvasAndPost(canvas);

        }
        private void controllaGioco() {
                //la frequenza di gioco dovrebbe essere di 13 HZ circa,quindi 13 visualizzazioni in  1 secondo
                // tutto il ciclo dovrebbe durare 77 millesimi


            tempoMillisDopo=System.currentTimeMillis();
            tempoRimasto=tempoMillisDopo-tempoMillisPrima;
            if((tempoRimasto)<17){
                         try {
                            Thread.sleep(17-tempoRimasto);
                            //1000/17 -> 60HZ è difficile che si arrivi a questa frequenza
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                         }}else {
                                 if((tempoRimasto)>77){
                                        Log.e("TEMPO","troppo tempo:"+(tempoRimasto));}
                               //la  frequenza di visualizzazione è inferiore a 13HZ è possibile notare rallentamenti quando
                               //tempoRimasto >77 -> 1000/77= 13HZ circa
                        }
        }

        public void pause(){
            stogiocando=false;
            try {
                //mette in pausa il gioco
                cominciaAcorrere.join();
                giocoFinito=true;
                Log.e("TEMPO","Gioco fermato");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void resume(){
            stogiocando=true;
            cominciaAcorrere=new Thread(this);
            cominciaAcorrere.start();
            giocoFinito=false;
            Log.e("TEMPO","Gioco partito");

        }

    }

}