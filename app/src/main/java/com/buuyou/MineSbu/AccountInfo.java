package com.buuyou.MineSbu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.buuyou.buuyoucard.R;

public class AccountInfo extends AppCompatActivity implements View.OnClickListener {
    private TextView bankname,bankcard,bankaccount;
    private EditText bankaddress;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        ImageView back= (ImageView) findViewById(R.id.iv_accountinfo_back);
        sp=getSharedPreferences("data",MODE_PRIVATE);
        bankaccount= (TextView) findViewById(R.id.tv_accountinfo_bankaccount);
        bankcard= (TextView) findViewById(R.id.tv_accountinfo_bankcard);
        bankaddress= (EditText) findViewById(R.id.et_accountinfo_bankaddress);
        bankname= (TextView) findViewById(R.id.tv_accountinfo_bankname);
        bankaddress.setText(sp.getString("bankaddress",null));
        bankcard.setText(sp.getString("bankcard",null));
        bankaccount.setText(sp.getString("bankaccount", null));
        bankname.setText(sp.getString("bankname", null));
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_accountinfo_back:
                finish();
                break;
        }
    }
}
