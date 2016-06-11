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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buuyou.HttpConnect.myHttpConnect;
import com.buuyou.buuyoucard.R;
import com.buuyou.imageload.MyApplication;
import com.buuyou.main.Watchdetail;
import com.buuyou.other.MyActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link noticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class noticeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String path;

    private TextView date,title;
    private ImageView pic;
    private RelativeLayout next;
    private String data_result;
    private OnFragmentInteractionListener mListener;
Handler handler=new Handler(){
    public void handleMessage(Message msg){
        switch (msg.what){
            case 1:
                Toast.makeText(getActivity().getApplication(),"网络连接错误",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                try {

                    JSONObject json = new JSONObject(data_result);
                    String status=json.getString("status");
                    SharedPreferences sp=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

                    if(status.equals("1")){
                        JSONArray data=json.getJSONArray("data");
                        for(int i=0;i<data.length();i++) {
                            JSONObject temp = (JSONObject) data.get(i);
                            date.setText(temp.getString("datatime"));
                            title.setText(temp.getString("title"));
                            String picpath = temp.getString("picpath");
                            String web = temp.getString("website");
                            path = web + picpath;
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("noticeweb", web);
                            editor.commit();
                            Log.e("+++", path);

                            /**
                             * 通过imageloader加载网络上的图片
                             */
                            //不加这一句会提示：ImageLoader must be init with configuration before 且不显示图片
                            MyApplication.imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
                            ImageLoader.getInstance().displayImage(path, pic);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }
};

    public noticeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment noticeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static noticeFragment newInstance(String param1, String param2) {
        noticeFragment fragment = new noticeFragment();
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
        View view=inflater.inflate(R.layout.fragment_notice, container, false);
        final String email,password;
        title= (TextView) view.findViewById(R.id.tv_fragmentnotice_title);
        date= (TextView) view.findViewById(R.id.tv_fragmentnotice_date);
        pic= (ImageView) view.findViewById(R.id.iv_fragmentnotice_pic);
        next= (RelativeLayout) view.findViewById(R.id.rlayout_fragmentnotice_next);
        SharedPreferences sp=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        email=sp.getString("email",null);
        password=sp.getString("clearpwd",null);

        new Thread(){
            public void run(){
                if(myHttpConnect.isConnnected(getActivity().getApplication())){
                    data_result=myHttpConnect.urlconnect_notice(email,password);
                    handler.sendEmptyMessage(2);
                }else{
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
        next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MyActivity.getIntent(getActivity(),Watchdetail.class);
        }
    });
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
}
