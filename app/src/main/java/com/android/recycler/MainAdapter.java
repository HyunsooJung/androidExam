package com.android.recycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private ArrayList<ActivityData> arrayList = new ArrayList<>();
    private Activity activity;

    public MainAdapter(Activity activity, ArrayList<ActivityData> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_name.setText(arrayList.get(position).getTv_name());
        holder.appName.setText(arrayList.get(position).getAppName());

        if(holder.appName.getText()==""){
            Log.v("an","an : "+holder.appName.getText().toString());
            holder.line1.setVisibility(View.GONE);
            holder.line2.setVisibility(View.VISIBLE);
        }else{
            holder.line1.setVisibility(View.VISIBLE);
            holder.line2.setVisibility(View.GONE);
        }

        holder.move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(v.getContext(),arrayList.get(position).getCls());
                context.startActivity(intent);
                if (activity != null) {
                    activity.finish();
                }
            }
        });

        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),position + "클릭되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position) {

        try {
            arrayList.remove(position);
            notifyDataSetChanged();//notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        protected Button btn_del;
        protected TextView tv_name;
        protected TextView appName;
        protected Button move;
        protected LinearLayout line1;
        protected LinearLayout line2;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.btn_del = itemView.findViewById(R.id.btn_del);
            this.tv_name = itemView.findViewById(R.id.tv_name);
            this.appName = itemView.findViewById(R.id.appName);
            this.move = itemView.findViewById(R.id.move);
            this.line1 = itemView.findViewById(R.id.line1);
            this.line2 = itemView.findViewById(R.id.line2);

        }
    }
}
