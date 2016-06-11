package com.buuyou.firstpageson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.buuyou.MineSbu.AccountInfo;
import com.buuyou.MineSbu.BasicInfo;
import com.buuyou.MineSbu.ChannelRate;
import com.buuyou.MineSbu.LoginLog;
import com.buuyou.MineSbu.SafeCenter;
import com.buuyou.MineSbu.Changepassword;
import com.buuyou.buuyoucard.R;
import com.buuyou.other.MyActivity;

public class Businessinfo extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private RelativeLayout basicinfo,accountinfo,safecenter,changepwd,loginlog,channelrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businessinfo);
        back= (ImageView) findViewById(R.id.iv_businessinfo_back);
        basicinfo= (RelativeLayout) findViewById(R.id.rlayout_businessinfo_basicinfo);
        accountinfo= (RelativeLayout) findViewById(R.id.rlayout_businessinfo_accountinfo);
        safecenter= (RelativeLayout) findViewById(R.id.rlayout_businessinfo_safecenter);
        changepwd= (RelativeLayout) findViewById(R.id.rlayout_businessinfo_changepwd);
        loginlog= (RelativeLayout) findViewById(R.id.rlayout_businessinfo_loginlog);
        channelrate= (RelativeLayout) findViewById(R.id.rlayout_businessinfo_channelrate);

        back.setOnClickListener(this);
        basicinfo.setOnClickListener(this);
        accountinfo.setOnClickListener(this);
        safecenter.setOnClickListener(this);
        changepwd.setOnClickListener(this);
        loginlog.setOnClickListener(this);
        channelrate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_businessinfo_back:
                finish();
                break;
            case R.id.rlayout_businessinfo_basicinfo:
                MyActivity.getIntent(Businessinfo.this, BasicInfo.class);
                break;
            case R.id.rlayout_businessinfo_accountinfo:
                MyActivity.getIntent(Businessinfo.this, AccountInfo.class);
                break;
            case R.id.rlayout_businessinfo_safecenter:
                MyActivity.getIntent(Businessinfo.this, SafeCenter.class);
                break;
            case R.id.rlayout_businessinfo_changepwd:
                MyActivity.getIntent(Businessinfo.this, Changepassword.class);
                break;
            case R.id.rlayout_businessinfo_loginlog:
                MyActivity.getIntent(Businessinfo.this, LoginLog.class);
                break;
            case R.id.rlayout_businessinfo_channelrate:
                MyActivity.getIntent(Businessinfo.this, ChannelRate.class);
                break;
        }
    }
}
