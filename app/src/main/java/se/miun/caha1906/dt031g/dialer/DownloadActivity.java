package se.miun.caha1906.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class DownloadActivity extends AppCompatActivity {

    // Webview to show voices to download
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        //Get the views
        findViews();

        // Load the web page into the WebView
        webView.loadUrl("https://dt031g.programvaruteknik.nu/dialer/voices/");

    }

    /**
     * Get the views
     * */
    private void findViews() {

        webView = findViewById(R.id.webViewDownloadActivity);

    }
}