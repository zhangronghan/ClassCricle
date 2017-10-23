package com.example.jia.classcircle.activity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jia.classcircle.R;
import com.example.jia.classcircle.activity.bmobTable.APPUser;
import com.example.jia.classcircle.activity.bmob_communication.DemoMessageHandler;
import com.example.jia.classcircle.activity.util.SharePreferenceUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class LoginActivity extends AppCompatActivity {
    private TextInputLayout account_number;
    private TextInputLayout account_password;
    private EditText edt_account_number;
    private EditText edt_account_password;
    private TextView tv_register;
    private Button btn_login;
    private String accountNumber="";
    private String accountPassWord="";
    private ProgressDialog wait_dialog;//自动旋转对话框，用于提示等待
    private Toolbar toolbar_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initBmob();//初始化Bmob数据库,初始化BmobNewIM SDK
        initView();
        onClickEvent();

    }



    private void initBmob() {
        Bmob.initialize(this, "9db39777bc7ec0846df5c87480543a31");
        BmobIM.init(this);
        BmobIM.registerDefaultMessageHandler(new DemoMessageHandler());



    }

    private void onClickEvent() {
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     accountNumber=edt_account_number.getText().toString().trim();//判断是否有输入和该是否有改账号
                     accountPassWord=edt_account_password.getText().toString().trim();

                    if(accountNumber.length()==0){
                        account_number.setError("用户名不能为空");
                        //这里接着判断是否有这账号
                        return;
                    }else if(accountNumber.length()>0){
                        account_number.setError("");
                    }
                    if(accountPassWord.length()==0){
                        account_password.setError("用户密码不能为空");
                        return;
                    }else if(accountPassWord.length()>0){
                        account_password.setError("");
                    }

                    wait_dialog.show();//显示进度旋转
                    BmobUser user=new BmobUser();
                    user.setUsername(accountNumber);
                    user.setPassword(accountPassWord);

                    user.login(new SaveListener<BmobUser>() {

                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if(e==null){

                                edt_account_number.setText("");
                                edt_account_password.setText("");
                                connectIM();  //登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作


                            }else {
                                Toast.makeText(LoginActivity.this,"登录失败"+e.toString(),Toast.LENGTH_SHORT).show();
                                wait_dialog.hide();//如果失败也要取消
                            }
                        }
                    });
                }
            });

            tv_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

                }
            });
    }



    private void initView() {
        APPUser bmobUser=BmobUser.getCurrentUser(APPUser.class);
        if(bmobUser !=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        }
        toolbar_login= (Toolbar) findViewById(R.id.toolbar_login);
        if (SharePreferenceUtil.getNightMode(this)){
            toolbar_login.setBackgroundColor(Color.parseColor("#534f4f"));
        }

        account_number= (TextInputLayout) findViewById(R.id.til_account_number);
        account_password= (TextInputLayout) findViewById(R.id.til_account_password);
        edt_account_number= (EditText) findViewById(R.id.edt_account_number);
        edt_account_password= (EditText) findViewById(R.id.edt_account_password);
        tv_register= (TextView) findViewById(R.id.tv_register);
        btn_login= (Button) findViewById(R.id.btn_login);
        wait_dialog=new ProgressDialog(LoginActivity.this);
        wait_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条风格，风格为圆形，旋转的
        wait_dialog.setMessage("登录中，请稍等...");
        wait_dialog.setIndeterminate(false);// 设置ProgressDialog 的进度条是否不明确
        wait_dialog.setCancelable(false);// 设置ProgressDialog 是否可以按退回按键取消
    }

    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wait_dialog.dismiss();
    }
    private void connectIM() {

        APPUser user = BmobUser.getCurrentUser(APPUser.class);
        if (!TextUtils.isEmpty(user.getObjectId())) {
            BmobIM.connect(user.getObjectId(), new ConnectListener() {
                @Override
                public void done(String uid, BmobException e) {
                    if (e == null) {
                        //连接成功
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                       // Toast.makeText(LoginActivity.this,"IM连接成功",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        wait_dialog.hide();//如果成功取消
                        finish();
                    } else {
                        //连接失败
                        // toast(e.getMessage());
                        wait_dialog.hide();
                        Toast.makeText(LoginActivity.this,"请重试一次",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
