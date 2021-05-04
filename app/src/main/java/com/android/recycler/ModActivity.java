package com.android.recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.R;
import com.android.notepad.AddActivity;
import com.android.notepad.Memo;
import com.android.notepad.NotePadActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModActivity extends AppCompatActivity {

    private TextView tdate;
    private EditText titlem, contentm;
    private Button btn_mod, btn_cancel;

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

        int seq = intent.getIntExtra("seq",0);
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String date = intent.getStringExtra("date");

        titlem.setText(title);
        contentm.setText(content);
        tdate.setText(date);
        Log.v("title : ","title : "+title);
        Log.v("seq : ","seq : "+seq);

        btn_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = titlem.getText().toString();
                String str2 = contentm.getText().toString();
                if(str.length() > 0 && str2.length() > 0 ) {
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String subDate = simpleDateFormat.format(date);

                    Intent intent = new Intent();
                    intent.putExtra("uSeq",seq);
                    intent.putExtra("uTitle", str);
                    intent.putExtra("uContent", str2);
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