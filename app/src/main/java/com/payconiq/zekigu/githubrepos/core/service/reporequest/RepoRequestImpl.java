package com.payconiq.zekigu.githubrepos.core.service.reporequest;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.util.Log;

import com.payconiq.zekigu.githubrepos.core.app.ApplicationManager;
import com.payconiq.zekigu.githubrepos.core.service.HttpRequestContract;
import com.payconiq.zekigu.githubrepos.core.service.repoparser.strategy.ParseStrategy;
import com.payconiq.zekigu.githubrepos.core.utils.AppUtils;
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
public final class RepoRequestImpl implements RepoRequestContract {

    private int page = 1;
    private ParseStrategy parseStrategy;
    private ExecutorService executorService;
    private CountingIdlingResource countingIdlingResource;

    public RepoRequestImpl(){
        this.executorService = Executors.newSingleThreadExecutor();
        this.countingIdlingResource = new CountingIdlingResource("RepoRequestImplSrc");
        activateExecutor();
    }

    public void addRepoResponseParser(ParseStrategy parseStrategy){
        this.parseStrategy = parseStrategy;
    }

    @Override
    public void retrieveRepos() {
        countingIdlingResource.increment();
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return makeRepoHTTPRequest();
            }

            @Override
            protected void onPostExecute(String content) {
                super.onPostExecute(content);
                countingIdlingResource.decrement();
                parseStrategy.parse(content);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                countingIdlingResource.decrement();
            }
        }.executeOnExecutor(executorService);
    }

    private String makeRepoHTTPRequest() {
        URL finalUrl = null;
        try {
            String urlStr = String.format(HttpConstants.RETRIEVE_REPO_REQUEST_URL, page++, AppUtils.REPO_ITEM_PER_PAGE);
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
    @VisibleForTesting (otherwise = VisibleForTesting.PRIVATE)
    public void activateExecutor(){
        if(executorService == null){
            executorService = Executors.newSingleThreadExecutor();
        } else{
            if(executorService.isShutdown()){
                executorService = Executors.newSingleThreadExecutor();
            }
        }
    }

    @VisibleForTesting (otherwise = VisibleForTesting.PRIVATE)
    public ParseStrategy getParseStrategy() {
        return parseStrategy;
    }

    @VisibleForTesting (otherwise = VisibleForTesting.PRIVATE)
    public ExecutorService getExecutorService() {
        return executorService;
    }

    @VisibleForTesting (otherwise = VisibleForTesting.PRIVATE)
    public CountingIdlingResource getCountingIdlingResource() {
        return countingIdlingResource;
    }

}
