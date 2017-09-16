package com.payconiq.zekigu.githubrepos.core.model.data;

/**
 * Created by zekigu on 15.09.2017
 */
public class RepoFactory {

    public static GithubRepo createGithubRepo(String id, String name, String fullName, String desc,
                                              boolean isPrivate, boolean fork, String url){
        return new GithubRepo(id, name, fullName, desc, isPrivate, fork, url);
    }

    public static GithubRepo.RepoOwner createGithubRepoOwner(String loginName, String id, String avatarUrl,
                                                             String userUrl, String followersUrl, String followingUrl,
                                                             String starredsUrl, String subscriptionsUrl, String reposUrl,
                                                             String type, boolean isAdmin ){
        return new GithubRepo.RepoOwner(loginName, id, avatarUrl, userUrl, followersUrl, followingUrl,
                starredsUrl, subscriptionsUrl,reposUrl, type, isAdmin);
    }
}
