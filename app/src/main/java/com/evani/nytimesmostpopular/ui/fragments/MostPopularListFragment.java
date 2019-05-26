package com.evani.nytimesmostpopular.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.evani.nytimesmostpopular.BuildConfig;
import com.evani.nytimesmostpopular.R;
import com.evani.nytimesmostpopular.adapters.MostPopularListAdapter;
import com.evani.nytimesmostpopular.adapters.NewsAdapter;
import com.evani.nytimesmostpopular.models.MostPopular;
import com.evani.nytimesmostpopular.models.MostPopularResponse;
import com.evani.nytimesmostpopular.models.Response;
import com.evani.nytimesmostpopular.ui.activities.MainActivity;
import com.evani.nytimesmostpopular.ui.activities.MostPopularDetailActivity;
import com.evani.nytimesmostpopular.utils.RecyclerviewItemDecorator;
import com.evani.nytimesmostpopular.utils.Utils;
import com.evani.nytimesmostpopular.viewmodels.MostPopularDataRequest;
import com.evani.nytimesmostpopular.viewmodels.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MostPopularListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MostPopularListFragment.class.getSimpleName();


    @Nullable
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    private RecyclerView recyclerView;

    @Nullable
    private MostPopularListAdapter mostPopularListAdapter;

    @Nullable
    private View errorLayout;

    @Nullable
    private Disposable disposable;

    @Nullable
    private MostPopularDataRequest mostPopularDataRequest;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this._context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_most_popular_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onRefresh();
    }

    private void initViews(@NonNull View view) {
        recyclerView = view.findViewById(R.id.recyclerView);

        mostPopularListAdapter = new MostPopularListAdapter(_context, newsItem -> MostPopularDetailActivity.start(_context, newsItem));
        recyclerView.setAdapter(mostPopularListAdapter);

        recyclerView.addItemDecoration(new RecyclerviewItemDecorator(getResources().getDimensionPixelSize(R.dimen.spacing_micro)));
        recyclerView.setLayoutManager(new LinearLayoutManager(_context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        errorLayout = view.findViewById(R.id.errorPage);
        Button buttonRetry = view.findViewById(R.id.retryBtn);

        buttonRetry.setOnClickListener(v -> onRefresh());

        initRequest();
    }

    private void initRequest() {
        if (mostPopularDataRequest == null) {
            mostPopularDataRequest = new MostPopularDataRequest();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mostPopularListAdapter = null;
        swipeRefreshLayout = null;
        recyclerView = null;
    }

    @Override
    public void onRefresh() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onRefresh() called");
        }
        showProgress(true);

        initRequest();

        disposable = mostPopularDataRequest.getMostPopularArticles()
                .subscribe(this::updateItems,
                        this::handleError);
    }

    private void showProgress(boolean shouldShow) {
        swipeRefreshLayout.setRefreshing(shouldShow);
        Utils.setVisible(recyclerView, !shouldShow);
        Utils.setVisible(errorLayout, !shouldShow);
    }

    private void updateItems(@Nullable Response response) {
        if (mostPopularListAdapter != null)
            mostPopularListAdapter.replaceItems(response.getResults());

        Utils.setVisible(recyclerView, true);
        swipeRefreshLayout.setRefreshing(false);
        Utils.setVisible(errorLayout, false);
    }

    private void handleError(Throwable th) {

        Utils.setVisible(errorLayout, true);
        swipeRefreshLayout.setRefreshing(false);
        Utils.setVisible(recyclerView, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        showProgress(false);
        Utils.disposeSafe(disposable);
        disposable = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mostPopularListAdapter != null && mostPopularListAdapter.getItemCount() > 0) {
            Utils.setVisible(errorLayout, false);
        }
    }


}
