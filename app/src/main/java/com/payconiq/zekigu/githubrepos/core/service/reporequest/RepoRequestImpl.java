package com.payconiq.zekigu.githubrepos.core.service.reporequest;

import android.os.AsyncTask;
import android.util.Log;

import com.payconiq.zekigu.githubrepos.core.app.ApplicationManager;
import com.payconiq.zekigu.githubrepos.core.service.HttpRequestContract;
import com.payconiq.zekigu.githubrepos.core.service.repoparser.strategy.ParseStrategy;
import com.payconiq.zekigu.githubrepos.core.utils.BroadcastConstants;
import com.payconiq.zekigu.githubrepos.core.utils.BroadcastSender;
import com.payconiq.zekigu.githubrepos.core.utils.HttpConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

/**
 *  Created by zekigu on 15.09.2017.
 */
public final class RepoRequestImpl implements HttpRequestContract, RepoRequestContract {

    private int page = 1;
    private ParseStrategy parseStrategy;
    private ExecutorService executorService = Executors.newSingleThreadExecutor(); // grab a seperate and operation specific TPE

    public RepoRequestImpl(){
        checkAndResetExecutor();
    }

    public void addRepoResponseParser(ParseStrategy parseStrategy){
        this.parseStrategy = parseStrategy;
    }

    @Override
    public void retrieveRepos() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return makeRepoHTTPRequest();
            }

            @Override
            protected void onPostExecute(String content) {
                super.onPostExecute(content);
                parseStrategy.parse(content);
            }
        }.executeOnExecutor(executorService);
    }

    @Override
    public void retrieveImages() {
        // TODO: Call ImageLoaderImpl module to retrieve images
    }

    private String makeRepoHTTPRequest() {
        URL finalUrl = null;
        try {
            Log.i("Test", "repo page count: " + page);
            String urlStr = String.format(HttpConstants.RETRIEVE_REPO_REQUEST_URL, page++, HttpConstants.REPO_ITEM_PER_PAGE);
            finalUrl = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpsURLConnection conn = null;
        try {
            conn = (HttpsURLConnection) finalUrl.openConnection();
            setConnectionParams(conn);

            int respCode = conn.getResponseCode();
            if (respCode == 200) {
                return retreiveResponse(conn);
            } else {
                BroadcastSender.sendBroadcast(ApplicationManager.getInstance().getContext(),
                        BroadcastConstants.REPO_REQUEST_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            BroadcastSender.sendBroadcast(ApplicationManager.getInstance().getContext(),
                    BroadcastConstants.REPO_REQUEST_FAILED);
        }

        return null;
    }

    private String retreiveResponse(HttpURLConnection connection) throws IOException {
        BufferedReader br = null;
        try {
            InputStream is;
            if ((is = connection.getInputStream()) != null) {
                br = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }
        } finally {
            if(br != null) {
                try {
                    br.close();
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
        return null;
    }

    private void setConnectionParams(HttpsURLConnection connection) {
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Cache-Control", "max-age=0, private, must-revalidate");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setChunkedStreamingMode(-1); // prevents auto-buffering
        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);
        int connectionTimeOut = HttpConstants.HTTP_REQUEST_TIMEOUT;
        connection.setConnectTimeout(connectionTimeOut);
        connection.setReadTimeout(connectionTimeOut);
    }

    /**
     * Desc: Method to start over or init thread pool executor
     */
    private void checkAndResetExecutor(){
        if(executorService == null){
            executorService = Executors.newSingleThreadExecutor();
        } else{
            if(executorService.isShutdown()){
                executorService = Executors.newSingleThreadExecutor();
            }
        }
    }

    @Override
    public void request(HttpConstants.HttpRequestTypes type) {
        if(type == HttpConstants.HttpRequestTypes.RETRIEVE_REPOS){
            retrieveRepos();
        } else if(type == HttpConstants.HttpRequestTypes.DOWNLOAD_IMAGES){
            retrieveImages();
        }
    }
}
