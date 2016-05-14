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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChannelRate extends AppCompatActivity {
    private ListView listview;
   private String emaile,pwd,data_result;
    private TextView lefttext,centertext,righttext;
    private ImageView back;
   private int num;
    ArrayList<String> listname=new ArrayList<String>();
    ArrayList<String> listrate=new ArrayList<String>();
    ArrayList<String> liststate=new ArrayList<String>();
    SharedPreferences sp;


    Handler handler=new Handler(){
      public void handleMessage(Message msg){
          switch (msg.what){
              case 1:
                  Toast.makeText(getApplication(),"网络连接错误",Toast.LENGTH_SHORT).show();
                  break;
              case 2:
                  try {

                      JSONObject json =new JSONObject(data_result);
                      String status=json.getString("status");
                      Log.e("++++++", json.toString());
                      if(status.equals("1")){
                          JSONArray data=json.getJSONArray("data");
                          Log.e("---------", data.toString());
                          num=data.length();

                          for(int i=0;i<data.length();i++){
                              JSONObject temp= (JSONObject) data.get(i);
                              String name=temp.getString("ChannelName");
                              String rate=temp.getString("ChannelRate");
                              if(!rate.equals("0"))
                                  rate=rate+"%";
                              String state=temp.getString("ChannelState");
                              if(state.equals("1"))
                                  state="开启";
                              else
                                  state="关闭";
                              listname.add(name);
                              listrate.add(rate);
                              liststate.add(state);


                          }
                          //当解析完数据，再设置listview适配器，要不然listview中没有数据
                          listview=(ListView)findViewById(R.id.listview);
                          listview.setAdapter(new Myadapter());


                      }
                  } catch (JSONException e) {
                      e.printStackTrace();
                      Log.e("+++++","异常");
                  }
                  break;
          }

      }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_rate);
        sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        emaile=sp.getString("email", null);
        pwd=sp.getString("clearpwd", null);
        back= (ImageView) findViewById(R.id.iv_channelrate_back);

        new Thread(){
            public void run(){
                if(myHttpConnect.isConnnected(getApplication())){
                    data_result= myHttpConnect.urlconnect_channelrate(emaile, pwd);
                    handler.sendEmptyMessage(2);

                }else{
                    handler.sendEmptyMessage(1);
                }

            }
        }.start();
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});

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

            View view=View.inflate(ChannelRate.this,R.layout.listview_channel,null);
            lefttext= (TextView) view.findViewById(R.id.left);
            centertext= (TextView) view.findViewById(R.id.center);
            righttext= (TextView) view.findViewById(R.id.right);

            for(int i=0;i<num;i++){
                if(position==i) {
                    lefttext.setText(listname.get(i));
                    centertext.setText(listrate.get(i));
                    righttext.setText(liststate.get(i));
                }

            }
            return view;
        }
    }

}


