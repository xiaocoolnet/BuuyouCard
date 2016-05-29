package com.buuyou.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.buuyou.buuyoucard.R;

import java.net.MalformedURLException;
import java.net.URL;

public class Watchdetail extends AppCompatActivity {

private WebView wv;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watchdetail);
        SharedPreferences sp=getSharedPreferences("data", Context.MODE_PRIVATE);
        String web=sp.getString("noticeweb", null);
        wv= (WebView) findViewById(R.id.webview);
        back= (ImageView) findViewById(R.id.iv_watchdetail_back);
        WebSettings webSettings = wv.getSettings();//设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);//设置可以访问文件
        webSettings.setAllowFileAccess(true);//设置支持缩放
        webSettings.setBuiltInZoomControls(true);//加载需要显示的网页
        //设置屏幕自适应
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);

        wv.loadUrl(web);
        wv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
