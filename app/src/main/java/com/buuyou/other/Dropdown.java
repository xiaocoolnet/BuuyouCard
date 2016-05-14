package com.buuyou.other;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.buuyou.buuyoucard.R;

import java.util.List;

/**
 * Created by Administrator on 2016/5/12.
 */
public class Dropdown {
    private static ListView contextview;
    private static PopupWindow popupWindow;
    //下拉框
    public static void dropdown(final TextView textView, final Context context, final List<String> str){
        contextview=new ListView(context);

        contextview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = str.get(position);
                textView.setText(s);
                popupWindow.dismiss();
            }
        });
        contextview.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return str.size();
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
                View view=View.inflate(context, R.layout.listview_popupwindow_item,null);
                TextView textView;
                textView= (TextView) view.findViewById(R.id.tv_item);
                textView.setText(str.get(position));
                return view;
            }
        });
        popupWindow=new PopupWindow(contextview,600,700,true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.showAsDropDown(textView);//在哪一个控件下显示
        //如果该方法生效，需要给popupWindow添加背景
        popupWindow.setOutsideTouchable(true);//是否允许popupWindow之外的地方响应触摸事件
    }
    //选择日期
    public static void choosedate(final TextView textView,Activity activity){
        new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView.setText(String.format("%d-%d-%d", year, monthOfYear+1, dayOfMonth));
            }
        },2016,5,1).show();
    }

}
