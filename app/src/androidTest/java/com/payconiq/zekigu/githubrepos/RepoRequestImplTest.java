package com.payconiq.zekigu.githubrepos;

import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.payconiq.zekigu.githubrepos.core.app.ApplicationManager;
import com.payconiq.zekigu.githubrepos.core.service.HttpRequestContract;
import com.payconiq.zekigu.githubrepos.core.service.repoparser.strategy.ParseStrategy;
import com.payconiq.zekigu.githubrepos.core.service.reporequest.RepoRequestImpl;
import com.payconiq.zekigu.githubrepos.ui.views.splash.SplashActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Backbase R&D B.V on 17/09/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RepoRequestImplTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityRule =
            new ActivityTestRule<>(SplashActivity.class);

    HttpRequestContract httpRequestContract;
    CountingIdlingResource countingIdlingResource;

    @Before
    public void setup(){
        ApplicationManager.getInstance().initApp(mActivityRule.getActivity().getApplicationContext());
        httpRequestContract = ApplicationManager.getInstance().getRequestHandler();
    }

    public void setupForIdlingResource(){
        countingIdlingResource = ((RepoRequestImpl)httpRequestContract.getRepoRequestContract()).getCountingIdlingResource();
        Espresso.registerIdlingResources(countingIdlingResource);
    }

    public void tearDown(){
        Espresso.unregisterIdlingResources(countingIdlingResource);
    }

    @Test
    public void testAddRepoResponseParser(){
        ((RepoRequestImpl)httpRequestContract.getRepoRequestContract()).addRepoResponseParser(new ParseStrategy() {
            @Override
            public void parse(String content) {

            }
        });
        assertTrue("@testAddRepoResponseParser - Unable to add a parser strategy", ((RepoRequestImpl)httpRequestContract.getRepoRequestContract()).getParseStrategy() != null);
    }

    @Test
    public void testActivateExecutor(){
        ((RepoRequestImpl)httpRequestContract.getRepoRequestContract()).activateExecutor();
        assertTrue("@testActivateExecutor - Unable to activate thread pool executor", ((RepoRequestImpl)httpRequestContract.getRepoRequestContract()).getExecutorService() != null);
    }

    @Test
    public void testMakeRepoHttpRequest(){
        setupForIdlingResource();
        ((RepoRequestImpl)httpRequestContract.getRepoRequestContract()).retrieveRepos();
        while(!countingIdlingResource.isIdleNow());
        assertTrue("@testMakeRepoHttpRequest - Unable to make a repo http request", ApplicationManager.getInstance().getRepoContainer() != null);
        tearDown();
    }
}
