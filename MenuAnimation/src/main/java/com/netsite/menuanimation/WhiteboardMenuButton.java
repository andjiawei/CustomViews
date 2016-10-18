package com.netsite.menuanimation;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：zhangjiawei
 * 时间：2016/10/13
 */
public class WhiteboardMenuButton extends FrameLayout implements View.OnClickListener {

    private static final int IMAGE_VIEW_TAG = 100;
    private Context context;
    private List<Integer> imageSrcList = new ArrayList<>();
    private List<ImageView> imageViewList = new ArrayList<>();
    private boolean flag = true;

    public WhiteboardMenuButton(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public WhiteboardMenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public WhiteboardMenuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getProperSize(0, widthMeasureSpec);//测量有问题 暂时写死
        int height = getProperSize(0, heightMeasureSpec);
        setMeasuredDimension(50, height);
    }

    private int getProperSize(int defaultSize, int measureSpec) {
        int properSize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                properSize = defaultSize;
                break;
            case MeasureSpec.EXACTLY:
                properSize = size;
                break;
            case MeasureSpec.AT_MOST:
                properSize = size;
        }
        return properSize;
    }

    private void init() {

    }

    private void startAnim() {

        for (int i = 0; i < imageViewList.size() - 1; i++) {
            float start = flag ? 50 + i * 50 : 0f;
            float end = flag ? 0f : 50 + i * 50;

            ObjectAnimator oa = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", start, end);
            oa.setDuration(500);
            oa.start();
        }
    }

    public void setData(List<Integer> sourceList) {
        imageSrcList.clear();
        imageSrcList.addAll(sourceList);
        for (int i = 0; i < imageSrcList.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setTag(imageSrcList.get(i));
            imageView.setOnClickListener(this);
            imageView.setBackgroundResource(imageSrcList.get(i));
            this.addView(imageView, 50, 50);
            imageViewList.add(imageView);
        }
    }

    @Override
    public void onClick(View view) {

        for (int i = 0; i < imageSrcList.size(); i++) {
            if (i == imageSrcList.size() - 1) {
                flag = !flag;
                startAnim();
            } else {
                if((int)view.getTag()==imageSrcList.get(i)){
                    notifyListener(imageSrcList.get(i));
                    findViewWithTag(imageSrcList.get(imageSrcList.size()-1)).setBackgroundResource(imageSrcList.get(i));//更改button图标
                }
            }
        }
    }

    private void notifyListener(int tag) {
        if (listener != null) {
            listener.onClick(tag);
        }
    }

    private OnWhiteboardMenuClickListener listener;

    public void setOnWhiteboardMenuClickListener(OnWhiteboardMenuClickListener listener) {
        this.listener = listener;
    }

    public interface OnWhiteboardMenuClickListener {
        void onClick(int tag);
    }
}
