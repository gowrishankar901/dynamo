package com.dynamo.spacex;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dynamo.spacex.databinding.ActivityMainBinding;
import com.dynamo.spacex.viewmodels.SpaceXListViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SpaceXListActivity extends AppCompatActivity {

    @Inject
    public SpaceXListViewModel spaceXListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(spaceXListViewModel);
        binding.spacexRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.spacexRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.spacexRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int fetch_offset = binding.spacexRecyclerView.getLayoutManager().getItemCount();
                spaceXListViewModel.fetchSpaceXPastLaunchList(fetch_offset);
            }
        });

        spaceXListViewModel.startLaunchDetailsActivity().observe(this, this::startLaunchDetailsActivity);
    }

    private void startLaunchDetailsActivity(int value) {
        Intent intent = new Intent(this, LaunchDetailsActivity.class);
        startActivity(intent);
    }
}
