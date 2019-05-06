package com.zhangbin.testproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        url= "https://www.sogou.com";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
