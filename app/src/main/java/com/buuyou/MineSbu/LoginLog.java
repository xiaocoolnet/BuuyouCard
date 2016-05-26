package com.buuyou.MineSbu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.other.MyActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginLog extends AppCompatActivity {
    ListView listview;
    private ImageView back;
    private String email,pwd,data_result;
    private int num;
    private TextView address,time,ip;
    SharedPreferences sp;

    ArrayList<String> listarea=new ArrayList<String>();
    ArrayList<String> listdate=new ArrayList<String>();
    ArrayList<String> listip=new ArrayList<String>();
    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Toast.makeText(getApplication(),"网络连接错误",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    try {

                        JSONObject json=new JSONObject(data_result);
                        //获取成功与否，1为成功，0为失败
                        String status=json.getString("status");


                        if(status.equals("1")){
                            JSONArray data=json.getJSONArray("data");

                            //得到data个数
                            num=data.length();

                            for(int i=0;i<data.length();i++){
                                JSONObject temp= (JSONObject) data.get(i);
                                String area=temp.getString("LoginArea");
                                String date= MyActivity.getTime(temp.getString("LoginDate"));
                                String ip=temp.getString("LoginIP");
                                listarea.add(area);
                                listdate.add(date);

                                listip.add(ip);
                            }
                            //当解析完数据，再设置listview适配器，要不然listview中没有数据
                            listview=(ListView)findViewById(R.id.listview);
                            listview.setAdapter(new Myadapter());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_log);

        back= (ImageView) findViewById(R.id.iv_loginlog_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        email=sp.getString("email", null);
        pwd=sp.getString("clearpwd", null);
        Log.e("pwd:",pwd);
        Log.e("email:",email);
        new Thread(){
            public void run(){
                //网络连接不成功
                if(myHttpConnect.isConnnected(getApplication())){
                   data_result=myHttpConnect.urlconnect_loginlog(email,pwd);
                    handler.sendEmptyMessage(2);
                }else{
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    public class Myadapter extends BaseAdapter{

        @Override
        public int getCount() {

            return num;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(LoginLog.this,R.layout.listview_log,null);
            address= (TextView) view.findViewById(R.id.address);
            time= (TextView) view.findViewById(R.id.time);
            ip= (TextView) view.findViewById(R.id.ip);

            for(int i=0;i<num;i++){
                if(position==i){
                    address.setText(listarea.get(i));
                    time.setText(listdate.get(i));
                    ip.setText(listip.get(i));
                }
            }
            return view;
        }
    }
}
