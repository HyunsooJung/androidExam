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
    //db연결
    private SQLiteHelper dbHelper;
    //리사이클러뷰
    private RecyclerView recyclerView;
    //리사이클러어댑터
    private RecyclerAdpater recyclerAdpater;
    //메모추가,취소,삭제 버튼
    private Button btnAdd, btnCanc, btndel;
    //메모 리스트
    List<Memo> memoList;

    //화면 초기화
    void selectAll(){
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotePadActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        memoList = dbHelper.selectAll();
        recyclerAdpater = new RecyclerAdpater(memoList);
        recyclerView.setAdapter(recyclerAdpater);
        recyclerAdpater.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //메모추가
        if(requestCode == 0){
            Memo addMemo = (Memo)data.getSerializableExtra("addMemo");
            recyclerAdpater.addItem(addMemo);
            dbHelper.InsertMemo(addMemo);
            recyclerAdpater.notifyDataSetChanged();
            selectAll();


        }
        //메모수정
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
        //메모화면 넘기기 이벤트
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotePadActivity.this, AddActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnCanc = findViewById(R.id.btnCc);
        //취소하기 이벤트
        btnCanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    //어댑터
    public class RecyclerAdpater extends RecyclerView.Adapter<RecyclerAdpater.ItemViewHolder> {
        //메모리스트
        private List<Memo> listdata;
        //체크박스버튼 보이는거에 필요한 불린값
        private boolean isDelete = false;
        //메모삭제 버튼
        private Button btnDel = findViewById(R.id.btnDel);
        //메모추가 버튼
        private Button btnAdd = findViewById(R.id.btnAdd);
        //취소 버튼
        private Button btnCc = findViewById(R.id.btnCc);

        //메모 삭제시 필요한 시퀀스 어레이
        int[] CkAr= new int[memoList.size()];
        //화면에서 메모삭제위한 포지션 어레이
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
            //불린값에 따라 체크박스버튼 보이는지 여부, true면 보이게
            holder.btn_ch.setVisibility(isDelete ? View.VISIBLE : View.GONE);

        }

        //메모추가
        void addItem (Memo memo){
            listdata.add(memo);
        }

        //삭제
        void removeItem(int position){
            listdata.remove(position);
        }

        @Override
        public int getItemCount() {
            return listdata.size();
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            //제목
            private TextView mtitle;
            //내용
            private TextView mcontent;
            //날짜
            private TextView mdate;
            //체크박스
            private CheckBox btn_ch;

            public ItemViewHolder(@NonNull View itemView) {

                super(itemView);
                mtitle = (TextView)itemView.findViewById(R.id.mtitle);
                this.mcontent = itemView.findViewById(R.id.mcontent);
                this.mdate = itemView.findViewById(R.id.mdate);
                this.btn_ch = itemView.findViewById(R.id.btn_ch);

                //화면 길게누를시 체크박스 띄우고 삭제버튼 취소버튼 나오게함
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

                        //각 위치
                        int position = getAdapterPosition();
                        //각 위치에서의 시퀀스번호
                        int seq = (int)mtitle.getTag();

                        //체크했을시 각 위치에 시퀀스와 위치값
                        if (btn_ch.isChecked()){
                            CkAr[position] = seq;
                            PoAr[position] = position;
                        }
                        //체크 해제했을시 각위치의 시퀀스는 0
                        else {
                            CkAr[position] = 0;
                        }
                    }
                });

                //삭제
                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder adb = new AlertDialog.Builder(NotePadActivity.this);
                        adb.setTitle("삭제");
                        adb.setMessage("메모를 삭제하시겠습니까?");

                        //삭제취소
                        adb.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        //삭제
                        adb.setNegativeButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //메모 위치, 시퀀스 값 넣고 삭제
                                for(int j=CkAr.length-1; j>=0; j--){
                                    //시퀀스는 1부터 시작 따라서 0보다 큰값일 경우
                                    if(CkAr[j]>0){
                                        dbHelper.deleteMemo(CkAr[j]);
                                        removeItem(PoAr[j]);
                                    }
                                }
                                notifyDataSetChanged();
                                //삭제시 삭제버튼과 취소버튼 숨기고 메모하기 버튼 보이기, 체크박스 안보이기
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

                //해당 메모 한번 클릭시 수정화면이동
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        int seq = (int)mtitle.getTag();

                        //화면이동시 메모수정에 필요한 데이터 넘기기
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