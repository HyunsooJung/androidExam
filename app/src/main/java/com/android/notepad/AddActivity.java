package com.android.notepad;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    EditText edtTitle, edtMemo;
    Button btnDone, btnNo;
    TextView dates;

    //메모 추가 취소시 전화면으로 이동
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddActivity.this, NotePadActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtTitle = findViewById(R.id.edtTitle);
        edtMemo = findViewById(R.id.edtMemo);

        btnDone = findViewById(R.id.btnDone);
        btnNo = (Button)findViewById(R.id.btnNo);

        dates= findViewById(R.id.date);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dates.setText("오늘날짜 : "+sdf.format(d));
        //메모추가 이벤트
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = edtTitle.getText().toString();
                String str2 = edtMemo.getText().toString();
                if(str.length() > 0 && str2.length() > 0 ){
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String substr = simpleDateFormat.format(date);

                    Memo addMemo = new Memo(str,str2,substr);

                    Intent intent = new Intent();
                    intent.putExtra("addMemo", addMemo);
                    setResult(0,intent);
                    finish();
                }
            }
        });

        //메모추가 취소 버튼 이벤트
        btnNo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}