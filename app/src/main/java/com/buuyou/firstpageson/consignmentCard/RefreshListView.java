package com.buuyou.firstpageson.consignmentCard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buuyou.buuyoucard.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/28.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener,AdapterView.OnItemClickListener {
    private View mview;
    private int startY = -1;
    private int measureHeight;
    private static final int STATE_PULL_TO_REFRESH = 1;//下拉刷新
    private static final int STATE_RELEASE_TO_REFRESH = 2;//松开刷新
    private static final int STATE_REFRESHING = 3;//正在刷新
    private int CurrenState = STATE_PULL_TO_REFRESH;
    private TextView mText;
    private RotateAnimation animUp;//向上动画
    private RotateAnimation animDown;//向下动画

    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView tvTime;
    private View footview;
    private int footViewHeight;
    private boolean isloadingmore;

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        //initFooter();
    }

    public RefreshListView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initView();
        // initFooter();
    }

    public RefreshListView(Context context) {
        super(context);
        initView();
        // initFooter();
    }

    private void initView() {

        mview = View.inflate(getContext(), R.layout.refresh, null);
        this.addHeaderView(mview);
        mview.measure(0, 0);
        measureHeight = mview.getMeasuredHeight();
        //隐藏下拉刷新
        mview.setPadding(0, -measureHeight, 0, 0);
        mText = (TextView) mview.findViewById(R.id.refresh_title);
        tvTime = (TextView) mview.findViewById(R.id.refresh_time);
        initAnim();
        setTime();
        //设置滑动监听
        this.setOnScrollListener(this);
    }

    /*
    onTouchEvent方法：
    onTouchEvent方法是override 的Activity的方法。
    重新了Activity的onTouchEvent方法后，当屏幕有touch事件时，此方法就会别调用。
    （当把手放到Activity上时，onTouchEvent方法就会一遍一遍地被调用）
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {//如果用户按住头条新闻向下滑动，会导致listview无法拿到actiondown，
                    //此时要重新获取starY
                    startY = (int) ev.getY();
                }
                if (CurrenState == STATE_REFRESHING) {//如果是正在刷新，则什么都不做
                    break;
                }
                int endY = (int) ev.getY();
                int dy = endY - startY;
                if (dy > 0 && getFirstVisiblePosition() == 0) {//向下滑动且是第一个item
                    int paddingTop = dy - measureHeight;//计算当前padding值
                    if (paddingTop >= 0 && CurrenState != STATE_RELEASE_TO_REFRESH) {
                        //切换到松开刷新
                        CurrenState = STATE_RELEASE_TO_REFRESH;
                        refreshState();
                    } else if (paddingTop < 0 && CurrenState != STATE_PULL_TO_REFRESH) {
                        //切换到下拉刷新
                        CurrenState = STATE_PULL_TO_REFRESH;
                        refreshState();
                    }
                    mview.setPadding(0, paddingTop, 0, 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;//起始状态归零
                if (CurrenState == STATE_RELEASE_TO_REFRESH) {//如果当前状态是松开刷新
                    CurrenState = STATE_REFRESHING;//切换为正在刷新
                    //显示头布局
                    mview.setPadding(0, 0, 0, 0);
                    refreshState();
                    //下拉刷新回调
                    if (mListener != null) {
                        mListener.onRefresh();
                    }
                } else if (CurrenState == STATE_PULL_TO_REFRESH) {
                    //隐藏头布局
                    mview.setPadding(0, -measureHeight, 0, 0);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    //根据当前语句刷新界面
    public void refreshState() {
        switch (CurrenState) {
            case STATE_PULL_TO_REFRESH:
                mText.setText("下拉刷新");
                break;
            case STATE_RELEASE_TO_REFRESH:
                mText.setText("松开刷新");
                break;
            case STATE_REFRESHING:
                mText.setText("正在刷新");

                break;
            default:
                break;
        }
    }

    /*
    初始化箭头方向
     */
    public void initAnim() {
        animUp = new RotateAnimation(-180, -0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(500);
        animUp.setFillAfter(true);

        animDown = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animDown.setDuration(500);
        animDown.setFillAfter(true);
    }

    public OnRefreshListener mListener;

    /*
    正在刷新
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }


    public interface OnRefreshListener {
        //下拉刷新
        public void onRefresh();

        //  public void loadMore();
    }

    //刷新完成
    /*
    此方法是刷新完成，应该在访问网络数据完后调用，此处因为没有访问网络，所以方法梅内调用
     */
    public void onRefreshComplete() {
        //隐藏下拉刷新
        mview.setPadding(0, -measureHeight, 0, 0);
        //更改状态
        CurrenState = STATE_PULL_TO_REFRESH;

    }

    //设置刷新时间
    public void setTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        tvTime.setText(time);
    }


    //OnScrollListener接口方法
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (scrollState == SCROLL_STATE_IDLE) {
            int lastVisiblePosition = getLastVisiblePosition();
            if (lastVisiblePosition >= getCount() - 1 && !isloadingmore) {
                isloadingmore = true;
                //System.out.println("下拉加载！！！！！！！！！！！！！！");
                //到底了
                //加载更多
//                footview.setPadding(0, 0, 0, 0);
                //listview设置当前展示的item位置
                setSelection(getCount() - 1);//跳到加载更多item的位置去展示
                if (mListener != null) {
                    // mListener.loadMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    //重写item点击方法
    private AdapterView.OnItemClickListener mItemClickListener
            ;    @Override
                 public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mItemClickListener=listener;
        super.setOnItemClickListener(this);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick(parent,view,position-getHeaderViewsCount(),id);
        }
    }

}
