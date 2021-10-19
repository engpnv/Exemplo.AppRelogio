package com.example.exemploapprelogio;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class FullscreenActivity2 extends AppCompatActivity {

    private ViewHolder viewHolder = new ViewHolder();
    //parte 3 handler e runnable

    private Handler handler = new Handler();
    private Runnable runnable;
    //
    //parte 5
    private boolean runnableStopped = false;
    private boolean bateriaChecked = true;
    //
//parte v1.1
    private BroadcastReceiver bateriaReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int nivel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            viewHolder.tv_nivelBateria.setText(String.valueOf(nivel) + "%");

        }
    };
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen2);
        viewHolder.tv_horasMinutos = findViewById(R.id.tv_horasMinutos);
        viewHolder.tv_segundos = findViewById(R.id.tv_segundos);
        viewHolder.tv_nivelBateria = findViewById(R.id.tv_nivelBateria);
        viewHolder.cb_nivelBateria = findViewById(R.id.cb_nivelBateria);
        viewHolder.iv_preferencias = findViewById(R.id.iv_preferencias);
        viewHolder.iv_sair = findViewById(R.id.iv_sair);
        viewHolder.ll_menu = findViewById(R.id.ll_menu);
//parte 2 Atualizar hora
        //parte 4 metodo vai para dentro do onResume()
        // AtualizarHorario();

//
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//parte v1.1
        registerReceiver(bateriaReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        viewHolder.cb_nivelBateria.setChecked(true);


        viewHolder.cb_nivelBateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bateriaChecked) {
                    bateriaChecked = false;
                    viewHolder.tv_nivelBateria.setVisibility(View.GONE);

                } else {
                    bateriaChecked = true;
                    viewHolder.tv_nivelBateria.setVisibility(View.VISIBLE);
                }
            }
        });


        viewHolder.ll_menu.animate().translationY(500);

        viewHolder.iv_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.ll_menu.animate()
                        .translationY(viewHolder.ll_menu.getMeasuredHeight())
                        .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));

            }
        });

        viewHolder.iv_preferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.ll_menu.setVisibility(View.VISIBLE);
                viewHolder.ll_menu.animate()
                        .translationY(0)
                        .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
            }
        });
//


    }


    //parte 4
    @Override
    protected void onResume() {
        super.onResume();
        runnableStopped = false;
        AtualizarHorario();
    }

    @Override
    protected void onStop() {
        super.onStop();
        runnableStopped = true;
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
                if (runnableStopped)
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
                long proximo = agora + (1000 - (agora % 1000));

                handler.postAtTime(runnable, proximo);
                //
                //parte v1.1
                viewHolder.tv_nivelBateria = findViewById(R.id.tv_nivelBateria);
                viewHolder.cb_nivelBateria = findViewById(R.id.cb_nivelBateria);
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

    private static class ViewHolder {
        TextView tv_horasMinutos;
        TextView tv_segundos;
        //parte v1.1
        CheckBox cb_nivelBateria;
        TextView tv_nivelBateria;
        ImageView iv_sair;
        ImageView iv_preferencias;
        LinearLayout ll_menu;

    }
}