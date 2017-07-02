package com.tamara.testproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tamara.testproject.R;
import com.tamara.testproject.data.ArticleItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tamara on 5/1/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder> {

    private ArrayList<ArticleItem> mList;
    private Context mContext;
    private OnItemClick onItemClickCallback;

    public NewsAdapter(ArrayList<ArticleItem> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        this.onItemClickCallback = ((OnItemClick) mContext);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        ArticleItem articleItem = mList.get(position);

        holder.mTitle.setText(articleItem.getTitle());
        holder.mPublishedAt.setText(articleItem.getPublishedAt());
        Glide.with(mContext)
                .load(articleItem.getUrlToImage())
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.root)
        RelativeLayout mRoot;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.publiched_at)
        TextView mPublishedAt;
        @BindView(R.id.image)
        ImageView mImage;

        @OnClick(R.id.root)
        public void onDetailScreen() {
            onItemClickCallback.onItemClick(getAdapterPosition());
        }

        public Holder(View itemView) {
            super(itemView);
            try {
                ButterKnife.bind(this, itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnItemClick {

        void onItemClick(int possition);
    }
}
