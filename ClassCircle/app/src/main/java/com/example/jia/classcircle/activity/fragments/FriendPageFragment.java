package com.example.jia.classcircle.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jia.classcircle.R;
import com.example.jia.classcircle.activity.activity.ShowClassmatesActivity;

/**
 * Created by jia on 2017/9/18.
 */

public class FriendPageFragment extends Fragment{//展示同个班级的同学（自己除外）
    public FriendPageFragment(){}
  /*  private RecyclerView recyclerView_show_classmate;
    private List<Classmates> classmatesList=new ArrayList<>();
    private ShowClassmatesAdapter classmatesAdapter=new ShowClassmatesAdapter(classmatesList);
    private List<AllTheClass> classList=new ArrayList<>();
    private APPUser user= BmobUser.getCurrentUser(APPUser.class);
    private SwipeRefreshLayout swipe_refresh;*/
    private TextView tv_showStuResult;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_friend_page,container,false);
        tv_showStuResult= (TextView) v.findViewById(R.id.tv_showStuResult);

       /* swipe_refresh= (SwipeRefreshLayout) v.findViewById(swipe_refresh);
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary);
        RecyclerView recyclerView_show_classmate= (RecyclerView) v.findViewById(R.id.recyclerView_show_classmate);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView_show_classmate.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView_show_classmate.setLayoutManager(layoutManager);
        recyclerView_show_classmate.setAdapter(classmatesAdapter);
        initData();*/
      /*
      点击传送到新界面*/
        tv_showStuResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                传递到展示同班同学界面
                * */
                startActivity(new Intent(getContext(), ShowClassmatesActivity.class));
            }
        });
      /*  swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reFreshData();
            }


        });*/








        return v;
    }

    /*private void reFreshData() {
        final List<APPUser> temporrary=new ArrayList<>();
        BmobQuery<AllTheClass>query=new BmobQuery<>();
        query.addWhereEqualTo("className",user.getClassName());
        query.findObjects(new FindListener<AllTheClass>() {
            @Override
            public void done(List<AllTheClass> list, BmobException e) {
                if(e==null){
                    classmatesList.clear();
                    classList=list;
                    List<APPUser> appUserList=list.get(0).getStu();
                    for(int i=0;i<appUserList.size();i++){ //把除自己外的本班学生筛选
                        APPUser a=appUserList.get(i);
                        if(user.getUsername().equals(a.getUsername())){
                            continue;
                        }else {
                            temporrary.add(a);
                            Classmates classmates=new Classmates();
                            classmates.setUserName(a.getUsername());
                            classmates.setHeadImg(getBitmapFromByte(a.getImg_msg()));
                            classmates.setIdentity(a.getIndentity());
                            classmatesList.add(classmates);
                        }


                    }
                    List<Classmates> list1=classmatesList;
                    classmatesAdapter.updateData(list1);
                   // classmatesList.clear();
                  //  Log.e("查询日志：","AllTheClass表的size"+list.size());
                    Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();
                    swipe_refresh.setRefreshing(false);
                }else {
                    Log.e("查询日志：","msg:"+e.getMessage().toString()+"   code"+e.getErrorCode());
                    Toast.makeText(getContext(),"班级数据获取失败，请刷新一次",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/

   /* private void initData() {
        Toast.makeText(getContext(),"获取数据中，请稍等",Toast.LENGTH_SHORT).show();
        final List<APPUser> temporrary=new ArrayList<>();
        BmobQuery<AllTheClass>query=new BmobQuery<>();
        query.addWhereEqualTo("className",user.getClassName());
        query.findObjects(new FindListener<AllTheClass>() {
            @Override
            public void done(List<AllTheClass> list, BmobException e) {
                if(e==null){
                    classList=list;
                    List<APPUser> appUserList=list.get(0).getStu();
                    for(int i=0;i<appUserList.size();i++){ //把除自己外的本班学生筛选
                        APPUser a=appUserList.get(i);
                        if(user.getUsername().equals(a.getUsername())){
                            continue;
                        }else {
                            temporrary.add(a);
                            Classmates classmates=new Classmates();
                            classmates.setUserName(a.getUsername());
                            classmates.setHeadImg(getBitmapFromByte(a.getImg_msg()));
                            classmates.setIdentity(a.getIndentity());
                            classmatesList.add(classmates);
                        }


                    }
                    classmatesAdapter.updateData(classmatesList);
                   // classmatesList.clear();
                    Log.e("查询日志：","AllTheClass表的size"+list.size());

                }else {
                    Log.e("查询日志：","msg:"+e.getMessage().toString()+"   code"+e.getErrorCode());
                    Toast.makeText(getContext(),"班级数据获取失败，请刷新一次",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/

    /*public Bitmap getBitmapFromByte(byte[] temp){
        if(temp != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        }else{
            return null;
        }
    }*/
}
