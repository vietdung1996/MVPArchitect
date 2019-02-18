package com.vietdung.mvparchitect.data.repository;

import com.vietdung.mvparchitect.data.model.User;

import java.util.List;

public interface OnTaskListerner {
    void OnTaskCompletion(List<User> user);
}
