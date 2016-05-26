package com.buuyou.firstpageson;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.buuyoucard.R;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoneyInfo extends AppCompatActivity implements View.OnClickListener {
    private String allmoney,availablemoney,usermoney,withdrawmoney,result;
    private TextView tv_allmoney1,tv_allmoney2,tv_available,tv_user,tv_withdraw;
    private int available=0,user=0,withdraw=0;
    private ImageView back;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moneyinfo);
        sp=getSharedPreferences("data", MODE_PRIVATE);
        result=sp.getString("result", null);
        tv_allmoney1= (TextView) findViewById(R.id.tv_moneyinfo_allmoney1);
        tv_allmoney2= (TextView) findViewById(R.id.tv_moneyinfo_allmoney2);
        tv_available= (TextView) findViewById(R.id.tv_moneyinfo_availablemoney);
        tv_user= (TextView) findViewById(R.id.tv_moneyinfo_usermoney);
        tv_withdraw= (TextView) findViewById(R.id.tv_moneyinfo_withdrawmoney);
        back= (ImageView) findViewById(R.id.iv_moneyinfo_back);
        back.setOnClickListener(this);
            try {
                JSONObject json=new JSONObject(result);
                if(json.getString("status").equals("1")){
                    JSONArray data=json.getJSONArray("data");
                    for(int i=0;i<data.length();i++){
                        JSONObject temp= (JSONObject) data.get(i);
                        allmoney=temp.getString("TotalBalance");
                        availablemoney=temp.getString("AvailableBalance");
                        usermoney=temp.getString("UserMoney");
                        withdrawmoney=temp.getString("UserWithdrawMoney");
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        available=(int)(Double.valueOf(availablemoney)*1000);
        user=(int)(Double.valueOf(usermoney)*1000);
        withdraw=(int)(Double.valueOf(withdrawmoney)*1000);

        PieGraph pg = (PieGraph)findViewById(R.id.graph);
        PieSlice slice = new PieSlice();
        if(available==0&&user==0&&withdraw==0){
            //可用余额
            slice.setColor(Color.parseColor("#F5F3F0"));
            slice.setValue(1310);
            pg.addSlice(slice);

        }else{
            if(available!=0){
                //可用余额
                slice.setColor(Color.parseColor("#ED6139"));
                slice.setValue(1310);
                pg.addSlice(slice);
                slice = new PieSlice();
            }
            if(user!=0){
                //可提现余额
                slice.setColor(Color.parseColor("#D78CE6"));
                slice.setValue(1310);
                pg.addSlice(slice);
                slice = new PieSlice();
            }
            if(withdraw!=0){
                //提现中金额
                slice.setColor(Color.parseColor("#8AE5FF"));
                slice.setValue(0);
                pg.addSlice(slice);
            }
        }


        tv_allmoney1.setText(allmoney);
        tv_allmoney2.setText(allmoney);
        tv_available.setText(availablemoney);
        tv_user.setText(usermoney);
        tv_withdraw.setText(withdrawmoney);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_moneyinfo_back:
                finish();
                break;
        }
    }
}
