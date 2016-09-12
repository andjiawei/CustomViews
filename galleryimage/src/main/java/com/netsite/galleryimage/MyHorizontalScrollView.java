package com.netsite.galleryimage;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * 有两个地方加载item 一个是initFirstScreenChildren 还有一个是loadNextImage
 */
public class MyHorizontalScrollView extends HorizontalScrollView implements View.OnClickListener {
    private int mScreenWidth;
    private int measuredWidth;
    private int measuredHeight;
    private LinearLayout ll;
    private HorizontalScrollViewAdapter adapter;
    int currentPosition;
    private int count;
    private int firstPosition;
    private Map<View,Integer> viewPosition= new HashMap<>();

    public MyHorizontalScrollView(Context context) {
        super(context);
        init(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
    }

    /**
     * 第一次进来先加载一屏数据
     */
    private void initFirstScreenChildren(int count) {
        //让ll 加载一屏即可
        ll.removeAllViews();
        for (int i = 0; i < count; i++) {
            View view = adapter.getView(i, null, ll);
            viewPosition.put(view,i);
            view.setOnClickListener(this);
            currentPosition = i;
            ll.addView(view);
        }
    }

    /**
     * 把图片资源传进来
     */
    public void setData(HorizontalScrollViewAdapter adapter) {
        this.adapter = adapter;
        ll = (LinearLayout) getChildAt(0);
        View view = adapter.getView(0, null, ll);
        ll.addView(view);//先加入一个View便于测量 仅测量 后边又清除了
        View child = ll.getChildAt(0);
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        child.measure(widthMeasureSpec, heightMeasureSpec);//强制测量 得到child的宽高
        measuredWidth = child.getMeasuredWidth();
        measuredHeight = child.getMeasuredHeight();
        //一屏显示的数量
        count = mScreenWidth / measuredWidth == 0 ? 1 : mScreenWidth / measuredWidth + 2;
        initFirstScreenChildren(count);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_MOVE:
                float scrollX = getScrollX();//原理是移动的幕布 可以简单的理解为坐标相反的移动。就是scrollTo（x,y）中的值
                if (scrollX >= measuredWidth) {//移动的距离大于一张图片
                    loadNextImage();
                }
                if (scrollX == 0) {
                   loadPreImage();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void loadPreImage() {
        //1 移除后一张图片 2 加进来前一张 3 调用监听
        if(firstPosition==0) return ;
//        int index=currentPosition-count;
        int index = currentPosition - count;
        if(index>0){
            int position=ll.getChildCount()-1;
            ll.removeViewAt(position);
            viewPosition.remove(ll.getChildAt(position));
            //添加前边
            View view = adapter.getView(index, null, ll);
            view.setOnClickListener(this);
            ll.addView(view,0);
            viewPosition.put(view,index);
            scrollTo(measuredWidth,0);
            currentPosition--;
            firstPosition--;
            if(onImageChangeListener!=null)  onImageChangeListener.onImageChange(firstPosition,view);
        }
    }

    private void loadNextImage() {
        if(currentPosition>=adapter.getCount()-1){
            return;
        }
        //1 移除第一张 2 加进来后一张 3 调用监听
        scrollTo(0,0);//清空getScrollx()的值
        ll.removeViewAt(0);
        viewPosition.remove(ll.getChildAt(0));//集合跟随ll的view
        View view = adapter.getView(++currentPosition, null, ll);
        viewPosition.put(view,currentPosition);
        view.setOnClickListener(this);
        ll.addView(view);
        firstPosition++;
        if (onImageChangeListener!=null) {
            onImageChangeListener.onImageChange(firstPosition,view);
        }
    }

    @Override
    public void onClick(View v) {
        //点击时候清楚所有的背景色 然后单个设置为蓝色（在外部设置）
        for (int i = 0; i < ll.getChildCount(); i++) {
            ll.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }
        if(onImageClickListener!=null ){
            //没位置怎么办 有View 搞个键值对 自己拿
            int position = viewPosition.get(v) == null ? 0 : viewPosition.get(v);
            Log.e("position", "onClick: "+position);
            onImageClickListener.onImageClick(position,v);
        }
    }

    //需要两个监听 一个是点击 一个是滚动监听
    //滚动时执行如下监听
    /**
     * 滚动时切换图片执行如下监听
     * 判断getScrollX的距离大于一张图片时就调用
     */
    private OnImageChangeListener onImageChangeListener;

    public void setOnImageChangeListener(OnImageChangeListener onImageChangeListener) {
        this.onImageChangeListener = onImageChangeListener;
    }

    public interface OnImageChangeListener {
        void onImageChange(int position,View child);
    }

    /**
     * 点击时执行如下监听
     * 当前item背景变蓝 其他无背景
     */
    private OnImageClickListener onImageClickListener;
    public void setOnImageClickListener(OnImageClickListener onImageClickListener){
        this.onImageClickListener=onImageClickListener;
    }
    public interface OnImageClickListener{
        void onImageClick(int position,View item);//把点击的位置和item传递出去
    }
}