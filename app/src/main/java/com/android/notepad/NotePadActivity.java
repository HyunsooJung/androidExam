package com.android.notepad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NotePadActivity extends AppCompatActivity {

    private SQLiteHelper dbHelper;

    private RecyclerView recyclerView;
    private RecyclerAdpater recyclerAdpater;
    private Button btnAdd, btnCanc, btndel;

    List<Memo> memoList;

    void selectAll(){
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotePadActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        memoList = dbHelper.selectAll();
        recyclerAdpater = new RecyclerAdpater(memoList);
        recyclerView.setAdapter(recyclerAdpater);
        recyclerAdpater.notifyDataSetChanged();
    }

    int i=0;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            Memo addMemo = (Memo)data.getSerializableExtra("addMemo");
            recyclerAdpater.addItem(addMemo);
            dbHelper.InsertMemo(addMemo);
            recyclerAdpater.notifyDataSetChanged();
            selectAll();


        }
        if(requestCode == 1){
            Memo memos = (Memo)data.getSerializableExtra("memos");
            String uDate = data.getStringExtra("uDate");

            memos.setMdate(uDate);

            dbHelper.updateMemo(memos);
            recyclerAdpater.notifyDataSetChanged();
            selectAll();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        recyclerAdpater.notifyDataSetChanged();

        btnAdd = findViewById(R.id.btnAdd);
        btndel = findViewById(R.id.btnDel);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotePadActivity.this, AddActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnCanc = findViewById(R.id.btnCc);
        btnCanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=0;
                recyclerView = findViewById(R.id.recycler_view);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotePadActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);

                memoList = dbHelper.selectAll();
                recyclerAdpater = new RecyclerAdpater(memoList);
                recyclerView.setAdapter(recyclerAdpater);
                btnAdd.setVisibility(View.VISIBLE);
                btnCanc.setVisibility(View.GONE);
                btndel.setVisibility(View.GONE);
                recyclerAdpater.notifyDataSetChanged();
            }
        });

    }
    public class RecyclerAdpater extends RecyclerView.Adapter<RecyclerAdpater.ItemViewHolder> {

        private List<Memo> listdata;
        private boolean isDelete = false;
        private boolean isbtn = false;
        private Button btnDel = findViewById(R.id.btnDel);
        private Button btnAdd = findViewById(R.id.btnAdd);
        private Button btnCc = findViewById(R.id.btnCc);

        int[] CkAr= new int[memoList.size()];
        int[] PoAr= new int[memoList.size()];

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
            holder.btn_ch.setVisibility(isDelete ? View.VISIBLE : View.GONE);

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
            private CheckBox btn_ch;

            public ItemViewHolder(@NonNull View itemView) {

                super(itemView);
                mtitle = (TextView)itemView.findViewById(R.id.mtitle);
                this.mcontent = itemView.findViewById(R.id.mcontent);
                this.mdate = itemView.findViewById(R.id.mdate);
                this.btn_ch = itemView.findViewById(R.id.btn_ch);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        isDelete = true;
                        btnDel.setVisibility(View.VISIBLE);
                        btnAdd.setVisibility(View.GONE);
                        btnCc.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                        return false;
                    }
                });

                //메모 버튼 클릭
                btn_ch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int position = getAdapterPosition();
                        int seq = (int)mtitle.getTag();

                        if (btn_ch.isChecked()){
                            CkAr[position] = seq;
                            PoAr[position] = position;
                            i++;
                        }
                        else {
                            i--;
                        }

                        Log.v("i: ","i :"+ i);


                    }
                });

                //삭제
                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder adb = new AlertDialog.Builder(NotePadActivity.this);
                        adb.setTitle("삭제");
                        adb.setMessage("메모를 삭제하시겠습니까?");

                        adb.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        adb.setNegativeButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                for(int j=CkAr.length-1; j>=0; j--){
                                    if(CkAr[j]>0){
                                        Log.v("ckar: ","ckar: "+CkAr[j]);
                                        Log.v("PoAr: ","PoAr: "+PoAr[j]);
                                        Log.v("ckarlength: ","ckarlength: "+CkAr.length);
                                        dbHelper.deleteMemo(CkAr[j]);
                                        removeItem(PoAr[j]);
                                    }
                                }
                                i=0;
                                notifyDataSetChanged();
                                recyclerView = findViewById(R.id.recycler_view);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotePadActivity.this);
                                recyclerView.setLayoutManager(linearLayoutManager);

                                memoList = dbHelper.selectAll();
                                recyclerAdpater = new RecyclerAdpater(memoList);
                                recyclerView.setAdapter(recyclerAdpater);
                                btnAdd.setVisibility(View.VISIBLE);
                                btnCanc.setVisibility(View.GONE);
                                btndel.setVisibility(View.GONE);
                                recyclerAdpater.notifyDataSetChanged();
                            }
                        });
                        adb.show();
                    }
                });

                //수정화면이동
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        int seq = (int)mtitle.getTag();

                        if(position != RecyclerView.NO_POSITION){
                            dbHelper.selectOne(seq);
                            Memo memo = listdata.get(position);
                            memo.setSeq(seq);
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ModActivity.class);
                            intent.putExtra("memo",memo);
                            startActivityForResult(intent,1);
                        }
                    }
                });
            }
        }
    }
}