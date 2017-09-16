package com.payconiq.zekigu.githubrepos.core.service.repoparser.strategy;

import com.payconiq.zekigu.githubrepos.core.app.ApplicationManager;
import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.GithubRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.RepoFactory;
import com.payconiq.zekigu.githubrepos.core.service.repoparser.ParserConstants;
import com.payconiq.zekigu.githubrepos.core.utils.BroadcastConstants;
import com.payconiq.zekigu.githubrepos.core.utils.BroadcastSender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zekigu on 15.09.2017
 */

public class JSONParser implements ParseStrategy {

    private JSONArray repoArray = null;

    @Override
    public void parse(String content) {
        try{
            if(content != null) {
                repoArray = new JSONArray(content);
                parseRepoArray();
            } else {
                BroadcastSender.sendBroadcast(ApplicationManager.getInstance().getContext(),
                        BroadcastConstants.REPO_REQUEST_FAILED);
            }
        } catch (JSONException je){
            je.printStackTrace();
            BroadcastSender.sendBroadcast(ApplicationManager.getInstance().getContext(),
                    BroadcastConstants.REPO_REQUEST_FAILED);
        }
    }

    private void parseRepoArray() throws  JSONException{
        for(int i=0; i < repoArray.length(); i++) {
            extractRepo(repoArray.getJSONObject(i));
        }
    }

    private void extractRepo(final JSONObject pRepoJSONObject) {
        Observable<BaseRepo> fetchRepo =
                Observable.create(new Observable.OnSubscribe<BaseRepo>() {
                    @Override
                    public void call(Subscriber<? super BaseRepo> subscriber) {
                        try {
                            BaseRepo repo = createGithubRepo(pRepoJSONObject);
                            subscriber.onNext(repo); // Emit repo is ready
                            subscriber.onCompleted();
                        }catch(Exception e){
                            subscriber.onError(e);
                        }
                    }
                });

        fetchRepo
                .subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(new Action1<BaseRepo>() {
                    @Override
                    public void call(BaseRepo repo) {
                        if(repo != null) {
                            ApplicationManager.getInstance().getRepoContainer().addRepo(repo);
                        }
                    }
                });
    }

    private BaseRepo createGithubRepo(final JSONObject repo) {
        try{
            String id = repo.getString(ParserConstants.ID_FIELD);
            String name = repo.getString(ParserConstants.NAME_FIELD);
            String fullname = repo.getString(ParserConstants.FULLNAME_FIELD);
            String description = repo.getString(ParserConstants.DESCRIPTION_FIELD);
            boolean privateRepo = repo.getBoolean(ParserConstants.PRIVATE_FIELD);
            boolean fork = repo.getBoolean(ParserConstants.FORK_FIELD);
            String url = repo.getString(ParserConstants.URL_FIELD);

            GithubRepo githubRepo = RepoFactory.createGithubRepo(id, name, fullname, description,
                    privateRepo, fork, url);

            JSONObject owner = repo.getJSONObject(ParserConstants.OWNER_OBJECT_FIELD);
            addOwner(githubRepo, owner);

            return githubRepo;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private void addOwner(final GithubRepo repo, final JSONObject owner) {
        try {
            repo.setOwner(RepoFactory.createGithubRepoOwner(owner.getString(ParserConstants.OWNER_LOGIN_FIELD),
                    owner.getString(ParserConstants.OWNER_ID_FIELD), owner.getString(ParserConstants.OWNER_AVATARURL_FIELD),
                    owner.getString(ParserConstants.OWNER_USER_URL_FIELD), owner.getString(ParserConstants.OWNER_FOLLOWERSURL_FIELD),
                    owner.getString(ParserConstants.OWNER_FOLLOWINGSURL_FIELD), owner.getString(ParserConstants.OWNER_STARREDURL_FIELD),
                    owner.getString(ParserConstants.OWNER_SUBSURL_FIELD), owner.getString(ParserConstants.OWNER_REPOSURL_FIELD),
                    owner.getString(ParserConstants.OWNER_TYPE_FIELD), owner.getBoolean(ParserConstants.OWNER_ADMIN_FIELD)));
        } catch (JSONException je){
            je.printStackTrace();
        }
    }
}
