package com.buuyou.firstpageson.financemanage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inputmoney extends AppCompatActivity implements View.OnClickListener {
    private EditText applyformoney;
    private EditText safecode;
    private String result;
    private SharedPreferences sp;
    private Button submit;
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplication(), "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(getApplication(), "提现成功！", Toast.LENGTH_LONG).show();
                        } else {
                            String message = jsonObject.getString("msg");
                            if (message.equals("No Record"))
                                Toast.makeText(getApplication(), "未查到相关记录", Toast.LENGTH_SHORT).show();
                            else if (message.equals("SafeCode Error"))
                                Toast.makeText(getApplication(), "安全码错误", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_inputmoney);
        sp=getSharedPreferences("data",MODE_PRIVATE);
        applyformoney= (EditText) findViewById(R.id.et_applyformoney_money);
        safecode= (EditText) findViewById(R.id.et_applyformoney_safecode);
        submit= (Button) findViewById(R.id.bt_inputmoney_submit);
        submit.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_inputmoney_submit:
                final String str_money,str_safecode;
                str_money=applyformoney.getText().toString().trim();
                str_safecode=safecode.getText().toString().trim();
                //判断提现金额和安全码是否为空
                if(!str_money.equals("")&&!str_safecode.equals("")){
                    //判断提现金额是否为整数
                    if(isNum(str_money)){
                        //判断提现金额是否大于余额
                        if((Double.parseDouble(str_money)+2.0)<=Double.parseDouble(sp.getString("usermoney",null))){
                            //判断提现金额是否满足最低提现金额
                            if(Double.parseDouble(str_money)>=100){
                                new Thread(){
                                    public void run(){
                                        if(myHttpConnect.isConnnected(getApplication())){
                                            result=myHttpConnect.urlconnect_applyformoney(sp.getString("email",null),sp.getString("clearpwd",null),str_money,str_safecode);
                                            handler.sendEmptyMessage(2);
                                        }else{
                                            handler.sendEmptyMessage(1);
                                        }
                                    }
                                }.start();
                            }else{
                                Toast.makeText(getApplication(), "低于最低提现金额!", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getApplication(),"超出可结算余额!",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplication(),"输入的提现金额为整数!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplication(),"请输入完整数据!",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.llayout_applyformoney_activity:
                InputMethodManager imm = (InputMethodManager)
                        getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }
    }
    public Boolean isNum(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            return false;
        }
        return true;
    }
}
