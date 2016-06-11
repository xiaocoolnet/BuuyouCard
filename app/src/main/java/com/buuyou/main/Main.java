package com.buuyou.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.buuyou.buuyoucard.R;
import com.buuyou.fragment.BlankFragment;
import com.buuyou.other.MyActivity;

public class Main extends AppCompatActivity implements View.OnClickListener {
    private Button regist,login;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp=getSharedPreferences("data",MODE_PRIVATE);

        boolean b1=sp.getBoolean("isboolean",true);
        Log.e("+++",b1+"");
        //第一次进入应用
        if (b1) {
            login= (Button) findViewById(R.id.bt_login);
            regist= (Button) findViewById(R.id.bt_regist);
            login.setOnClickListener(this);
        }else{
            MyActivity.getIntent(Main.this, BlankFragment.class);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                MyActivity.getIntent(Main.this, Mylogin.class);
                finish();
        }
    }
}
