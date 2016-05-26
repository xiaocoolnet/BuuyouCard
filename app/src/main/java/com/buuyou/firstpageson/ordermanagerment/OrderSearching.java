package com.buuyou.firstpageson.ordermanagerment;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.List;

public class OrderSearching extends AppCompatActivity {
    private ListView listView;
    private ImageView back;
    private int num;
    private TextView name,time,ordermoney,suremoney,usermoney,ordernum;
    private SharedPreferences sp;
    ArrayList<String> listname=new ArrayList<String>();
    ArrayList<String> listtime=new ArrayList<String>();
    ArrayList<String> listordermoney=new ArrayList<String>();
    ArrayList<String> listsuremoney=new ArrayList<String>();
    ArrayList<String> listusermoney=new ArrayList<String>();
    ArrayList<String> listordernum=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_searching);
        sp=getSharedPreferences("data",MODE_PRIVATE);
        back= (ImageView) findViewById(R.id.iv_ordersearching_back);
        try {
            JSONObject json=new JSONObject(sp.getString("result",null));
            JSONArray temp=json.getJSONArray("data");
            num=temp.length();
            for(int i=0;i<num;i++){
                JSONObject data= (JSONObject) temp.get(i);
                listname.add(data.getString("ChannelName"));
                listtime.add(MyActivity.getTime(data.getString("PayDate")));
                listordermoney.add("（￥"+data.getString("OrderMoney")+"）");
                listsuremoney.add(data.getString("Realmoney")+"元");
                listusermoney.add(data.getString("UserMoney")+"元");
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
        listView= (ListView) findViewById(R.id.lv_ordersearching);
        listView.setAdapter(new MyAdapter());
    }
    public class MyAdapter extends BaseAdapter{

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
            View view=View.inflate(getApplication(),R.layout.listview_ordersearching,null);
            name= (TextView) view.findViewById(R.id.tvlv_ordersearching_name);
            time= (TextView) view.findViewById(R.id.tvlv_ordersearching_time);
            ordermoney= (TextView) view.findViewById(R.id.tvlv_ordersearching_ordermoney);
            suremoney= (TextView) view.findViewById(R.id.tvlv_ordersearching_suremoney);
            usermoney= (TextView) view.findViewById(R.id.tvlv_ordersearching_usermoney);
            ordernum= (TextView) view.findViewById(R.id.tvlv_ordersearching_ordernum);
            for (int i=0;i<num;i++){
                if(i==position){
                    name.setText(listname.get(i));
                    time.setText(listtime.get(i));
                    ordermoney.setText(listordermoney.get(i));
                    suremoney.setText(listsuremoney.get(i));
                    usermoney.setText(listusermoney.get(i));
                    ordernum.setText(listordernum.get(i));
                }
            }
            return view;
        }
    }
}
