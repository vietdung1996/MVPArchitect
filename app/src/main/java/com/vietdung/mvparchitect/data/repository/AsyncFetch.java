package com.vietdung.mvparchitect.data.repository;

import android.os.AsyncTask;

import com.vietdung.mvparchitect.data.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AsyncFetch extends AsyncTask<String, String, List<User>> {
    public static final String SURL = "https://api.github.com/search/repositories?q=";
    public static final int SPARAMS = 0;
    public static final String SKEY_NAME = "name";
    public static final String SKEY_FULL_NAME = "full_name";
    public static final String SKEY_ITEMS = "items";
    private String mRequestMethod = "GET";
    private List<User> mUsers;
    private OnTaskListerner mTaskCompele;

    public AsyncFetch(OnTaskListerner taskCompele) {
        mTaskCompele = taskCompele;
    }

    @Override
    protected List<User> doInBackground(String... params) {
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(SURL + params[SPARAMS]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(mRequestMethod);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String responseString = readStream(urlConnection.getInputStream());
                mUsers = parseUserData(responseString);
            }
        } catch (Exception e) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(List<User> users) {
        super.onPostExecute(users);
        mTaskCompele.OnTaskCompletion(mUsers);
    }

    private List<User> parseUserData(String jString) {
        List<User> users = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jString);
            if (jsonObject != null) {
                JSONArray jsonArray = jsonObject.getJSONArray(SKEY_ITEMS);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectName = jsonArray.getJSONObject(i);
                    String name = jsonObjectName.getString(SKEY_NAME);
                    String full_name = jsonObjectName.getString(SKEY_FULL_NAME);
                    users.add(new User(name, full_name));
                }

            }
        } catch (Exception e) {

        }
        return users;
    }
}
