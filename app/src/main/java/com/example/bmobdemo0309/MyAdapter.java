package com.example.bmobdemo0309;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.security.PublicKey;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 日不落 on 2017/3/9.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Person> personList;
    private onDelListener listener;


    public MyAdapter(List<Person> personList,onDelListener listener){
        this.personList = personList;
        this.listener = listener;


    }

    public void changeData(List<Person> personList){
        this.personList = personList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView headView;
        private TextView nameView;
        private TextView ageView;
        private Button btnDel;

        public ViewHolder(View itemView) {
            super(itemView);
            headView = (ImageView) itemView.findViewById(R.id.head_img);
            nameView = (TextView) itemView.findViewById(R.id.name_text);
            ageView = (TextView) itemView.findViewById(R.id.age_text);
            btnDel = (Button) itemView.findViewById(R.id.btn_del);



        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final  Person p = personList.get(position);
        BmobFile file = p.getHeadImg();
        if(file != null){
            String imgUrl = file.getFileUrl();
            if(imgUrl != null){
                Glide.with(holder.headView.getContext()).load(imgUrl).into(holder.headView);

            }
        }
        holder.nameView.setText(p.getName());
        holder.ageView.setText(String.valueOf(p.getAge()));
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        listener.refresh();
                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return personList.size();
    }


}
