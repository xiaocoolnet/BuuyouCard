package com.buuyou.firstpageson.financemanage;

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
import com.buuyou.other.MyActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApplyforMoney.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApplyforMoney#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplyforMoney extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ApplyforMoney() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplyforMoney.
     */
    private TextView bankcard,bankaccount;
    private EditText bankaddress;
    private TextView bankname;
    private SharedPreferences sp;
    private TextView availablemoney,usermoney;
    private EditText applyformoney,safecode;
    private Button submit;
    private String result;
    private String result_temp;
    private LinearLayout activity;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(getActivity(),"网络连接错误",Toast.LENGTH_SHORT).show();
                    break;
//                case 2:
//                    try {
//                        JSONObject jsonObject=new JSONObject(result);
//                        if (jsonObject.getString("status").equals("1")){
//                            Toast.makeText(getActivity(),"提现成功！",Toast.LENGTH_LONG).show();
//                        }else{
//                            String message=jsonObject.getString("msg");
//                            if(message.equals("No Record"))
//                                Toast.makeText(getActivity(),"未查到相关记录",Toast.LENGTH_SHORT).show();
//                            else if(message.equals("SafeCode Error"))
//                                Toast.makeText(getActivity(),"安全码错误",Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    break;
                case 3:
                    try {
//                        JSONObject json1=new JSONObject(result_bank);//解析银行信息，取银行名
                        JSONObject json2=new JSONObject(result_temp);//解析财务信息，取可用余额和可提现余额
//                        JSONArray temp1=json1.getJSONArray("data");
                        JSONArray temp2=json2.getJSONArray("data");
                        SharedPreferences.Editor editor=sp.edit();
//                        for(int i=0;i<temp1.length();i++){
//                            JSONObject data= (JSONObject) temp1.get(i);
//                            if(data.getString("BankID").equals(sp.getString("bankID",null))){
//                                editor.putString("bankname", data.getString("BankName"));
//
//                                editor.commit();
//                                Log.e("+++", sp.getString("bankname", null));
//                                bankname.setText(sp.getString("bankname", null));
//
//                            }
//
//                        }
                        for(int j=0;j<temp2.length();j++){
                            JSONObject data= (JSONObject) temp2.get(j);
                            editor.putString("availablemoney", data.getString("AvailableBalance"));
                            editor.putString("usermoney",data.getString("UserMoney"));
                            editor.commit();
                            availablemoney.setText(sp.getString("availablemoney",null));
                            usermoney.setText(sp.getString("usermoney",null));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    // TODO: Rename and change types and number of parameters
    public static ApplyforMoney newInstance(String param1, String param2) {
        ApplyforMoney fragment = new ApplyforMoney();
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
        View v=inflater.inflate(R.layout.fragment_applyfor_money, container, false);
        sp=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        new Thread(){
            public void run(){
                if(myHttpConnect.isConnnected(getActivity())){
                   // result_bank=myHttpConnect.urlconnect_banklist(sp.getString("email", null), sp.getString("clearpwd", null));
                    result_temp=myHttpConnect.urlconnect_moneyinfo(sp.getString("email", null), sp.getString("clearpwd", null));
                    handler.sendEmptyMessage(3);
                }
                else{
                    //网络连接错误
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();

        bankaccount= (TextView) v.findViewById(R.id.tv_applyformoney_bankaccount);
        bankcard= (TextView) v.findViewById(R.id.tv_applyformoney_bankcard);
        bankaddress= (EditText) v.findViewById(R.id.et_applyformoney_bankaddress);
        bankname= (TextView) v.findViewById(R.id.tv_applyformoney_bankname);

        availablemoney= (TextView) v.findViewById(R.id.tv_applyformoney_availablemoney);
        usermoney= (TextView) v.findViewById(R.id.tv_applyformoney_usermoney);
        submit= (Button) v.findViewById(R.id.bt_applyformoney_submit);
        activity= (LinearLayout) v.findViewById(R.id.llayout_applyformoney_activity);
        activity.setOnClickListener(this);
        bankaddress.setText(sp.getString("bankaddress",null));
        bankcard.setText(sp.getString("bankcard",null));
        bankaccount.setText(sp.getString("bankaccount", null));
        bankname.setText(sp.getString("bankname", null));
        availablemoney.setText(sp.getString("availablemoney",null));
        usermoney.setText(sp.getString("usermoney",null));
        submit.setOnClickListener(this);
        return v;
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
            case R.id.bt_applyformoney_submit:
                MyActivity.getIntent(getActivity(),Inputmoney.class);
//                final String str_money,str_safecode;
//                str_money=applyformoney.getText().toString().trim();
//                str_safecode=safecode.getText().toString().trim();
//                //判断提现金额和安全码是否为空
//                if(!str_money.equals("")&&!str_safecode.equals("")){
//                    //判断提现金额是否为整数
//                    if(isNum(str_money)){
//                        //判断提现金额是否大于余额
//                        if((Double.parseDouble(str_money)+2.0)<=Double.parseDouble(sp.getString("usermoney",null))){
//                            //判断提现金额是否满足最低提现金额
//                            if(Double.parseDouble(str_money)>=100){
//                               new Thread(){
//                                   public void run(){
//                                       if(myHttpConnect.isConnnected(getActivity())){
//                                           result=myHttpConnect.urlconnect_applyformoney(sp.getString("email",null),sp.getString("clearpwd",null),str_money,str_safecode);
//                                           handler.sendEmptyMessage(2);
//                                       }else{
//                                           handler.sendEmptyMessage(1);
//                                       }
//                                   }
//                               }.start();
//                            }else{
//                                Toast.makeText(getActivity(),"低于最低提现金额!",Toast.LENGTH_SHORT).show();
//                            }
//
//                        }else{
//                            Toast.makeText(getActivity(),"超出可结算余额!",Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        Toast.makeText(getActivity(),"输入的提现金额为整数!",Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(getActivity(),"请输入完整数据!",Toast.LENGTH_SHORT).show();
//                }

                break;
            case R.id.llayout_applyformoney_activity:
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }
    }
//    public Boolean isNum(String str){
//        Pattern pattern = Pattern.compile("[0-9]*");
//        Matcher isNum = pattern.matcher(str);
//        if(!isNum.matches()){
//            return false;
//        }
//        return true;
//    }

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
