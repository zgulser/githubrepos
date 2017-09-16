package com.payconiq.zekigu.githubrepos.core.model.data;

import com.orm.SugarRecord;

/**
 * Created by zekigu on 15.09.2017
 */
public class GithubRepo extends BaseRepo{

    public RepoOwner owner;
    protected boolean isForked;

    public GithubRepo(){}

    GithubRepo(String repoId, String name, String fullName, String desc, boolean isPrivate,
               boolean isForked, String url){
        super(repoId, name, fullName, desc, url, isPrivate);
        this.isForked = isForked;
    }

    public void setOwner(RepoOwner owner) {
        this.owner = owner;
    }

    public boolean isForked() {
        return isForked;
    }

    @Override
    public boolean isOwnerAdmin() {
        if(owner != null) {
            return this.owner.isAdmin;
        }
        return false;
    }

    @Override
    public String getOwnerLoginName() {
        if(owner != null) {
            return owner.loginName;
        }
        return "";
    }

    @Override
    public String getOwnerAvatarUrl() {
        if(owner != null)
            return owner.avatarUrl;

        return "";
    }

    @Override
    public String getOwnerType() {
        if(owner != null)
            return owner.type;

        return "";
    }

    public String getOwnerUrl() {
        if(owner != null)
            return owner.userUrl;

        return "";
    }

    public String getFollowersUrl() {
        if(owner != null) {
            return owner.followersUrl;
        }

        return "";
    }

    public String getFollowingUrl() {
        if(owner != null) {
            return owner.followingUrl;
        }

        return "";
    }

    public String getStarredsUrl() {
        if(owner != null)
            return owner.starredsUrl;

        return "";
    }

    public String getSubscriptionsUrl() {
        if(owner != null)
            return owner.subscriptionsUrl;

        return "";
    }

    public String getReposUrl() {
        if(owner != null)
            return owner.reposUrl;

        return "";
    }

    static class RepoOwner extends SugarRecord<RepoOwner> {

        String loginName;
        String id;
        String avatarUrl;
        String userUrl;
        String followersUrl;
        String followingUrl;
        String starredsUrl;
        String subscriptionsUrl;
        String reposUrl;
        String type;
        boolean isAdmin;

        RepoOwner(){}

        RepoOwner(String loginName, String id, String avatarUrl, String userUrl, String followersUrl,
                  String followingUrl, String starredsUrl, String subscriptionsUrl, String reposUrl,
                  String type, boolean isAdmin) {
            this.loginName = loginName;
            this.id = id;
            this.avatarUrl = avatarUrl;
            this.userUrl = userUrl;
            this.followersUrl = followersUrl;
            this.followingUrl = followingUrl;
            this.starredsUrl = starredsUrl;
            this.subscriptionsUrl = subscriptionsUrl;
            this.reposUrl = reposUrl;
            this.type = type;
            this.isAdmin = isAdmin;
        }
    }
}
