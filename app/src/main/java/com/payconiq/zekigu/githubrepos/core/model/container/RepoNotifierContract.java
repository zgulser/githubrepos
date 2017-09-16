package com.payconiq.zekigu.githubrepos.core.model.container;

import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;

/**
 * Created by zekigu on 15.09.2017.
 */
public interface RepoNotifierContract {
    public void repoAdded();
    public void repoChanged(BaseRepo pBaseRepo);
    public void repoRemoved();
}
