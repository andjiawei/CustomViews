package com.netsite.plusminbutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.netsite.plusminbutton.widget.PlusMinButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlusMinButton plusMinButton = (PlusMinButton) findViewById(R.id.ll_plus_min_button);
        plusMinButton.setCountPage("1");
        plusMinButton.setCurrentPage("1");
        plusMinButton.setOnPageChangeListener(new PlusMinButton.OnPageChangeListener() {
            @Override
            public void onPageChange(int currentPage) {
            }
        });
    }
}
