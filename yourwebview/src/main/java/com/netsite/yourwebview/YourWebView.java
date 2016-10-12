package com.netsite.yourwebview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * 作者：zhangjiawei
 * 时间：2016/10/11
 */
public class YourWebView extends FrameLayout {

    private static final String ASSETS_INDEX = "file:///android_asset/hellotest.html";
    private Context context;
    private WebView webView;

    public YourWebView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public YourWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public YourWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        webView = new WebView(context);
//      webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);//取消滚动条
        initSetting();
        initWebViewClient();
        initWebChromeClient();
        webView.loadUrl(ASSETS_INDEX);
    }

    public void loadUrl(final String url){
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(url);
            }
        });
    }

    public void addJavascriptInterface(String controlName){//"control"
        webView.addJavascriptInterface(new JsInteration(),controlName);
    }

    private void initWebChromeClient() {
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //message为js方法返回值
                Log.e("message", "onJsAlert: " + message);
                if (!"undefined".equals(message) && jsReturnListener != null) {
                    jsReturnListener.onReceiveValue("jsAlert"+message);
                }
                result.confirm();
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
//                   ViewUtil.setVisibility(progressBar, View.INVISIBLE);
                } else {
//                    ViewUtil.setVisibility(progressBar, View.VISIBLE);
//                    progressBar.setProgress(newProgress);
                }
            }
        });
    }

    private void initWebViewClient() {

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //在当前的webview中跳转到新的url
                view.loadUrl(url);
                //启动手机浏览器来打开新的url
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            //载入页面开始的事件
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            // 载入页面完成的事件
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//如果只是简单的接受所有证书的话，就直接调process()方法就行了
                // handler.cancel();
                // handler.handleMessage(null); } });
            }

        });
    }

    private void initSetting() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setUseWideViewPort(false);//支持Viewport标签 适应窗口缩放
        webSettings.setBuiltInZoomControls(true);//支持手势缩放
        webSettings.setDisplayZoomControls(false);//隐藏（— +）图标
    }

    /**
     * 有返回值时：
     * 1. 4.4前 用js调android方法当参数传回来 JsInteration onSumResult中 。
     * 2. 4.4后 用webView.evaluateJavascript
     * 3.重写onJsAlert方法的返回值(习惯用这种)
     */
    public void callJs(final String jsMethod) {
        //"javascript:alertMessage(\"" + "xxx" + "\")";
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(jsMethod);
            }
        });
    }

    /**
     * js调用android的方法
     */
    class JsInteration {
        @JavascriptInterface
        public void toastMessage(String message) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void onSumResult(int result) {
//           Toast.makeText(context, "我是android调用js方法(4.4前)，入参是1和2，js返回结果是" + result, Toast.LENGTH_SHORT).show();
            if(jsReturnListener!=null){
                jsReturnListener.onReceiveValue("我是android调用js方法(4.4前)，入参是1和2，js返回结果是"+result);
            }
        }
    }


    /**
     * android调用js有参有返回值函数（4.4之后）
     * evaluateJavascript方法必须在UI线程（主线程）调用，因此onReceiveValue也执行在主线程
     * 第一个参数是js方法名
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void Android2JsHaveParmHaveResult2(String jsMethod) {//"sumToJava2(3,4)"
        webView.evaluateJavascript(jsMethod, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String str) {
//                Toast.makeText(context, "我是android调用js方法(4.4后)，入参是3和4，js返回结果是" + Str, Toast.LENGTH_SHORT).show();
                if(jsReturnListener!=null){
                    jsReturnListener.onReceiveValue("我是android调用js方法(4.4后)，入参是3和4，js返回结果是"+str);
                }
            }
        });
    }

    /**
     * 网页回退
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * activity生命周期调此方法
     */
    public void onDestroy() {
        if (webView != null) {
            webView.setVisibility(View.GONE);
            webView.removeAllViews();
            webView.destroy();
            releaseAllWebViewCallback();
        }
    }

    /**
     * 防止内存泄露
     */
    public void releaseAllWebViewCallback() {
        if (android.os.Build.VERSION.SDK_INT < 16) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 调用js的return
     */
    public interface OnJsReturnListener {
        void onReceiveValue(String s);
    }

    private OnJsReturnListener jsReturnListener;

    public void setOnJsReturnListener(final OnJsReturnListener jsReturnListener) {
        this.jsReturnListener = jsReturnListener;
    }
}
