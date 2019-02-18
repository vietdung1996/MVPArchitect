package com.vietdung.mvparchitect.data.source;

import com.vietdung.mvparchitect.data.repository.OnTaskListerner;

public interface UserDataSource {
    interface Remote {
        void searchUser(String query, OnTaskListerner taskListerner);
    }
}
