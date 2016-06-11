package com.buuyou.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.firstpageson.financemanage.ApplyforItem;
import com.buuyou.firstpageson.financemanage.ApplyforMoney;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FinanceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FinanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinanceFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ImageView back;
    private RelativeLayout left,center,right;
    private TextView tv_left,tv_center,tv_right;
    private LinearLayout leftline,centerline,rightline;
    private String status;
    private SharedPreferences sp;
    public FinanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinanceFragment newInstance(String param1, String param2) {
        FinanceFragment fragment = new FinanceFragment();
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
        View view=inflater.inflate(R.layout.fragment_finance, container, false);
        sp=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        left= (RelativeLayout) view.findViewById(R.id.rlayout_finance_left);
        center= (RelativeLayout) view.findViewById(R.id.rlayout_finance_center);
        right= (RelativeLayout) view.findViewById(R.id.rlayout_finance_right);
        tv_left= (TextView) view.findViewById(R.id.tv_finance_lefttitle);
        tv_center= (TextView) view.findViewById(R.id.tv_finance_centertitle);
        tv_right= (TextView) view.findViewById(R.id.tv_finance_righttitle);
        leftline= (LinearLayout) view.findViewById(R.id.llayout_finance_leftline);
        centerline= (LinearLayout) view.findViewById(R.id.llayout_finance_centerline);
        rightline= (LinearLayout) view.findViewById(R.id.llayout_finance_rightline);
        left.setOnClickListener(this);
        center.setOnClickListener(this);
        right.setOnClickListener(this);
        FragmentManager fm=getFragmentManager();
        FragmentTransaction t=fm.beginTransaction();
        ApplyforMoney m1=new ApplyforMoney();
        t.replace(R.id.fragment_finance, m1);
        t.commit();
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
        SharedPreferences.Editor editor=sp.edit();
        switch (v.getId()){
            case R.id.rlayout_finance_left:
                init();
                tv_left.setTextColor(getResources().getColor(R.color.colororange));
                leftline.setBackgroundColor(getResources().getColor(R.color.colororange));
                FragmentManager fm=getFragmentManager();
                FragmentTransaction t=fm.beginTransaction();
                ApplyforMoney m1=new ApplyforMoney();
                t.replace(R.id.fragment_finance, m1);
                t.commit();
                break;
            case R.id.rlayout_finance_center:
                init();
                tv_center.setTextColor(getResources().getColor(R.color.colororange));
                centerline.setBackgroundColor(getResources().getColor(R.color.colororange));
                FragmentManager fm2=getFragmentManager();
                FragmentTransaction t2=fm2.beginTransaction();
                ApplyforItem m2=new ApplyforItem();
                t2.replace(R.id.fragment_finance, m2);
                t2.commit();
                editor.putString("withdraw", "0");
                editor.commit();
                break;
            case R.id.rlayout_finance_right:
                init();
                tv_right.setTextColor(getResources().getColor(R.color.colororange));
                rightline.setBackgroundColor(getResources().getColor(R.color.colororange));
                FragmentManager fm3=getFragmentManager();
                FragmentTransaction t3=fm3.beginTransaction();
                ApplyforItem m3=new ApplyforItem();
                t3.replace(R.id.fragment_finance, m3);
                t3.commit();
                editor.putString("withdraw", "2");
                editor.commit();
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
