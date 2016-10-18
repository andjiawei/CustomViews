package com.netsite.refreshview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    String[] items = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RefreshView refreshView = (RefreshView) findViewById(R.id.refreshable_view);

        recyclerView = (RecyclerView) findViewById(R.id.rc_view);
        recyclerView.setAdapter(new MyAdapter(Arrays.asList(items)));
        refreshView.setOnRefreshListener(new RefreshView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshView.finishRefreshing();
            }
        }, 0);

    }
}
