package com.vietdung.mvparchitect.data.repository;

import com.vietdung.mvparchitect.data.source.UserDataSource;
import com.vietdung.mvparchitect.data.source.remote.UserRemoteDataSource;

public class SearchUserRepository implements UserDataSource.Remote {
    private UserDataSource.Remote mRemote;
    public static SearchUserRepository sInstance;

    public SearchUserRepository(UserDataSource.Remote remote) {
        mRemote = remote;
    }

    public static SearchUserRepository getInstance(UserRemoteDataSource searchUserRemoteDataSource) {
        if (sInstance == null) {
            sInstance = new SearchUserRepository(searchUserRemoteDataSource);
        }
        return sInstance;
    }

    @Override
    public void searchUser(String query, OnTaskListerner taskListerner) {
        mRemote.searchUser(query, taskListerner);
    }
}
