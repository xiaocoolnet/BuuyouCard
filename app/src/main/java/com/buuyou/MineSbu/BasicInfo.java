package com.buuyou.MineSbu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buuyou.buuyoucard.R;

public class BasicInfo extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout emailcg,phonecg;
    private TextView id,name,email,qq,phone;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
        emailcg=(RelativeLayout)findViewById(R.id.emailcg);
        phonecg=(RelativeLayout)findViewById(R.id.phonecg);
        back= (ImageView) findViewById(R.id.iv_basicinfo_back);
        emailcg.setOnClickListener(this);
        phonecg.setOnClickListener(this);
        back.setOnClickListener(this);
        id= (TextView) findViewById(R.id.tv_basicinfo_id);
        name= (TextView) findViewById(R.id.tv_basicinfo_name);
        email= (TextView) findViewById(R.id.tv_basicinfo_email);
        phone= (TextView) findViewById(R.id.tv_basicinfo_phone);
        qq= (TextView) findViewById(R.id.tv_basicinfo_qq);
        //从data文件中获取基本信息中需要的数据
        id.setText(sp.getString("userid",null));
        name.setText(sp.getString("name",null));
        email.setText(sp.getString("email",null));
        phone.setText(sp.getString("phone",null));
        qq.setText(sp.getString("QQ",null));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.emailcg:
                Intent intent=new Intent(BasicInfo.this,ExchangeEmail.class);
                startActivity(intent);
                break;
            case R.id.phonecg:
                Intent intent1=new Intent(BasicInfo.this,ExchangePhone.class);
                startActivity(intent1);
                break;
            case R.id.iv_basicinfo_back:
                finish();
                break;
        }
    }
}
