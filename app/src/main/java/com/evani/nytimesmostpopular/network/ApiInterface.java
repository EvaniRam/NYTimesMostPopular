package com.evani.nytimesmostpopular.network;

import com.evani.nytimesmostpopular.models.MostPopularResponse;
import com.evani.nytimesmostpopular.models.Response;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @GET("mostpopular/v2/mostviewed/{section}/{period}.json")
    Call<MostPopularResponse> getArticleDetails(@Path("section") String section, @Path("period") String period,
                                                @Query("api-key") String apiKey);

    @GET("mostpopular/v2/mostviewed/all-sections/7.json")
    Observable<Response> getMostPopularArticles(@QueryMap Map<String, String> options);
}