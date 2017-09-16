package com.payconiq.zekigu.githubrepos.core.model.data;

/**
 * Created by zekigu on 15.09.2017
 */
public class RepoFactory {

    public static GithubRepo createGithubRepo(String pID, String pName, String pFullname, String pDesc,
                                              boolean pPrivate, boolean pFork, String pUrl, String pHtmlUrl ){
        return new GithubRepo(pID, pName, pFullname, pDesc, pPrivate, pFork, pUrl, pHtmlUrl);
    }
}
