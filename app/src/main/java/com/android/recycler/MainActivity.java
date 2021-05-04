package com.android.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.R;
import com.android.deign.DesignActivity;
import com.android.exam.StopWatchActivity;
import com.android.notepad.NotePadActivity;
import com.android.sharedpreference.SharedPreferenceActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ActivityData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reactivity_main);

        recyclerView = findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        mainAdapter = new MainAdapter(this, arrayList);
        recyclerView.setAdapter((mainAdapter));

        ActivityData mainData = new ActivityData();
        mainData.setAppName("디자인");
        mainData.setCls(DesignActivity.class);
        arrayList.add(mainData);
        mainAdapter.notifyDataSetChanged();

        ActivityData data = new ActivityData();
        data.setAppName("스탑워치");
        data.setCls(StopWatchActivity.class);
        arrayList.add(data);
        mainAdapter.notifyDataSetChanged();

        ActivityData shdata = new ActivityData();
        shdata.setAppName("shared");
        shdata.setCls(SharedPreferenceActivity.class);
        arrayList.add(shdata);
        mainAdapter.notifyDataSetChanged();

        ActivityData ndata = new ActivityData();
        ndata.setAppName("note");
        ndata.setCls(NotePadActivity.class);
        arrayList.add(ndata);
        mainAdapter.notifyDataSetChanged();

        Button btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityData mainData = new ActivityData();
                mainData.setTv_name("정현수"+(i++));
                mainData.setBtn_del(1);
                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged();

            }
        });

    }
}