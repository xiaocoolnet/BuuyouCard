package com.buuyou.firstpageson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.buuyou.MineSbu.AccountInfo;
import com.buuyou.MineSbu.BasicInfo;
import com.buuyou.MineSbu.Changepassword;
import com.buuyou.MineSbu.SafeCenter;
import com.buuyou.buuyoucard.R;

public class Shortcut extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout basicinfo,accountinfo,safecenter,changepwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);
        basicinfo= (RelativeLayout) findViewById(R.id.rlayout_shortcut_basicinfo);
        accountinfo= (RelativeLayout) findViewById(R.id.rlayout_shortcut_accountinfo);
        safecenter= (RelativeLayout) findViewById(R.id.rlayout_shortcut_safecenter);
        changepwd= (RelativeLayout) findViewById(R.id.rlayout_shortcut_changepwd);
        basicinfo.setOnClickListener(this);
        accountinfo.setOnClickListener(this);
        safecenter.setOnClickListener(this);
        changepwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rlayout_shortcut_basicinfo:
                Intent intent1=new Intent(Shortcut.this, BasicInfo.class);
                startActivity(intent1);
                break;
            case R.id.rlayout_shortcut_accountinfo:
                Intent intent2=new Intent(Shortcut.this, AccountInfo.class);
                startActivity(intent2);
                break;
            case R.id.rlayout_shortcut_safecenter:
                Intent intent3=new Intent(Shortcut.this, SafeCenter.class);
                startActivity(intent3);
                break;
            case R.id.rlayout_shortcut_changepwd:
                Intent intent4=new Intent(Shortcut.this, Changepassword.class);
                startActivity(intent4);
                break;
        }
    }
}
