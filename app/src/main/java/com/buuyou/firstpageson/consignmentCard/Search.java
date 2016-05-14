package com.buuyou.firstpageson.consignmentCard;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.buuyou.buuyoucard.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    private ListView listView;
    private SharedPreferences sp;
    private String result;
    private TextView tv_name,tv_no,tv_status,tv_time,tv_submitmoney,tv_successmoney;
    private int num;
    ArrayList<String> listNo=new ArrayList<String>();
    ArrayList<String> listName=new ArrayList<String>();
    ArrayList<String> listStatus=new ArrayList<String>();
    ArrayList<String> listDate=new ArrayList<String>();
    ArrayList<String> listOrdermoney=new ArrayList<String>();
    ArrayList<String> listRealmoney=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignorser_search);
        sp=getSharedPreferences("data", MODE_PRIVATE);
        result=sp.getString("result", null);

        Log.e("result+++",result);
        try {
            JSONObject json=new JSONObject(result);
            JSONArray temp=json.getJSONArray("data");
            num=temp.length();
            for(int i=0;i<num;i++){
                JSONObject data= (JSONObject) temp.get(i);
                listNo.add(data.getString("BillNO"));
                listName.add(data.getString("ChannelName"));

                listOrdermoney.add(data.getString("OrderMoney"));
                listRealmoney.add(data.getString("Realmoney"));
                //以“|”分隔，需要这样写“\\|”
                String []a=data.getString("GateMsg").split("\\|");
                listStatus.add(a[1]);
                String []b=data.getString("PayDate").split("T");
                listDate.add(b[0]+" "+b[1]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView= (ListView) findViewById(R.id.lv_seach);
        listView.setAdapter(new MyAdapter());
    }
    public class MyAdapter extends BaseAdapter{

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
            View view=View.inflate(Search.this,R.layout.listview_search_item,null);
            tv_name= (TextView) view.findViewById(R.id.tv_ordersearch_title);
            tv_no= (TextView) view.findViewById(R.id.tv_ordersearch_num);
            tv_status=(TextView) view.findViewById(R.id.tv_ordersearch_status);
            tv_submitmoney= (TextView) view.findViewById(R.id.tv_ordersearch_submitmoney);
            tv_successmoney= (TextView) view.findViewById(R.id.tv_ordersearch_successmoney);
            tv_time= (TextView) view.findViewById(R.id.tv_ordersearch_time);
            for(int i=0;i<num;i++){
                if(position==i){
                    tv_time.setText(listDate.get(i));
                    tv_no.setText(listNo.get(i));
                    tv_name.setText(listName.get(i));
                    tv_successmoney.setText(listRealmoney.get(i));
                    tv_submitmoney.setText(listOrdermoney.get(i));

                    if(listStatus.get(i).equals("支付成功")){
                        tv_status.setText("（"+listStatus.get(i)+"）");
                    }else {
                        tv_status.setTextColor(getResources().getColor(R.color.failordercolor));
                        tv_status.setText("（"+listStatus.get(i)+"）");
                    }
                }
            }
            return view;
        }
    }
}
