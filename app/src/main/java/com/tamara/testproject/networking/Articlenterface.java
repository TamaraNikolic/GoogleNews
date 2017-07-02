package com.tamara.testproject.networking;

import com.tamara.testproject.data.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tamara on 5/1/17.
 */

public interface Articlenterface {
    @GET("articles")
    Call<ArticleResponse> getNewsItems (@Query("source") String source,
                                        @Query("apiKey") String apiKey);

}
