package pl.aib.fortfrem.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.ui.FortfremFragment;

public class LoginFragment extends FortfremFragment {
    ViewModel viewModel;
    TextView registerLink;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        this.registerLink = root.findViewById(R.id.register_link);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(this.navController == null && getView() != null) {
            this.navController = Navigation.findNavController(getView());
        }
        this.registerLink.setOnClickListener(v -> this.goToRegister());
    }

    private void goToRegister() {
        if(this.navController != null) {
            this.navController.navigate(R.id.navigation_register);
        }
    }
}
