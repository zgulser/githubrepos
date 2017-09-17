package com.payconiq.zekigu.githubrepos;

import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.GithubRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.NullGithubRepo;
import com.payconiq.zekigu.githubrepos.core.service.repoparser.strategy.JSONParser;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Created by zekigu on 16.09.2017.
 */

public class DataModuleTest {

    private final String MOCK_GITHUB_REPO_ID = String.valueOf(UUID.randomUUID());
    private final String MOCK_GITHUB_REPO_NAME = "TEST_NAME";
    private final String MOCK_GITHUB_REPO_FULL_NAME = "TEST_FULL_NAME";
    private final String MOCK_GITHUB_REPO_DESC = "TEST_DESC";
    private final String MOCK_GITHUB_REPO_URL = "TEST_URL";
    private final boolean MOCK_GITHUB_REPO_PRIVATE = true;
    private final boolean MOCK_GITHUB_REPO_FORKED = true;

    GithubRepo githubRepo = null;
    NullGithubRepo nullGithubRepo = null;

    @Before
    public void setup(){
        githubRepo = new GithubRepo.RepoBuilder(
                MOCK_GITHUB_REPO_ID, MOCK_GITHUB_REPO_NAME,
                MOCK_GITHUB_REPO_FULL_NAME, MOCK_GITHUB_REPO_DESC,
                MOCK_GITHUB_REPO_PRIVATE, MOCK_GITHUB_REPO_FORKED, MOCK_GITHUB_REPO_URL).build();
    }

    @Test
    public void testInheritanceRelationship() throws Exception {
        assertTrue("@testInheritanceRelationship - Unable to create inherited object", githubRepo instanceof BaseRepo);
    }

    @Test
    public void testNullGithubRepoCreation() throws Exception {
        nullGithubRepo = new NullGithubRepo();
        assertTrue("@testNullRepoCreation - Unable to null github should have empty avatar url", nullGithubRepo.getOwnerAvatarUrl().isEmpty());
        assertTrue("@testNullRepoCreation - Unable to create null github repo obj", nullGithubRepo instanceof BaseRepo);
    }
}
