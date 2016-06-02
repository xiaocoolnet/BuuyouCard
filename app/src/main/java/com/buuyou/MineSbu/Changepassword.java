package com.buuyou.MineSbu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.main.Mylogin;

import org.json.JSONException;
import org.json.JSONObject;

public class Changepassword extends AppCompatActivity {

    private LinearLayout back,activity;
    private Button sure;
    private EditText et_newpassword,et_againpassword,et_email,et_oldpassword;
    private String email,emailsure,oldpwd,newpwd,againnewpwd,result;
    SharedPreferences sp;
    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Toast.makeText(getApplication(),"确认密码和新密码不同",Toast.LENGTH_SHORT).show();
                    et_againpassword.setText("");
                    break;
                case 2:
                    Toast.makeText(getApplication(),"请输入自己的邮箱",Toast.LENGTH_SHORT).show();
                    et_email.setText("");
                    break;
                case 3:
                    try {
                        JSONObject json=new JSONObject(result);
                        String status=json.getString("status");
                        SharedPreferences.Editor editor=sp.edit();
                        if(status.equals("1")){
                            Toast.makeText(getApplication(),"修改成功",Toast.LENGTH_LONG).show();
                            editor.putString("clearpwd", newpwd);
                            editor.putBoolean("isboolean",true);
                            editor.commit();
                            Intent intent=new Intent(Changepassword.this,Mylogin.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplication(),"修改失败",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 4:
                    Toast.makeText(getApplication(),"网络连接错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        sp=getSharedPreferences("data", Context.MODE_PRIVATE);
        back= (LinearLayout) findViewById(R.id.iv_changepassword_back);
        sure= (Button) findViewById(R.id.bt_changepassword_sure);
        et_email= (EditText) findViewById(R.id.et_changepassword_email);
        et_oldpassword= (EditText) findViewById(R.id.et_changepassword_oldpwd);
        et_newpassword= (EditText) findViewById(R.id.et_changepassword_newpwd);
        et_againpassword= (EditText) findViewById(R.id.et_changepassword_againnewpwd);
        activity= (LinearLayout) findViewById(R.id.llayout_changepassword_activity);
        emailsure=sp.getString("email",null);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=et_email.getText().toString().trim();
                oldpwd=et_oldpassword.getText().toString().trim();
                newpwd=et_newpassword.getText().toString().trim();
                againnewpwd=et_againpassword.getText().toString().trim();
                new Thread(){
                    public void run(){
                        if(!newpwd.equals(againnewpwd)){//新密码和确认密码不一样
                            handler.sendEmptyMessage(1);
                        }
                        else if(!email.equals(emailsure)){//输入的邮箱和该用户不一样
                            handler.sendEmptyMessage(2);
                        }
                        else {
                            if(myHttpConnect.isConnnected(getApplication())){
                                result=myHttpConnect.urlconnect_changepass(email,oldpwd,newpwd);
                                handler.sendEmptyMessage(3);
                            }else{
                                handler.sendEmptyMessage(4);
                            }
                        }
                    }
                }.start();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

    }

}
