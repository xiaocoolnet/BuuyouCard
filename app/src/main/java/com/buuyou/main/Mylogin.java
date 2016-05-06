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
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.fragment.BlankFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mylogin extends AppCompatActivity {
    EditText phone, pass;
    Button login;
    TextView findpassword;
    String result_data;
    SharedPreferences sp;
    private String myphone;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:

                    try {
                        JSONObject json = new JSONObject(result_data);
                        String status = json.getString("status");
                        //登陆成功   status等于1
                        if (status.equals("1")) {
                            SharedPreferences.Editor editor = sp.edit();
                            Toast.makeText(Mylogin.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Mylogin.this, BlankFragment.class);
                            startActivity(intent);
                            //获得json中的data数据
                            JSONArray data = json.getJSONArray("data");

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject temp = (JSONObject) data.get(i);
                                Log.e("++++++",temp.toString());
                                //得到“data”中各个值
                                String userid = temp.getString("Userid");
                                String username=temp.getString("UserRealName");
                                String userphone=temp.getString("RealTel");
                                String userQQ=temp.getString("MsnQQ");
                                String password = temp.getString("PassWord");
                                String lastTimes = temp.getString("LastTimes");
                                String safeCode=temp.getString("SafeCode");
                                String email=temp.getString("Email");
                                //将值通过sp放到data文件中
                                editor.putString("userid", userid);
                                editor.putString("password", password);
                                editor.putString("email",email);
                                editor.putString("lastTimes", lastTimes);
                                editor.putString("name",username);
                                editor.putString("phone",userphone);
                                editor.putString("QQ",userQQ);
                                editor.putString("safecode",safeCode);
                                //记录用户输入的明文密码
                                editor.putString("clearpwd",pass.getText().toString());
                                editor.commit();
                            }
                            finish();
                            //登录失败 status等于0
                        } else if (status.equals("0")) {
                            Toast.makeText(Mylogin.this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    Toast.makeText(Mylogin.this,"网络错误，请稍后重试！",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(Mylogin.this, "请输入正确账号或密码!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }



    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylogin);
        sp = getSharedPreferences("data", Context.MODE_PRIVATE);

        //定义状态b1，b1为true，进入登录界面，否则跳过登录界面
        boolean bl = sp.getBoolean("isboolean",true);
        //第一次进入应用
        if (bl) {
            init();
        } else {
            Intent intent = new Intent(Mylogin.this, BlankFragment.class);
            startActivity(intent);
            finish();
        }
    }


    public void init() {
        findpassword = (TextView) findViewById(R.id.findpass);
        phone = (EditText) findViewById(R.id.phone);
        pass = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //输入的账号和密码
                myphone = phone.getText().toString().trim();
                final String mypass = pass.getText().toString().trim();
                //改变isboolean状态为false
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("isboolean", false);
                editor.commit();

                //判断账号格式是否正确 如果错误给出提示
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        //账号密码为空
                        if (TextUtils.isEmpty(myphone) || TextUtils.isEmpty(mypass)) {
                            handler.sendEmptyMessage(1);

                        } else {
                            //连接数据库，解析数据
                            if (myHttpConnect.isConnnected(getApplication())) {
                                //如果网络连接，调用myHttpConect的urlconnect方法
                                result_data = myHttpConnect.urlconnect(myphone, mypass);
                                handler.sendEmptyMessage(3);

                            } else {
                                //网络有问题
                                handler.sendEmptyMessage(2);
                            }
                        }
                    }
                }).start();

            }
        });
        findpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mylogin.this, Findpassword.class);
                startActivity(intent);
            }
        });
    }



}
