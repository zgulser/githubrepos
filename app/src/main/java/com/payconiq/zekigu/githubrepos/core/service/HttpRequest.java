package com.payconiq.zekigu.githubrepos.core.service;

import com.payconiq.zekigu.githubrepos.core.service.reporequest.RepoRequestContract;
import com.payconiq.zekigu.githubrepos.core.utils.HttpConstants;

/**
 * Created by zekigu on 15.09.2017
 */
public class HttpRequest implements Request {

    private RepoRequestContract repoRequestContract;

    public HttpRequest(){
    }

    public void addRepoRequesContract(RepoRequestContract pRepoRequestHandler){
        repoRequestContract = pRepoRequestHandler;
    }

    @Override
    public void request(HttpConstants.HttpRequestTypes pType) {
        if(pType == HttpConstants.HttpRequestTypes.RETRIEVE_REPOS){
            repoRequestContract.retrieveRepos();
        }
    }
}
