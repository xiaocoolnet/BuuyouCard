package com.buuyou.firstpageson;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Transactioninfo extends AppCompatActivity {
    private TextView allnum,allmoney,todaynum,todaymoney,yesterdaynum,yesterdaymoney;
    private SharedPreferences sp;
    private String result;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactioninfo);
        sp=getSharedPreferences("data", MODE_PRIVATE);
        result=sp.getString("result", null);
        allnum= (TextView) findViewById(R.id.tv_transinfo_allnum);
        allmoney= (TextView) findViewById(R.id.tv_transinfo_allmoney);
        todaynum= (TextView) findViewById(R.id.tv_transinfo_todaynum);
        todaymoney= (TextView) findViewById(R.id.tv_transinfo_todaymoney);
        yesterdaynum= (TextView) findViewById(R.id.tv_transinfo_yesterdaynum);
        yesterdaymoney= (TextView) findViewById(R.id.tv_transinfo_yesterdaymoney);
        back= (ImageView) findViewById(R.id.iv_transinfo_back);

            try {
                JSONObject json=new JSONObject(result);
                if(json.getString("status").equals("1")){
                    JSONArray data=json.getJSONArray("data");
                    for(int i=0;i<data.length();i++){
                        JSONObject temp= (JSONObject) data.get(i);
                        allnum.setText(temp.getString("allnum"));
                        allmoney.setText(temp.getString("allmoney"));
                        todaynum.setText(temp.getString("todaynum"));
                        todaymoney.setText(temp.getString("todaymoney"));
                        yesterdaynum.setText(temp.getString("yesterdaynum"));
                        yesterdaymoney.setText(temp.getString("yesterdaymoney"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
