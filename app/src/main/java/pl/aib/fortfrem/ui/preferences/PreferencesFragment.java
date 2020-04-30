package pl.aib.fortfrem.ui.preferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Preference;
import pl.aib.fortfrem.ui.FortfremFragment;
import pl.aib.fortfrem.ui.favourites.FavouritesViewModel;
import pl.aib.fortfrem.ui.list.offers.OffersAdapter;

public class PreferencesFragment extends FortfremFragment {
    private PreferencesViewModel viewModel;
    private SwipeRefreshLayout refreshLayout;
    private View indicatorLayer;
    private RecyclerView recyclerView;
    private AdapterPreferences adapter;
    private ArrayList<Preference> preferencesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.viewModel = ViewModelProviders.of(this).get(PreferencesViewModel.class);
        fillList();
        View root = inflater.inflate(R.layout.fragment_preferences, container, false);
        this.bottomPopover = root.findViewById(R.id.bottom_popover);
        this.bottomPopoverText = root.findViewById(R.id.bottom_popover_text);
        this.bottomPopoverAccept = root.findViewById(R.id.bottom_popover_accept);
        //this.refreshLayout = root.findViewById(R.id.container);
        this.indicatorLayer = root.findViewById(R.id.indicator_container);
        //ProgressBar progressBar = root.findViewById(R.id.indicator);
        //this.resolveProgressBarColor(progressBar);
        this.initList(root.findViewById(R.id.recycleView));
        return root;
    }

    private void initList(RecyclerView view) {
        this.recyclerView = view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //manager.setOrientation(RecyclerView.VERTICAL);

        //this.list.setHasFixedSize(false);

        if (getContext() != null) {
            adapter = new AdapterPreferences(getActivity(), preferencesList);
            this.recyclerView.setAdapter(adapter);
        }
    }

    private void fillList() {
        preferencesList = new ArrayList<Preference>();
        preferencesList.add(new Preference("Ulubione kategorie"));
        preferencesList.add(new Preference("Ulubione produkty"));
        preferencesList.add(new Preference("Ulubione sklepy"));
        preferencesList.add(new Preference(""));

    }
}
