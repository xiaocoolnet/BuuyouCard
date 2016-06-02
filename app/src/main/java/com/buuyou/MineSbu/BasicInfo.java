package com.buuyou.MineSbu;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.fragment.mineFragment;
import com.buuyou.other.MyActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class BasicInfo extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout emailcg,phonecg;
    private TextView id,email,phone;
    private EditText name,qq,webname,weburl;
    private LinearLayout back,activity;
    private TextView edit;
    private String result;
    private SharedPreferences sp;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(getApplication(),"网络连接错误",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    try {
                        JSONObject jsonObject=new JSONObject(result);
                        if(jsonObject.getString("status").equals("1")){
                            SharedPreferences.Editor editor=sp.edit();
                            editor.putString("name", name.getText().toString().trim());
                            editor.putString("QQ", qq.getText().toString().trim());
                            editor.putString("webname", webname.getText().toString().trim());
                            editor.putString("weburl", weburl.getText().toString().trim());
                            editor.commit();
                            mineFragment.minefragment_name.setText(name.getText().toString().trim());
                            Log.e("+++",sp.getString("name",null));
                            Toast.makeText(getApplication(),"修改成功",Toast.LENGTH_SHORT).show();
                        }else{
                            if(jsonObject.getString("msg").equals("NickName Empty"))
                                Toast.makeText(getApplication(),"昵称不能为空",Toast.LENGTH_SHORT).show();
                            else if(jsonObject.getString("msg").equals("TenQQ Empty"))
                                Toast.makeText(getApplication(),"QQ不能为空",Toast.LENGTH_SHORT).show();
                            else if(jsonObject.getString("msg").equals("WebName Empty"))
                                Toast.makeText(getApplication(),"网站名称不能为空",Toast.LENGTH_SHORT).show();
                            else if(jsonObject.getString("msg").equals("WebUrl Empty"))
                                Toast.makeText(getApplication(),"网站地址不能为空",Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        sp=getSharedPreferences("data",MODE_PRIVATE);
        emailcg=(RelativeLayout)findViewById(R.id.emailcg);
        phonecg=(RelativeLayout)findViewById(R.id.phonecg);
        back= (LinearLayout) findViewById(R.id.iv_basicinfo_back);
        edit= (TextView) findViewById(R.id.tv_basicinfo_edit);
        edit.setOnClickListener(this);
        emailcg.setOnClickListener(this);
        phonecg.setOnClickListener(this);
        back.setOnClickListener(this);
        id= (TextView) findViewById(R.id.tv_basicinfo_id);
        name= (EditText) findViewById(R.id.tv_basicinfo_name);
        email= (TextView) findViewById(R.id.tv_basicinfo_email);
        phone= (TextView) findViewById(R.id.tv_basicinfo_phone);
        qq= (EditText) findViewById(R.id.tv_basicinfo_qq);
        webname= (EditText) findViewById(R.id.tv_basicinfo_webname);
        weburl= (EditText) findViewById(R.id.tv_basicinfo_weburl);
        activity= (LinearLayout) findViewById(R.id.llayout_basicinfo_activity);
        //从data文件中获取基本信息中需要的数据
        id.setText(sp.getString("userid",null));
        name.setText(sp.getString("name",null));
        email.setText(sp.getString("email",null));
        phone.setText(sp.getString("phone", null));
        qq.setText(sp.getString("QQ", null));
        weburl.setText(sp.getString("weburl", null));
        webname.setText(sp.getString("webname", null));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_basicinfo_back:
                finish();
                break;
            case R.id.tv_basicinfo_edit:
                if(edit.getText().toString().equals("编辑")){
                    name.setFocusable(true);
                    name.setFocusableInTouchMode(true);
                    name.requestFocus();
                    qq.setFocusable(true);
                    qq.setFocusableInTouchMode(true);
                    qq.requestFocus();
                    webname.setFocusable(true);
                    webname.setFocusableInTouchMode(true);
                    webname.requestFocus();
                    weburl.setFocusable(true);
                    weburl.setFocusableInTouchMode(true);
                    weburl.requestFocus();
                    edit.setText("完成");

                }
                else if(edit.getText().toString().equals("完成")){
                    name.setFocusable(false);
                    qq.setFocusable(false);
                    webname.setFocusable(false);
                    weburl.setFocusable(false);
                    edit.setText("编辑");
                    new Thread(){
                        public void run(){
                            if(myHttpConnect.isConnnected(getApplication())){
                                result=myHttpConnect.urlconnect_changebasicinfo(sp.getString("email",null),sp.getString("clearpwd",null), URLEncoder.encode(name.getText().toString().trim()),
                                        URLEncoder.encode(qq.getText().toString().trim()),URLEncoder.encode(webname.getText().toString().trim()),URLEncoder.encode(weburl.getText().toString().trim()));
                                handler.sendEmptyMessage(2);
                            }else{
                                handler.sendEmptyMessage(1);
                            }
                        }
                    }.start();
                }
                break;
            case R.id.llayout_basicinfo_activity:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }
    }
}
