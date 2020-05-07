package pl.aib.fortfrem.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import pl.aib.fortfrem.R;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<Boolean> processing;
    private MutableLiveData<Integer> emailError;
    private MutableLiveData<Integer> passwordError;
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;

    private boolean success = false;

    private FirebaseAuth auth;

    public LoginViewModel() {
        this.processing = new MutableLiveData<>();
        this.email = new MutableLiveData<>();
        this.emailError = new MutableLiveData<>();
        this.password = new MutableLiveData<>();
        this.passwordError = new MutableLiveData<>();

        this.auth = FirebaseAuth.getInstance();
    }

    public void signInWithGoogle(String token) {
        this.processing.setValue(true);
        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
        this.auth.signInWithCredential(credential)
                .addOnCompleteListener(this::onSignInResult);
    }

    public MutableLiveData<Boolean> getProcessing() {
        return processing;
    }

    public boolean isSuccess() {
        return success;
    }

    public MutableLiveData<Integer> getEmailError() {
        return emailError;
    }

    public MutableLiveData<Integer> getPasswordError() {
        return passwordError;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
        validateEmail();
    }

    public void setPassword(String password) {
        this.password.setValue(password);
        validatePassword();
    }

    private boolean validateEmail() {
        boolean isValid = this.email.getValue() != null && this.email.getValue().trim().length() > 0;
        Integer error = !isValid ? R.string.field_required : null;
        this.emailError.setValue(error);
        return isValid;
    }

    private boolean validatePassword() {
        boolean isValid = this.password.getValue() != null && this.password.getValue().length() > 0;
        Integer error = !isValid ? R.string.field_required : null;
        this.passwordError.setValue(error);
        return isValid;
    }

    public void processLogin() {
        if(validateEmail() && validatePassword()) {
            this.processing.setValue(true);
            this.signInWithEmail();
        }
    }

    private void signInWithEmail() {
        String email = this.email.getValue() != null ? this.email.getValue().trim() : null;
        String password = this.password.getValue();
        if(email != null && password != null) {
            this.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this::onSignInResult);
        } else {
            this.success = false;
            this.processing.setValue(false);
        }
    }

    private void onSignInResult(@NonNull Task<AuthResult> result) {
        this.success = result.isSuccessful();
        this.processing.setValue(false);
    }
}
