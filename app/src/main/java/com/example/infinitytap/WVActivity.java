package com.example.infinitytap;

import androidx.appcompat.app.AppCompatActivity;
import com.clevertap.android.sdk.*;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WVActivity extends AppCompatActivity
{
    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wvactivity);
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);

        //WebView Implementation

        wv=findViewById(R.id.webView);
        wv.setWebViewClient(new MyBrowser());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new CTWebInterface(CleverTapAPI.getDefaultInstance(this)),"CleverTap");

        //to enable alerts
        //wv.setWebChromeClient(new WebChromeClient());

        wv.loadUrl("https://prerna-1997.github.io/myctwebsite/webview.html");
    }
}
class MyBrowser extends WebViewClient
{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        view.loadUrl(url);
        return true;
    }
}