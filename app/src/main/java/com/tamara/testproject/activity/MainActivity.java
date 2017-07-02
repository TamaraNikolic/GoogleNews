package com.tamara.testproject.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tamara.testproject.R;
import com.tamara.testproject.adapter.NewsAdapter;
import com.tamara.testproject.data.Constant;
import com.tamara.testproject.data.ArticleItem;
import com.tamara.testproject.data.ArticleResponse;
import com.tamara.testproject.fragment.ArticleDetailsFragment;
import com.tamara.testproject.fragment.ArticleListFragment;
import com.tamara.testproject.networking.RestClinet;
import com.tamara.testproject.networking.Articlenterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnItemClick {

    public static final String REQUEST = "request";

    private ArticleListFragment articleListFragment;
    private ArticleDetailsFragment articleDetailsFragment;
    private ArrayList<ArticleItem> mNewsItem;
    private ArticleResponse articleResponse;
    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isXLargeDevice(this)) {
            setContentView(R.layout.activity_main_large);
        } else {
            setContentView(R.layout.activity_main);
        }

        mSharedPrefs = getSharedPreferences(Constant.SHARED_PREFS_KEY, MODE_PRIVATE);

        if (savedInstanceState != null) {
            articleResponse = savedInstanceState.getParcelable(REQUEST);
            mNewsItem = articleResponse.getArticles();
        } else {
            articleListFragment = new ArticleListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, articleListFragment).commit();
            requestArticles();
        }

    }

    @Override
    public void onItemClick(int position) {
        articleDetailsFragment = new ArticleDetailsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isXLargeDevice(this)) {
            fragmentTransaction.add(R.id.fragment_container2,articleDetailsFragment).commit();
        } else {
            fragmentTransaction.replace(R.id.fragment_container, articleDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        articleDetailsFragment.setmAtricleItem(mNewsItem.get(position));
    }

    private void requestArticles() {
        Articlenterface mApiService = RestClinet.getClient().create(Articlenterface.class);
        Call<ArticleResponse> mCall = mApiService.getNewsItems(Constant.NEWS_TYPE, Constant.NEWS_KEY);

        mCall.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                SharedPreferences.Editor editor = mSharedPrefs.edit();
                JsonObject post = (JsonObject) new Gson().toJsonTree(response.body());
                editor.putString(Constant.RESPONSE, post.toString());
                editor.apply();

                articleResponse = response.body();
                mNewsItem = articleResponse.getArticles();
                articleListFragment.setmAtricleList(mNewsItem);
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                String response = mSharedPrefs.getString(Constant.RESPONSE, "");
                Gson gson = new Gson();
                articleResponse = gson.fromJson(response, ArticleResponse.class);
                mNewsItem = articleResponse.getArticles();
                articleListFragment.setmAtricleList(mNewsItem);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(REQUEST, articleResponse);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private static boolean isXLargeDevice(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return false;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return false;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return false;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE: // Configuration.SCREENLAYOUT_SIZE_XLARGE is API >= 9
                return true;
            default:
                return false;
        }
    }
}
