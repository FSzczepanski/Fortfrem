package pl.aib.fortfrem.ui.pager.auth;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.ui.login.LoginFragment;
import pl.aib.fortfrem.ui.register.RegisterFragment;

public class AuthPagerAdapter extends FragmentPagerAdapter {
    private final int ITEM_COUNT = 2;

    @NonNull
    private Context context;

    public AuthPagerAdapter(@NonNull FragmentManager fm, int behavior, @NotNull Context context) {
        super(fm, behavior);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new LoginFragment();
        } else {
            fragment = new RegisterFragment();
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
                title = this.context.getString(R.string.log_in_screen_title);
                break;
            case 1:
                title = this.context.getString(R.string.register_screen_title);
                break;
                default:
                    title = null;
                    break;
        }
        return title;
    }
}
