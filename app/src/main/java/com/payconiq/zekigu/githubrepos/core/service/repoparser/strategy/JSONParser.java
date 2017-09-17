package com.payconiq.zekigu.githubrepos.core.service.repoparser.strategy;

import android.support.annotation.VisibleForTesting;

import com.payconiq.zekigu.githubrepos.core.app.ApplicationManager;
import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.GithubRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.NullGithubRepo;
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

    private void extractRepo(final JSONObject repoJSON) {
        Observable<BaseRepo> fetchRepo =
                Observable.create(new Observable.OnSubscribe<BaseRepo>() {
                    @Override
                    public void call(Subscriber<? super BaseRepo> subscriber) {
                        try {
                            BaseRepo repo = createGithubRepo(repoJSON);
                            subscriber.onNext(repo); // Emit repo is ready
                            subscriber.onCompleted();
                        }catch(Exception e){
                            subscriber.onError(e);
                        }
                    }
                });

        fetchRepo
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseRepo>() {
                    @Override
                    public void call(BaseRepo repo) {
                        ApplicationManager.getInstance().getRepoContainer().addRepoAndPersist(repo);
                    }
                });
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public BaseRepo createGithubRepo(final JSONObject repoJSON){
        try{
            String id = repoJSON.getString(ParserConstants.ID_FIELD);
            String name = repoJSON.getString(ParserConstants.NAME_FIELD);
            String fullname = repoJSON.getString(ParserConstants.FULLNAME_FIELD);
            String description = repoJSON.getString(ParserConstants.DESCRIPTION_FIELD);
            boolean privateRepo = repoJSON.getBoolean(ParserConstants.PRIVATE_FIELD);
            boolean fork = repoJSON.getBoolean(ParserConstants.FORK_FIELD);
            String url = repoJSON.getString(ParserConstants.URL_FIELD);

            GithubRepo.RepoBuilder builder = new GithubRepo.RepoBuilder(id, name, fullname, description,
                    privateRepo, fork, url);

            JSONObject owner = repoJSON.getJSONObject(ParserConstants.OWNER_OBJECT_FIELD);
            return injectRepoOwner(builder, owner);
        }catch(JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    private BaseRepo injectRepoOwner(final GithubRepo.RepoBuilder builder, final JSONObject owner) throws JSONException {
        return builder.addOwnerLoginName(owner.getString(ParserConstants.OWNER_LOGIN_FIELD))
                .addOwnerId(owner.getString(ParserConstants.OWNER_ID_FIELD))
                .addOwnerAvatarUrl(owner.getString(ParserConstants.OWNER_AVATARURL_FIELD))
                .addOwnerUserUrl(owner.getString(ParserConstants.OWNER_USER_URL_FIELD))
                .addOwnerFollowersUrl(owner.getString(ParserConstants.OWNER_FOLLOWERSURL_FIELD))
                .addOwnerFollowingUrl(owner.getString(ParserConstants.OWNER_FOLLOWINGSURL_FIELD))
                .addOwnerStarredsUrl(owner.getString(ParserConstants.OWNER_STARREDURL_FIELD))
                .addOwnerSubscriptionsUrl(owner.getString(ParserConstants.OWNER_SUBSURL_FIELD))
                .addUserType(owner.getString(ParserConstants.OWNER_TYPE_FIELD))
                .addUserAdmin(owner.getBoolean(ParserConstants.OWNER_ADMIN_FIELD)).build();
    }

    public JSONArray getRepoArray() {
        return repoArray;
    }

}
