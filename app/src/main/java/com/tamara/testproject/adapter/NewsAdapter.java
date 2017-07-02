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
        RelativeLayout mRoot;
        TextView mTitle;
        TextView mPublishedAt;
        ImageView mImage;

        public Holder(View itemView) {
            super(itemView);
            mRoot = (RelativeLayout) itemView.findViewById(R.id.root);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mPublishedAt = (TextView) itemView.findViewById(R.id.publiched_at);
            mImage = (ImageView) itemView.findViewById(R.id.image);

            mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallback.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClick {

        void onItemClick(int possition);
    }
}
