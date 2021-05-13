package com.notes.notepad;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.notes.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    EditText edtTitle, edtMemo;
    Button btnDone, btnNo;
//    Button btn_mod;
    TextView dates;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        return true;
    }

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
        dates= findViewById(R.id.date);

        btnDone = findViewById(R.id.btnDone);
        btnNo = (Button)findViewById(R.id.btnNo);
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

        //btn_mod = findViewById(R.id.btn_mod);


        //메모 수정, 인텐트로 데이터 받기
/* 한 화면에서 수정, 메모추가 하기
        Intent intent = getIntent();

        if(intent.getSerializableExtra("memo") != null){

            btn_mod.setVisibility(View.VISIBLE);
            btnDone.setVisibility(View.GONE);
            Log.v("aaa","aaa");
            Memo memo = (Memo)intent.getSerializableExtra("memo");
            edtTitle.setText(memo.getMtitle());
            edtMemo.setText(memo.getMcontent());
            dates.setText(memo.getMdate());

            btn_mod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Memo memos = new Memo(edtTitle.getText().toString(),edtMemo.getText().toString(),dates.getText().toString());
                    memos.setSeq(memo.getSeq());
                    if(memos.getMtitle().length() > 0 && memos.getMcontent().length() > 0 ) {
                        Date date = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String subDate = simpleDateFormat.format(date);

                        Intent intent = new Intent();
                        intent.putExtra("memos",memos);
                        intent.putExtra("uDate", subDate);
                        setResult(1, intent);
                        finish();
                    }
                }
            });
        }
*/
    }
}