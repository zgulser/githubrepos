package com.payconiq.zekigu.githubrepos.core.persistency;

import android.content.Context;
import android.util.Log;

import com.payconiq.zekigu.githubrepos.core.app.ApplicationManager;
import com.payconiq.zekigu.githubrepos.core.model.container.RepoContainer;
import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.GithubRepo;
import com.payconiq.zekigu.githubrepos.core.utils.AppUtils;

import java.util.List;

/**
 * Created by Backbase R&D B.V on 16/09/2017
 */

public class PersistencyManager {

    private int fetchedRepoCount = 0;
    private int fetchedRepoCoef = 1;

    public PersistencyManager(){}

    public void saveRepo(BaseRepo repo){
        GithubRepo grepo = (GithubRepo) repo;
        grepo.save();
    }

    public void loadRepositories(){
        List<GithubRepo> repositories = GithubRepo.listAll(GithubRepo.class);
        if(fetchedRepoCount < repositories.size()) {
            for (int i=fetchedRepoCount; i < AppUtils.REPO_ITEM_PER_PAGE*fetchedRepoCoef; i++) {
                BaseRepo repo = repositories.get(i);
                ApplicationManager.getInstance().getRepoContainer().addRepo(repo);
                fetchedRepoCount += 1;
            }

            fetchedRepoCoef += 1;
        }
    }
}
