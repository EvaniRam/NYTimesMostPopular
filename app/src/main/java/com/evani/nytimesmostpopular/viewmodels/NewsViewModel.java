package com.evani.nytimesmostpopular.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.evani.nytimesmostpopular.models.MostPopular;
import com.evani.nytimesmostpopular.repositories.NewsRepository;

import java.util.List;

public class NewsViewModel extends ViewModel {
    private MutableLiveData<List<MostPopular>> mResults = new MutableLiveData<>();
    private MutableLiveData<Boolean> showProgress = new MutableLiveData<> ();
    private NewsRepository mRepo;


    public void init() {

        if (mResults.getValue() != null) {
            return;
        }

        mResults = NewsRepository.getInstance().getMostPopularArticles();

    }

    public LiveData<List<MostPopular>> getMostPopularNews() {
        if (mResults.getValue() != null) {
            return mResults;
        }

        return mResults = NewsRepository.getInstance().getMostPopularArticles();

    }

    public LiveData<Boolean> showHideProgress() {
       return NewsRepository.getInstance().getShowProgress();
    }

}
