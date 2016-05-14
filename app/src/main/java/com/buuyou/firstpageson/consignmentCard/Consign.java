package com.buuyou.firstpageson.consignmentCard;

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

public class Consign extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout left,right;
    private TextView leftcontext,rightcontext;
    private LinearLayout leftline,rightline;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consign);
        left= (RelativeLayout) findViewById(R.id.rlayout_consign_left);
        right= (RelativeLayout) findViewById(R.id.rlayout_consign_right);
        leftcontext= (TextView) findViewById(R.id.tv_consign_left);
        rightcontext= (TextView) findViewById(R.id.tv_consign_right);
        leftline= (LinearLayout) findViewById(R.id.llayout_consign_left);
        rightline= (LinearLayout) findViewById(R.id.llayout_consign_right);
        back= (ImageView) findViewById(R.id.iv_consign_back);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        back.setOnClickListener(this);
        leftcontext.setTextColor(getResources().getColor(R.color.colororange));
        leftline.setBackgroundColor(getResources().getColor(R.color.colororange));
        FragmentManager fm=getFragmentManager();
        FragmentTransaction t=fm.beginTransaction();
        ConsignCard c1=new ConsignCard();
        t.replace(R.id.fragment_consign, c1);
        t.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlayout_consign_left:
                rightcontext.setTextColor(getResources().getColor(R.color.textcolor64));
                rightline.setBackgroundColor(Color.TRANSPARENT);
                leftcontext.setTextColor(getResources().getColor(R.color.colororange));
                leftline.setBackgroundColor(getResources().getColor(R.color.colororange));
                FragmentManager fm=getFragmentManager();
                FragmentTransaction t=fm.beginTransaction();
                ConsignCard c1=new ConsignCard();
                t.replace(R.id.fragment_consign, c1);
                t.commit();
                break;
            case R.id.rlayout_consign_right:
                leftcontext.setTextColor(getResources().getColor(R.color.textcolor64));
                leftline.setBackgroundColor(Color.TRANSPARENT);
                rightcontext.setTextColor(getResources().getColor(R.color.colororange));
                rightline.setBackgroundColor(getResources().getColor(R.color.colororange));
                FragmentManager fm1=getFragmentManager();
                FragmentTransaction t1=fm1.beginTransaction();
                ConsignOrder c2=new ConsignOrder();
                t1.replace(R.id.fragment_consign, c2);
                t1.commit();
                break;
            case R.id.iv_consign_back:
                finish();
                break;
        }
    }
}
