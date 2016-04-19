package com.buuyou.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConect;
import com.buuyou.buuyoucard.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;

public class findpassword extends AppCompatActivity {
    EditText email,phone, valiCode,newpass;
    private Button bt_next;
    private Button bt_yanzhengma;
    private ImageView iv_back;
    private String myemail,myphone,myvaliCode,result,mynewpass,result_data,getdata;
    SharedPreferences sp;
    //设置倒计时时间
    int count=60;
    Timer timer = new Timer();
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    bt_yanzhengma.setText("" + count+"s后重新获取");
                    bt_yanzhengma.setClickable(false);
                    break;
                case 1:
                    timer.cancel();
                    bt_yanzhengma.setClickable(true);
                    bt_yanzhengma.setText("重新获取");
                    count=60;
                    break;
                case 2:
                    try {
                        JSONObject json=new JSONObject(result);
                        String status=json.getString("status");
                        if (status.equals("1")){
                            //修改密码成功，跳转到登录界面
                            SharedPreferences.Editor editor=sp.edit();
                            editor.putBoolean("isboolean", true);
                            editor.commit();
                            Toast.makeText(findpassword.this,"密码修改成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(findpassword.this, Mylogin.class);
                            startActivity(intent);
                        }else if(status.equals("0")){
                            Toast.makeText(findpassword.this,"修改密码失败",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        JSONObject json=new JSONObject(result_data);
                        String status=json.getString("status");
                        if (status.equals("1")){
                          getdata=json.getString("data");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    Toast.makeText(findpassword.this,"网络错误，请稍后重试！",Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(findpassword.this,"邮箱和手机号不能为空！",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpassword);
        sp=getSharedPreferences("data", Context.MODE_PRIVATE);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone);
        valiCode=(EditText)findViewById(R.id.valiCode);
        newpass=(EditText)findViewById(R.id.newpass);
        bt_next= (Button) findViewById(R.id.bt_findpassword_next);
        bt_yanzhengma= (Button) findViewById(R.id.getvaliCode);
        iv_back= (ImageView) findViewById(R.id.iv_findpassword_back);

        //跳转到下一个界面
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                @Override
                    public void run() {
                        myvaliCode = valiCode.getText().toString().trim();
                        myemail = email.getText().toString().trim();
                        mynewpass=newpass.getText().toString().trim();
                        result = myHttpConect.urlconnect_updapass(myemail, myvaliCode, mynewpass, getdata);
                        handler.sendEmptyMessage(2);
                   }
                }).start();

            }
        });

        //获取验证码
        bt_yanzhengma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 网络请求
                 */
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        if(myHttpConect.isConnnected(findpassword.this)){
                            myphone = phone.getText().toString().trim();
                            myemail = email.getText().toString().trim();
                            result_data= myHttpConect.urlconnect_pass(myemail, myphone);

                            handler.sendEmptyMessage(3);
                            if (!TextUtils.isEmpty(myphone)&&!TextUtils.isEmpty(myemail)) {
                                new Thread() {
                                    public void run() {
                                        for (int i = 0; i < 60; i++) {
                                            try {
                                                count--;
                                                handler.sendEmptyMessage(0);
                                                Thread.sleep(1000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        handler.sendEmptyMessage(1);
                                    }
                                }.start();
                            }else{
                                handler.sendEmptyMessage(5);
                            }
                        }
                        else{
                            handler.sendEmptyMessage(4);
                        }


                    }
                }).start();
                /**
                 * 获取验证码后进行倒计时
                 */

            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



}
