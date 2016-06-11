package com.buuyou.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.firstpageson.financemanage.Finance;
import com.buuyou.firstpageson.ordermanagerment.Order;
import com.buuyou.other.MyActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class BlankFragment extends FragmentActivity {

    private RelativeLayout rlayout_firstpage;
    private RelativeLayout rlayout_notice;
    private RelativeLayout rlayout_mine;
    private RelativeLayout rlayout_order;
    private RelativeLayout rlayout_finance;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private SharedPreferences sp;
    private String result,result_temp,result_bank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blank);
        sp=getSharedPreferences("data",MODE_PRIVATE);
        rlayout_firstpage= (RelativeLayout)findViewById(R.id.rlayout_fragment_firstpage);
        rlayout_notice= (RelativeLayout)findViewById(R.id.rlayout_fragment_notice);
        rlayout_mine= (RelativeLayout)findViewById(R.id.rlayout_fragment_mine);
        rlayout_order= (RelativeLayout)findViewById(R.id.rlayout_fragment_bill);
        rlayout_finance= (RelativeLayout) findViewById(R.id.rlayout_fragment_accountmanagement);
        img1= (ImageView) findViewById(R.id.iv_fragmentblank_1);
        img2= (ImageView) findViewById(R.id.iv_fragmentblank_2);
        img3= (ImageView) findViewById(R.id.iv_fragmentblank_3);
        img4= (ImageView) findViewById(R.id.iv_fragmentblank_4);
        img5= (ImageView) findViewById(R.id.iv_fragmentblank_5);
        tv1= (TextView) findViewById(R.id.tv_fragmentblank_1);
        tv2= (TextView) findViewById(R.id.tv_fragmentblank_2);
        tv3= (TextView) findViewById(R.id.tv_fragmentblank_3);
        tv4= (TextView) findViewById(R.id.tv_fragmentblank_4);
        tv5= (TextView) findViewById(R.id.tv_fragmentblank_5);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction t = fm.beginTransaction();
        FirstpageFragment fm_first = new FirstpageFragment();
        t.replace(R.id.fragment_content, fm_first);
        t.commit();

        rlayout_firstpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                img1.setImageResource(R.mipmap.firstpage_selected);
                tv1.setTextColor(getResources().getColor(R.color.colororange));
                FragmentManager fm = getFragmentManager();
                FragmentTransaction t = fm.beginTransaction();
                FirstpageFragment fm_first = new FirstpageFragment();
                t.replace(R.id.fragment_content, fm_first);
                t.commit();
            }
        });
        rlayout_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                img2.setImageResource(R.mipmap.bill_selected);
                tv2.setTextColor(getResources().getColor(R.color.colororange));
                FragmentManager fm = getFragmentManager();
                FragmentTransaction t = fm.beginTransaction();
                OrderFragment fm_order = new OrderFragment();
                t.replace(R.id.fragment_content, fm_order);
                t.commit();
            }
        });
        rlayout_finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                img3.setImageResource(R.mipmap.accountmanagement_selected);
                tv3.setTextColor(getResources().getColor(R.color.colororange));

                FragmentManager fm = getFragmentManager();
                FragmentTransaction t = fm.beginTransaction();
                FinanceFragment fm_finance = new FinanceFragment();
                t.replace(R.id.fragment_content, fm_finance);
                t.commit();

            }
        });
        rlayout_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                img4.setImageResource(R.mipmap.notice_selected);
                tv4.setTextColor(getResources().getColor(R.color.colororange));
                FragmentManager fm = getFragmentManager();
                FragmentTransaction t = fm.beginTransaction();
                noticeFragment fm_notice = new noticeFragment();
                t.replace(R.id.fragment_content, fm_notice);
                t.commit();

            }
        });
        rlayout_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                img5.setImageResource(R.mipmap.mine_selected);
                tv5.setTextColor(getResources().getColor(R.color.colororange));
                FragmentManager fm=getFragmentManager();
                FragmentTransaction t=fm.beginTransaction();
                mineFragment fm_mine=new mineFragment();
                t.replace(R.id.fragment_content,fm_mine);
                t.commit();

            }
        });
    }

    /**
     * 初始化，点击之前全部换成未选中图片和灰色文字
     */
    public void init(){
        img1.setImageResource(R.mipmap.firstpage_unselect);
        img2.setImageResource(R.mipmap.bill_unselect);
        img3.setImageResource(R.mipmap.accountmanagement_unselect);
        img4.setImageResource(R.mipmap.notice_unselect);
        img5.setImageResource(R.mipmap.mine_unselect);
        tv1.setTextColor(getResources().getColor(R.color.colorgray));
        tv2.setTextColor(getResources().getColor(R.color.colorgray));
        tv3.setTextColor(getResources().getColor(R.color.colorgray));
        tv4.setTextColor(getResources().getColor(R.color.colorgray));
        tv5.setTextColor(getResources().getColor(R.color.colorgray));
    }

}
