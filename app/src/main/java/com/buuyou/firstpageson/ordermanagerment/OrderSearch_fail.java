package com.buuyou.firstpageson.ordermanagerment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.buuyou.buuyoucard.R;
import com.buuyou.other.MyActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderSearch_fail extends AppCompatActivity {
    private ListView listView;
    private ImageView back;
    private int num;
    private TextView name,time,ordermoney,ordernum;
    private SharedPreferences sp;
    ArrayList<String> listname=new ArrayList<String>();
    ArrayList<String> listtime=new ArrayList<String>();
    ArrayList<String> listordermoney=new ArrayList<String>();
    ArrayList<String> listordernum=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersearch_fail);
        sp=getSharedPreferences("data",MODE_PRIVATE);
        back= (ImageView) findViewById(R.id.iv_failorder_back);
        try {
            JSONObject json=new JSONObject(sp.getString("result",null));
            JSONArray temp=json.getJSONArray("data");
            num=temp.length();
            for(int i=0;i<num;i++){
                JSONObject data= (JSONObject) temp.get(i);
                listname.add(data.getString("ChannelName"));
                listtime.add(MyActivity.getTime(data.getString("PayDate")));
                listordermoney.add(data.getString("OrderMoney")+"元");
                listordernum.add(data.getString("BillNO"));
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
        listView = (ListView) findViewById(R.id.lv_seach);
        listView.setAdapter(new MyAdapter());
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return num;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(OrderSearch_fail.this, R.layout.listview_ordersearch_fail, null);
            name= (TextView) view.findViewById(R.id.tvlv_ordersearch_fail_name);
            time= (TextView) view.findViewById(R.id.tvlv_ordersearch_fail_time);
            ordermoney= (TextView) view.findViewById(R.id.tvlv_ordersearch_fail_ordermoney);
            ordernum= (TextView) view.findViewById(R.id.tvlv_ordersearch_fail_num);
            for (int i=0;i<num;i++){
                if(i==position){
                    name.setText(listname.get(i));
                    time.setText(listtime.get(i));
                    ordermoney.setText(listordermoney.get(i));
                    ordernum.setText(listordernum.get(i));
                }
            }
            return view;
        }
    }
}
