package com.android.deign;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.R;
import com.android.exam.StopWatchActivity;


public class DesignActivity extends AppCompatActivity {

    private ImageButton btn_outs;//종료
    private Button btn_move;
    private TextView tv_exam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.deactivity_main);

        btn_outs = findViewById(R.id.btn_outs);
        btn_move = findViewById(R.id.btn_move);
        tv_exam = findViewById(R.id.tv_exam);

        Intent intent = getIntent();
        String str = intent.getStringExtra("str");

        tv_exam.setText(str);

        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DesignActivity.this, StopWatchActivity.class);
                startActivity(intent);
            }
        });

        //앱종료
        btn_outs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder out = new AlertDialog.Builder(DesignActivity.this);
                out.setTitle("종료");
                out.setMessage("앱을 종료하시겠습니까?");

                out.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                out.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                out.show();
            }
        });
    }
}