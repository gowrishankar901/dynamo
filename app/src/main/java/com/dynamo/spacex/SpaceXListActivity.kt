package com.dynamo.spacex

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dynamo.spacex.databinding.ActivityMainBinding
import com.dynamo.spacex.viewmodels.SpaceXListViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class SpaceXListActivity : AppCompatActivity() {
    @Inject
    lateinit var spaceXListViewModel: SpaceXListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = spaceXListViewModel
        binding.spacexRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.spacexRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.spacexRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val fetchOffset = binding.spacexRecyclerView.layoutManager?.itemCount
                spaceXListViewModel.fetchSpaceXPastLaunchList(fetchOffset)
            }
        })

        spaceXListViewModel.fetchSpaceXPastLaunchList(0)
        spaceXListViewModel.startLaunchDetailsActivity().observe(this, Observer { startLaunchDetailsActivity(it) })
    }

    private fun startLaunchDetailsActivity(value:Int) {
        val intent = Intent(this, LaunchDetailsActivity::class.java)
        startActivity(intent)
    }
}