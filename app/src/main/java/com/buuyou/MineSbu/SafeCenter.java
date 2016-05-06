package com.buuyou.MineSbu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.buuyou.buuyoucard.R;

public class SafeCenter extends AppCompatActivity implements View.OnClickListener {
   private EditText safecode,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_cen);
        SharedPreferences sp=getSharedPreferences("data", MODE_PRIVATE);
        RelativeLayout safenum=(RelativeLayout)findViewById(R.id.safenum);
        safenum.setOnClickListener(this);
        RelativeLayout safeemail=(RelativeLayout)findViewById(R.id.safeemail);
        safeemail.setOnClickListener(this);
        RelativeLayout safephone=(RelativeLayout)findViewById(R.id.safephone);
        safephone.setOnClickListener(this);
        RelativeLayout myidentity=(RelativeLayout)findViewById(R.id.myidentity);
        myidentity.setOnClickListener(this);
        ImageView back= (ImageView) findViewById(R.id.iv_security_back);
        back.setOnClickListener(this);
        //显示安全码，EditText不可编辑，设置文本颜色
        safecode= (EditText) findViewById(R.id.ed_security_safecode);
        safecode.setText(sp.getString("safecode", null));
        safecode.setKeyListener(null);
        safecode.setTextColor(getResources().getColor(R.color.defaultcolor));
        //显示设置的密保邮箱，EditText不可编辑，设置文本颜色
        email= (EditText) findViewById(R.id.ed_security_email);
        email.setKeyListener(null);
        email.setTextColor(getResources().getColor(R.color.defaultcolor));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.safenum :
                Intent intent=new Intent(SafeCenter.this,ChangeSafeCode.class);
                startActivity(intent);
            break;
            case R.id.safeemail :
                Intent intent2=new Intent(SafeCenter.this,ExchangeEmail.class);
                startActivity(intent2);
                break;
            case R.id.safephone :
                Intent intent3=new Intent(SafeCenter.this,ExchangePhone.class);
                startActivity(intent3);
                break;
            case R.id.myidentity :
                Intent intent4=new Intent(SafeCenter.this,Authentication.class);
                startActivity(intent4);
                break;
            case R.id.iv_security_back:
                finish();
                break;
        }

    }
}
