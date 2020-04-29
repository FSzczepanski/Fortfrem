package pl.aib.fortfrem.ui.offers;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Offer;
import pl.aib.fortfrem.ui.FortfremFragment;
import pl.aib.fortfrem.ui.favourites.FavouritesViewModel;
import pl.aib.fortfrem.ui.list.offers.OffersAdapter;

public class OffersFragment extends FortfremFragment {
    private OffersViewModel viewModel;
    private SwipeRefreshLayout refreshLayout;
    private View indicatorLayer;
    private RecyclerView list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.viewModel = ViewModelProviders.of(this).get(OffersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_offers, container, false);
        this.bottomPopover = root.findViewById(R.id.bottom_popover);
        this.bottomPopoverText = root.findViewById(R.id.bottom_popover_text);
        this.bottomPopoverAccept = root.findViewById(R.id.bottom_popover_accept);
        this.refreshLayout = root.findViewById(R.id.container);
        this.indicatorLayer = root.findViewById(R.id.indicator_container);
        ProgressBar progressBar = root.findViewById(R.id.indicator);
        this.resolveProgressBarColor(progressBar);
        this.initList(root.findViewById(R.id.list));
        return root;
    }

    private void initList(RecyclerView view) {
        this.list = view;
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        this.list.setLayoutManager(manager);
        this.list.setHasFixedSize(false);
        if(getContext() != null) {
            OffersAdapter adapter = new OffersAdapter(getContext());
            this.list.setAdapter(adapter);
        }
    }

    private void resolveProgressBarColor(ProgressBar progressBar) {
        int color = getContext() != null ? getContext().getColor(R.color.colorPrimary) : Color.BLUE;
        Drawable drawable = progressBar.getIndeterminateDrawable();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        this.viewModel.getOffers().observe(this, this::onOffersSetUpdated);
        this.viewModel.getInitialized().observe(this, this::onInitializeStatusChanged);
        this.viewModel.getPopoverMessage().observe(this, this::onPopoverMessageChanged);
        if(getContext() != null) {
            this.viewModel.initOffersIfNeeded(getContext());
        }
        this.refreshLayout.setOnRefreshListener(this::onRefresh);
    }

    private void onOffersSetUpdated(@Nullable List<Offer> offers) {
        this.checkListAdapter();
        if(this.list.getAdapter() instanceof OffersAdapter && offers != null) {
            OffersAdapter adapter = (OffersAdapter) this.list.getAdapter();
            adapter.setData(offers);
        }
        if(this.refreshLayout.isRefreshing()) {
            this.refreshLayout.setRefreshing(false);
        }
    }

    private void checkListAdapter() {
        if(this.list.getAdapter() == null && getContext() != null) {
            OffersAdapter adapter = new OffersAdapter(getContext());
            this.list.setAdapter(adapter);
        }
    }

    private void onInitializeStatusChanged(boolean initialized) {
        int visibility = initialized ? View.GONE : View.VISIBLE;
        this.indicatorLayer.setVisibility(visibility);
    }

    private void onPopoverMessageChanged(@Nullable Integer messageId) {
        if(messageId != null && getContext() != null) {
            this.showBottomPopoverMessage(getContext().getString(messageId));
        }
        if(this.refreshLayout.isRefreshing()) {
            this.refreshLayout.setRefreshing(false);
        }
    }

    private void onRefresh() {
        if(getContext() != null) {
            this.viewModel.refreshOffers(getContext());
        }
    }
}
