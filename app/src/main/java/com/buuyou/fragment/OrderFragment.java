package com.buuyou.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.firstpageson.ordermanagerment.Order;
import com.buuyou.firstpageson.ordermanagerment.OrderSearch_fail;
import com.buuyou.firstpageson.ordermanagerment.OrderSearch_success;
import com.buuyou.firstpageson.ordermanagerment.OrderSearching;
import com.buuyou.other.Dropdown;
import com.buuyou.other.MyActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String email,pwd;
    private String result_channel;
    private String channelid;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    private RelativeLayout left,center,right;
    private TextView tv_left,tv_center,tv_right;
    private LinearLayout leftline,centerline,rightline,activity;
    private LinearLayout choosetype,choosechannel,choosebegindate,chooseenddate;
    private TextView type,channel,begindate,enddate;
    private Button search;
    private EditText et_ordernum,et_cardnum;
    private String result;
    private SharedPreferences sp;
    String endtime = null;
    private int orderstatus=1;
    String ordertype;
    List<String> str=new ArrayList<String>();
    List<String> strchannel=new ArrayList<String>();
    String a[]={"所有类型","网银","点卡","支付宝","财付通","微信"};
    String b[]={"所有通道","网银通道","骏网一卡通","盛大卡","神州行","征途卡","QQ卡","联通卡", "久游卡",
            "网易卡","完美卡","搜狐卡","电信卡","纵游一卡通","天下一卡通","天宏一卡通","盛付通卡", "光宇一卡通",
            "京东E卡通","中石化加油卡","微信扫码","支付宝余额","财付通余额","手机支付宝","手机财付通","手机微信",};
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(getActivity(), "请选择开始时间", Toast.LENGTH_SHORT).show();
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
                                    MyActivity.getIntent(getActivity(), OrderSearch_success.class);
                                    break;
                                case 2:
                                    MyActivity.getIntent(getActivity(),OrderSearch_fail.class);
                                    break;
                                case 3:
                                    MyActivity.getIntent(getActivity(),OrderSearching.class);
                                    break;
                            }

                        }else{
                            if(json.getString("msg").equals("No Record"))
                                Toast.makeText(getActivity(),"未查到订单",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getActivity(),"查询失败",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    Toast.makeText(getActivity(),"网络连接错误",Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    try {
                        strchannel.clear();
                        JSONObject json=new JSONObject(result_channel);
                        strchannel.add(0,"所有通道");
                        if(json.getString("status").equals("1")){
                            JSONArray array=json.getJSONArray("data");
                            for(int i=0;i<array.length();){
                                JSONObject data= (JSONObject) array.get(i);
                                strchannel.add(++i, data.getString("ChannelName"));

                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_order, container, false);
        sp=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        left= (RelativeLayout) view.findViewById(R.id.rlayout_fragmentorder_left);
        center= (RelativeLayout) view.findViewById(R.id.rlayout_fragmentorder_center);
        right= (RelativeLayout) view.findViewById(R.id.rlayout_fragmentorder_right);
        tv_left= (TextView) view.findViewById(R.id.tv_fragmentorder_lefttitle);
        tv_center= (TextView) view.findViewById(R.id.tv_fragmentorder_centertitle);
        tv_right= (TextView) view.findViewById(R.id.tv_fragmentorder_righttitle);
        leftline= (LinearLayout) view.findViewById(R.id.llayout_fragmentorder_leftline);
        centerline= (LinearLayout) view.findViewById(R.id.llayout_fragmentorder_centerline);
        rightline= (LinearLayout) view.findViewById(R.id.llayout_fragmentorder_rightline);
        choosetype= (LinearLayout) view.findViewById(R.id.llayout_fragmentorder_choosetype);
        choosechannel= (LinearLayout) view.findViewById(R.id.llayout_fragmentorder_choosechannel);
        type= (TextView) view.findViewById(R.id.tv_fragmentorder_choosetype);
        channel= (TextView) view.findViewById(R.id.tv_fragmentorder_choosechannel);
        choosebegindate= (LinearLayout) view.findViewById(R.id.llayout_fragmentorder_begindate);
        chooseenddate= (LinearLayout) view.findViewById(R.id.llayout_fragmentorder_enddate);
        begindate= (TextView) view.findViewById(R.id.tv_fragmentorder_choosebegindate);
        enddate= (TextView) view.findViewById(R.id.tv_fragmentorder_chooseenddate);
        search= (Button) view.findViewById(R.id.bt_fragmentorder_search);
        et_ordernum= (EditText) view.findViewById(R.id.et_fragmentorder_ordernum);
        et_cardnum= (EditText) view.findViewById(R.id.et_fragmentorder_cardnum);
        activity= (LinearLayout) view.findViewById(R.id.llayout_fragmentorder_activity);
        activity.setOnClickListener(this);
        left.setOnClickListener(this);
        center.setOnClickListener(this);
        right.setOnClickListener(this);
        search.setOnClickListener(this);
        choosechannel.setOnClickListener(this);
        choosetype.setOnClickListener(this);
        choosebegindate.setOnClickListener(this);
        chooseenddate.setOnClickListener(this);
        email=sp.getString("email",null);
        pwd=sp.getString("clearpwd", null);
        new Thread(){
            public void run(){
                if(myHttpConnect.isConnnected(getActivity())){
                    result_channel=myHttpConnect.urlconnect_channellist(email,pwd);
                    handler.sendEmptyMessage(4);
                }else{
                    handler.sendEmptyMessage(3);
                }
            }
        }.start();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlayout_fragmentorder_left:
                init();
                orderstatus=1;
                tv_left.setTextColor(getResources().getColor(R.color.colororange));
                leftline.setBackgroundColor(getResources().getColor(R.color.colororange));
                break;
            case R.id.rlayout_fragmentorder_center:
                init();
                orderstatus=2;
                tv_center.setTextColor(getResources().getColor(R.color.colororange));
                centerline.setBackgroundColor(getResources().getColor(R.color.colororange));
                break;
            case R.id.rlayout_fragmentorder_right:
                init();
                orderstatus=0;
                tv_right.setTextColor(getResources().getColor(R.color.colororange));
                rightline.setBackgroundColor(getResources().getColor(R.color.colororange));
                break;
            case R.id.llayout_fragmentorder_choosetype:
                //清空list数组
                str.clear();
                for(int i=0;i<a.length;i++){
                    str.add(a[i]);
                }
                //调用Dropdown类中的dropdown函数，实现下拉菜单
                Dropdown.dropdown(type, getActivity(), str);
                break;
            case R.id.llayout_fragmentorder_choosechannel:
                str.clear();
                for(int i=0;i<b.length;i++){
                    str.add(b[i]);
                }
                Dropdown.dropdown(channel, getActivity(), str);
                break;
            case R.id.llayout_fragmentorder_begindate:
                Dropdown.choosedate(begindate,getActivity());
                break;
            case R.id.llayout_fragmentorder_enddate:
                Dropdown.choosedate(enddate,getActivity());
                break;
            case R.id.bt_fragmentorder_search:
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
                    for(int i=1;i<strchannel.size();i++){
                        if(channel.getText().toString().equals(strchannel.get(i)))
                            channelid=i+"";
                    }
                }

                new Thread(){
                    public void run(){
                        if(begindate.getText().toString().equals("选择日期")){
                            handler.sendEmptyMessage(1);
                        }else{
                            if (myHttpConnect.isConnnected(getActivity())) {
                                //点单编号记得改回来！！！！
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
            case R.id.llayout_fragmentorder_activity:
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
