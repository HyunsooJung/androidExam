package com.android.exam;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.R;


public class MainActivity extends AppCompatActivity {

    private Button btn_start, btn_record, btn_pause, btn_reset, btn_exit;//시작,기록,일시정지,리셋,종료
    private TextView timeView, recordView; // 스탑워치시간, 기록시간
    private Thread timeThread = null;
    private Boolean isRunning = true;

    private Handler mHandler = new Handler();
    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.btn_start);//시작버튼
        btn_record = findViewById(R.id.btn_record);//기록버튼
        btn_pause = findViewById(R.id.btn_pause);//일시정지버튼
        btn_reset = findViewById(R.id.btn_reset);//리셋버튼
        timeView = findViewById(R.id.timeView);//스탑워치시간
        recordView = findViewById(R.id.recordView);//기록시간
        btn_exit = findViewById(R.id.btn_exit);//앱 종료

        //앱종료
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(com.android.exam.MainActivity.this);
                ad.setTitle("스탑워치");
                ad.setMessage("앱을 종료하시겠습니까?");

                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });

                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ad.show();
            }
        });

        //시작버튼 누를시
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("run : "+isRunning);
                v.setVisibility(View.GONE);
                //시작버튼 누를시 기록, 일시정지, 리셋버튼 보이기
                btn_record.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.VISIBLE);
                btn_reset.setVisibility(View.VISIBLE);

                run();
                mHandler.postDelayed(runnable, 10);
                System.out.println("run12 : "+isRunning);
            }
        });

        //리셋버튼 누를시
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                //기록,일시정지 버튼 숨기고 시작버튼 보이기
                btn_record.setVisibility(View.GONE);
                btn_pause.setVisibility(View.GONE);
                btn_start.setVisibility(View.VISIBLE);
                //기록시간 초기화
                recordView.setText("");


                timeView.setText("");
                timeView.setText("00:00:00:00");
                mHandler.removeCallbacks(runnable);
                //isRunning = false;
                //중지버튼 눌렀을시 isrunning이 false여서
                System.out.println("resetisRun : " + isRunning);
                if(isRunning.equals(false)){
                    btn_pause.setText("중지");
                    isRunning = true;
                    mHandler.removeCallbacks(runnable);
                }
            }
        });

        //기록버튼 누를시
        btn_record.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //한줄씩 기록 보이기
                recordView.setText(recordView.getText() + timeView.getText().toString() + "\n");
            }
        });

        //일시정지버튼 누를시
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = !isRunning;
                System.out.println("isRunjning: "+isRunning);
                if(isRunning) {
                    System.out.println("isssss123213s : " + isRunning);

                    mHandler.postDelayed(runnable,0);
                    btn_pause.setText("중지");
                }
                else {
                    System.out.println("issssss : " + isRunning);
                    mHandler.removeCallbacks(runnable);
                    btn_pause.setText("시작");
                }
            }
        });

        System.out.println("isrunning"+isRunning);
        /*runnable = new Runnable() {
            long startTime = System.currentTimeMillis();
            @Override
            public void run() {
                System.out.println("isrunning1"+isRunning);
                System.out.println("bbbbbbbbb");
                long endTime = System.currentTimeMillis();
                long time = endTime-startTime;
                long mSec = time / 10;
                long sec = mSec / 100;
                long min = sec / 60;
                long hour = min / 360;

                @SuppressLint("DefaultLocale") String a = String.format("%02d:%02d:%02d:%02d",hour % 100, min % 60, sec % 60, mSec % 100);
                System.out.println("결과값"+a);
                timeView.setText(a);
                mHandler.postDelayed(runnable, 10);
            }
        };*/

    }

    public void run(){
        if(isRunning){
            System.out.println("sysrunning"+ isRunning);
            runnable = new Runnable() {
                long startTime = System.currentTimeMillis();
                @Override
                public void run() {
                    System.out.println("isrunning1"+isRunning);
                    System.out.println("bbbbbbbbb");
                    long endTime = System.currentTimeMillis();
                    long time = endTime-startTime;
                    long mSec = time / 10;
                    long sec = mSec / 100;
                    long min = sec / 60;
                    long hour = min / 360;

                    @SuppressLint("DefaultLocale") String a = String.format("%02d:%02d:%02d:%02d",hour % 100, min % 60, sec % 60, mSec % 100);
                    System.out.println("결과값"+a);
                    timeView.setText(a);
                    mHandler.postDelayed(runnable, 10);
                }
            };
        }

    }

    /*@SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            *//*int mSec = msg.arg1 % 100;
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            int hour = (msg.arg1 / 100) / 360;*//*
            int mSec = msg.arg1 / 10;
            int sec = mSec / 100;
            int min = sec / 60;
            int hour = min / 360;
            //1000이 1초 1000*60 은 1분 1000*60*10은 10분 1000*60*60은 한시간

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d:%02d", hour % 100, min % 60, sec % 60, mSec % 100 );
            if (result.equals("00:00:01:15")) {
                Toast.makeText(MainActivity.this, "1분 15초가 지났습니다.", Toast.LENGTH_SHORT).show();
            }
            System.out.println(result);
            timeView.setText(result);
        }
    };*/

   /*@SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            int mSec = msg.arg1 / 10;
            int sec = mSec / 100;
            int min = sec / 60;
            int hour = min / 360;

            String a = String.format("%02d:%02d:%02d:%02d",hour % 100, min % 60, sec % 60, mSec % 100);
            System.out.println("결과값"+a);
            timeView.setText(a);

        }
    };*/

    /*private void convertTime(long time) {
        long mSec = time / 10;
        long sec = mSec / 100;
        long min = sec / 60;
        long hour = min / 360;

        @SuppressLint("DefaultLocale") String a = String.format("%02d:%02d:%02d:%02d",hour % 100, min % 60, sec % 60, mSec % 100);
        System.out.println("결과값"+a);
        timeView.setText(a);
    }*/

    /*public Thread starts() {
        long currentTime = System.currentTimeMillis();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                while (isRunning){
                    long endTime = System.currentTimeMillis();
                    long time = endTime - currentTime;
                    System.out.println("시간 : "+time);
                    System.out.println("현재시간 : "+currentTime);
                    System.out.println("끝나는시간 : "+endTime);
                    convertTime(time);
                   Message msg = new Message();
                    msg.arg1 = (int) time;

                    handler.handleMessage(msg);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeView.setText("");
                                timeView.setText("00:00:00:00");
                            }
                        });
                        return;//인터럽트 받을 경우 return
                    }
                }
            }
        }, 10);
        return null;
    }*/

    /*public class timeThread implements Runnable {
        long currentTime = System.currentTimeMillis();
        @Override
        public void run() {
            int i = 0;

            while (true) {
                while (isRunning) { //일시정지 누를시 멈춤
                    long endTime = System.currentTimeMillis();
                    long time = endTime - currentTime;
                    Message msg = new Message();
                    msg.arg1 = (int) time;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeView.setText("");
                                timeView.setText("00:00:00:00");
                            }
                        });
                        return;//인터럽트 받을 경우 return
                    }
                }
            }
        }
    }*/

}