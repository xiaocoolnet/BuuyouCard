package com.buuyou.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.MineSbu.AboutBuu;
import com.buuyou.MineSbu.AccountInfo;
import com.buuyou.MineSbu.BasicInfo;
import com.buuyou.MineSbu.ChannelRate;
import com.buuyou.MineSbu.LoginLog;
import com.buuyou.MineSbu.SafeCenter;
import com.buuyou.MineSbu.Updata;
import com.buuyou.buuyoucard.R;
import com.buuyou.main.Main;
import com.buuyou.main.Mylogin;
import com.buuyou.MineSbu.Changepassword;
import com.buuyou.other.MyActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link mineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mineFragment extends Fragment implements View.OnClickListener {
   private String result;
    public TextView tv_fragmentmine_id;
    public static TextView minefragment_name;
    private EditText et_fragmentmine_pwd;
    private Button exit;
    private SharedPreferences sp;
    private LinearLayout basicinfo,accountinfo,safecenter,changepwd,loginlog,channelrate,about,versionupdate;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public mineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static mineFragment newInstance(String param1, String param2) {
        mineFragment fragment = new mineFragment();
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


        View view=inflater.inflate(R.layout.fragment_mine, container, false);
        sp=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        tv_fragmentmine_id=(TextView)view.findViewById(R.id.tv_fragmentmine_id);
        et_fragmentmine_pwd=(EditText)view.findViewById(R.id.et_fragmentmine_pwd);
        basicinfo= (LinearLayout) view.findViewById(R.id.llayout_fragmentmine_basicinfo);
        accountinfo= (LinearLayout) view.findViewById(R.id.llayout_fragmentmine_accountinfo);
        safecenter= (LinearLayout) view.findViewById(R.id.llayout_fragmentmine_safecenter);
        changepwd= (LinearLayout) view.findViewById(R.id.llayout_fragmentmine_changepwd);
        loginlog= (LinearLayout) view.findViewById(R.id.llayout_fragmentmine_loginlog);
        channelrate= (LinearLayout) view.findViewById(R.id.llayout_fragmentmine_channelRate);
        about= (LinearLayout) view.findViewById(R.id.llayout_fragmentmine_about);
        versionupdate= (LinearLayout) view.findViewById(R.id.llayout_fragmentmine_versionupdate);
        minefragment_name= (TextView) view.findViewById(R.id.tv_fragmentmine_name);
        exit=(Button)view.findViewById(R.id.exit);
        minefragment_name.setText(sp.getString("name",null));
        tv_fragmentmine_id.setText("ID:"+sp.getString("email",null));
        et_fragmentmine_pwd.setText(sp.getString("password", null));
        et_fragmentmine_pwd.setKeyListener(null);
        basicinfo.setOnClickListener(this);
        accountinfo.setOnClickListener(this);
        safecenter.setOnClickListener(this);
        changepwd.setOnClickListener(this);
        loginlog.setOnClickListener(this);
        channelrate.setOnClickListener(this);
        about.setOnClickListener(this);
        versionupdate.setOnClickListener(this);
        exit.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llayout_fragmentmine_basicinfo:
                MyActivity.getIntent(getActivity(), BasicInfo.class);
                break;
            case R.id.llayout_fragmentmine_accountinfo:
                MyActivity.getIntent(getActivity(), AccountInfo.class);
                break;
            case R.id.llayout_fragmentmine_safecenter:
                MyActivity.getIntent(getActivity(), SafeCenter.class);
                break;
            case R.id.llayout_fragmentmine_changepwd:
                MyActivity.getIntent(getActivity(), Changepassword.class);
                break;
            case R.id.llayout_fragmentmine_loginlog:
                MyActivity.getIntent(getActivity(), LoginLog.class);
                break;
            case R.id.llayout_fragmentmine_channelRate:
                MyActivity.getIntent(getActivity(), ChannelRate.class);
                break;
            case R.id.llayout_fragmentmine_about:
                MyActivity.getIntent(getActivity(), AboutBuu.class);
                break;
            case R.id.llayout_fragmentmine_versionupdate:
                MyActivity.getIntent(getActivity(),Updata.class);
                break;
            case R.id.exit:
                final SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("isboolean", true);
                editor.commit();
                MyActivity.getIntent(getActivity(), Main.class);
                getActivity().finish();
                break;

        }
    }

}
