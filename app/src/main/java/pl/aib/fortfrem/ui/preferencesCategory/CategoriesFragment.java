package pl.aib.fortfrem.ui.preferencesCategory;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLDisplay;

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
    private EditText editText;
    private NavController navController;


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
        editText= root.findViewById(R.id.edittext);
        initSearch();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(this.navController == null && getView() != null) {
            this.navController = Navigation.findNavController(getView());
        }
    }
    @Override
    public void onStart() {
        super.onStart();

    }

    private void initList(RecyclerView view) {
        this.recyclerView = view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (getContext() != null) {
            adapter = new AdapterCategories(getActivity(), categoriesList);
            this.recyclerView.setAdapter(adapter);
        }
    }

    public void initSearch(){
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }
    private void filter(String text){
        ArrayList<Category> filteredList= new ArrayList<>();

        for(Category item : categoriesList){
            if(item.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
    private void fillList() {
        categoriesList = new ArrayList<Category>();
        categoriesList.add(new Category("Odzież",R.drawable.ubrania));
        categoriesList.add(new Category("Obuwie",R.drawable.rtv));
        categoriesList.add(new Category("Meble",R.drawable.mebel));
        categoriesList.add(new Category("Samochody",R.drawable.car));
        categoriesList.add(new Category("Nieruchomości",R.drawable.rtv));
        categoriesList.add(new Category("Książki",R.drawable.app_logo));
        categoriesList.add(new Category("Panasonic",R.drawable.car));
        categoriesList.add(new Category("AGD",R.drawable.mebel));

    }
}