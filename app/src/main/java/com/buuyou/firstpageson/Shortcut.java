package com.buuyou.firstpageson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.MineSbu.AccountInfo;
import com.buuyou.MineSbu.BasicInfo;
import com.buuyou.MineSbu.Changepassword;
import com.buuyou.MineSbu.ChannelRate;
import com.buuyou.MineSbu.SafeCenter;
import com.buuyou.buuyoucard.R;
import com.buuyou.firstpageson.financemanage.Finance;
import com.buuyou.firstpageson.ordermanagerment.Order;
import com.buuyou.other.MyActivity;

public class Shortcut extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout basicinfo,accountinfo,safecenter,changepwd;
    private LinearLayout ordermanage,financemanage;
    private String result;
    private SharedPreferences sp;
    private ImageView back;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(getApplication(), "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("result",result);
                    editor.commit();
                    MyActivity.getIntent(Shortcut.this, SafeCenter.class);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);
        sp=getSharedPreferences("data",MODE_PRIVATE);
        basicinfo= (RelativeLayout) findViewById(R.id.rlayout_shortcut_basicinfo);
        accountinfo= (RelativeLayout) findViewById(R.id.rlayout_shortcut_accountinfo);
        safecenter= (RelativeLayout) findViewById(R.id.rlayout_shortcut_safecenter);
        changepwd= (RelativeLayout) findViewById(R.id.rlayout_shortcut_changepwd);
        ordermanage= (LinearLayout) findViewById(R.id.llayout_shortcut_order);
        financemanage= (LinearLayout) findViewById(R.id.llayout_shortcut_finance);
        back= (ImageView) findViewById(R.id.iv_shortcut_back);
        ordermanage.setOnClickListener(this);
        basicinfo.setOnClickListener(this);
        accountinfo.setOnClickListener(this);
        safecenter.setOnClickListener(this);
        changepwd.setOnClickListener(this);
        back.setOnClickListener(this);
        financemanage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rlayout_shortcut_basicinfo:
                MyActivity.getIntent(Shortcut.this, BasicInfo.class);
                break;
            case R.id.rlayout_shortcut_accountinfo:
                MyActivity.getIntent(Shortcut.this, AccountInfo.class);
                break;
            case R.id.rlayout_shortcut_safecenter:
                new Thread(){
                    public void run(){
                        if(myHttpConnect.isConnnected(getApplicationContext())){
                            result=myHttpConnect.urlconnect_safeinfo(sp.getString("email",null),sp.getString("clearpwd",null));
                            handler.sendEmptyMessage(2);
                        }else{
                            handler.sendEmptyMessage(1);
                        }
                    }
                }.start();
                break;
            case R.id.rlayout_shortcut_changepwd:
                MyActivity.getIntent(Shortcut.this, Changepassword.class);
                break;
            case R.id.llayout_shortcut_order:
                MyActivity.getIntent(Shortcut.this, Order.class);
                break;
            case R.id.llayout_shortcut_finance:
                MyActivity.getIntent(Shortcut.this, Finance.class);
                break;
            case R.id.iv_shortcut_back:
                finish();
                break;

        }
    }
}
