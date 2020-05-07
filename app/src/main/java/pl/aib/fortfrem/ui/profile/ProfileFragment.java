package pl.aib.fortfrem.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.ui.FortfremFragment;
import pl.aib.fortfrem.ui.favourites.FavouritesViewModel;
import pl.aib.fortfrem.ui.login.LoginFragment;

public class ProfileFragment extends FortfremFragment {
    private ProfileViewModel viewModel;
    private FirebaseUser user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
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
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {
            NavDirections authAction = ProfileFragmentDirections.goToAuth();
            if (this.navController != null) {
                this.navController.navigate(authAction);
            }
        }
    }
}
