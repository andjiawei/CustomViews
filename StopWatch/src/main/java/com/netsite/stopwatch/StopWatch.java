package com.netsite.stopwatch;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 描述:
 * 作者：zhangjiawei
 * 时间：2016/10/25
 */
public class StopWatch extends TextView {

    private static final int DEFAULT_MESSAGE =100 ;
    private  Context context;
    int time;

    public StopWatch(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public StopWatch(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public StopWatch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    private void init() {
        setText("00:00:00");//默认值
        startTimer();
    }

    public void startTimer(){
        handler.sendEmptyMessage(DEFAULT_MESSAGE);
    }

    public void stopTimer(){
        handler.removeMessages(DEFAULT_MESSAGE);
        setText("00:00:00");//默认值
    }

   Handler handler =new Handler(Looper.getMainLooper()){

       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);

           if (time >= 3600) {
               setText(String.format("%d:%02d:%02d", time / 3600, (time % 3600) / 60, (time % 60)));
           } else {
               setText(String.format("00:%02d:%02d", (time % 3600) / 60, (time % 60)));
           }

           time++;
           handler.sendEmptyMessageDelayed(DEFAULT_MESSAGE,1000);
       }
    };
}
