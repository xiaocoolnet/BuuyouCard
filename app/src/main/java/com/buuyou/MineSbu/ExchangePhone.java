package com.buuyou.MineSbu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.buuyou.buuyoucard.R;

public class ExchangePhone extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_phone);
        LinearLayout back= (LinearLayout) findViewById(R.id.iv_changephone_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_changeemail_back:
                finish();
                break;
        }

    }
}
