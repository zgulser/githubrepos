package com.payconiq.zekigu.githubrepos.core.model.container;

import android.content.Context;

import com.payconiq.zekigu.githubrepos.core.app.ApplicationManager;
import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.GithubRepo;
import com.payconiq.zekigu.githubrepos.core.persistency.PersistencyManager;
import com.payconiq.zekigu.githubrepos.core.utils.BroadcastConstants;
import com.payconiq.zekigu.githubrepos.core.utils.BroadcastSender;

import java.util.ArrayList;

/**
 * Created by zekigu on 15.09.2017
 */
public class RepoContainer implements RepoNotifierContract {

    private ArrayList<BaseRepo> repositories;
    private ArrayList<BaseRepo> searchedRepositories;
    private boolean inSearchMode;
    private RepoReporterContract repoReporter;
    private PersistencyManager persistencyManager;

    public RepoContainer(Context context) {
        this.repositories = new ArrayList<>();
        this.persistencyManager = new PersistencyManager(context);
    }

    public void addRepoReporter(RepoReporterContract repoReporter) {
        this.repoReporter = repoReporter;
    }

    public synchronized void addRepo(BaseRepo repo) {
        repositories.add(repo);
        repoAdded();
    }

    public synchronized void addRepoAndPersist(BaseRepo repo) {
        addRepo(repo);
        persistencyManager.saveRepo(repo);
    }

    @Override
    public void repoAdded() {
        BroadcastSender.sendBroadcast(ApplicationManager.getInstance().getContext(),
                BroadcastConstants.REPO_ADDED);
    }

    @Override
    public void repoChanged(BaseRepo pBaseRepo) {
        BroadcastSender.sendBroadcast(ApplicationManager.getInstance().getContext(),
                BroadcastConstants.REPO_CHANGED);
    }

    @Override
    public void repoRemoved() {
        BroadcastSender.sendBroadcast(ApplicationManager.getInstance().getContext(),
                BroadcastConstants.REPO_REMOVED);
    }

    public void searchRepos(String searchQuery) {
        ArrayList<BaseRepo> bufferedRepositories = new ArrayList<>();
        ArrayList<BaseRepo> shallowRepositories = new ArrayList<>(repositories);
        for(BaseRepo repo : shallowRepositories) {
            if(repo.getName().toLowerCase()
                    .startsWith(searchQuery.toLowerCase())){
                bufferedRepositories.add(repo);
            }
        }

        clearSearch();
        searchedRepositories = bufferedRepositories;

        BroadcastSender.sendBroadcast(ApplicationManager.getInstance().getContext(),
                BroadcastConstants.REPO_SEARCH_DONE);
    }

    public ArrayList<BaseRepo> getRepos() {
        return repositories;
    }

    public ArrayList<BaseRepo> getSearchedRepos() {
        return searchedRepositories;
    }

    public boolean isInSearchMode() {
        return inSearchMode;
    }

    public RepoReporterContract getRepoReporter() { return repoReporter;}

    public PersistencyManager getPersistencyManager() {
        return persistencyManager;
    }

    public void setInSearchMode(boolean inSearchMode) {
        this.inSearchMode = inSearchMode;
    }

    public void clearSearch() {
        if(searchedRepositories != null){
            searchedRepositories.clear();
        }
        searchedRepositories = null;
    }
}
