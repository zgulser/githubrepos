package com.payconiq.zekigu.githubrepos.ui.views;

import android.support.v7.app.AppCompatActivity;

import com.payconiq.zekigu.githubrepos.ui.errorhandler.UIErrorHandlerImpl;
import com.payconiq.zekigu.githubrepos.ui.utils.UIConstants;

/**
 * Created by zekigu on 15.09.2017.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected com.payconiq.zekigu.githubrepos.ui.errorhandler.UIErrorHandler UIErrorHandler = new UIErrorHandlerImpl();
    protected abstract void promptMessageViaSnackBar(UIConstants.WarningType pWarnintType);
    public abstract void tuneVisibilities();
    public abstract void onRepoAdded();
    public abstract void onRepoRemoved();
    public abstract void onRepoSearchCompleted();
    public abstract void onRepoRequestFailed();
    public abstract void onNoInternetConnection();
}
