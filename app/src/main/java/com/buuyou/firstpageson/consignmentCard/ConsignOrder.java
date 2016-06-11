package com.buuyou.firstpageson.consignmentCard;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
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
 * {@link ConsignOrder.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsignOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsignOrder extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String channelid;

    public ConsignOrder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsignOrder.
     */
    // TODO: Rename and change types and number of parameters
    private LinearLayout choosebegindate,chooseenddate,choosetype,choosestatus,activity;
    private TextView tv_begindate,tv_enddate,tv_type,tv_status;
    private Button bt_submit;
    private EditText et_cardnum;
    private String email,pwd,result,result_channel;
    SharedPreferences sp;

    String b[]={"所有状态","处理中","成功","失败"};
    List<String> str=new ArrayList<String>();
    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Toast.makeText(getActivity().getApplication(), "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    try {
                        JSONObject json=new JSONObject(result);
                        String status=json.getString("status");
                        String message=json.getString("msg");
                        if(status.equals("1")){
                            MyActivity.getIntent(getActivity(), ConsignSearch.class);
                        }else{
                            if(message.equals("No Record")) {
                                Toast.makeText(getActivity().getApplication(),"未查到相关数据",Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    Toast.makeText(getActivity().getApplication(), "请选择开始日期", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    try {
                        str.clear();
                        JSONObject json=new JSONObject(result_channel);
                        str.add(0,"所有通道");
                        if(json.getString("status").equals("1")){
                            JSONArray array=json.getJSONArray("data");
                            for(int i=0;i<array.length();){
                                JSONObject data= (JSONObject) array.get(i);
                                str.add(++i, data.getString("ChannelName"));

                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    public static ConsignOrder newInstance(String param1, String param2) {
        ConsignOrder fragment = new ConsignOrder();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_consign_order, container, false);
        sp=getActivity().getSharedPreferences("data", getActivity().MODE_PRIVATE);
        email=sp.getString("email", null);
        pwd=sp.getString("clearpwd", null);
        choosebegindate= (LinearLayout) view.findViewById(R.id.llayout_consignorder_begindate);
        chooseenddate= (LinearLayout) view.findViewById(R.id.llayout_consignorder_enddate);
        tv_begindate= (TextView) view.findViewById(R.id.tv_consignorder_choosebegindate);
        tv_enddate= (TextView) view.findViewById(R.id.tv_consignorder_chooseenddate);
        choosetype= (LinearLayout) view.findViewById(R.id.llayout_consignorder_choosetype);
        choosestatus= (LinearLayout) view.findViewById(R.id.llayout_consignorder_choosestatus);
        tv_type= (TextView) view.findViewById(R.id.tv_consignorder_choosetype);
        tv_status= (TextView) view.findViewById(R.id.tv_consignorder_choosestatus);
        bt_submit= (Button) view.findViewById(R.id.bt_consignorder_submit);
        et_cardnum= (EditText) view.findViewById(R.id.et_consignorder_cardnum);
        activity= (LinearLayout) view.findViewById(R.id.llayout_consignorder_activity);
        activity.setOnClickListener(this);
        choosebegindate.setOnClickListener(this);
        chooseenddate.setOnClickListener(this);
        choosetype.setOnClickListener(this);
        choosestatus.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        new Thread(){
            public void run(){
                if(myHttpConnect.isConnnected(getActivity())){
                    result_channel=myHttpConnect.urlconnect_channellist(email,pwd);
                    handler.sendEmptyMessage(4);
                }else{
                    handler.sendEmptyMessage(1);
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
            case R.id.llayout_consignorder_begindate://开始日期
                Dropdown.choosedate(tv_begindate, getActivity());
                break;
            case R.id.llayout_consignorder_enddate://结束日期
                Dropdown.choosedate(tv_enddate,getActivity());
                break;
            case R.id.llayout_consignorder_choosetype:
                //调用Dropdown类中的dropdown函数，实现下拉菜单
                Dropdown.dropdown(tv_type, getActivity().getApplicationContext(), str);
                break;
            case R.id.llayout_consignorder_choosestatus:
                //清空list数组
                str.clear();
                for(int i=0;i<b.length;i++){
                    str.add(b[i]);
                }
                //调用Dropdown类中的dropdown函数，实现下拉菜单
                Dropdown.dropdown(tv_status,getActivity().getApplicationContext(),str);
                break;
            case R.id.bt_consignorder_submit:

                new Thread(){
                    SharedPreferences.Editor editor=sp.edit();
                    String str_num=et_cardnum.getText().toString().trim();
                    String str_type=tv_type.getText().toString().trim();
                    String str_status=tv_status.getText().toString().trim();
                    String str_begintime=MyActivity.getBegindate(tv_begindate);
                    String str_endtime=tv_enddate.getText().toString().trim();
                    int statusid;
                    public void run(){
                        if(tv_begindate.getText().toString().trim().equals("选择日期")){
                            handler.sendEmptyMessage(3);

                        }else{
                            if (myHttpConnect.isConnnected(getActivity().getApplication())) {
                                if(str_status.equals("所有状态"))
                                    statusid=3;
                                else if(str_status.equals("成功"))
                                    statusid=1;
                                else if(str_status.equals("失败"))
                                    statusid=2;
                                else if(str_status.equals("处理中"))
                                    statusid=0;
                                if (str_endtime.equals("选择日期")){
                                    str_endtime="";
                                }else{
                                    str_endtime=MyActivity.getEnddate(tv_enddate);
                                }
                                Log.e("+++++++", str_begintime + "----" + str_endtime);
                                if(tv_type.getText().toString().equals("所有通道")){
                                    channelid="";
                                }else{
                                    for(int i=1;i<str.size();i++){
                                        if(tv_type.getText().toString().equals(str.get(i)))
                                            channelid=i+"";
                                    }
                                }

                                result=myHttpConnect.urlconnect_ordermanage(email, pwd, str_begintime, str_endtime, "", "", channelid, str_num,statusid,1);
                                editor.putString("result", result);
                                editor.commit();
                                handler.sendEmptyMessage(2);


                            } else {
                                //网络有问题
                                handler.sendEmptyMessage(1);
                            }
                        }
                    }
                }.start();

                break;
            case R.id.llayout_consignorder_activity:
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;

        }
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
