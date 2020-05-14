package pl.aib.fortfrem.ui.preferences.tabSubcategories;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Category;
import pl.aib.fortfrem.ui.FortfremFragment;

public class TabSubcategories extends FortfremFragment {

    private TabSubcategoriesViewModel tabSubcategoriesViewModel;

    private SwipeRefreshLayout refreshLayout;
    private View indicatorLayer;
    private RecyclerView recyclerView;
    private AdapterTabSubcategories adapter;
    private ArrayList<Category> subcategoriesList;
    private FloatingActionButton button;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.tabSubcategoriesViewModel = ViewModelProviders.of(this).get(TabSubcategoriesViewModel.class);

        View root = inflater.inflate(R.layout.tab_subcategories_fragment, container, false);
        fillList();
        this.bottomPopover = root.findViewById(R.id.bottom_popover);
        this.bottomPopoverText = root.findViewById(R.id.bottom_popover_text);
        this.bottomPopoverAccept = root.findViewById(R.id.bottom_popover_accept);
        //this.refreshLayout = root.findViewById(R.id.container);
        // this.indicatorLayer = root.findViewById(R.id.indicator_container);
        //ProgressBar progressBar = root.findViewById(R.id.indicator);
        //this.resolveProgressBarColor(progressBar);
        this.initList(root.findViewById(R.id.listSubcategories));
        button = root.findViewById(R.id.buttonA);
        buttonListener();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(this.navController == null && getView() != null) {
            navController = Navigation.findNavController(getView());
        }

        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.categoriesFragment, null));
    }

    private void initList(RecyclerView view) {
        this.recyclerView = view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //manager.setOrientation(RecyclerView.VERTICAL);

        //this.list.setHasFixedSize(false);

        if (getContext() != null) {
            adapter = new AdapterTabSubcategories(getActivity(), subcategoriesList);
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
        subcategoriesList = new ArrayList<Category>();
        subcategoriesList.add(new Category("Koszulki",R.drawable.ubrania));
        subcategoriesList.add(new Category("Buty Sportowe",R.drawable.car));
        subcategoriesList.add(new Category("Gry xbox",R.drawable.app_logo));
        subcategoriesList.add(new Category("Gry ps4",R.drawable.rtv));
        subcategoriesList.add(new Category("Gry PC",R.drawable.app_logo));
        subcategoriesList.add(new Category("Buty zimowe",R.drawable.rtv));
        subcategoriesList.add(new Category("Gry",R.drawable.app_logo));

    }
}
