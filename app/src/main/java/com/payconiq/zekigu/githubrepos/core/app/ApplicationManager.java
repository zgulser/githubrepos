package com.payconiq.zekigu.githubrepos.core.app;

import android.content.Context;

import com.payconiq.zekigu.githubrepos.core.imageloader.ImageLoaderImpl;
import com.payconiq.zekigu.githubrepos.core.model.container.RepoReporterImpl;
import com.payconiq.zekigu.githubrepos.core.persistency.PersistencyManager;
import com.payconiq.zekigu.githubrepos.core.service.HttpRequestService;
import com.payconiq.zekigu.githubrepos.core.service.HttpRequestContract;
import com.payconiq.zekigu.githubrepos.core.service.repoparser.strategy.JSONParser;
import com.payconiq.zekigu.githubrepos.core.service.reporequest.RepoRequestImpl;
import com.payconiq.zekigu.githubrepos.core.model.container.RepoContainer;
import com.payconiq.zekigu.githubrepos.core.imageloader.ImageLoaderContract;

/**
 * Created by zekigu on 15.09.2017.
 *
 * Desc: Composition root
 */
public class ApplicationManager {

    private static final Object APP_MANAGER_LOCK = new Object();
    private static ApplicationManager applicationManager;
    private Context appContext;
    private RepoContainer repoContainer;
    private ImageLoaderContract imageLoader;
    private HttpRequestContract requestService;
    private static boolean initialized;

    private ApplicationManager(){}

    public static ApplicationManager getInstance() {
        if(applicationManager == null) {
            synchronized (APP_MANAGER_LOCK) { // naive thread-safe creation
                applicationManager = new ApplicationManager();
            }
        }
        return  applicationManager;
    }

    public void initApp(Context context) {
        setContext(context);
        if(!initialized) {
            addRepoRequestService();
            addRepoContainer(context);
            addImageLoader(new ImageLoaderImpl());
            initialized = true;
        }
    }

    public void addImageLoader(ImageLoaderContract pImageLoader){
        imageLoader = pImageLoader;
    }

    private void addRepoRequestService(){
        RepoRequestImpl repoRequestService = new RepoRequestImpl();
        repoRequestService.addRepoResponseParser(new JSONParser());
        HttpRequestService httpReqHandler = new HttpRequestService();
        httpReqHandler.addRepoRequesContract(repoRequestService);
        addRequestHandler(httpReqHandler);
    }

    private void addRepoContainer(Context context) {
        repoContainer = new RepoContainer(context);
        repoContainer.addRepoReporter(new RepoReporterImpl());
    }

    public void addRequestHandler(HttpRequestContract requestService){ this.requestService = requestService;}

    public RepoContainer getRepoContainer() {return repoContainer;}

    public ImageLoaderContract getImageLoader() {return imageLoader;}

    public HttpRequestContract getRequestHandler() {return requestService;}

    public Context getContext() {
        return appContext;
    }

    public void setContext(Context context) {
        this.appContext = context;
    }

}
