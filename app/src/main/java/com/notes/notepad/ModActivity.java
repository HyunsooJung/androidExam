package com.notes.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.notes.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModActivity extends AppCompatActivity {

    private TextView tdate;
    private EditText titlem, contentm;
    private Button btn_mod, btn_cancel;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ModActivity.this, NotePadActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod);

        tdate = findViewById(R.id.tdate);
        titlem = findViewById(R.id.titlem);
        contentm = findViewById(R.id.contentm);
        btn_mod = findViewById(R.id.btn_mod);
        btn_cancel = findViewById(R.id.btn_cancel);

        Intent intent = getIntent();

        Memo memo = (Memo)intent.getSerializableExtra("memo");

        titlem.setText(memo.getMtitle());
        contentm.setText(memo.getMcontent());
        tdate.setText(memo.getMdate());

        Log.v("titlem : ","titlem : "+titlem);
        btn_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memo memos = new Memo(titlem.getText().toString(),contentm.getText().toString(),tdate.getText().toString());
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

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}