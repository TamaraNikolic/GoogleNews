package com.tamara.testproject.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

import butterknife.BindString;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnItemClick {
    @BindString(R.string.error)
    String error;
    @BindString(R.string.close)
    String close;

    private ArticleListFragment articleListFragment;
    private ArticleDetailsFragment articleDetailsFragment;
    private ArrayList<ArticleItem> mNewsItem;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isXLargeDevice(this)) {
            setContentView(R.layout.activity_main_large);
        } else {
            setContentView(R.layout.activity_main);
        }

        ButterKnife.bind(this);

        articleListFragment = new ArticleListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, articleListFragment).commit();
        requestArticles();
    }

    @Override
    public void onItemClick(int position) {
        articleDetailsFragment = new ArticleDetailsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isXLargeDevice(this)) {
            fragmentTransaction.add(R.id.fragment_container2, articleDetailsFragment).commit();
        } else {
            fragmentTransaction.replace(R.id.fragment_container, articleDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        articleDetailsFragment.setmAtricleItem(mNewsItem.get(position));
    }

    private void requestArticles() {
        Articlenterface mApiService = RestClinet.getClient().create(Articlenterface.class);
        Observable<ArticleResponse> articleResponse = mApiService.getNewsItems(Constant.NEWS_TYPE, Constant.NEWS_KEY);

        articleResponse.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);
    }

    private void handleError(Throwable throwable) {
        Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_SHORT)
                .setAction(close, view -> finish())
                .setActionTextColor(Color.RED)
                .show();
    }

    private void handleResponse(ArticleResponse articleResponse) {
        mNewsItem = articleResponse.getArticles();
        articleListFragment.setmAtricleList(mNewsItem);
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
