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
                    public void call(BaseRepo pBaseRepo) {
                        if(pBaseRepo != null) {
                            ApplicationManager.getInstance().getRepoContainer().addRepo(pBaseRepo);
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
            String htmlUrl = repo.getString(ParserConstants.HTML_URL_FIELD);
            GithubRepo githubRepo = RepoFactory.createGithubRepo(id, name, fullname, description,
                    privateRepo, fork, url, htmlUrl);

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
            repo.setLogin(owner.getString(ParserConstants.OWNER_LOGIN_FIELD));
            repo.setOwnerObjectId(owner.getString(ParserConstants.OWNER_ID_FIELD));
            repo.setType(owner.getString(ParserConstants.OWNER_TYPE_FIELD));
            repo.setOwnerAvatarUrl(owner.getString(ParserConstants.OWNER_AVATARURL_FIELD));
            repo.setOwnerGravatarId(owner.getString(ParserConstants.OWNER_GRAVATAR_ID_FIELD));
            repo.setOwnerUrl(owner.getString(ParserConstants.OWNER_URL_FIELD));
            repo.setOwnerHtmlUrl(owner.getString(ParserConstants.OWNER_HTMLURL_FIELD));
            repo.setFollowersUrl(owner.getString(ParserConstants.OWNER_FOLLOWERSURL_FIELD));
            repo.setFollowingUrl(owner.getString(ParserConstants.OWNER_FOLLOWINGSURL_FIELD));
            repo.setStarredsUrl(owner.getString(ParserConstants.OWNER_FOLLOWINGSURL_FIELD));
            repo.setSubscriptionsUrl(owner.getString(ParserConstants.OWNER_SUBSURL_FIELD));
            repo.setReposUrl(owner.getString(ParserConstants.OWNER_REPOSURL_FIELD));
        } catch (JSONException je){
            je.printStackTrace();
        }
    }
}
