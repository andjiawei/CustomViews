package com.netsite.yourwebview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements YourWebView.OnJsReturnListener {

    private static final String TAG = "MainActivity";
    private Context context = this;
    private YourWebView yourWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yourWebView = (YourWebView) findViewById(R.id.your_webView);
        yourWebView.addJavascriptInterface("control");//js中使用此参数控制 若不需要js调用android方法 可注释掉
        yourWebView.setOnJsReturnListener(this);
    }

    /**
     * android调用js无参无返回值函数
     */
    public void Android2JsNoParmNoResult(View view) {
        String call = "javascript:sayHello()";
        yourWebView.loadUrl(call);
    }

    /**
     * android调用js有参无返回值函数
     */
    public void Android2JsHaveParmNoResult(View view) {
        String call = "javascript:alertMessage(\"" + "我是android传过来的内容,hey man" + "\")";
        yourWebView.loadUrl(call);
    }

    /**
     * android调用js有参有返回值函数（4.4之前）
     * 返回值在JsInteration类中方法的参数
     */
    public void Android2JsHaveParmHaveResult(View view) {
//      String call = "javascript:sumToJava(1,2)";
        String call = "javascript:alert(sumToJava(1,2))";//不分之前之后
        yourWebView.loadUrl(call);
    }

    /**
     * android调用js有参有返回值函数（4.4之后）
     */
    public void Android2JsHaveParmHaveResult2(View view) {
        yourWebView.Android2JsHaveParmHaveResult2("sumToJava2(3,4)");
    }

    /**
     * 调用js后的返回值
     */
    @Override
    public void onReceiveValue(String s) {
        Log.e(TAG, "onReceiveValue:js的返回值: " + s);
    }

    @Override
    protected void onDestroy() {
        yourWebView.onDestroy();
        super.onDestroy();
    }
}
