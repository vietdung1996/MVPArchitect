package com.vietdung.mvparchitect.ui;

public interface SearchUserContract {
    interface View {
        void clearForcus();
    }

    interface Presenter {
        void searchUser(String query);
    }
}
