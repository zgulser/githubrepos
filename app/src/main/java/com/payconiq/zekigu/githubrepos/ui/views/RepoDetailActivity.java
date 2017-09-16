package com.payconiq.zekigu.githubrepos.ui.views;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.payconiq.zekigu.githubrepos.core.app.ApplicationManager;
import com.payconiq.zekigu.githubrepos.core.model.container.RepoContainer;
import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;
import com.payconiq.zekigu.githubrepos.databinding.ActivityRepoDetailBinding;
import com.payconiq.zekigu.githubrepos.ui.utils.UIConstants;
import com.payconiq.zekigu.githubrepos.R;

/**
 *  Created by zekigu on 15.09.2017
 */
public class RepoDetailActivity extends BaseActivity implements View.OnClickListener {

    private int indexOfRepo = -1;
    private ActivityRepoDetailBinding detailBindingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivity();
    }

    private void setupActivity() {
        detailBindingView = DataBindingUtil.setContentView(this, R.layout.activity_repo_detail);

        setupStatusBar();
        setupActionBar();
        setupListeners();
        init();
    }

    private void setupStatusBar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setupActionBar() {
        Toolbar toolbar = detailBindingView.toolbar;
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.activity_repo_title);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
    }

    private void setupListeners(){
        detailBindingView.baseRepoDetailView.ownerView.followersView.getRoot().setOnClickListener(this);
        detailBindingView.baseRepoDetailView.ownerView.followingView.getRoot().setOnClickListener(this);
        detailBindingView.baseRepoDetailView.ownerView.starredView.getRoot().setOnClickListener(this);
        detailBindingView.baseRepoDetailView.ownerView.subscriptionsView.getRoot().setOnClickListener(this);
        detailBindingView.baseRepoDetailView.ownerView.reposView.getRoot().setOnClickListener(this);
        detailBindingView.baseRepoDetailView.ownerView.eventsView.getRoot().setOnClickListener(this);
    }

    private void init() throws ArrayIndexOutOfBoundsException{
        indexOfRepo = getIntent().getIntExtra(UIConstants.REPO_INDEX_KEY, -1);
        setRepoData(getRepo());
    }

    private BaseRepo getRepo() {
        RepoContainer repoContainer = ApplicationManager.getInstance().getRepoContainer();
        if(!repoContainer.isInSearchMode()) {
            return repoContainer.getRepos().get(indexOfRepo);
        } else {
            return repoContainer.getSearchedRepos().get(indexOfRepo);
        }
    }

    private void setRepoData(BaseRepo repo) {
        getSupportActionBar().setTitle(ApplicationManager.getInstance().getRepoContainer().
                                       getRepoReporter().getRepoVisibleName(repo));

        setRepoOwnerAvatar(repo);
        setRepoInformativeData(repo);
        setRepoOwnereData(repo);
    }

    private void setRepoOwnerAvatar(BaseRepo repo){
        ApplicationManager.getInstance().getImageLoader().loadImageByUrl(
                Uri.parse(repo.getOwnerAvatarUrl()),
                detailBindingView.repoAvatarImageView, 200, 200, true);
    }

    private void setRepoInformativeData(BaseRepo repo) {
        detailBindingView.baseRepoDetailView.topView.fullnameTextView.setText(repo.getFullName());
        detailBindingView.baseRepoDetailView.topView.descTextView.setText(repo.getDesc());
        detailBindingView.baseRepoDetailView.topView.urlTextView.setText(repo.getUrl());
    }

    private void setRepoOwnereData(BaseRepo repo) {
        detailBindingView.baseRepoDetailView.ownerView.loginTextView.setText(repo.getLogin());
        detailBindingView.baseRepoDetailView.ownerView.typeTextView.setText(repo.getType());
        detailBindingView.baseRepoDetailView.ownerView.adminTextView.setText((repo.isOwnerAdmin() ?
                getResources().getString(R.string.yes) : getResources().getString(R.string.no) ));
        detailBindingView.baseRepoDetailView.ownerView.followersView.ownerTextView.setText(getString(R.string.activity_repo_detail_followers_title));
        detailBindingView.baseRepoDetailView.ownerView.followingView.ownerTextView.setText(getString(R.string.activity_repo_detail_following_title));
        detailBindingView.baseRepoDetailView.ownerView.starredView.ownerTextView.setText(getString(R.string.activity_repo_detail_starred_title));
        detailBindingView.baseRepoDetailView.ownerView.subscriptionsView.ownerTextView.setText(getString(R.string.activity_repo_detail_subs_title));
        detailBindingView.baseRepoDetailView.ownerView.reposView.ownerTextView.setText(getString(R.string.activity_repo_detail_repos_title));
        detailBindingView.baseRepoDetailView.ownerView.eventsView.ownerTextView.setText(getString(R.string.activity_repo_detail_events_title));
    }

    @Override
    protected void promptMessageViaSnackBar(UIConstants.WarningType warningType) {
        if(warningType == UIConstants.WarningType.NO_INTERNET_CONNECTION){
            UIErrorHandler.showSnackBar(detailBindingView.baseRepoDetailView.getRoot(), getResources().
                    getString(R.string.no_connection_warning_text), true, false );
        }
    }

    @Override
    public void tuneVisibilities() {
        // TODO
    }

    @Override
    public void onRepoAdded() {
        // TODO
    }

    @Override
    public void onRepoRemoved() {
        // TODO
    }

    @Override
    public void onRepoSearchCompleted() {
        // TODO
    }

    @Override
    public void onRepoRequestFailed() {
        // TODO
    }

    @Override
    public void onNoInternetConnection() {
        // TODO
    }

    @Override
    public void onClick(View v) {
        // TODO
    }
}
