package com.payconiq.zekigu.githubrepos.ui.views;

import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.payconiq.zekigu.githubrepos.R;
import com.payconiq.zekigu.githubrepos.core.app.ApplicationManager;
import com.payconiq.zekigu.githubrepos.core.utils.AppUtils;
import com.payconiq.zekigu.githubrepos.core.utils.HttpConstants;
import com.payconiq.zekigu.githubrepos.databinding.ActivityReposListBinding;
import com.payconiq.zekigu.githubrepos.ui.eventhandler.ErrorEventBroadcastListener;
import com.payconiq.zekigu.githubrepos.ui.eventhandler.RepoEventBroadcastListener;
import com.payconiq.zekigu.githubrepos.ui.views.adapters.RepoListAdapter;
import com.payconiq.zekigu.githubrepos.ui.specialcase.CustomQueryTextListener;
import com.payconiq.zekigu.githubrepos.ui.utils.UIConstants;

/**
 *  Created by zekigu on 15.09.2017
 */
public class RepoListActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    private RepoListAdapter recyclerViewAdapter;
    private ActivityReposListBinding listViewBinding;
    private RepoEventBroadcastListener repoEventBroadcastListener = new RepoEventBroadcastListener(RepoListActivity.this);
    private ErrorEventBroadcastListener errorEventBroadcastListener = new ErrorEventBroadcastListener(RepoListActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivity();
    }

    private void setupActivity() {
        listViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_repos_list);

        setupStatusBar();
        setupActionBar();
        setupSwipeRefreshLayout();
        setupRecyclerViewAndAdapter();
        setupScrollListener();
        tuneVisibilities();

        if(!AppUtils.isConnectedToInternet()) {
            promptMessageViaSnackBar(UIConstants.WarningType.NO_INTERNET_CONNECTION);
        }
    }

    private void setupStatusBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void setupActionBar(){
        Toolbar toolbar = listViewBinding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.activity_repos_title);
    }

    private void setupSwipeRefreshLayout(){
        listViewBinding.swipeToRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listViewBinding.appbarLayout.addOnOffsetChangedListener(this);
        initReceiver();
        retrieveRepos();
    }

    @Override
    protected void onPause() {
        super.onPause();
        listViewBinding.appbarLayout.removeOnOffsetChangedListener(this);
        unregisterReceiver();
    }

    private void initReceiver(){
        repoEventBroadcastListener.registerReceiver();
        errorEventBroadcastListener.registerReceiver();
    }

    private void unregisterReceiver(){
        repoEventBroadcastListener.unregisterReceiver();
        errorEventBroadcastListener.unregisterReceiver();
    }

    private void retrieveRepos() {
        if(AppUtils.isConnectedToInternet()) {
            ApplicationManager.getInstance().getRequestHandler().request(HttpConstants.HttpRequestTypes.RETRIEVE_REPOS);
        } else {
            ApplicationManager.getInstance().getRepoContainer().getPersistencyManager().loadRepositories();
        }
    }

    @Override
    public void tuneVisibilities() {
        listViewBinding.emptyViewLayout.setVisibility(recyclerViewAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
        listViewBinding.reposRecyclerView.setVisibility(recyclerViewAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
    }

    private void setupRecyclerViewAndAdapter() {
        listViewBinding.reposRecyclerView.setHasFixedSize(true);
        listViewBinding.reposRecyclerView.setItemAnimator(new DefaultItemAnimator());
        listViewBinding.reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RepoListAdapter(
                ApplicationManager.getInstance().getRepoContainer().getRepos(),
                RepoListActivity.this
        );
        listViewBinding.reposRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private void setupScrollListener(){
        listViewBinding.reposRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemIndex = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if(pagify(totalItemCount-1, lastVisibleItemIndex, newState)){
                    retrieveRepos();
                }
            }
        });
    }

    private boolean pagify(int totalItemCount, int lastVisibleIndex, int scrollingState){
        switch (scrollingState){
            case RecyclerView.SCROLL_STATE_DRAGGING:
                return (totalItemCount-2) == lastVisibleIndex;
            case RecyclerView.SCROLL_STATE_SETTLING:
                return false;
            case RecyclerView.SCROLL_STATE_IDLE:
                return totalItemCount == lastVisibleIndex;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_repos_list, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new CustomQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                ApplicationManager.getInstance().getRepoContainer().searchRepos(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_search){
            ApplicationManager.getInstance().getRepoContainer().setInSearchMode(true);
            listViewBinding.swipeToRefresh.setEnabled(false);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        listViewBinding.swipeToRefresh.setEnabled(verticalOffset == 0);
    }

    @Override
    protected void promptMessageViaSnackBar(UIConstants.WarningType warningType) {
        if(warningType == UIConstants.WarningType.REPO_REQUEST_FAILED){
            UIErrorHandler.showSnackBar(listViewBinding.swipeToRefresh, getResources().
                    getString(R.string.add_repo_warning_text), true, false );
        } else if(warningType == UIConstants.WarningType.NO_INTERNET_CONNECTION){
            UIErrorHandler.showSnackBar(listViewBinding.swipeToRefresh, getResources().
                    getString(R.string.no_connection_warning_text), true, false );
        }
    }

    @Override
    public void onRepoAdded() {
        listViewBinding.reposRecyclerView.getRecycledViewPool().clear();
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRepoRemoved() {
        listViewBinding.reposRecyclerView.getRecycledViewPool().clear();
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRepoSearchCompleted() {
        recyclerViewAdapter = new RepoListAdapter(ApplicationManager.
                getInstance().getRepoContainer().getSearchedRepos(), RepoListActivity.this);
        listViewBinding.reposRecyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onRepoRequestFailed() {
        promptMessageViaSnackBar(UIConstants.WarningType.REPO_REQUEST_FAILED);
    }

    @Override
    public void onNoInternetConnection() {
        promptMessageViaSnackBar(UIConstants.WarningType.NO_INTERNET_CONNECTION);
    }
}
