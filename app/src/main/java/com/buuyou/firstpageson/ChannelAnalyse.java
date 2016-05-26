package com.buuyou.firstpageson;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.other.Dropdown;
import com.buuyou.other.MyActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChannelAnalyse extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private LinearLayout choosebegin,chooseend;
    private TextView begindate,enddate,name,no,contractmoney,dividemoney;
    private ImageView back;
    private Button submit;
    private String str_email,str_pwd,str_begin,str_end,result;
    private ArrayList<String> namelist=new ArrayList<String>();
    private ArrayList<String> nolist=new ArrayList<String>();
    private ArrayList<String> contractmoneylist=new ArrayList<String>();
    private ArrayList<String> dividemoneylist=new ArrayList<String>();
    private SharedPreferences sp;
    private int num=0;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Toast.makeText(getApplication(),"请选择日期",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    try {
                        Log.e("11111111",result);
                        JSONObject json=new JSONObject(result);
                        String status=json.getString("status");
                        if(status.equals("1")){
                            listView.setVisibility(View.VISIBLE);
                            JSONArray temp=json.getJSONArray("data");
                            num=temp.length();
                            Log.e("num:",num+"");
                            for(int i=0;i<num;i++){
                                JSONObject data= (JSONObject) temp.get(i);
                                namelist.add(data.getString("Mname"));
                                Log.e("+++",namelist.get(i));
                                nolist.add(data.getString("Mnum"));
                                contractmoneylist.add(data.getString("TruePrice")+"元");
                                dividemoneylist.add(data.getString("UserPrice")+"元");
                            }
                            //listView.setAdapter(new MyAdapter());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 3:
                    Toast.makeText(getApplication(),"网络连接错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channelanalyse);
        sp = getSharedPreferences("data",MODE_PRIVATE);
        str_email=sp.getString("email", null);
        str_pwd=sp.getString("clearpwd",null);
        listView = (ListView) findViewById(R.id.income_lv);
        choosebegin= (LinearLayout) findViewById(R.id.llayout_channelanalyse_begindate);
        chooseend= (LinearLayout) findViewById(R.id.llayout_channelanalyse_enddate);
        begindate= (TextView) findViewById(R.id.tv_channelanalyse_choosebegindate);
        enddate= (TextView) findViewById(R.id.tv_channelanalyse_chooseenddate);
        back= (ImageView) findViewById(R.id.iv_channelanalyse_back);
        submit= (Button) findViewById(R.id.bt_channelanalyse_submit);

        submit.setOnClickListener(this);
        back.setOnClickListener(this);
        choosebegin.setOnClickListener(this);
        chooseend.setOnClickListener(this);
        listView.setAdapter(new MyAdapter());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llayout_channelanalyse_begindate:
                Dropdown.choosedate(begindate,ChannelAnalyse.this);
                break;
            case R.id.llayout_channelanalyse_enddate:
                Dropdown.choosedate(enddate,ChannelAnalyse.this);
                break;
            case R.id.iv_channelanalyse_back:
                finish();
                break;
            case R.id.bt_channelanalyse_submit:
                new Thread(){
                    public void run(){
                        if(begindate.getText().toString().equals("选择日期")||enddate.getText().toString().equals("选择日期")){
                            handler.sendEmptyMessage(1);
                        }else{
                            if(myHttpConnect.isConnnected(getApplication())){
                                str_begin= MyActivity.getBegindate(begindate);
                                str_end=MyActivity.getEnddate(enddate);
                                result=myHttpConnect.urlconnect_channelanalyse(str_email,str_pwd,str_begin,str_end);
                                handler.sendEmptyMessage(2);

                            }else{
                                handler.sendEmptyMessage(3);
                            }
                        }
                    }
                }.start();
        }
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            Log.e("........",num+"");
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
            View view = View.inflate(ChannelAnalyse.this, R.layout.listview_channelanalyse, null);
            name= (TextView) view.findViewById(R.id.tvlv_channelanalyse_name);
            no= (TextView) view.findViewById(R.id.tvlv_channelanalyse_num);
            contractmoney= (TextView) view.findViewById(R.id.tvlv_channelanalyse_contractmoney);
            dividemoney= (TextView) view.findViewById(R.id.tvlv_channelanalyse_dividemoney);
            for(int i=0;i<num;i++){
                if(position==i){
                    name.setText(namelist.get(i));
                    no.setText(nolist.get(i));
                    contractmoney.setText(contractmoneylist.get(i));
                    dividemoney.setText(dividemoneylist.get(i));
                }
            }
            return view;
        }
    }
}
