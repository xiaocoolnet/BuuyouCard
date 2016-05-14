package com.buuyou.firstpageson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.buuyou.buuyoucard.R;

public class ChannelAnalyse extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channelanalyse);
        listView = (ListView) findViewById(R.id.income_lv);
        listView.setAdapter(new MyAdapter());
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 2;
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
            return view;
        }
    }
}
