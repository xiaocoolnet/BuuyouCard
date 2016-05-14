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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.other.Dropdown;
import com.buuyou.other.MyActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsignCard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsignCard#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ConsignCard extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ConsignCard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsignCard.
     */
    // TODO: Rename and change types and number of parameters
    private ListView listView;
    private ImageView back;
    private LinearLayout choose;
    private TextView tv_choosemoney;
    private TextView tv_cardno,tv_name,tv_status,tv_date,tv_ordermoney,tv_realmoney;
    private EditText et_cardnum,et_cardpwd;
    private Button commit;
    private ImageView refrush;
    private String result_card,result_order;
    private String email,pwd,facemoney,cardnum,cardpwd,date,time;
    private int item=0;
    private RelativeLayout tendata,consignorder;
    ArrayList<String> listNo=new ArrayList<String>();
    ArrayList<String> listName=new ArrayList<String>();
    ArrayList<String> listStatus=new ArrayList<String>();
    ArrayList<String> listDate=new ArrayList<String>();
    ArrayList<String> listOrdermoney=new ArrayList<String>();
    ArrayList<String> listRealmoney=new ArrayList<String>();
    List<String> str=new ArrayList<String>();
    Dropdown d=new Dropdown();
    private SharedPreferences sp;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Toast.makeText(getActivity().getApplication(), "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    listRealmoney.clear();
                    listStatus.clear();
                    listName.clear();
                    listOrdermoney.clear();
                    listDate.clear();
                    listNo.clear();
                    JSONObject json_card = null;
                    JSONObject json_order=null;
                    try {

                        json_card = new JSONObject(result_card);
                        String status_card=json_card.getString("status");
                        json_order=new JSONObject(result_order);
                        String status_order=json_order.getString("status");
                        if(status_card.equals("1")){
                            Toast.makeText(getActivity().getApplication(),"支付成功",Toast.LENGTH_LONG).show();
                        }
                        Boolean flag=true;
                        if(status_card.equals("0")){
                            String message=json_card.getString("msg");
                            if(message.equals("Could not recognize")){
                                flag=false;
                                Toast.makeText(getActivity().getApplication(),"无法识别卡类",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getActivity().getApplication(),"充值卡无效",Toast.LENGTH_SHORT).show();
                            }
                        }
                        if(flag&&status_order.equals("1")){
                            tendata.setVisibility(View.VISIBLE);
                            JSONArray temp=json_order.getJSONArray("data");
                            item=temp.length();
                            for(int i=0;i<temp.length();i++){
                                JSONObject data= (JSONObject) temp.get(i);
                                listNo.add(data.getString("BillNO"));
                                listName.add(data.getString("ChannelName"));
                                listOrdermoney.add(data.getString("OrderMoney"));
                                listRealmoney.add(data.getString("Realmoney"));
                                //以“|”分隔，需要这样写“\\|”
                                String []a=data.getString("GateMsg").split("\\|");
                                listStatus.add(a[1]);
                                String []b=data.getString("PayDate").split("T");
                                listDate.add(b[0]+" "+b[1]);
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    Toast.makeText(getActivity().getApplication(),"请填写完整信息",Toast.LENGTH_SHORT).show();
                    break;
                case 4:

                    listRealmoney.clear();
                    listStatus.clear();
                    listName.clear();
                    listOrdermoney.clear();
                    listDate.clear();
                    listNo.clear();
                    JSONObject json_refrush=null;
                    try {

                        json_refrush=new JSONObject(result_order);
                        String status_order=json_refrush.getString("status");
                        if(status_order.equals("1")){
                            JSONArray temp=json_refrush.getJSONArray("data");
                            item=temp.length();
                            for(int i=0;i<temp.length();i++){
                                JSONObject data= (JSONObject) temp.get(i);
                                listNo.add(data.getString("BillNO"));
                                listName.add(data.getString("ChannelName"));

                                listOrdermoney.add(data.getString("OrderMoney"));
                                listRealmoney.add(data.getString("Realmoney"));
                                //以“|”分隔，需要这样写“\\|”
                                String []a=data.getString("GateMsg").split("\\|");
                                listStatus.add(a[1]);
                                String []b=data.getString("PayDate").split("T");
                                listDate.add(b[0]+" "+b[1]);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //重新给listview设置适配器，使listview刷新
                    listView.setAdapter(new Myadapter());
                    break;

            }
        }
    };
    public static ConsignCard newInstance(String param1, String param2) {
        ConsignCard fragment = new ConsignCard();
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
        View view=inflater.inflate(R.layout.fragment_consign_card, container, false);
        String m[]={"5","10","15","20","25","30","50","100","200","300","500","1000"};
        sp=getActivity().getSharedPreferences("data", getActivity().MODE_PRIVATE);
        email=sp.getString("email", null);
        pwd=sp.getString("clearpwd", null);
        Log.e("+++++", email);
        Log.e("------",pwd);
        for(int i=0;i<m.length;i++){
            str.add(m[i]);
        }
        listView= (ListView) view.findViewById(R.id.listview);
        choose= (LinearLayout) view.findViewById(R.id.llayout_consigncard_choose);
        tv_choosemoney= (TextView) view.findViewById(R.id.tv_consigncard_money);
        refrush= (ImageView) view.findViewById(R.id.iv_consigncard_refrush);
        et_cardnum= (EditText) view.findViewById(R.id.et_consigncard_cardnum);
        et_cardpwd= (EditText) view.findViewById(R.id.et_consigncard_cardpwd);
        commit= (Button) view.findViewById(R.id.bt_consignment_commit);
        tendata= (RelativeLayout) view.findViewById(R.id.rlayout_consignment_tendata);

        refrush.setOnClickListener(this);
        commit.setOnClickListener(this);
        listView.setAdapter(new Myadapter());

        choose.setOnClickListener(this);


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
            case R.id.llayout_consigncard_choose:
                Dropdown.dropdown(tv_choosemoney,getActivity().getApplicationContext(),str);
                break;
            case R.id.bt_consignment_commit:
                //点击提交按钮，记录当前日期
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date = dateFormat.format(new java.util.Date());
                //获取时间
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                time = timeFormat.format(new java.util.Date());
                date=date+time;
                Log.e("date:",date);
                new Thread(){
                    public void run(){
                        facemoney=tv_choosemoney.getText().toString().trim();
                        cardnum=et_cardnum.getText().toString().trim();
                        cardpwd=et_cardpwd.getText().toString().trim();
                        if(facemoney.equals("")||cardpwd.equals("")||cardnum.equals("")){
                            handler.sendEmptyMessage(3);

                        }else{
                            if (myHttpConnect.isConnnected(getActivity().getApplication())) {
                                result_card = myHttpConnect.urlconnect_consigncard(email, pwd, facemoney, cardnum, cardpwd);
                                result_order=myHttpConnect.urlconnect_ordermanage(email, pwd, "2016-01-01", date, "", "", "", cardnum,3,1);
                                handler.sendEmptyMessage(2);

                            } else {
                                //网络有问题
                                handler.sendEmptyMessage(1);
                            }
                        }

                    }
                }.start();
                break;
            //刷新
            case R.id.iv_consigncard_refrush:
                //点击提交按钮，记录当前日期
                SimpleDateFormat dateFormat0 = new SimpleDateFormat("yyyy-MM-dd");
                date = dateFormat0.format(new java.util.Date());
                //获取时间
                SimpleDateFormat timeFormat0 = new SimpleDateFormat("HH:mm:ss");
                time = timeFormat0.format(new java.util.Date());
                //结合
                date=date+"T"+time;
                Log.e("date:",date);
                new Thread(){
                    public void run(){
                        if (myHttpConnect.isConnnected(getActivity().getApplication())) {
                            result_order=myHttpConnect.urlconnect_ordermanage(email, pwd, "2016-01-01", date, "", "", "", cardnum,3,1);
                            handler.sendEmptyMessage(4);

                        } else {
                            //网络有问题
                            handler.sendEmptyMessage(1);
                        }
                    }
                }.start();
                break;
        }
    }
    //显示十条数据
    public class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return item;
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
            View v=View.inflate(getActivity(),R.layout.listview_consignmentcard,null);
            tv_cardno=(TextView) v.findViewById(R.id.tvlv_consigncard_cardno);
            tv_date=(TextView) v.findViewById(R.id.tvlv_consigncard_date);
            tv_name=(TextView) v.findViewById(R.id.tvlv_consigncard_name);
            tv_ordermoney=(TextView) v.findViewById(R.id.tvlv_consigncard_money1);
            tv_realmoney=(TextView) v.findViewById(R.id.tvlv_consigncard_money2);
            tv_status=(TextView) v.findViewById(R.id.tvlv_consigncard_status);
            for(int i=0;i<item;i++){
                if(position==i){
                    tv_date.setText(listDate.get(i));
                    tv_cardno.setText(listNo.get(i));
                    tv_name.setText(listName.get(i));
                    tv_realmoney.setText(listRealmoney.get(i));
                    tv_ordermoney.setText(listOrdermoney.get(i));

                    if(listStatus.get(i).equals("支付成功")){
                        tv_status.setText("（"+listStatus.get(i)+"）");
                    }else {
                        tv_status.setTextColor(getResources().getColor(R.color.failordercolor));
                        tv_status.setText("（"+listStatus.get(i)+"）");
                    }
                }
            }
            return v;
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
