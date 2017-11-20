package com.doganoguz.santra;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class puand extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puand);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("http://www.doganoguz.96.lt/puan.html");
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        // webView'i tasarımdakiyle bağlıyoruz.
        webView = (WebView) findViewById(R.id.webView);

        // webView'i JavaScript kodlarını çalıştıracak şekilde set ediyoruz.
        webView.getSettings().setJavaScriptEnabled(true);

        // Sayfanın yüklendiğinin anlaşılması için ProgressDialog açıyoruz.
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Santra",
                "Puan Durumu Yükleniyor...", true);

        webView.setWebViewClient(new WebViewClient() {

            // Sayfa Yüklenirken bir hata oluşursa kullanıcıyı uyarıyoruz.
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), "Hay Aksi! Bir Hata Oluştu :(",
                        Toast.LENGTH_SHORT).show();
            }

            // Sayfanın yüklenme işlemi bittiğinde progressDialog'u kapatıyoruz.
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

        //Web sayfamızın url'ini webView'e yüklüyoruz.
        webView.loadUrl("http://www.doganoguz.96.lt/puan.html");











    }

}
