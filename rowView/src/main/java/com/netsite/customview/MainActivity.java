package com.netsite.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.netsite.customview.interfaces.OnRowClickListener;
import com.netsite.customview.model.RowModel;
import com.netsite.customview.widget.SingleRowView;

public class MainActivity extends AppCompatActivity implements OnRowClickListener {

    private SingleRowView rowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rowView = (SingleRowView) findViewById(R.id.wg_row_view);
        rowView.setData(new RowModel(R.drawable.more_my_favorite,"favorite"),this);//传递进listener 内部调用后 这里实现
    }

    @Override
    public void onRowClick() {
        Toast.makeText(this,"点击监听",Toast.LENGTH_SHORT).show();
    }
}
