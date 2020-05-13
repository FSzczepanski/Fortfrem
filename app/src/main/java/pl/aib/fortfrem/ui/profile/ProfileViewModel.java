package pl.aib.fortfrem.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<FirebaseUser> user;

    private FirebaseAuth auth;

    public ProfileViewModel() {
        this.auth = FirebaseAuth.getInstance();
        this.user = new MutableLiveData<>();
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void init(@NonNull FirebaseUser user) {
        this.user.setValue(user);
    }

    public void logout() {
        this.auth.signOut();
        this.user.setValue(this.auth.getCurrentUser());
    }
}
