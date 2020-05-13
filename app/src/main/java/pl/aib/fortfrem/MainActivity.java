package pl.aib.fortfrem;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavAction;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import pl.aib.fortfrem.data.entity.Offer;
import pl.aib.fortfrem.utility.exception.FortfremException;
import pl.aib.fortfrem.utility.repository.OfferRepository;
import pl.aib.fortfrem.utility.repository.listener.OffersErrorListener;
import pl.aib.fortfrem.utility.repository.listener.OffersListener;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener, FirebaseAuth.AuthStateListener {

    private TextView screenTitle;
    private ImageView userIcon;
    private View backButtonContainer;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.backButtonContainer = findViewById(R.id.back_button_container);
        this.screenTitle = findViewById(R.id.screen_title);
        this.userIcon = findViewById(R.id.user_icon);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> navController.popBackStack());

        navController.addOnDestinationChangedListener(this);
        FirebaseAuth.getInstance().addAuthStateListener(this);
        this.navController = navController;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        if(this.screenTitle != null) {
            this.screenTitle.setText(destination.getLabel());
        }

        boolean showBackButton = getListOfDestinationsWithBackButton().contains(destination.getId());
        adjustActionBar(showBackButton);
    }

    private List<Integer> getListOfDestinationsWithBackButton() {
        return Arrays.asList(
                R.id.navigation_offer_details
        );
    }

    private void adjustActionBar(boolean showBackButton) {
        if(showBackButton && !isBackButtonVisible()) {
            adjustActionBarWithBackButton();
        } else if(!showBackButton && isBackButtonVisible()) {
            adjustActionBarWithoutBackButton();
        }
    }

    private void adjustActionBarWithBackButton() {
        View logoContainer = findViewById(R.id.logo_container);
        int width = 0;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        this.backButtonContainer.setLayoutParams(new LinearLayout.LayoutParams(width, height, 0.1f));
        logoContainer.setLayoutParams(new LinearLayout.LayoutParams(width, height, 0.2f));

        this.backButtonContainer.setVisibility(View.VISIBLE);
    }

    private void adjustActionBarWithoutBackButton() {
        View logoContainer = findViewById(R.id.logo_container);
        int width = 0;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        this.backButtonContainer.setLayoutParams(new LinearLayout.LayoutParams(width, height, 0));
        logoContainer.setLayoutParams(new LinearLayout.LayoutParams(width, height, 0.3f));

        this.backButtonContainer.setVisibility(View.GONE);

    }

    private boolean isBackButtonVisible() {
        return this.backButtonContainer.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user == null) {
            hideUserIcon();
        } else {
            showUserIcon(user);
        }
    }

    private void hideUserIcon() {
        this.userIcon.setOnClickListener(null);
        this.userIcon.setVisibility(View.GONE);
    }

    private void showUserIcon(FirebaseUser user) {
        this.userIcon.setVisibility(View.VISIBLE);
        updateUserPicture(user.getPhotoUrl());
        this.userIcon.setOnClickListener(v -> navigateToUserProfile());
    }

    private void navigateToUserProfile() {
        this.navController.navigate(R.id.navigation_profile) ;
    }

    private void updateUserPicture(Uri uri) {
        RequestOptions requestOptions = (new RequestOptions())
                .fitCenter()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(this.userIcon.getWidth())
                .transform(new CenterCrop(), new RoundedCorners(100));

        Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.app_logo_round)
                .apply(requestOptions)
                .into(this.userIcon);
    }

}
