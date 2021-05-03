package com.android.notepad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.R;

import java.util.ArrayList;
import java.util.List;

public class NotePadActivity extends AppCompatActivity {

    private SQLiteHelper dbHelper;

    private RecyclerView recyclerView;
    private RecyclerAdpater recyclerAdpater;
    private Button btnAdd;

    List<Memo> memoList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            String strMain = data.getStringExtra("main");
            String strMain2 = data.getStringExtra("contents");
            String strSub = data.getStringExtra("sub");

            Memo memo = new Memo(strMain, strMain2, strSub, 0);
            recyclerAdpater.addItem(memo);
            recyclerAdpater.notifyDataSetChanged();

            dbHelper.InsertMemo(memo);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noactivity_main);

        dbHelper = new SQLiteHelper(NotePadActivity.this);
        memoList = dbHelper.selectAll();

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotePadActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdpater = new RecyclerAdpater(memoList);
        recyclerView.setAdapter(recyclerAdpater);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotePadActivity.this, AddActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }
    public class RecyclerAdpater extends RecyclerView.Adapter<RecyclerAdpater.ItemViewHolder> {
        private List<Memo> listdata;

        public RecyclerAdpater(List<Memo> listdata) {
            this.listdata = listdata;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.memo_item, viewGroup, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Memo memo = listdata.get(position);

            holder.mtitle.setTag(memo.getSeq());

            holder.mtitle.setText(memo.getMtitle());
            holder.mcontent.setText(memo.getMcontent());
            holder.mdate.setText(memo.getMdate());

        }

        void addItem (Memo memo){
            listdata.add(memo);
        }

        void removeItem(int position){
            listdata.remove(position);
        }

        @Override
        public int getItemCount() {
            return listdata.size();
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {

            private TextView mtitle;
            private TextView mcontent;
            private TextView mdate;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                mtitle = (TextView)itemView.findViewById(R.id.mtitle);
                this.mcontent = itemView.findViewById(R.id.mcontent);
                this.mdate = itemView.findViewById(R.id.mdate);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        int position = getAdapterPosition();
                        int seq = (int)mtitle.getTag();
                        Log.v("c: ","seq : "+seq);
                        if(position != RecyclerView.NO_POSITION){
                            Log.v("123c: ","seq : "+seq);
                            dbHelper.deleteMemo(seq);
                            removeItem(position);
                            notifyDataSetChanged();
                        }

                        return false;
                    }
                });
            }
        }
    }
}