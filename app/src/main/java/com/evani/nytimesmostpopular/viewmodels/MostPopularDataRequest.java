package com.evani.nytimesmostpopular.viewmodels;

import com.evani.nytimesmostpopular.models.Response;
import com.evani.nytimesmostpopular.network.ApiClient;
import com.evani.nytimesmostpopular.network.ApiInterface;
import com.evani.nytimesmostpopular.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class MostPopularDataRequest {

    private static final String TAG = MostPopularDataRequest.class.getSimpleName();

    private final ApiInterface apiEndPoint;

    public MostPopularDataRequest() {
        this.apiEndPoint = ApiClient.createService(ApiInterface.class);
    }

    public Observable<Response> getMostPopularArticles() {

        Map<String, String> data = new HashMap<>();
        data.put("api-key", Constants.API_KEY);

        return apiEndPoint.getMostPopularArticles(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
