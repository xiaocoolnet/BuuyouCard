package com.buuyou.HttpConnect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/4/6.
 */
public class myHttpConnect {

    public static boolean isConnnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo networkInfo[] = connectivityManager.getAllNetworkInfo();

            if (null != networkInfo) {
                for (NetworkInfo info : networkInfo) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        Log.e("检查网络", "the net is ok");
                        return true;
                    }
                }
            }
        }
        return false;
    }
//登录
    public static String urlconnect(String myphone, String mypass) {

        String url=UrlPath.NET_API+UrlPath.loginType+UrlPath.data+myphone+","+mypass;
        String urlresult=HttpResult(url);
        return urlresult;
    }
    //得到验证码
    public static String urlconnect_pass(String email, String phone) {

        String url=UrlPath.NET_API+UrlPath.passType+UrlPath.data+email+","+phone;
        String urlresult=HttpResult(url);
        return urlresult;
    }
    //根据验证码更改密码
    public static String urlconnect_updapass(String email, String valiCode,String newpassword,String valistr) {

        String url=UrlPath.NET_API+UrlPath.updatapassType+UrlPath.data+email+","+valiCode+","+newpassword+","+2+","+valistr;
        String urlresult=HttpResult(url);
        return urlresult;
    }
    //根据原密码更改密码
    public static String urlconnect_changepass(String email, String oldpassword,String newpassword) {

        String url=UrlPath.NET_API+UrlPath.updatapassType+UrlPath.data+email+","+oldpassword+","+newpassword+","+1;
        String urlresult=HttpResult(url);
        Log.e("changepwd:",urlresult);
        return urlresult;
    }
    //通道费率
    public static String urlconnect_channelrate(String email,String password){
        String url=UrlPath.NET_API+UrlPath.channelrateType+UrlPath.data+email+","+password;
        String urlresult=HttpResult(url);
        Log.e("channel:",urlresult);
        return urlresult;
    }
    //登录日志
    public static String urlconnect_loginlog(String email,String password){
        String url=UrlPath.NET_API+UrlPath.loginlogType+UrlPath.data+email+","+password+","+10+","+1;
        String urlresult=HttpResult(url);
        Log.e("log:",urlresult);
        return urlresult;
    }
    //通知公告
    public static String urlconnect_notice(String email,String password){
        String url=UrlPath.NET_API+UrlPath.noticeType+UrlPath.data+email+","+password;
        String urlresult=HttpResult(url);
        Log.e("log:",urlresult);
        return urlresult;
    }
    //点卡寄售
    public static String urlconnect_consigncard(String email,String password,String facemoney,String cardnumber,String cardpass){
        String url=UrlPath.NET_API+UrlPath.consigncardType+UrlPath.data+email+","+password+","+facemoney+","+cardnumber+","+cardpass;
        String urlresult=HttpResult(url);
        Log.e("log:",urlresult);
        return urlresult;
    }
    //订单管理
    public static String urlconnect_ordermanage(String email,String password,String begintime,String endtime,String ordernum,String paytype,String channelID,String cardnumber,int ordertype,int subtype){
        String url=UrlPath.NET_API+UrlPath.ordermanageType+UrlPath.data+email+","+password+","+begintime+","+endtime+","+ordernum+","+paytype+","+channelID+","+cardnumber+","+ordertype+","+subtype+","+10+","+1;
        String urlresult=HttpResult(url);
        Log.e("log:",urlresult);
        return urlresult;
    }
    //通道收入分析
    public static String urlconnect_channelanalyse(String email,String password,String begintime,String endtime){
        String url=UrlPath.NET_API+UrlPath.channelanalyseType+UrlPath.data+email+","+password+","+begintime+","+endtime;
        String urlresult=HttpResult(url);
        Log.e("log:",urlresult);
        return urlresult;
    }

//连接服务器
    public static String HttpResult(String url) {
        String httpresult="";
        try {
            URL myurl=new URL(url);
            HttpURLConnection connection= (HttpURLConnection) myurl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            int code=connection.getResponseCode();
            if(code==200){
                InputStream in=connection.getInputStream();
                httpresult = HttpBuff(in);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpresult;
    }
//读入数据
   public static String HttpBuff(InputStream in) {
       String buffresult="";
        try {
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            byte[] bufe=new byte[1024];
            int len;
            while ((len=in.read(bufe))!= -1){
             out.write(bufe,0,len);
            }
            in.close();
            out.close();
             buffresult=new String(out.toByteArray());
            return buffresult;
        } catch (IOException e) {
            e.printStackTrace();
            return buffresult="";
        }

   }



}
