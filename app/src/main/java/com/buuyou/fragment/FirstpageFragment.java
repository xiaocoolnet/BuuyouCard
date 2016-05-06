package com.buuyou.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buuyou.buuyoucard.R;
import com.buuyou.firstpageson.Businessinfo;
import com.buuyou.firstpageson.Shortcut;

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
    private LinearLayout businessinfo,shortcut;
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
        sp=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        tv_fragmentfirstpage_id0.setText(sp.getString("email", null));
       et_fragmentfirstpage_pwd0.setText(sp.getString("password",null));
        et_fragmentfirstpage_pwd0.setKeyListener(null);
        tv_fragmentfirstpage_lastlogin0.setText(sp.getString("lastTimes", null));
        businessinfo.setOnClickListener(this);
        shortcut.setOnClickListener(this);
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
                Intent intent1=new Intent(getActivity().getApplication(), Businessinfo.class);
                startActivity(intent1);
                break;
            case R.id.llayout_firstpage_shortcut:
                Intent intent2=new Intent(getActivity().getApplication(), Shortcut.class);
                startActivity(intent2);
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
