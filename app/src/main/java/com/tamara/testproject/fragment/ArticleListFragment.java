package com.tamara.testproject.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tamara.testproject.R;
import com.tamara.testproject.adapter.NewsAdapter;
import com.tamara.testproject.data.ArticleItem;
import com.tamara.testproject.data.ArticleResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by tamara on 5/1/17.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)

public class ArticleListFragment extends Fragment {
    @BindView(R.id.recyclerVIew)
    RecyclerView mRecyclerView;

    private ArrayList<ArticleItem> mAtricleList = new ArrayList<>();
    private NewsAdapter mAdapter;
    private String TAG_ARTICLE_LIST = ArticleListFragment.class.getName();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_layout, container, false);
        try {
            ButterKnife.bind(this, view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAdapter = new NewsAdapter(mAtricleList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public void setmAtricleList(ArrayList<ArticleItem> atricleList) {
        if (!this.mAtricleList.isEmpty()) {
            this.mAtricleList.clear();
        }

        this.mAtricleList.addAll(atricleList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TAG_ARTICLE_LIST, mAtricleList);
    }

}
