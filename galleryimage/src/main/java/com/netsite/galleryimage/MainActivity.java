package com.netsite.galleryimage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Integer> mDatas = new ArrayList<>(Arrays.asList(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.l));
    private ImageView imageView;
    private LinearLayout ll_container;
    MyHorizontalScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
        setData();
    }

    private void intiView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        scrollView = (MyHorizontalScrollView) findViewById(R.id.scrollView);
        scrollView.setOnImageChangeListener(new MyHorizontalScrollView.OnImageChangeListener() {
            @Override
            public void onImageChange(int position,View item) {
                imageView.setImageResource(mDatas.get(position));
            }
        });

        scrollView.setOnImageClickListener(new MyHorizontalScrollView.OnImageClickListener() {
            @Override
            public void onImageClick(int position, View item) {
                imageView.setImageResource(mDatas.get(position));
                item.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });

    }

    private void setData() {
        imageView.setImageResource(mDatas.get(0));//默认设置第一张
        scrollView.setData(new HorizontalScrollViewAdapter(this,mDatas));
    }
}