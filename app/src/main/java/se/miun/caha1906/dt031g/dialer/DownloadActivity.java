package se.miun.caha1906.dt031g.dialer;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DownloadActivity extends AppCompatActivity {

    private static final String TAG = "Util";

    // Webview to show voices to download
    private WebView webView;

    // To get paths from xml
    String url, voiceStoragePath;

    // To show the download progress
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        //Get the views
        findViews();

        // Initialize the progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        // Gets the values from xml.resource
        url = getString(R.string.url);
        voiceStoragePath = getString(R.string.voice_storage_path);

        // Load the web page into the WebView
        webView.loadUrl(url);

        // Create a custom WebViewClient
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                // Load all URL links inside the WebView
                String url = request.getUrl().toString();

                view.loadUrl(url);
                return true;
            }
        };

        // Set the custom WebViewClient to the WebView
        webView.setWebViewClient(webViewClient);

        // Create a custom DownloadListener
        DownloadListener downloadListener = new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {

                // Show the progress dialog
                progressDialog.show();

                // Add a delay of 20ms
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 20);

                // Start the download in the background thread
                Executors.newSingleThreadExecutor().execute(() -> {

                    try {

                        // Add sleep for 20ms
                        Thread.sleep(20);

                        // Download the file
                        URL downloadUrl = new URL(url);
                        HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setDoOutput(true);
                        connection.connect();

                        // Create the destination file
                        String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
                        File destinationFile = new File(voiceStoragePath, fileName);

                        // Write the downloaded file to the destination
                        InputStream input = connection.getInputStream();
                        FileOutputStream output = new FileOutputStream(destinationFile);
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = input.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }
                        output.close();
                        input.close();

                        // Creates destination folder
                        File destinationDir = new File(getFilesDir(), getString(R.string.voices_path));

                        // Creates folder if it doesnt exist
                        if (!destinationDir.exists()) {
                            destinationDir.mkdirs();
                        }

                        // Unzip the file
                        boolean success = Util.unzip(destinationFile, destinationDir);

                        if (success) {

                            Log.d(TAG, "Unzip completed successfully");

                            // Delete the downloaded file
                            if (destinationFile.delete()) {

                                Log.d(TAG, "Deleted downloaded file");

                            } else {

                                Log.w(TAG, "Failed to delete downloaded file");

                            }
                        } else {

                            Log.e(TAG, "Unzip failed");
                        }
                    } catch (InterruptedException e) {

                        Log.e(TAG, "Error downloading or unzipping file: " + e.getMessage());

                    } catch (IOException e) {

                        Log.e(TAG, "Error downloading or unzipping file: " + e.getMessage());

                    } finally {

                        // Dismiss the progress dialog
                        progressDialog.dismiss();

                    }
                });
            }

        };

        // Set the custom DownloadListener to the WebView
        webView.setDownloadListener(downloadListener);

    }

    /**
     * Get the views
     * */
    private void findViews() {

        webView = findViewById(R.id.webViewDownloadActivity);

    }
}