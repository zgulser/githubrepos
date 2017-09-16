package com.payconiq.zekigu.githubrepos.core.app;

import android.content.Context;

import com.payconiq.zekigu.githubrepos.core.imageloader.ImageLoaderImpl;
import com.payconiq.zekigu.githubrepos.core.service.HttpRequest;
import com.payconiq.zekigu.githubrepos.core.service.Request;
import com.payconiq.zekigu.githubrepos.core.service.repoparser.strategy.JSONParser;
import com.payconiq.zekigu.githubrepos.core.service.reporequest.RepoRequestImpl;
import com.payconiq.zekigu.githubrepos.core.model.container.RepoContainer;
import com.payconiq.zekigu.githubrepos.core.model.container.RepoReporterImpl;
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
    private Request requestHandler;
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

    public void initApp(Context pContext) {
        setContext(pContext);

        if(!initialized) {
            addImageLoader(new ImageLoaderImpl());
            RepoRequestImpl repoRequestService = new RepoRequestImpl();
            repoRequestService.addRepoResponseParser(new JSONParser());
            HttpRequest httpReqHandler = new HttpRequest();
            httpReqHandler.addRepoRequesContract(repoRequestService);
            addRequestHandler(httpReqHandler);
            repoContainer = new RepoContainer();
            repoContainer.addRepoReporter(new RepoReporterImpl());

            initialized = true;
        }
    }

    public void addImageLoader(ImageLoaderContract pImageLoader){
        imageLoader = pImageLoader;
    }

    public void addRequestHandler(Request pRequestHandler){ requestHandler = pRequestHandler;}

    public RepoContainer getRepoContainer() {return repoContainer;}

    public ImageLoaderContract getImageLoader() {return imageLoader;}

    public Request getRequestHandler() {return requestHandler;}

    public Context getContext() {
        return appContext;
    }

    public void setContext(Context pContext) {
        this.appContext = pContext;
    }

}
