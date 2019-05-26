package com.evani.nytimesmostpopular.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.evani.nytimesmostpopular.models.MostPopular;
import com.evani.nytimesmostpopular.models.MostPopularResponse;
import com.evani.nytimesmostpopular.network.ApiClient;
import com.evani.nytimesmostpopular.network.ApiInterface;
import com.evani.nytimesmostpopular.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsRepository {

    private static final String TAG = "NewsRepository";

    private static NewsRepository instance;

    private MutableLiveData<List<MostPopular>> mMostPopularArticles = new MutableLiveData<>();

    public MutableLiveData<Boolean> getShowProgress() {
        return showProgress;
    }

    private final MutableLiveData<Boolean> showProgress = new MutableLiveData<>();

    private List<MostPopular> dataSet = new ArrayList<>();

    public static NewsRepository getInstance() {

        if (instance == null) {
            instance = new NewsRepository();
        }
        return instance;
    }

    private NewsRepository() {
        Retrofit apiClient = ApiClient.getClient();

    }

    //Make Api call to NYTimes Api and retrieve the reults
    public MutableLiveData<List<MostPopular>> getMostPopularArticles() {

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        String period = "7";
        String section = "all-sections";
        Call<MostPopularResponse> call = service.getArticleDetails(section, period, Constants.API_KEY);
        showProgress.setValue(true);
        call.enqueue(new Callback<MostPopularResponse>() {
            @Override
            public void onResponse(Call<MostPopularResponse> call, Response<MostPopularResponse> response) {

                Log.d(TAG, "onResponse: success "+ response.body());
                int statusCode = response.code();
                if (statusCode == 200 && response.isSuccessful() ) {

                    mMostPopularArticles.setValue(response.body().getResults());
                    showProgress.setValue(false);
                }

            }

            @Override
            public void onFailure(Call<MostPopularResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: failed "+t.getMessage(),t);
                showProgress.setValue(true);
                //getMostPopularArticles();
            }
        });

        return mMostPopularArticles;
    }



    public void setmMostPopularArticles(MutableLiveData<List<MostPopular>> mMostPopularArticles) {
        this.mMostPopularArticles = mMostPopularArticles;
    }


}
