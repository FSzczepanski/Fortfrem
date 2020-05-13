package pl.aib.fortfrem.ui.preferences;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import org.jetbrains.annotations.NotNull;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.ui.preferences.tabCategories.TabCategories;
import pl.aib.fortfrem.ui.preferences.tabShops.TabShops;
import pl.aib.fortfrem.ui.preferences.tabSubcategories.TabSubcategories;

public class AdapterPreferences extends FragmentPagerAdapter {
    private final int ITEM_COUNT = 3;

    @NonNull
    private Context context;

    public AdapterPreferences(@NonNull FragmentManager fm,  @NotNull Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new TabCategories();
        } else if(position == 1) {
            fragment = new TabSubcategories();
        }
        else {
            fragment = new TabShops();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position) {
            case 0:
                title = this.context.getString(R.string.tab_text_1);
                break;
            case 1:
                title = this.context.getString(R.string.tab_text_2);
                break;
            case 2:
                title = this.context.getString(R.string.tab_text_3);
                break;
            default:
                title = null;
                break;
        }
        return title;
    }
}
