package com.example.exemploapprelogio;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Calendar;

public class FullscreenActivity2 extends AppCompatActivity {

    private ViewHolder viewHolder = new ViewHolder();
    //parte 3 handler e runnable

    private Handler handler = new Handler();
    private Runnable runnable;
//
    //parte 5
    private boolean runnableStopped =false;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen2);
        viewHolder.tv_horasMinutos = findViewById(R.id.tv_horasMinutos);
        viewHolder.tv_segundos = findViewById(R.id.tv_segundos);
//parte 2 Atualizar hora
        //parte 4 metodo vai para dentro do onResume()
       // AtualizarHorario();

//
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


//parte 4
    @Override
    protected void onResume() {
        super.onResume();
        runnableStopped=false;
        AtualizarHorario();
    }

    @Override
    protected void onStop() {
        super.onStop();
        runnableStopped=true;
    }
    //
/*parte 3
    private void AtualizarHorario() {

        runnable = new Runnable() {
            @Override
            public void run() {

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                String horasMinutosFormatado = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));
                String segundosFormatado = String.format("%02d", calendar.get(Calendar.SECOND));
                viewHolder.tv_horasMinutos.setText(horasMinutosFormatado);
                viewHolder.tv_segundos.setText(segundosFormatado);
                //parte 3 handler
                long agora = SystemClock.uptimeMillis();
                long proximo = agora + (1000-(agora%1000));

                handler.postAtTime(runnable, proximo);
                //
            }
        };
        runnable.run();
    */
    //parte Atualizar hora parte 4
    private void AtualizarHorario() {

        runnable = new Runnable() {
            @Override
            public void run() {
                //parte 5
                if(runnableStopped)
                    return;
                //
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                String horasMinutosFormatado = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));
                String segundosFormatado = String.format("%02d", calendar.get(Calendar.SECOND));
                viewHolder.tv_horasMinutos.setText(horasMinutosFormatado);
                viewHolder.tv_segundos.setText(segundosFormatado);
                //parte 3 handler
                long agora = SystemClock.uptimeMillis();
                long proximo = agora + (1000-(agora%1000));

                handler.postAtTime(runnable, proximo);
                //
            }
        };
        runnable.run();
        /* parte 3 copia esse codigo para dentro do run(){ }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        String horasMinutosFormatado = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
        String segundosFormatado = String.format("%02d", calendar.get(Calendar.SECOND));
        viewHolder.tv_horasMinutos.setText(horasMinutosFormatado);
        viewHolder.tv_segundos.setText(segundosFormatado);
        */

    }

    private static class ViewHolder{
        TextView tv_horasMinutos;
        TextView tv_segundos;
    }
}