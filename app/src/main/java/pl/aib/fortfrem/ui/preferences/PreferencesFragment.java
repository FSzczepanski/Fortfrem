package pl.aib.fortfrem.ui.preferences;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.ui.FortfremFragment;


public class PreferencesFragment extends FortfremFragment {
    private ViewPager pager;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tabbed_activity, container, false);
        this.pager = root.findViewById(R.id.view_pager);
        return root;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AdapterPreferences pagerAdapter = null;

        pagerAdapter = new AdapterPreferences(getChildFragmentManager(), getContext());
        this.pager.setAdapter(pagerAdapter);



    }
    @Override
    public void onStart() {
        super.onStart();
        if (this.navController == null && getView() != null) {
            this.navController = Navigation.findNavController(getView());
        }
       // this.navController.navigate(R.id.action_navigation_preferences_to_tabCategories);


    }
}