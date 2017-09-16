package com.payconiq.zekigu.githubrepos.core.persistency;

import android.content.Context;
import android.util.Log;

import com.payconiq.zekigu.githubrepos.core.app.ApplicationManager;
import com.payconiq.zekigu.githubrepos.core.model.container.RepoContainer;
import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.GithubRepo;

import java.util.List;

/**
 * Created by Backbase R&D B.V on 16/09/2017
 */

public class PersistencyManager {

    private Context context;

    public PersistencyManager(Context context){
        this.context = context;
    }

    public void saveRepo(BaseRepo repo){
        GithubRepo grepo = (GithubRepo) repo;
        grepo.save();
        //loadRepositories();
    }

    public void loadRepositories(){
        List<GithubRepo> repositories = GithubRepo.listAll(GithubRepo.class);
        for (GithubRepo repo : repositories){
            ApplicationManager.getInstance().getRepoContainer().addRepo(repo);
        }
    }
}
