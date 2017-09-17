package com.payconiq.zekigu.githubrepos;

import android.content.Context;

import com.payconiq.zekigu.githubrepos.core.model.container.RepoContainer;
import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.GithubRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.NullGithubRepo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by zekigu on 16.09.2017
 */

@RunWith(MockitoJUnitRunner.class)
public class ContainerModuleTest {

    @Mock
    Context mockContext;
    RepoContainer repoContainer;

    @Before
    public void setup(){
        repoContainer = new RepoContainer();
    }

    @Test
    public void testPersistencyManager() {
        assertTrue("@testPersistencyManager - Unable to create persistency manager", repoContainer.getPersistencyManager() != null);
    }

    @Test
    public void testAddRepo() {
        int prevSize = repoContainer.getRepos().size();
        try {
            repoContainer.addRepo(new NullGithubRepo());
        } catch (NullPointerException npe){ // thrown for the lack of localbroadcastmanager
            npe.printStackTrace();
        }
        int curSize = repoContainer.getRepos().size();
        assertTrue("@testAddRepo - Unable to add a repo", curSize > prevSize);
    }
}
