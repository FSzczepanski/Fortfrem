package pl.aib.fortfrem.ui.preferencesCategory;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Category;
import pl.aib.fortfrem.ui.FortfremFragment;

public class CategoriesFragment extends FortfremFragment {
    private CategoriesViewModel categoriesViewModel;
    private SwipeRefreshLayout refreshLayout;
    private View indicatorLayer;
    private RecyclerView recyclerView;
    private AdapterCategories adapter;
    private ArrayList<Category> categoriesList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        fillList();
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        this.bottomPopover = root.findViewById(R.id.bottom_popover);
        this.bottomPopoverText = root.findViewById(R.id.bottom_popover_text);
        this.bottomPopoverAccept = root.findViewById(R.id.bottom_popover_accept);
        //this.refreshLayout = root.findViewById(R.id.container);
       // this.indicatorLayer = root.findViewById(R.id.indicator_container);
        //ProgressBar progressBar = root.findViewById(R.id.indicator);
        //this.resolveProgressBarColor(progressBar);
        this.initList(root.findViewById(R.id.recycleView2));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initList(RecyclerView view) {
        this.recyclerView = view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //manager.setOrientation(RecyclerView.VERTICAL);

        //this.list.setHasFixedSize(false);

        if (getContext() != null) {
            adapter = new AdapterCategories(getActivity(), categoriesList);
            this.recyclerView.setAdapter(adapter);
        }
    }

    private void fillList() {
        categoriesList = new ArrayList<Category>();
        categoriesList.add(new Category("Odzież",R.drawable.ubrania));
        categoriesList.add(new Category("Obuwie",R.drawable.app_logo));
        categoriesList.add(new Category("Meble",R.drawable.ubrania));
        categoriesList.add(new Category("RTV",R.drawable.app_logo));

    }
}