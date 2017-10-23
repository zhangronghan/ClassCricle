package com.example.jia.classcircle.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jia.classcircle.R;
import com.example.jia.classcircle.activity.bmobTable.Classmates;
import com.example.jia.classcircle.activity.util.ImageViewPlus;

import java.util.List;

/**
 * Created by jia on 2017/10/20.
 */

public class ShowClassmatesAdapter extends RecyclerView.Adapter<ShowClassmatesAdapter.ViewHolder> {
    private List<Classmates> classmatesList;

    public ShowClassmatesAdapter(List<Classmates> classmatesList){
        this.classmatesList=classmatesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.classmates_item,parent,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Classmates classmates=classmatesList.get(position);
        holder.imageViewPlus.setImageBitmap(classmates.getHeadImg());
        holder.tv_user_name.setText(classmates.getUserName());
       // if(classmates.getIdentity()==null){//管理员
         //   holder.tv_user_classMates_identity.setText("管理员");
       // }else {
            holder.tv_user_classMates_identity.setText(classmates.getIdentity());
       // }

    }

    @Override
    public int getItemCount() {
        if(classmatesList!=null){
            return classmatesList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageViewPlus imageViewPlus;
        TextView tv_user_name;
        TextView tv_user_classMates_identity;
        public ViewHolder(View itemView) {
            super(itemView);
            imageViewPlus= (ImageViewPlus) itemView.findViewById(R.id.imageViewPlus);
            tv_user_name= (TextView) itemView.findViewById(R.id.tv_user_classMates_name);

            tv_user_classMates_identity= (TextView) itemView.findViewById(R.id.tv_user_classMates_identity);

        }
    }
    public void updateData(List<Classmates> classmatesList){
        this.classmatesList=classmatesList;
        notifyDataSetChanged();
    }
}
