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

/**
 * Created by tamara on 5/2/17.
 */

public class ArticleDetailsFragment extends Fragment {
    private TextView mTitle;
    private TextView mDescription;
    private TextView mAuthor;
    private TextView mUrl;
    private TextView mDate;
    private ImageView mImage;
    private ArticleItem mAtricleItem;
    private String TAG_FOR_PARCEL = ArticleDetailsFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_layout, container, false);
        if (savedInstanceState != null) {
            mAtricleItem = savedInstanceState.getParcelable(TAG_FOR_PARCEL);
        }
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        mTitle = (TextView) view.findViewById(R.id.article_title);
        mDescription = (TextView) view.findViewById(R.id.article_description);
        mAuthor = (TextView) view.findViewById(R.id.article_author);
        mUrl = (TextView) view.findViewById(R.id.article_url);
        mDate = (TextView) view.findViewById(R.id.article_date);
        mImage = (ImageView) view.findViewById(R.id.article_image);

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
