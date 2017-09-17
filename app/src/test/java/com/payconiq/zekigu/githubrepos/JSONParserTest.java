package com.payconiq.zekigu.githubrepos;

import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;
import com.payconiq.zekigu.githubrepos.core.service.repoparser.strategy.JSONParser;
import com.payconiq.zekigu.githubrepos.core.service.repoparser.strategy.ParseStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class JSONParserTest {

    JSONParser parseStrategy;

    @Before
    public void setup(){
        parseStrategy = new JSONParser();
    }

    @Test
    public void testParserCreation() throws Exception {
        assertTrue("@testParserCreation - Unable to create parser", parseStrategy != null);
    }

    @Test
    public void testParsing() throws Exception {
        try {
            parseStrategy.parse(SampleJSONRepoData.sampleReposJSONArray);
        } catch (Exception e){
            e.printStackTrace();
        }
        assertTrue("@testParsing - Unable to parse given string", parseStrategy.getRepoArray().length() != 0);
    }

    @Test
    public void testCreateGithubRepo() throws Exception {
        BaseRepo repo = null;
        try {
            String input = SampleJSONRepoData.sampleRepoJSONObject.replace("\t", "");
            input = input.replace("\n", "");
            input = input.replace(" ", "");
            JSONObject repoJSONObj = new JSONObject(input);
            repo = parseStrategy.createGithubRepo(repoJSONObj);
        } catch (Exception e){
            e.printStackTrace();
        }
        assertTrue("@testCreateGithubRepo - Unable to create a github repo obj", repo != null);
    }
}