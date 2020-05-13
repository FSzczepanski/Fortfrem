package pl.aib.fortfrem.ui.preferences.tabShops;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Category;
import pl.aib.fortfrem.ui.FortfremFragment;
import pl.aib.fortfrem.ui.preferences.tabSubcategories.AdapterTabSubcategories;

public class TabShops extends FortfremFragment {

    private TabShopsViewModel tabShopsViewModel;
    private SwipeRefreshLayout refreshLayout;
    private View indicatorLayer;
    private RecyclerView recyclerView;
    private AdapterTabSubcategories adapter;
    private ArrayList<Category> shopsList;
    private ImageButton button;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.tabShopsViewModel = ViewModelProviders.of(this).get(TabShopsViewModel.class);

        View root = inflater.inflate(R.layout.tab_shops_fragment, container, false);
        fillList();
        this.bottomPopover = root.findViewById(R.id.bottom_popover);
        this.bottomPopoverText = root.findViewById(R.id.bottom_popover_text);
        this.bottomPopoverAccept = root.findViewById(R.id.bottom_popover_accept);
        //this.refreshLayout = root.findViewById(R.id.container);
        // this.indicatorLayer = root.findViewById(R.id.indicator_container);
        //ProgressBar progressBar = root.findViewById(R.id.indicator);
        //this.resolveProgressBarColor(progressBar);
        this.initList(root.findViewById(R.id.listShops));
        button = root.findViewById(R.id.buttonA);
        buttonListener();

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
            adapter = new AdapterTabSubcategories(getActivity(), shopsList);
            this.recyclerView.setAdapter(adapter);
        }
    }
    private void buttonListener() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Test", Toast.LENGTH_SHORT).show();



            }
        });
    }
    //temporary
    private void fillList() {
        shopsList = new ArrayList<Category>();
        shopsList.add(new Category("EURO RTV agd",R.drawable.ubrania));
        shopsList.add(new Category("Biedronka",R.drawable.app_logo));
        shopsList.add(new Category("Tesco",R.drawable.app_logo));
        shopsList.add(new Category("X-kom",R.drawable.ubrania));
        shopsList.add(new Category("kucciap",R.drawable.app_logo));
        shopsList.add(new Category("Piotr I Paweł",R.drawable.app_logo));
        shopsList.add(new Category("media",R.drawable.ubrania));

    }
}
