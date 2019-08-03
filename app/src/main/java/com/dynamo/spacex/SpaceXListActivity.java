package com.dynamo.spacex;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class SpaceXListActivity extends AppCompatActivity {

    private SpaceXListViewModel spaceXListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spaceXListViewModel = ViewModelProviders.of(this).get(SpaceXListViewModel.class);
    }
}
