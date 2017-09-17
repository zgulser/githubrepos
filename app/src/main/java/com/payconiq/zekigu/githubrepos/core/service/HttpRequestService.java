package com.payconiq.zekigu.githubrepos.core.service;

import android.support.annotation.VisibleForTesting;

import com.payconiq.zekigu.githubrepos.core.service.reporequest.RepoRequestContract;
import com.payconiq.zekigu.githubrepos.core.utils.HttpConstants;

/**
 * Created by zekigu on 15.09.2017
 */
public class HttpRequestService implements HttpRequestContract {

    private RepoRequestContract repoRequestService;

    public HttpRequestService(){}

    public void addRepoRequesContract(RepoRequestContract repoRequestService){
        this.repoRequestService = repoRequestService;
    }

    @Override
    public void request(HttpConstants.HttpRequestTypes type) {
        if(type == HttpConstants.HttpRequestTypes.RETRIEVE_REPOS){
            repoRequestService.retrieveRepos();
        }
    }

    @Override
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public RepoRequestContract getRepoRequestContract() {
        return repoRequestService;
    }
}
