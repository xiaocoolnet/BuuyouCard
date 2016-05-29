package com.buuyou.firstpageson.financemanage;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buuyou.buuyoucard.R;
import com.buuyou.firstpageson.consignmentCard.ConsignCard;

public class Finance extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private RelativeLayout left,center,right;
    private TextView tv_left,tv_center,tv_right;
    private LinearLayout leftline,centerline,rightline;
    private String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        back= (ImageView) findViewById(R.id.iv_finance_back);
        left= (RelativeLayout) findViewById(R.id.rlayout_finance_left);
        center= (RelativeLayout) findViewById(R.id.rlayout_finance_center);
        right= (RelativeLayout) findViewById(R.id.rlayout_finance_right);
        tv_left= (TextView) findViewById(R.id.tv_finance_lefttitle);
        tv_center= (TextView) findViewById(R.id.tv_finance_centertitle);
        tv_right= (TextView) findViewById(R.id.tv_finance_righttitle);
        leftline= (LinearLayout) findViewById(R.id.llayout_finance_leftline);
        centerline= (LinearLayout) findViewById(R.id.llayout_finance_centerline);
        rightline= (LinearLayout) findViewById(R.id.llayout_finance_rightline);
//        tvBackName.setText(data.getString("BankName"));

        back.setOnClickListener(this);
        left.setOnClickListener(this);
        center.setOnClickListener(this);
        right.setOnClickListener(this);
        FragmentManager fm=getFragmentManager();
        FragmentTransaction t=fm.beginTransaction();
        ApplyforMoney m1=new ApplyforMoney();
        t.replace(R.id.fragment_finance, m1);
        t.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_finance_back:
                finish();
                break;
            case R.id.rlayout_finance_left:
                init();
                tv_left.setTextColor(getResources().getColor(R.color.colororange));
                leftline.setBackgroundColor(getResources().getColor(R.color.colororange));
                FragmentManager fm=getFragmentManager();
                FragmentTransaction t=fm.beginTransaction();
                ApplyforMoney m1=new ApplyforMoney();
                t.replace(R.id.fragment_finance, m1);
                t.commit();
                break;
            case R.id.rlayout_finance_center:
                init();
                tv_center.setTextColor(getResources().getColor(R.color.colororange));
                centerline.setBackgroundColor(getResources().getColor(R.color.colororange));
                FragmentManager fm2=getFragmentManager();
                FragmentTransaction t2=fm2.beginTransaction();
                ApplyforItem m2=new ApplyforItem();
                t2.replace(R.id.fragment_finance, m2);
                t2.commit();
                break;
            case R.id.rlayout_finance_right:
                init();
                tv_right.setTextColor(getResources().getColor(R.color.colororange));
                rightline.setBackgroundColor(getResources().getColor(R.color.colororange));
                FragmentManager fm3=getFragmentManager();
                FragmentTransaction t3=fm3.beginTransaction();
                ApplyforItem m3=new ApplyforItem();
                t3.replace(R.id.fragment_finance, m3);
                t3.commit();
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
