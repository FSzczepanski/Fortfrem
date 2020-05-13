package pl.aib.fortfrem.ui.profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.ui.FortfremFragment;
import pl.aib.fortfrem.ui.list.profile.ProfileMenuAdapter;
import pl.aib.fortfrem.ui.list.profile.ProfileMenuItem;
import pl.aib.fortfrem.ui.list.profile.ProfileMenuItemSelectedListener;

public class ProfileFragment extends FortfremFragment implements ProfileMenuItemSelectedListener {
    private ProfileViewModel viewModel;
    private ImageView picture;
    private TextView username;
    private RecyclerView menu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        this.picture = root.findViewById(R.id.profile_pic);
        this.username = root.findViewById(R.id.username);
        this.menu = root.findViewById(R.id.menu);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(this.navController == null && getView() != null) {
            this.navController = Navigation.findNavController(getView());
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            this.viewModel.init(user);
        } else {
            goToAuth();
        }

        this.viewModel.getUser().observe(getViewLifecycleOwner(), this::onUserChanged);

        setupMenu(view.getContext());
    }

    private void setupMenu(Context context) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        ProfileMenuAdapter adapter = new ProfileMenuAdapter(this);
        adapter.setData(getMenuItems());
        this.menu.setLayoutManager(manager);
        this.menu.setHasFixedSize(false);
        this.menu.setAdapter(adapter);
    }

    private void onLogoutRequested() {
        this.viewModel.logout();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void onUserChanged(@Nullable FirebaseUser user) {
        if(user != null) {
            updateUser(user);
        } else {
            goToAuth();
        }
    }

    private void updateUser(@NonNull FirebaseUser user) {
        updatePicture(user.getPhotoUrl());
        this.username.setText(user.getDisplayName());

    }

    private void updatePicture(Uri uri) {
        RequestOptions requestOptions = (new RequestOptions())
                .fitCenter()
                .override(128)
                .transform(new CenterCrop(), new RoundedCorners(100));

        Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.app_logo)
                .apply(requestOptions)
                .into(this.picture);
    }

    private void goToAuth() {
        NavDirections authAction = ProfileFragmentDirections.goToAuth();
        if (this.navController != null) {
            this.navController.navigate(authAction);
        }
    }

    private List<ProfileMenuItem> getMenuItems() {
        return Arrays.asList(
                new ProfileMenuItem(R.drawable.ic_person_outline_green_24dp, R.string.edit_profile_menu_item, ProfileMenuItem.Type.PROFILE),
                new ProfileMenuItem(R.drawable.ic_settings_green_24dp, R.string.settings_menu_item, ProfileMenuItem.Type.PROFILE),
                new ProfileMenuItem(R.drawable.ic_info_outline_green_24dp, R.string.app_info_menu_item, ProfileMenuItem.Type.INFO),
                new ProfileMenuItem(R.drawable.ic_info_outline_green_24dp, R.string.terms_of_use, ProfileMenuItem.Type.INFO),
                new ProfileMenuItem(R.drawable.ic_info_outline_green_24dp, R.string.privacy_policy_menu_item, ProfileMenuItem.Type.INFO),
                new ProfileMenuItem(R.drawable.ic_exit_to_app_red_24dp, R.string.log_out_menu_item, ProfileMenuItem.Type.LOGOUT)
        );
    }

    @Override
    public void onProfileMenuItemSelected(ProfileMenuItem item) {
        if(item.getType() == ProfileMenuItem.Type.LOGOUT) {
            onLogoutRequested();
        }
    }
}

