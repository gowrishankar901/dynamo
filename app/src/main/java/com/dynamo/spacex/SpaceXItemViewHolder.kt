package com.dynamo.spacex

import androidx.recyclerview.widget.RecyclerView
import com.dynamo.spacex.databinding.ItemSpacexViewHolderBinding
import com.dynamo.spacex.viewmodels.SpaceXListViewModel
import com.dynamo.spacex.viewmodels.SpaceXViewModel

class SpaceXItemViewHolder (private val itemSpacexViewHolderBinding: ItemSpacexViewHolderBinding): RecyclerView.ViewHolder (itemSpacexViewHolderBinding.root) {

    fun bind(spaceXViewModel: SpaceXViewModel, spaceXListViewModel: SpaceXListViewModel) {
        itemSpacexViewHolderBinding.viewModel = spaceXViewModel
        itemSpacexViewHolderBinding.listViewModel = spaceXListViewModel
        itemSpacexViewHolderBinding.executePendingBindings()
    }
}