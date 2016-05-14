package com.buuyou.other;


import android.app.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/5/13.
 */
public class MyActivity {
    private static String date;
    private static String time;

    //开启页面
    public static void getIntent(Activity activity,Class c){
        Intent intent=new Intent(activity,c);
        activity.startActivity(intent);
    }
    //得到开始时间
    public static String getBegindate(TextView textView){
        date=textView.getText().toString().trim();
        return date+"T00:00:00";
    }
    //得到结束时间
    public static String getEnddate(TextView textView){
        date = textView.getText().toString().trim();
        //获取时间
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        time = timeFormat.format(new java.util.Date());
        //结合
        return date+"T"+time;
    }
}
