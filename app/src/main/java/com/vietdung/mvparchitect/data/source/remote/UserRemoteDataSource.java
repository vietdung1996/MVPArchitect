package com.vietdung.mvparchitect.data.source.remote;

import com.vietdung.mvparchitect.data.repository.AsyncFetch;
import com.vietdung.mvparchitect.data.repository.OnTaskListerner;
import com.vietdung.mvparchitect.data.source.UserDataSource;

public class UserRemoteDataSource implements UserDataSource.Remote {
    public static UserRemoteDataSource sInstance;

    private UserRemoteDataSource() {

    }

    public static UserRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new UserRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void searchUser(String query, OnTaskListerner taskListerner) {
        new AsyncFetch(taskListerner).execute(query);
    }
}
