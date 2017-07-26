package com.schibsted.chatme.data.cache;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.schibsted.chatme.data.MessageWrapperEntity;
import com.schibsted.chatme.data.exceptions.InvalidLoginException;
import com.schibsted.chatme.presentation.app.ChatmeApplication;
import com.schibsted.chatme.presentation.app.Constants;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by diego.galico
 */

public class DiskCache {

    public static final String KEY_DATA = "JSON_DATA";
    public static final String KEY_FIRST_TIME = "FIRST_TIME";

    private static final List<String> mList = Arrays.asList("Carrie", "Anthony", "Eleanor", "Rodney", "Oliva", "Merve", "Lily");
    private static final String INVALID_USERNAME = "Invalid username";
    private static final String EMPTY_USERNAME = "Username is empty";

    private ChatmeApplication mApplication;

    private SharedPreferences mSharedPreferences;

    @Inject
    public DiskCache(ChatmeApplication application, SharedPreferences sharedPreferences) {
        mApplication = application;
        mSharedPreferences = sharedPreferences;
    }

    public Observable<MessageWrapperEntity> getMessageWrapper() {
        return Observable.defer(() -> {
            Observable<MessageWrapperEntity> result;
            String data = mSharedPreferences.getString(KEY_DATA, null);
            Gson gson = new Gson();
            MessageWrapperEntity jsonObject = gson.fromJson(data, MessageWrapperEntity.class);
            if (data != null) {
                result = Observable.just(jsonObject);
            } else {
                result = Observable.just(null);
            }
            return result;
        });
    }

    public Observable<Boolean> saveMessageWrapper(MessageWrapperEntity value) {
        return Observable.defer(() -> {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(value);
            editor.putString(KEY_DATA, json);
            return Observable.just(editor.commit());
        });
    }

    public String getLoggedUser() {
        return mSharedPreferences.getString(KEY_FIRST_TIME, Constants.EMTPY_STRING);
    }

    public boolean isLoggedIn() {
        String previouslyStarted = mSharedPreferences.getString(KEY_FIRST_TIME, null);
        return previouslyStarted != null;
    }

    public void removeLoggedUser() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(KEY_FIRST_TIME);
        editor.apply();
    }

    public void saveLoggedUser(String username) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(KEY_FIRST_TIME, username);
        edit.commit();
    }

    public Observable<Boolean> isUsernameValid(String username) {
        return Observable.defer(() -> {
            Observable<Boolean> result;
            if (mList.contains(username)) {
                result = Observable.just(true).map(input -> {
                    throw new InvalidLoginException(INVALID_USERNAME);
                });
            } else if (username.isEmpty()) {
                result = Observable.just(true).map(input -> {
                    throw new InvalidLoginException(EMPTY_USERNAME);
                });
            } else {
                result = Observable.just(true);
                saveLoggedUser(username);
            }
            return result;
        });
    }
}