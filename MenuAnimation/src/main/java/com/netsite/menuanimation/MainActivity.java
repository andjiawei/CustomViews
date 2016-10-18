package com.netsite.menuanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.onClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WhiteboardMenuButton whiteboardMenuButton= (WhiteboardMenuButton) findViewById(R.id.menuButton);
        List<Integer> imageSrcList=new ArrayList<>();
        imageSrcList.add(R.drawable.f);
        imageSrcList.add(R.drawable.g);
        imageSrcList.add(R.drawable.h);
        imageSrcList.add(R.drawable.b);
        imageSrcList.add(R.drawable.c);
        imageSrcList.add(R.drawable.d);
        imageSrcList.add(R.drawable.e);

        imageSrcList.add(R.drawable.a);

        whiteboardMenuButton.setData(imageSrcList);
        whiteboardMenuButton.setOnWhiteboardMenuClickListener(new WhiteboardMenuButton.OnWhiteboardMenuClickListener() {
            @Override
            public void onClick(int index) {
                Log.e("onClick", "onClick: "+index );
            }
        });
    }
}
