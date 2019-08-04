package com.dynamo.spacex;

import androidx.recyclerview.widget.RecyclerView;

import com.dynamo.spacex.databinding.ItemSpacexViewHolderBinding;
import com.dynamo.spacex.viewmodels.SpaceXListViewModel;
import com.dynamo.spacex.viewmodels.SpaceXViewModel;

public class SpaceXItemViewHolder extends RecyclerView.ViewHolder {

    private final ItemSpacexViewHolderBinding itemSpacexViewHolderBinding;

    public SpaceXItemViewHolder(ItemSpacexViewHolderBinding itemSpacexViewHolderBinding) {
        super(itemSpacexViewHolderBinding.getRoot());
        this.itemSpacexViewHolderBinding = itemSpacexViewHolderBinding;
    }

    public void bind(SpaceXViewModel spaceXViewModel, SpaceXListViewModel spaceXListViewModel) {
        itemSpacexViewHolderBinding.setViewModel(spaceXViewModel);
        itemSpacexViewHolderBinding.setListViewModel(spaceXListViewModel);
        itemSpacexViewHolderBinding.executePendingBindings();
    }
}
