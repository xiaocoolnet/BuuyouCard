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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.other.MyActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApplyforItem.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApplyforItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplyforItem extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView listView;
    private int num=0;
    private String result;
    private SharedPreferences sp;
    private TextView withdrawid,time,money,status;
    List<String> str_id=new ArrayList<>();
    List<String> str_time=new ArrayList<>();
    List<String> str_money=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(getActivity(),"网络连接错误",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    try {
                        str_money.clear();
                        str_time.clear();
                        str_id.clear();
                        JSONObject json=new JSONObject(result);
                        if(json.getString("status").equals("1")){
                            JSONArray data=json.getJSONArray("data");
                            num=data.length();
                            for(int i=0;i<num;i++){
                                JSONObject array= (JSONObject) data.get(i);
                                str_id.add(array.getString("WithdrawID"));
                                str_time.add(MyActivity.getTime(array.getString("WithdrawAddtime")));
                                str_money.add(array.getString("WithdrawMoney"));
                            }
                            listView = (ListView) getActivity().findViewById(R.id.lv_applyforitem);
                            listView.setAdapter(new MyAdapter());

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };


    public ApplyforItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplyforItem.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplyforItem newInstance(String param1, String param2) {
        ApplyforItem fragment = new ApplyforItem();
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
        View v=inflater.inflate(R.layout.fragment_applyforitem,container,false);
        sp=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        new Thread(){
            public void run(){
                if (myHttpConnect.isConnnected(getActivity())){
                    result=myHttpConnect.urlconnect_withdrawsearch(sp.getString("email",null),sp.getString("clearpwd",null),sp.getString("withdraw",null));
                    handler.sendEmptyMessage(2);
                }else{
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();



        return v;
    }
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return num;
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
            View view = View.inflate(getActivity(), R.layout.listview_applyforitem, null);
            withdrawid= (TextView) view.findViewById(R.id.tv_applyforitem_withdrawid);
            time= (TextView) view.findViewById(R.id.tv_applyforitem_time);
            money= (TextView) view.findViewById(R.id.tv_applyforitem_money);
            status= (TextView) view.findViewById(R.id.tv_applyforitem_status);
            if(sp.getString("withdraw",null).equals("2")){
                status.setText("已提现");
                status.setTextColor(view.getResources().getColor(R.color.applyformoneysuccess));
            }
            for(int i=0;i<num;i++){
                if(position==i){
                    withdrawid.setText(str_id.get(i));
                    time.setText(str_time.get(i));
                    money.setText(str_money.get(i));

                }
            }
            return view;
        }
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
