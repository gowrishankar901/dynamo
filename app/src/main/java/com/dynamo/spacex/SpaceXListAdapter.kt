package com.dynamo.spacex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dynamo.spacex.databinding.ItemSpacexViewHolderBinding
import com.dynamo.spacex.viewmodels.SpaceXListViewModel
import com.dynamo.spacex.viewmodels.SpaceXViewModel

class SpaceXListAdapter (private val spaceXListViewModel: SpaceXListViewModel): RecyclerView.Adapter<SpaceXItemViewHolder>() {

    private val spaceXViewModels = ArrayList<SpaceXViewModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceXItemViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val spacexViewHolderBinding: ItemSpacexViewHolderBinding = ItemSpacexViewHolderBinding.inflate(layoutInflater, parent, false)
        return SpaceXItemViewHolder(spacexViewHolderBinding)
    }

    override fun getItemCount(): Int {
        return spaceXViewModels.size
    }

    override fun onBindViewHolder(holder: SpaceXItemViewHolder, position: Int) {
        val viewModel: SpaceXViewModel = spaceXViewModels[position]
        holder.bind(viewModel, spaceXListViewModel)
    }

    fun setSpaceXViewModels(spaceXViewModels: List<SpaceXViewModel>) {
        this.spaceXViewModels.addAll(spaceXViewModels)
        notifyDataSetChanged()
    }
}