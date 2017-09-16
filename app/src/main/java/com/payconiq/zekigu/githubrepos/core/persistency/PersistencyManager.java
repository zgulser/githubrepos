package com.payconiq.zekigu.githubrepos.core.persistency;

import android.content.Context;
import android.util.Log;

import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.GithubRepo;

import java.util.List;

/**
 * Created by Backbase R&D B.V on 16/09/2017.
 */

public class PersistencyManager {

    private Context context;

    public PersistencyManager(Context context){
        this.context = context;
    }

    public void saveRepo(BaseRepo repo){
        //GithubRepo grepo = (GithubRepo) repo;
        //Log.i("Test", "repo owner null: " + (grepo.owner == null));
        //grepo.save();
        loadRepositories();
    }

    public void loadRepositories(){
        List<GithubRepo> repositories = GithubRepo.listAll(GithubRepo.class);
        for (GithubRepo repo : repositories){
            Log.i("Test", "repo name: " + repo.getName());
            Log.i("Test", "repo id: " + repo.getRepoId());
            Log.i("Test", "repo owner null: " + (repo.owner == null));
            Log.i("Test", "repo owner followers: " + (repo.getFollowersUrl()));
        }
    }
}
