package com.tamara.testproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tamara.testproject.R;
import com.tamara.testproject.data.ArticleItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tamara on 5/2/17.
 */

public class ArticleDetailsFragment extends Fragment {
    private String TAG_FOR_PARCEL = ArticleDetailsFragment.class.getName();

    @BindView(R.id.article_title)
    TextView mTitle;
    @BindView(R.id.article_description)
    TextView mDescription;
    @BindView(R.id.article_author)
    TextView mAuthor;
    @BindView(R.id.article_url)
    TextView mUrl;
    @BindView(R.id.article_date)
    TextView mDate;
    @BindView(R.id.article_image)
    ImageView mImage;

    private ArticleItem mAtricleItem;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_layout, container, false);
        try {
            ButterKnife.bind(this, view);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initComponents();

        return view;
    }

    private void initComponents() {
        mTitle.setText(mAtricleItem.getTitle());
        mDescription.setText(mAtricleItem.getDescription());
        mAuthor.setText(mAtricleItem.getAuthor());
        mUrl.setText(mAtricleItem.getUrl());
        mDate.setText(mAtricleItem.getPublishedAt());

        Glide.with(getActivity())
                .load(mAtricleItem.getUrlToImage())
                .centerCrop()
                .into(mImage);
    }

    public void setmAtricleItem(ArticleItem atricleItem) {
        this.mAtricleItem = atricleItem;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(TAG_FOR_PARCEL, mAtricleItem);
    }
}
