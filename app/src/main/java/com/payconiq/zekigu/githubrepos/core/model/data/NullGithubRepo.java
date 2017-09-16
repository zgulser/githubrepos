package com.payconiq.zekigu.githubrepos.core.model.data;

/**
 * Created by Backbase R&D B.V on 16/09/2017.
 */

public class NullGithubRepo extends BaseRepo {

    public NullGithubRepo(){
        super.repoId = "";
        super.name ="";
        super.fullName = "";
        super.desc = "";
        super.url = "";
        super.isPrivate = true;
        super.isForked = false;
    }

    @Override
    public String getOwnerAvatarUrl() {
        return "";
    }

    @Override
    public String getOwnerLoginName() {
        return "";
    }

    @Override
    public String getOwnerType() {
        return "";
    }

    @Override
    public boolean isOwnerAdmin() {
        return false;
    }

    @Override
    public boolean isForked() {
        return false;
    }
}
