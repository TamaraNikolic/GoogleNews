package com.tamara.testproject.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tamara on 5/1/17.
 */

public class ArticleResponse implements Parcelable{

    @SerializedName("status")
    private String status;

    @SerializedName("source")
    private String source;

    @SerializedName("sortBy")
    private String sortBy;

    @SerializedName("articles")
    private ArrayList<ArticleItem> articles;


    protected ArticleResponse(Parcel in) {
        status = in.readString();
        source = in.readString();
        sortBy = in.readString();
        in.readTypedList(articles, ArticleItem.CREATOR);
    }

    public static final Creator<ArticleResponse> CREATOR = new Creator<ArticleResponse>() {
        @Override
        public ArticleResponse createFromParcel(Parcel in) {
            return new ArticleResponse(in);
        }

        @Override
        public ArticleResponse[] newArray(int size) {
            return new ArticleResponse[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public String getSource() {
        return source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public ArrayList<ArticleItem> getArticles() {
        return articles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(source);
        parcel.writeString(sortBy);
        parcel.writeList(articles);

    }
}
