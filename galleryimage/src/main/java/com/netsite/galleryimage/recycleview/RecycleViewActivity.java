package com.netsite.galleryimage.recycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.netsite.galleryimage.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecycleViewActivity extends AppCompatActivity implements MyRecycleView.OnItemScrollChangeListener, MyAdapter.OnItemClickLitener {

    private ImageView imageView;
    private MyRecycleView recycleView;
    List<Integer> mDatas = new ArrayList<>(Arrays.asList(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.l));
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        imageView = (ImageView) findViewById(R.id.imageView);
        recycleView = (MyRecycleView) findViewById(R.id.recycleView);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setOnItemScrollChangeListener(this);
        //设置适配器
        recycleView.setAdapter(adapter =new MyAdapter(mDatas));
        adapter.setOnItemClickLitener(this);
    }

    @Override
    public void onChange(View view, int position) {
        imageView.setImageResource(mDatas.get(position));
    }

    @Override
    public void onItemClick(View view, int position) {
        imageView.setImageResource(mDatas.get(position));
    }
}
