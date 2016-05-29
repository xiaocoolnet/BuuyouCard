package com.buuyou.MineSbu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.other.MyActivity;
import com.echo.holographlibrary.Line;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SafeCenter extends AppCompatActivity implements View.OnClickListener {
   private TextView safecode,email,phone,identity,address;
    private LinearLayout back;
    private SharedPreferences sp;
    private String result;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(getApplication(),"网络连接错误",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    try {
                        JSONObject json=new JSONObject(result);
                        if(json.getString("status").equals("1")){
                            JSONArray temp=json.getJSONArray("data");
                            for(int i=0;i<temp.length();i++){
                                JSONObject data= (JSONObject) temp.get(i);
                                //数据中有“未”字，设置成红色
                                if(data.getString("safecode").contains("未"))
                                    safecode.setTextColor(getResources().getColor(R.color.safecenter_wei));
                                if(data.getString("email").contains("未"))
                                    email.setTextColor(getResources().getColor(R.color.safecenter_wei));
                                if(data.getString("mobile").contains("未"))
                                    phone.setTextColor(getResources().getColor(R.color.safecenter_wei));
                                if(data.getString("IdentityAuth").contains("未"))
                                    identity.setTextColor(getResources().getColor(R.color.safecenter_wei));
                                if(data.getString("CallBackUrl").contains("未"))
                                    address.setTextColor(getResources().getColor(R.color.safecenter_wei));
                                safecode.setText(data.getString("safecode"));
                                email.setText(data.getString("email"));
                                phone.setText(data.getString("mobile"));
                                identity.setText(data.getString("IdentityAuth"));
                                address.setText(data.getString("CallBackUrl"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_cen);
        sp=getSharedPreferences("data",MODE_PRIVATE);
        safecode= (TextView) findViewById(R.id.tv_security_safecode);
        email= (TextView) findViewById(R.id.tv_security_email);
        phone= (TextView) findViewById(R.id.tv_security_phone);
        identity= (TextView) findViewById(R.id.tv_security_identity);
        address= (TextView) findViewById(R.id.tv_security_address);
        back= (LinearLayout) findViewById(R.id.iv_security_back);
        back.setOnClickListener(this);
        new Thread(){
            public void run(){
                if(myHttpConnect.isConnnected(getApplication())){
                    result=myHttpConnect.urlconnect_safeinfo(sp.getString("email",null),sp.getString("clearpwd",null));
                    handler.sendEmptyMessage(2);
                }else{
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.safenum :
//                MyActivity.getIntent(SafeCenter.this, ChangeSafeCode.class);
//            break;
//            case R.id.safeemail :
//                MyActivity.getIntent(SafeCenter.this, ExchangeEmail.class);
//                break;
//            case R.id.safephone :
//                MyActivity.getIntent(SafeCenter.this, ExchangePhone.class);
//                break;
//            case R.id.myidentity :
//                MyActivity.getIntent(SafeCenter.this,Authentication.class);
//                break;
            case R.id.iv_security_back:
                finish();
                break;
        }

    }
}
