package com.example.jia.classcircle.activity.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jia.classcircle.R;
import com.example.jia.classcircle.activity.activity.AlertUserMsgActivity;
import com.example.jia.classcircle.activity.bmobTable.APPUser;
import com.example.jia.classcircle.activity.util.DayNightEvent;
import com.example.jia.classcircle.activity.util.MyToggleButton;
import com.example.jia.classcircle.activity.util.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by jia on 2017/9/18.
 */

public class MyPageFragment extends Fragment {//修改成功后，需要将数据刷新

    public MyPageFragment() {
    }

    APPUser currentUser = APPUser.getCurrentUser(APPUser.class);
    private ImageView userHeadView;
    private TextView tv_user_name;
    private Bitmap head_bitmap;
    private MyToggleButton mToggleButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_page, container, false);
        refreshMsg();
        byte[] img = currentUser.getImg_msg();
        head_bitmap = getBitmapFromByte(img);
        userHeadView = (ImageView) v.findViewById(R.id.userImageView);
        ImageView iv_alert = (ImageView) v.findViewById(R.id.iv_alert);//点击修改，跳到新界面
        tv_user_name = (TextView) v.findViewById(R.id.tv_user_name);
        TextView tv_user_class = (TextView) v.findViewById(R.id.tv_user_class);
        TextView tv_user_identity = (TextView) v.findViewById(R.id.tv_user_identity);
        mToggleButton= (MyToggleButton) v.findViewById(R.id.toggleButton);

        tv_user_name.setText(currentUser.getUsername());
        tv_user_class.setText(currentUser.getClassName());
        tv_user_identity.setText(currentUser.getIndentity());
        userHeadView.setImageBitmap(head_bitmap);

        iv_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AlertUserMsgActivity.class));
            }
        });


        initToggleButton();

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshMsg();

    }


    private void refreshMsg() {
        currentUser = APPUser.getCurrentUser(APPUser.class);

    }

    public Bitmap getBitmapFromByte(byte[] temp) {
        if (temp != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        } else {
            return null;
        }
    }

    private void initToggleButton() {
        boolean isNight=SharePreferenceUtil.getNightMode(getContext());
        mToggleButton.setToggleOn(isNight);

        mToggleButton.setOnToggleChanged(new MyToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                SharePreferenceUtil.setNightMode(getContext(),on);
                EventBus.getDefault().post(new DayNightEvent(on));

            }
        });


    }


    @Override
    public void onResume() {//这是为实现修改用户信息刷新
        super.onResume();

        tv_user_name.setText(currentUser.getUsername());
        byte[] img = currentUser.getImg_msg();
        head_bitmap = getBitmapFromByte(img);
        userHeadView.setImageBitmap(head_bitmap);

    }


}
