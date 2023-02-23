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

    String url, voiceStoragePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        //Get the views
        findViews();

        // Load the web page into the WebView
        url = getString(R.string.url);
        voiceStoragePath = getString(R.string.voice_storage_path);
        webView.loadUrl(url);

        // Skapa Intent för DownloadActivity och ange URL och sökväg som extras
//        Intent downloadIntent = new Intent(this, DownloadActivity.class);
//        downloadIntent.putExtra("url", url);
//        downloadIntent.putExtra("voice_storage_path", voiceStoragePath);
//        startActivity(downloadIntent);


        // Create a custom WebViewClient
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // Load all URL links inside the WebView
//                view.loadUrl(request.getUrl().toString());
//                return true;
                String url = request.getUrl().toString();
                Toast.makeText(getApplicationContext(), "Clicked on: " + url, Toast.LENGTH_SHORT).show();
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
                // Handle the download here





                // Start the download in the background thread
                Executors.newSingleThreadExecutor().execute(() -> {
                    try {
                        // Download the file
                        URL downloadUrl = new URL(url);
                        HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setDoOutput(true);
                        connection.connect();

                        // Create the destination file
                        String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
                        File destinationFile = new File(voiceStoragePath, fileName);

                        Toast.makeText(getApplicationContext(), "DOWNLOAD: " + fileName, Toast.LENGTH_SHORT).show();


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

                        // Unzip the file

                        File destinationDir = new File(getFilesDir(), getString(R.string.voices_path));

                        if (!destinationDir.exists()) {
                            destinationDir.mkdirs();
                        }

                        boolean success = Util.unzip(destinationFile, destinationDir);

                        if (success) {
                            Log.d(TAG, "Unzip completed successfully");
                        } else {
                            Log.e(TAG, "Unzip failed");
                        }

                    } catch (IOException e) {
                        Log.e(TAG, "Error downloading or unzipping file: " + e.getMessage());
                    }
                });
            }

//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));//TODO:laddar ner till downloads
//                request.setMimeType(mimetype);
//                // Ange sökvägen för att spara nedladdade filen
//                String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
//                //request.setD
//                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//                // Starta nedladdning och visa en förloppsindikator
//                downloadManager.enqueue(request);//TODO:laddar ner till downloads

//                String filename = "filename.mp3";
//                File file = new File(getFilesDir(), "voices/" + filename);
//
//                try (InputStream in = new URL(url).openStream();
//                     OutputStream out = new FileOutputStream(file)) {
//                    // Kopiera data från in till out med hjälp av en byte buffer
//                    byte[] buffer = new byte[2048];
//
//                    int bytesRead;
//                    while ((bytesRead = in.read(buffer)) != -1) {
//                        out.write(buffer, 0, bytesRead);
//                    }
//                } catch (IOException e) {
//                    Log.e(TAG, "Error copying file: " + e.getMessage());
//                    //return false;
//                }
//
//
//                //return false;
//            }
        };

        // Set the custom DownloadListener to the WebView
        webView.setDownloadListener(downloadListener);

        // Load the URL into the WebView
        //webView.loadUrl("https://example.com");
    }






    private class DownloadTask extends AsyncTask<String, Void, Void> { //TODO:för download

        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            String voiceStoragePath = params[1];



            // Skapa en fil för nedladdningen
            File file = new File(getExternalFilesDir(null), "download.zip");

            try {
                // Ladda ner filen från URL
                URLConnection connection = new URL(url).openConnection();
                InputStream input = new BufferedInputStream(connection.getInputStream());
                OutputStream output = new FileOutputStream(file);

                byte[] data = new byte[1024];
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

                // Packa upp filen
                ZipFile zipFile = new ZipFile(file);
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    File entryFile = new File(getExternalFilesDir(null), voiceStoragePath + entry.getName());
                    if (entry.isDirectory()) {
                        entryFile.mkdirs();
                    } else {
                        InputStream zipInput = zipFile.getInputStream(entry);
                        OutputStream zipOutput = new FileOutputStream(entryFile);

                        byte[] zipData = new byte[1024];
                        int zipCount;
                        while ((zipCount = zipInput.read(zipData)) != -1) {
                            zipOutput.write(zipData, 0, zipCount);
                        }

                        zipOutput.flush();
                        zipOutput.close();
                        zipInput.close();
                    }
                }

                // Radera den nedladdade filen
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    // Använd DownloadTask för att starta nedladdning och uppackning i bakgrunden
    //DownloadTask downloadTask = new DownloadTask();
    //downloadTask.execute(url, voiceStoragePath);


//    private ProgressDialog mProgressDialog;
//
//    // Metod för att ladda ner filen från URL
//    private void downloadFile(String fileUrl, String fileName) {
//        try {
//            URL url = new URL(fileUrl);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setDoOutput(true);
//            urlConnection.connect();
//
//            // Ladda ner filen till enheten
//            File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/voices");
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            File file = new File(dir, fileName);
//            FileOutputStream fileOutput = new FileOutputStream(file);
//            InputStream inputStream = urlConnection.getInputStream();
//
//            byte[] buffer = new byte[1024];
//            int bufferLength = 0;
//
//            while ((bufferLength = inputStream.read(buffer)) > 0) {
//                fileOutput.write(buffer, 0, bufferLength);
//            }
//
//            fileOutput.close();
//            urlConnection.disconnect();
//
//            // Packa upp filen och radera zipfilen
//            //Util.unzip(file.getPath(), dir.getPath());
//            file.delete();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Get the views
     * */
    private void findViews() {

        webView = findViewById(R.id.webViewDownloadActivity);

    }
}