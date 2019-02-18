package com.vietdung.mvparchitect.ui;

import com.vietdung.mvparchitect.data.repository.OnTaskListerner;
import com.vietdung.mvparchitect.data.repository.SearchUserRepository;

public class SearchUserPresenter implements SearchUserContract.Presenter {
    private SearchUserContract.View mView;
    private OnTaskListerner mOnTaskListerner;
    private SearchUserRepository mUserRepository;

    public SearchUserPresenter(SearchUserContract.View view, OnTaskListerner onTaskListerner, SearchUserRepository userRepository) {
        mView = view;
        mOnTaskListerner = onTaskListerner;
        mUserRepository = userRepository;
    }

    @Override
    public void searchUser(String query) {
        mView.clearForcus();
        mUserRepository.searchUser(query,mOnTaskListerner);
    }
}
