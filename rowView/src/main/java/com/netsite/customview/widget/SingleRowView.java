package com.netsite.customview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.netsite.customview.R;
import com.netsite.customview.interfaces.OnRowClickListener;
import com.netsite.customview.model.BaseModel;
import com.netsite.customview.model.RowModel;

/**
 * Created by QYer on 2016/8/26.
 */
public class SingleRowView  extends BaseRowView {


    private ImageView iv_row_icon;
    private TextView tv_row_text;
    private Context context;
    private OnRowClickListener listener;

    public SingleRowView(Context context) {
        super(context);
        this.context =context;
        init();
    }

    public SingleRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =context;
        init();
    }

    public SingleRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context =context;
        init();

    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.widget_general_row,this);
        iv_row_icon = (ImageView) findViewById(R.id.iv_row_icon);
        tv_row_text = (TextView) findViewById(R.id.tv_row_text);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onRowClick();
                }

            }
        });
    }

    public void setData(BaseModel baseModel, OnRowClickListener listener){
        this.listener = listener;
        //数据包括 ImageViewIcon text
        RowModel rowModel=(RowModel)baseModel;
        iv_row_icon.setImageResource(rowModel.resId);
        tv_row_text.setText(rowModel.text);
    }
}
