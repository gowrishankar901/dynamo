package com.dynamo.spacex;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dynamo.spacex.databinding.ItemSpacexViewHolderBinding;
import com.dynamo.spacex.viewmodels.SpaceXListViewModel;
import com.dynamo.spacex.viewmodels.SpaceXViewModel;

import java.util.List;

public class SpaceXListAdapter extends RecyclerView.Adapter<SpaceXItemViewHolder> {

    private List<SpaceXViewModel> spaceXViewModels;
    private SpaceXListViewModel spaceXListViewModel;

    public SpaceXListAdapter(List<SpaceXViewModel> spaceXViewModels, SpaceXListViewModel spaceXListViewModel) {
        this.spaceXViewModels = spaceXViewModels;
        this.spaceXListViewModel = spaceXListViewModel;
    }

    @NonNull
    @Override
    public SpaceXItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSpacexViewHolderBinding spacexViewHolderBinding = ItemSpacexViewHolderBinding.inflate(layoutInflater, parent, false);
        return new SpaceXItemViewHolder(spacexViewHolderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SpaceXItemViewHolder holder, int position) {
        SpaceXViewModel viewModel = spaceXViewModels.get(position);
        holder.bind(viewModel, spaceXListViewModel);
    }

    @Override
    public int getItemCount() {
        return (spaceXViewModels != null ? spaceXViewModels.size() : 0);
    }
}
