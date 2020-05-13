package pl.aib.fortfrem.ui.preferences.tabCategories;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Category;
import pl.aib.fortfrem.ui.FortfremFragment;
import pl.aib.fortfrem.ui.preferencesCategory.CategoriesFragment;
import pl.aib.fortfrem.ui.preferencesCategory.CategoriesViewModel;

public class TabCategories extends FortfremFragment {
    private TabCategoriesViewModel tabCategoriesViewModel;
    private SwipeRefreshLayout refreshLayout;
    private View indicatorLayer;
    private RecyclerView recyclerView;
    private AdapterTabCategories adapter;
    private ArrayList<Category> categoriesList;
    private ImageButton button;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.tabCategoriesViewModel = ViewModelProviders.of(this).get(TabCategoriesViewModel.class);
        View root = inflater.inflate(R.layout.tab_categories_fragment, container, false);
        fillList();

        this.bottomPopover = root.findViewById(R.id.bottom_popover);
        this.bottomPopoverText = root.findViewById(R.id.bottom_popover_text);
        this.bottomPopoverAccept = root.findViewById(R.id.bottom_popover_accept);
        //this.refreshLayout = root.findViewById(R.id.container);
        // this.indicatorLayer = root.findViewById(R.id.indicator_container);
        //ProgressBar progressBar = root.findViewById(R.id.indicator);
        //this.resolveProgressBarColor(progressBar);
        this.initList(root.findViewById(R.id.listCategories));
        button = root.findViewById(R.id.buttonA);
        buttonListener();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void initList(RecyclerView view) {
        this.recyclerView = view;

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //manager.setOrientation(RecyclerView.VERTICAL);

        //this.categoriesList.setHasFixedSize(false);


        if (getContext() != null) {
            adapter = new AdapterTabCategories(getActivity(), categoriesList);
            this.recyclerView.setAdapter(adapter);
        }

    }

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

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
        categoriesList = new ArrayList();
        categoriesList.add(new Category("Odzież", R.drawable.ubrania));
        categoriesList.add(new Category("Elektronika", R.drawable.app_logo));
        categoriesList.add(new Category("Obuwie", R.drawable.app_logo));
        categoriesList.add(new Category("Kosmetyki", R.drawable.app_logo));
        categoriesList.add(new Category("Części Samochodowe", R.drawable.app_logo));
        categoriesList.add(new Category("Meble", R.drawable.app_logo));
        categoriesList.add(new Category("RTV", R.drawable.app_logo));
        categoriesList.add(new Category("joo", R.drawable.app_logo));

    }
}
