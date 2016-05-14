package com.buuyou.firstpageson.ordermanagerment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.buuyou.buuyoucard.R;
import com.buuyou.other.Dropdown;

import java.util.ArrayList;
import java.util.List;

public class OrderSuccess extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private ImageView back;
    private LinearLayout choosetype,choosechannel,choosebegindate,chooseenddate;
    private TextView type,channel,begindate,enddate;
    List<String> str=new ArrayList<String>();
    String a[]={"所有类型","网银","点卡","支付宝","财付通","微信","手机支付宝","手机财付通","手机QQ","其它"};
    String b[]={"所有通道","网银通道","骏网一卡通","盛大卡","神州行","征途卡","QQ卡","联通卡", "久游卡",
            "网易卡","完美卡","搜狐卡","电信卡","纵游一卡通","天下一卡通","天宏一卡通","盛付通卡", "光宇一卡通",
            "京东E卡通","中石化加油卡","微信扫码","支付宝余额","财付通余额","手机支付宝","手机财付通","手机微信",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        listView= (ListView) findViewById(R.id.listview);
        back= (ImageView) findViewById(R.id.iv_ordersuccess_back);
        choosetype= (LinearLayout) findViewById(R.id.llayout_ordersuccess_choosetype);
        choosechannel= (LinearLayout) findViewById(R.id.llayout_ordersuccess_choosechannel);
        type= (TextView) findViewById(R.id.tv_ordersuccess_choosetype);
        channel= (TextView) findViewById(R.id.tv_ordersuccess_choosechannel);
        choosebegindate= (LinearLayout) findViewById(R.id.llayout_ordersuccess_begindate);
        chooseenddate= (LinearLayout) findViewById(R.id.llayout_ordersuccess_enddate);
        begindate= (TextView) findViewById(R.id.tv_ordersuccess_choosebegindate);
        enddate= (TextView) findViewById(R.id.tv_ordersuccess_chooseenddate);
        choosechannel.setOnClickListener(this);
        choosetype.setOnClickListener(this);
        choosebegindate.setOnClickListener(this);
        chooseenddate.setOnClickListener(this);
        listView.setAdapter(new Myadapter());
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_ordersuccess_back:
                finish();
                break;
            case R.id.llayout_ordersuccess_choosetype:
                //清空list数组
                str.clear();
                for(int i=0;i<a.length;i++){
                    str.add(a[i]);
                }
                //调用Dropdown类中的dropdown函数，实现下拉菜单
                Dropdown.dropdown(type, getApplicationContext(), str);
                break;
            case R.id.llayout_ordersuccess_choosechannel:
                str.clear();
                for(int j=0;j<b.length;j++){
                    str.add(b[j]);
                }
                Dropdown.dropdown(channel, getApplicationContext(), str);
                break;
            case R.id.llayout_ordersuccess_begindate:
                Dropdown.choosedate(begindate,OrderSuccess.this);
                break;
            case R.id.llayout_ordersuccess_enddate:
                Dropdown.choosedate(enddate,OrderSuccess.this);
                break;
        }
    }

    public class Myadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 1;
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
            View v=View.inflate(OrderSuccess.this,R.layout.listview_ordersuccess,null);
            return v;
        }
    }
//    //下拉框
//    public void dropdown(final TextView textView){
//        contextview=new ListView(getApplicationContext());
//
//        contextview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String s = str.get(position);
//                textView.setText(s);
//                popupWindow.dismiss();
//            }
//        });
//        contextview.setAdapter(new contextAdapter());
//        popupWindow=new PopupWindow(contextview,600,700,true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        popupWindow.showAsDropDown(textView);//在哪一个控件下显示
//        //如果该方法生效，需要给popupWindow添加背景
//        popupWindow.setOutsideTouchable(true);//是否允许popupWindow之外的地方响应触摸事件
//    }
//    //下拉框listview
//    public class contextAdapter extends BaseAdapter{
//
//        @Override
//        public int getCount() {
//            return str.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view=View.inflate(getApplicationContext(),R.layout.listview_popupwindow_item,null);
//            TextView textView;
//            textView= (TextView) view.findViewById(R.id.tv_item);
//            textView.setText(str.get(position));
//            return view;
//        }
//    }
}
