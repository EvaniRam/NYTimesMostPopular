
package com.evani.nytimesmostpopular.ui.activities;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evani.nytimesmostpopular.R;
import com.evani.nytimesmostpopular.adapters.NewsAdapter;
import com.evani.nytimesmostpopular.models.MostPopular;
import com.evani.nytimesmostpopular.ui.fragments.MostPopularListFragment;
import com.evani.nytimesmostpopular.viewmodels.NewsViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "MainActivity";

    private NewsViewModel viewModel;
    private ArrayList<MostPopular> articleList = new ArrayList<>();
    private ArrayList<String> imageUrls = new ArrayList<>();

    private NewsAdapter mAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        handleMostPopular();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }


    private void initMostPoupular() {
        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        viewModel.init();

        viewModel.getMostPopularNews().observe(this, new Observer<List<MostPopular>>() {
            @Override
            public void onChanged(List<MostPopular> mostPopulars) {
                if (mostPopulars !=null) {
                    articleList.addAll(mostPopulars);
                    //initRecyclerView();
                }


            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_most_popular) {
            initMostPoupular();
            handleMostPopular();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void handleMostPopular() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MostPopularListFragment()).commit();
    }


    /*private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview.");

        viewModel.getMostPopularNews().observe(this, new Observer<List<MostPopular>>() {
            @Override
            public void onChanged(final List<MostPopular> mostPopulars) {
                if( mostPopulars.size() > 0 && (articleList.size() != mostPopulars.size())) {
                    articleList.clear();
                    articleList.addAll(mostPopulars);
                    for (MostPopular mostPopular : mostPopulars) {
                        imageUrls.add(mostPopular.getStandardThumnail());
                    }

                    //Java 8 Lamda expression
                    //imageUrls.addAll(mostPopulars.forEach(result -> result.getStandardThumnail()));
                }
            }
        });

        if(articleList.size()  > 0 && imageUrls.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            mAdapter = new NewsAdapter(MainActivity.this,articleList,imageUrls);
            recyclerView.setAdapter(mAdapter);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(llm);
            recyclerView.setHasFixedSize(true);
        }
    }*/
}
