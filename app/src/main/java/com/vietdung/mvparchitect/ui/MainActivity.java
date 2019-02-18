package com.vietdung.mvparchitect.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.vietdung.mvparchitect.R;
import com.vietdung.mvparchitect.data.model.User;
import com.vietdung.mvparchitect.data.repository.OnTaskListerner;
import com.vietdung.mvparchitect.data.repository.SearchUserRepository;
import com.vietdung.mvparchitect.data.source.remote.UserRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnTaskListerner, SearchUserContract.View {

    private RecyclerView mRecyclerView;
    private List<User> mUsers;
    private UserAdapter mUserAdapter;
    private SearchView mSearchView = null;
    private SearchUserContract.Presenter mPresenter;
    private String mQuery;
    private SearchUserRepository mUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            mSearchView = (SearchView) searchItem.getActionView();
        }
        if (mSearchView != null) {
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            mSearchView.setIconified(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
           mQuery = intent.getStringExtra(SearchManager.QUERY);
        }
        mPresenter.searchUser(mQuery);
    }

    @Override
    public void OnTaskCompletion(List<User> user) {
        mUsers = user;
        mUserAdapter.setUsers(mUsers);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view_main);
        mUsers = new ArrayList<>();
        mUserAdapter = new UserAdapter(MainActivity.this, mUsers);
        mRecyclerView.setAdapter(mUserAdapter);
        mRecyclerView.setHasFixedSize(true);
        mUserRepository = SearchUserRepository.getInstance(UserRemoteDataSource.getInstance());
        mPresenter = new SearchUserPresenter(this, this, mUserRepository);
    }

    @Override
    public void clearForcus() {
        if (mSearchView != null) {
            mSearchView.clearFocus();
        }
    }
}
