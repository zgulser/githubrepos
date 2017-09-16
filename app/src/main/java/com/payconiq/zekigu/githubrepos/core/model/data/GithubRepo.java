package com.payconiq.zekigu.githubrepos.core.model.data;

import com.orm.SugarRecord;

/**
 * Created by zekigu on 15.09.2017
 */
public class GithubRepo extends BaseRepo{

    private String ownerLoginName;
    private String ownerId;
    private String ownerAvatarUrl;
    private String ownerUserUrl;
    private String ownerFollowersUrl;
    private String ownerFollowingUrl;
    private String ownerStarredsUrl;
    private String ownerSubscriptionsUrl;
    private String ownerType;
    private boolean isOwnerAdmin;

    public GithubRepo(){}

    GithubRepo(RepoBuilder builder){
        super(builder.repoId, builder.name, builder.fullName, builder.desc, builder.url, builder.isPrivate, builder.isForked);
        this.ownerLoginName = builder.ownerLoginName;
        this.ownerId = builder.ownerId;
        this.ownerAvatarUrl = builder.ownerAvatarUrl;
        this.ownerUserUrl = builder.ownerUserUrl;
        this.ownerFollowersUrl = builder.ownerFollowersUrl;
        this.ownerFollowingUrl = builder.ownerFollowingUrl;
        this.ownerStarredsUrl = builder.ownerStarredsUrl;
        this.ownerSubscriptionsUrl = builder.ownerSubscriptionsUrl;
        this.ownerType = builder.ownerType;
        this.isOwnerAdmin = builder.isOwnerAdmin;
    }

    @Override
    public boolean isForked() {
        return isForked;
    }

    @Override
    public String getOwnerLoginName() {
        return ownerLoginName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    @Override
    public String getOwnerAvatarUrl() {
        return ownerAvatarUrl;
    }

    @Override
    public String getOwnerType() {
        return ownerType;
    }

    @Override
    public boolean isOwnerAdmin() {
        return isOwnerAdmin;
    }

    public String getOwnerUrl() {
        return ownerUserUrl;
    }

    public String getOwnerFollowersUrl() {
        return ownerFollowersUrl;
    }

    public String getOwnerFollowingUrl() {
        return ownerFollowingUrl;
    }

    public String getStarredsUrl() {
        return ownerStarredsUrl;
    }

    public String getSubscriptionsUrl() {
        return ownerSubscriptionsUrl;
    }

    public static class RepoBuilder {

        protected String repoId;
        protected String name;
        protected String fullName;
        protected String desc;
        protected String url;
        protected boolean isPrivate;
        private boolean isForked;
        private String ownerLoginName;
        private String ownerId;
        private String ownerAvatarUrl;
        private String ownerUserUrl;
        private String ownerFollowersUrl;
        private String ownerFollowingUrl;
        private String ownerStarredsUrl;
        private String ownerSubscriptionsUrl;
        private String ownerType;
        private boolean isOwnerAdmin;

        public RepoBuilder(String repoId, String name, String fullName, String desc, boolean isPrivate,
                    boolean isForked, String url) {
            this.repoId = repoId;
            this.name = name;
            this.fullName = fullName;
            this.desc = desc;
            this.isPrivate = isPrivate;
            this.url = url;
            this.isForked = isForked;
        }

        public GithubRepo build(){
            return new GithubRepo(this);
        }

        public RepoBuilder addOwnerLoginName(String ownerLoginName) {
            this.ownerLoginName = ownerLoginName;
            return this;
        }

        public RepoBuilder addOwnerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public RepoBuilder addOwnerAvatarUrl(String ownerAvatarUrl) {
            this.ownerAvatarUrl = ownerAvatarUrl;
            return this;
        }

        public RepoBuilder addOwnerUserUrl(String ownerUserUrl) {
            this.ownerUserUrl = ownerUserUrl;
            return this;
        }

        public RepoBuilder addOwnerFollowersUrl(String ownerFollowersUrl) {
            this.ownerFollowersUrl = ownerFollowersUrl;
            return this;
        }

        public RepoBuilder addOwnerFollowingUrl(String ownerFollowingUrl) {
            this.ownerFollowingUrl = ownerFollowingUrl;
            return this;
        }

        public RepoBuilder addOwnerStarredsUrl(String ownerStarredsUrl) {
            this.ownerStarredsUrl = ownerStarredsUrl;
            return this;
        }

        public RepoBuilder addOwnerSubscriptionsUrl(String ownerSubscriptionsUrl) {
            this.ownerSubscriptionsUrl = ownerSubscriptionsUrl;
            return this;
        }

        public RepoBuilder addUserType(String ownerType) {
            this.ownerType = ownerType;
            return this;
        }

        public RepoBuilder addUserAdmin(boolean isOwnerAdmin) {
            this.isOwnerAdmin = isOwnerAdmin;
            return this;
        }
    }
}