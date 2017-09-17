package com.payconiq.zekigu.githubrepos.core.model.container;


import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;

/**
 * Created by zekigu on 15.09.2017
 */
public class RepoReporterImpl implements RepoReporterContract {

    @Override
    public String getRepoVisibleName(BaseRepo baseRepo) {
        return baseRepo.getName();
    }
}
