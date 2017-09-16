package com.payconiq.zekigu.githubrepos.core.model.data;

/**
 * Created by zekigu on 15.09.2017.
 */
public class GithubRepo extends BaseRepo{

    protected RepoOwner owner;
    protected String htmlUrl;
    protected boolean isForked;

    GithubRepo(String id, String name, String fullName, String desc, boolean isPrivate,
               boolean isForked, String url, String htmlUrl){
        super(id, name, fullName, desc, url, isPrivate);

        this.isForked = isForked;
        this.htmlUrl = htmlUrl;
        this.owner = new RepoOwner();
    }

    public RepoOwner getRepoOwner() { return owner;}

    public void setRepoOwner(RepoOwner owner) {
        this.owner = owner;
    }

    public boolean isForked() {
        return isForked;
    }

    public void setFork(boolean isForked) {
        this.isForked = isForked;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getOwnerObjectId() {
        if(owner != null)
            return owner.id;

        return "";
    }

    public void setOwnerObjectId(String id) {
        if(owner != null)
            owner.id = id;
    }

    @Override
    public String getLogin() {
        if(owner != null)
            return owner.login;

        return "";
    }

    public void setLogin(String login) {
        if(owner != null)
            owner.login = login;
    }

    @Override
    public String getOwnerAvatarUrl() {
        if(owner != null)
            return owner.avatarUrl;

        return "";
    }

    public void setOwnerAvatarUrl(String avatarUrl) {
        if(owner != null)
            owner.avatarUrl = avatarUrl;
    }

    @Override
    public boolean isOwnerAdmin() {
        if(owner != null)
            return owner.isAdmin;

        return false;
    }

    public void setOwnerAdmin(boolean isAdmin) {
        if(owner != null)
            owner.isAdmin = isAdmin;
    }

    public String getOwnerGravatarId() {
        if(owner != null)
            return owner.gravatarId;

        return "";
    }

    public void setOwnerGravatarId(String gravatarId) {
        if(owner != null)
            owner.gravatarId = gravatarId;
    }

    public String getOwnerUrl() {
        if(owner != null)
            return owner.url;

        return "";
    }

    public void setOwnerUrl(String url) {
        if(owner != null)
            owner.url = url;
    }

    public String getOwnerHtmlUrl() {
        if(owner != null)
            return owner.htmlUrl;

        return "";
    }

    public void setOwnerHtmlUrl(String htmlUrl) {
        if(owner != null)
            owner.htmlUrl = htmlUrl;
    }

    public String getFollowersUrl() {
        if(owner != null)
            return owner.followersUrl;

        return "";
    }

    public void setFollowersUrl(String followersUrl) {
        if(owner != null)
            owner.followersUrl = followersUrl;
    }

    public String getFollowingUrl() {
        if(owner != null)
            return owner.followingUrl;

        return "";
    }

    public void setFollowingUrl(String followingUrl) {
        if(owner != null)
            owner.followingUrl = followingUrl;
    }

    public String getStarredsUrl() {
        if(owner != null)
            return owner.starredsUrl;

        return "";
    }

    public void setStarredsUrl(String starredsUrl) {
        if(owner != null)
            owner.starredsUrl = starredsUrl;
    }

    public String getSubscriptionsUrl() {
        if(owner != null)
            return owner.subscriptionsUrl;

        return "";
    }

    public void setSubscriptionsUrl(String subscriptionsUrl) {
        if(owner != null)
            owner.subscriptionsUrl = subscriptionsUrl;
    }

    public String getReposUrl() {
        if(owner != null)
            return owner.reposUrl;

        return "";
    }

    public void setReposUrl(String reposUrl) {
        if(owner != null)
            owner.reposUrl = reposUrl;
    }

    @Override
    public String getType() {
        if(owner != null)
            return owner.type;

        return "";
    }

    public void setType(String type) {
        if(owner != null)
            owner.type = type;
    }

    private static class RepoOwner {

        String login;
        String id;
        String avatarUrl;
        String gravatarId;
        String url;
        String htmlUrl;
        String followersUrl;
        String followingUrl;
        String starredsUrl;
        String subscriptionsUrl;
        String reposUrl;
        String type;
        boolean isAdmin;

        RepoOwner(){}

        RepoOwner(String login, String id, String avatarUrl, String gravatarId, String url,
                  String htmlUrl, String followersUrl, String followingUrl, String starredsUrl,
                  String subscriptionsUrl, String reposUrl, String type, boolean isAdmin) {
            this.login = login;
            this.id = id;
            this.avatarUrl = avatarUrl;
            this.gravatarId = gravatarId;
            this.url = url;
            this.htmlUrl = htmlUrl;
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
