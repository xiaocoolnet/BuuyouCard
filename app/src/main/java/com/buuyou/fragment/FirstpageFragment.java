package com.buuyou.fragment;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.firstpageson.Businessinfo;
import com.buuyou.firstpageson.ChannelAnalyse;
import com.buuyou.firstpageson.MoneyInfo;
import com.buuyou.firstpageson.Transactioninfo;
import com.buuyou.firstpageson.consignmentCard.Consign;
import com.buuyou.firstpageson.Shortcut;
import com.buuyou.firstpageson.financemanage.Finance;
import com.buuyou.firstpageson.ordermanagerment.Order;
import com.buuyou.other.MyActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstpageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstpageFragment extends Fragment implements View.OnClickListener {

   private TextView tv_fragmentfirstpage_id0,tv_fragmentfirstpage_lastlogin0;
   private EditText et_fragmentfirstpage_pwd0;
   private SharedPreferences sp;
    private LinearLayout businessinfo,shortcut,ordermanagement,consignmentcard,channelanalyse,transinfo,transinfo2,moneyinfo,finance;
    private String result="",result_temp="";
    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Toast.makeText(getActivity(),"网络连接错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FirstpageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstpageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstpageFragment newInstance(String param1, String param2) {
        FirstpageFragment fragment = new FirstpageFragment();
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
        View view=inflater.inflate(R.layout.fragment_firstpage, container, false);
        tv_fragmentfirstpage_id0=(TextView)view.findViewById(R.id.tv_fragmentfirstpage_id0);
        et_fragmentfirstpage_pwd0=(EditText)view.findViewById(R.id.et_fragmentfirstpage_pwd0);
        tv_fragmentfirstpage_lastlogin0=(TextView)view.findViewById(R.id.tv_fragmentfirstpage_lastlogin0);
        businessinfo= (LinearLayout) view.findViewById(R.id.llayout_firstpage_businessinfo);
        shortcut= (LinearLayout) view.findViewById(R.id.llayout_firstpage_shortcut);
        ordermanagement= (LinearLayout) view.findViewById(R.id.llayout_firstpage_ordermanagerment);
        consignmentcard= (LinearLayout) view.findViewById(R.id.llayout_firstpage_consigncard);
        transinfo= (LinearLayout) view.findViewById(R.id.llayout_firstpage_transactioninfo);
        transinfo2= (LinearLayout) view.findViewById(R.id.llayout_firstpage_transactioninfo2);
        channelanalyse= (LinearLayout) view.findViewById(R.id.llayout_firstpage_channelanalyse);
        moneyinfo= (LinearLayout) view.findViewById(R.id.llayout_firstpage_moneyinfo);
        finance= (LinearLayout) view.findViewById(R.id.llayout_firstpage_financemanage);

        sp=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        tv_fragmentfirstpage_id0.setText(sp.getString("email", null));
        et_fragmentfirstpage_pwd0.setText(sp.getString("password",null));
        et_fragmentfirstpage_pwd0.setKeyListener(null);
        tv_fragmentfirstpage_lastlogin0.setText(sp.getString("lastTimes", null));
        businessinfo.setOnClickListener(this);
        shortcut.setOnClickListener(this);
        ordermanagement.setOnClickListener(this);
        consignmentcard.setOnClickListener(this);
        channelanalyse.setOnClickListener(this);
        transinfo.setOnClickListener(this);
        transinfo2.setOnClickListener(this);
        moneyinfo.setOnClickListener(this);
        finance.setOnClickListener(this);
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
            case R.id.llayout_firstpage_businessinfo:
                MyActivity.getIntent(getActivity(), Businessinfo.class);
                break;
            case R.id.llayout_firstpage_shortcut:
                MyActivity.getIntent(getActivity(), Shortcut.class);
                break;
            case R.id.llayout_firstpage_ordermanagerment:
                MyActivity.getIntent(getActivity(), Order.class);
                break;
            case R.id.llayout_firstpage_consigncard:
                MyActivity.getIntent(getActivity(),Consign.class);
                break;
            case R.id.llayout_firstpage_channelanalyse:
                MyActivity.getIntent(getActivity(),ChannelAnalyse.class);
                break;
            case R.id.llayout_firstpage_financemanage:
                MyActivity.getIntent(getActivity(),Finance.class);
                break;
            case R.id.llayout_firstpage_transactioninfo:
                final SharedPreferences.Editor editor=sp.edit();
                new Thread(){
                    public void run(){
                        if(myHttpConnect.isConnnected(getActivity())){
                            result=myHttpConnect.urlconnect_transinfo(sp.getString("email",null),sp.getString("clearpwd",null));
                            MyActivity.getIntent(getActivity(), Transactioninfo.class);
                        }
                        else{
                            //网络连接错误
                            handler.sendEmptyMessage(1);
                        }
                        editor.putString("result",result);
                        editor.commit();
                    }
                }.start();

                break;
            case R.id.llayout_firstpage_transactioninfo2:
                final SharedPreferences.Editor editor2=sp.edit();
                new Thread(){
                    public void run(){
                        if(myHttpConnect.isConnnected(getActivity())){
                            result=myHttpConnect.urlconnect_transinfo(sp.getString("email",null),sp.getString("clearpwd",null));
                            MyActivity.getIntent(getActivity(), Transactioninfo.class);
                        }
                        else{
                            //网络连接错误
                            handler.sendEmptyMessage(1);
                        }
                        editor2.putString("result",result);
                        editor2.commit();
                    }
                }.start();

                break;
            case R.id.llayout_firstpage_moneyinfo:
                final SharedPreferences.Editor editor3=sp.edit();
                new Thread(){
                    public void run(){
                        if(myHttpConnect.isConnnected(getActivity())){
                            result=myHttpConnect.urlconnect_moneyinfo(sp.getString("email",null),sp.getString("clearpwd",null));
                            MyActivity.getIntent(getActivity(), MoneyInfo.class);
                        }
                        else{
                            //网络连接错误
                            handler.sendEmptyMessage(1);
                        }
                        editor3.putString("result",result);
                        editor3.commit();
                    }
                }.start();

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
