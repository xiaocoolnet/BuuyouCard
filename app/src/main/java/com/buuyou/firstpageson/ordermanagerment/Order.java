package com.buuyou.firstpageson.ordermanagerment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.other.Dropdown;
import com.buuyou.other.MyActivity;
import com.echo.holographlibrary.Line;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Order extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout left,center,right;
    private TextView tv_left,tv_center,tv_right;
    private LinearLayout leftline,centerline,rightline;
    private ImageView back;
    private LinearLayout choosetype,choosechannel,choosebegindate,chooseenddate;
    private LinearLayout activity;
    private TextView type,channel,begindate,enddate;
    private Button search;
    private EditText et_ordernum,et_cardnum;
    private String result,result_channel;
    String endtime = null;
    private SharedPreferences sp;
    private int orderstatus=1;
    String ordertype,channelid;
    List<String> str=new ArrayList<String>();
    String a[]={"所有类型","网银","点卡","支付宝","财付通","微信"};

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(getApplication(), "请选择开始时间", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    try {
                        JSONObject json=new JSONObject(result);
                        String status=json.getString("status");
                        if(status.equals("1")){
                            SharedPreferences.Editor editor;
                            editor=sp.edit();
                            editor.putString("result", result);
                            editor.commit();

                            switch (orderstatus){
                                case 1:
                                    MyActivity.getIntent(Order.this, OrderSearch_success.class);
                                    break;
                                case 2:
                                    MyActivity.getIntent(Order.this,OrderSearch_fail.class);
                                    break;
                                case 3:
                                    MyActivity.getIntent(Order.this,OrderSearching.class);
                                    break;
                            }

                        }else{
                            if(json.getString("msg").equals("No Record"))
                                Toast.makeText(getApplication(),"未查到订单",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplication(),"查询失败",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    Toast.makeText(getApplication(),"网络连接错误",Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    try {
                        str.clear();
                        JSONObject json=new JSONObject(result_channel);
                        str.add(0,"所有通道");
                        if(json.getString("status").equals("1")){
                            JSONArray array=json.getJSONArray("data");
                            for(int i=0;i<array.length();){
                                JSONObject data= (JSONObject) array.get(i);
                                str.add(++i, data.getString("ChannelName"));

                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private String email;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        left= (RelativeLayout) findViewById(R.id.rlayout_order_left);
        center= (RelativeLayout) findViewById(R.id.rlayout_order_center);
        right= (RelativeLayout) findViewById(R.id.rlayout_order_right);
            tv_left= (TextView) findViewById(R.id.tv_order_lefttitle);
            tv_center= (TextView) findViewById(R.id.tv_order_centertitle);
            tv_right= (TextView) findViewById(R.id.tv_order_righttitle);
            leftline= (LinearLayout) findViewById(R.id.llayout_order_leftline);
            centerline= (LinearLayout) findViewById(R.id.llayout_order_centerline);
            rightline= (LinearLayout) findViewById(R.id.llayout_order_rightline);
            back= (ImageView) findViewById(R.id.iv_order_back);
            activity= (LinearLayout) findViewById(R.id.llayout_order_activity);
            sp=getSharedPreferences("data", Context.MODE_PRIVATE);
            choosetype= (LinearLayout) findViewById(R.id.llayout_order_choosetype);
            choosechannel= (LinearLayout) findViewById(R.id.llayout_order_choosechannel);
            type= (TextView) findViewById(R.id.tv_order_choosetype);
            channel= (TextView) findViewById(R.id.tv_order_choosechannel);
            choosebegindate= (LinearLayout) findViewById(R.id.llayout_order_begindate);
            chooseenddate= (LinearLayout) findViewById(R.id.llayout_order_enddate);
            begindate= (TextView) findViewById(R.id.tv_order_choosebegindate);
            enddate= (TextView) findViewById(R.id.tv_order_chooseenddate);
            search= (Button) findViewById(R.id.bt_order_search);
            et_ordernum= (EditText) findViewById(R.id.et_order_ordernum);
            et_cardnum= (EditText) findViewById(R.id.et_order_cardnum);
            left.setOnClickListener(this);
            center.setOnClickListener(this);
            right.setOnClickListener(this);
            back.setOnClickListener(this);
            search.setOnClickListener(this);
            choosechannel.setOnClickListener(this);
            choosetype.setOnClickListener(this);
            choosebegindate.setOnClickListener(this);
            chooseenddate.setOnClickListener(this);
            activity.setOnClickListener(this);
            email=sp.getString("email",null);
            pwd=sp.getString("clearpwd", null);
            new Thread(){
                public void run(){
                    if(myHttpConnect.isConnnected(getApplication())){
                        result_channel=myHttpConnect.urlconnect_channellist(email,pwd);
                        handler.sendEmptyMessage(4);
                    }else{
                        handler.sendEmptyMessage(3);
                    }
                }
            }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlayout_order_left:
                init();
                orderstatus=1;
                tv_left.setTextColor(getResources().getColor(R.color.colororange));
                leftline.setBackgroundColor(getResources().getColor(R.color.colororange));
                break;
            case R.id.rlayout_order_center:
                init();
                orderstatus=2;
                tv_center.setTextColor(getResources().getColor(R.color.colororange));
                centerline.setBackgroundColor(getResources().getColor(R.color.colororange));
                break;
            case R.id.rlayout_order_right:
                init();
                orderstatus=0;
                tv_right.setTextColor(getResources().getColor(R.color.colororange));
                rightline.setBackgroundColor(getResources().getColor(R.color.colororange));
                break;
            case R.id.iv_order_back:
                finish();
                break;
            case R.id.llayout_order_choosetype:
                //清空list数组
                str.clear();
                for(int i=0;i<a.length;i++){
                    str.add(a[i]);
                }
                //调用Dropdown类中的dropdown函数，实现下拉菜单
                Dropdown.dropdown(type,getApplicationContext(),str);
                break;
            case R.id.llayout_order_choosechannel:
                Dropdown.dropdown(channel, getApplicationContext(), str);
                break;
            case R.id.llayout_order_begindate:
                Dropdown.choosedate(begindate,Order.this);
                break;
            case R.id.llayout_order_enddate:
                Dropdown.choosedate(enddate,Order.this);
                break;
            case R.id.bt_order_search:

                final String begintime=MyActivity.getBegindate(begindate);

                final String ordernum=et_ordernum.getText().toString().trim();
                final String cardnum=et_cardnum.getText().toString().trim();

                if(type.getText().toString().trim().equals("网银"))
                    ordertype=1+"";
                else if(type.getText().toString().trim().equals("点卡"))
                    ordertype=2+"";
                else if(type.getText().toString().trim().equals("支付宝"))
                    ordertype=3+"";
                else if(type.getText().toString().trim().equals("财付通"))
                    ordertype=4+"";
                else if(type.getText().toString().trim().equals("微信"))
                    ordertype=5+"";
                else if(type.getText().toString().trim().equals("所有类型"))
                    ordertype="";
                if(enddate.getText().toString().trim().equals("选择日期")){
                    endtime="";
                }else{
                    endtime=MyActivity.getEnddate(enddate);
                }
                if(channel.getText().toString().equals("所有通道")){
                    channelid="";
                }else{
                    for(int i=1;i<str.size();i++){
                        if(channel.getText().toString().equals(str.get(i)))
                            channelid=i+"";
                    }
                }

                new Thread(){
                    public void run(){
                        if(begindate.getText().toString().equals("选择日期")){
                            handler.sendEmptyMessage(1);
                        }else{
                            if (myHttpConnect.isConnnected(getApplication())) {
                                result=myHttpConnect.urlconnect_ordermanage(email, pwd, begintime, endtime, ordernum, ordertype, channelid, cardnum, orderstatus, 1);
                                handler.sendEmptyMessage(2);

                            } else {
                                //网络有问题
                                handler.sendEmptyMessage(3);
                            }
                        }
                    }
                }.start();

                break;
            case R.id.llayout_order_activity:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }

    }
    public void init(){
        tv_left.setTextColor(getResources().getColor(R.color.textcolor64));
        tv_center.setTextColor(getResources().getColor(R.color.textcolor64));
        tv_right.setTextColor(getResources().getColor(R.color.textcolor64));
        leftline.setBackgroundColor(Color.TRANSPARENT);
        centerline.setBackgroundColor(Color.TRANSPARENT);
        rightline.setBackgroundColor(Color.TRANSPARENT);


    }
}
