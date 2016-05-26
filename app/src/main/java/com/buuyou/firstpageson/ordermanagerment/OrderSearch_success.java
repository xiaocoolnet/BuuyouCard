package com.buuyou.firstpageson.ordermanagerment;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class OrderSearch_success extends AppCompatActivity {
    private ListView listView;
    private ImageView back;
    private int num;
    private TextView name,time,ordermoney,suremoney,usermoney;
    private SharedPreferences sp;
    ArrayList<String> listname=new ArrayList<String>();
    ArrayList<String> listtime=new ArrayList<String>();
    ArrayList<String> listordermoney=new ArrayList<String>();
    ArrayList<String> listsuremoney=new ArrayList<String>();
    ArrayList<String> listusermoney=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_search);
        sp=getSharedPreferences("data",MODE_PRIVATE);
        back= (ImageView) findViewById(R.id.iv_ordersearch_back);
        try {
            JSONObject json=new JSONObject(sp.getString("result",null));
            JSONArray temp=json.getJSONArray("data");
            num=temp.length();
            for(int i=0;i<num;i++){
                JSONObject data= (JSONObject) temp.get(i);
                listname.add(data.getString("ChannelName"));
                listtime.add(MyActivity.getTime(data.getString("PayDate")));
                listordermoney.add(data.getString("OrderMoney")+"元");
                listsuremoney.add(data.getString("Realmoney")+"元");
                listusermoney.add(data.getString("UserMoney")+"元");
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
        listView= (ListView) findViewById(R.id.lv_ordersearch);
        listView.setAdapter(new Myadapter());
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
            View view=View.inflate(getApplication(),R.layout.listview_ordersearch_success,null);
            name= (TextView) view.findViewById(R.id.tvlv_ordersearch_success_name);
            time= (TextView) view.findViewById(R.id.tvlv_ordersearch_success_time);
            ordermoney= (TextView) view.findViewById(R.id.tvlv_ordersearch_success_ordermoney);
            suremoney= (TextView) view.findViewById(R.id.tvlv_ordersearch_success_suremoney);
            usermoney= (TextView) view.findViewById(R.id.tvlv_ordersearch_success_dividemoney);
            for (int i=0;i<num;i++){
                if(i==position){
                    name.setText(listname.get(i));
                    time.setText(listtime.get(i));
                    ordermoney.setText(listordermoney.get(i));
                    suremoney.setText(listsuremoney.get(i));
                    usermoney.setText(listusermoney.get(i));
                }
            }
            return view;
        }
    }
}
