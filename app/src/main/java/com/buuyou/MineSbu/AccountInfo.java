package com.buuyou.MineSbu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.firstpageson.financemanage.ApplyforMoney;
import com.buuyou.fragment.mineFragment;
import com.buuyou.other.Dropdown;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AccountInfo extends AppCompatActivity implements View.OnClickListener {
    private TextView bankname,edit;
    private EditText bankcard,bankaccount,bankaddress;
    private SharedPreferences sp;
    private RelativeLayout choosebank;
    private LinearLayout back;
//    private String result_bank,result;
//    private int bankid;
//    private StringBuffer bank=new StringBuffer();//存储银行信息，下标为id，内容为银行名
//    List<String> str=new ArrayList<String>();
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 1:
//                    Toast.makeText(getApplication(),"网络连接错误",Toast.LENGTH_SHORT).show();
//                    break;
//                case 2:
//                    try {
//                        JSONObject json=new JSONObject(result_bank);
//                        if(json.getString("status").equals("1")){
//                            JSONArray temp=json.getJSONArray("data");
//                            for(int i=0;i<temp.length();i++){
//                                JSONObject data= (JSONObject) temp.get(i);
//                                str.add(i,data.getString("BankName"));
//                            }
//
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case 3:
//                    try {
//                        JSONObject jsonObject=new JSONObject(result);
//                        if(jsonObject.getString("status").equals("1")){
//                            SharedPreferences.Editor editor=sp.edit();
//                            editor.putString("bankname", bankname.getText().toString().trim());
//                            Log.e("2222", bankname.getText().toString().trim());
//
//                            editor.putString("bankaccount", bankaccount.getText().toString().trim());
//                            editor.putString("bankcard", bankcard.getText().toString().trim());
//                            editor.putString("bankaddress", bankaddress.getText().toString().trim());
//                            editor.commit();
//                            Log.e("3333", sp.getString("bankname", null));
//                            Log.e("3333", sp.getString("bankaccount", null));
//                            Log.e("3333", sp.getString("bankcard", null));
//                            Log.e("3333", sp.getString("bankaddress", null));
//                            bankaccount.setFocusable(false);
//                            bankcard.setFocusable(false);
//                            bankaddress.setFocusable(false);
//                            edit.setText("编辑");
//                            Toast.makeText(getApplication(),"修改成功",Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(getApplication(),"修改失败",Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        back= (LinearLayout) findViewById(R.id.iv_accountinfo_back);
        sp=getSharedPreferences("data",MODE_PRIVATE);
        bankaccount= (EditText) findViewById(R.id.tv_accountinfo_bankaccount);
        bankcard= (EditText) findViewById(R.id.tv_accountinfo_bankcard);
        bankaddress= (EditText) findViewById(R.id.et_accountinfo_bankaddress);
        bankname= (TextView) findViewById(R.id.tv_accountinfo_bankname);
        edit= (TextView) findViewById(R.id.tv_accountinfo_edit);
        choosebank= (RelativeLayout) findViewById(R.id.rlayout_accountinfo_choosebank);
        bankaddress.setText(sp.getString("bankaddress",null));
        bankcard.setText(sp.getString("bankcard",null));
        bankaccount.setText(sp.getString("bankaccount", null));
        bankname.setText(sp.getString("bankname", null));
        back.setOnClickListener(this);
//        edit.setOnClickListener(this);
//        choosebank.setOnClickListener(this);
//        new Thread() {
//            public void run() {
//                if (myHttpConnect.isConnnected(getApplication())) {
//                    result_bank = myHttpConnect.urlconnect_banklist(sp.getString("email", null), sp.getString("clearpwd", null));
//                    handler.sendEmptyMessage(2);
//                } else {
//                    handler.sendEmptyMessage(1);
//                }
//            }
//        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_accountinfo_back:
                finish();
                break;
//            case R.id.tv_accountinfo_edit:
//                if(edit.getText().toString().equals("编辑")){
//                    bankaccount.setFocusable(true);
//                    bankaccount.setFocusableInTouchMode(true);
//                    bankaccount.requestFocus();
//                    bankcard.setFocusable(true);
//                    bankcard.setFocusableInTouchMode(true);
//                    bankcard.requestFocus();
//                    bankaddress.setFocusable(true);
//                    bankaddress.setFocusableInTouchMode(true);
//                    bankaddress.requestFocus();
//                    edit.setText("完成");
//                }else if(edit.getText().toString().equals("完成")){
//                    SharedPreferences.Editor editor=sp.edit();
//                    for(int j=0;j<str.size();j++){
//                        if(str.get(j).equals(bankname.getText().toString().trim())) {
//                            bankid = j + 1;
//                            break;
//                        }
//                    }
//                    editor.putString("bankID",bankid+"");
//                    Log.e("++++",bankid+"");
//                    new Thread(){
//                        public void run(){
//                            if(myHttpConnect.isConnnected(getApplication())){
//                                Log.e("++++",bankid+"");
//                                result=myHttpConnect.urlconnect_changeaccountinfo(sp.getString("email", null), sp.getString("clearpwd", null), bankid, URLEncoder.encode(bankaccount.getText().toString().trim()),
//                                        URLEncoder.encode(bankcard.getText().toString().trim()), URLEncoder.encode(bankaddress.getText().toString().trim()));
//                                handler.sendEmptyMessage(3);
//                            }else{
//                                handler.sendEmptyMessage(1);
//                            }
//                        }
//                    }.start();
//
//
//                }
//                break;
//            case R.id.rlayout_accountinfo_choosebank:
//                if(edit.getText().toString().equals("完成")) {
//                    Dropdown.dropdown(bankname,getApplication(),str);
//                }
//                break;


        }
    }
}
