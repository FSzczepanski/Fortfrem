package pl.aib.fortfrem.ui.register;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import pl.aib.fortfrem.R;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;
    private MutableLiveData<String> confirmPassword;
    private MutableLiveData<Boolean> agreement;

    private MutableLiveData<Integer> emailError;
    private MutableLiveData<Integer> passwordError;
    private MutableLiveData<Integer> confirmPasswordError;
    private MutableLiveData<Integer> agreementError;
    private MutableLiveData<Integer> formError;

    private MutableLiveData<Boolean> registrationProcessing;
    private MutableLiveData<Boolean> registrationSuccess;
    private MutableLiveData<Boolean> logged;

    private MutableLiveData<Boolean> oAuthProcessing;

    private boolean oAuthFailed = false;

    private FirebaseAuth auth;

    public RegisterViewModel() {
        this.email = new MutableLiveData<>();
        this.password = new MutableLiveData<>();
        this.confirmPassword = new MutableLiveData<>();
        this.agreement = new MutableLiveData<>();

        this.emailError = new MutableLiveData<>();
        this.passwordError = new MutableLiveData<>();
        this.confirmPasswordError = new MutableLiveData<>();
        this.agreementError = new MutableLiveData<>();
        this.formError = new MutableLiveData<>();

        this.registrationProcessing = new MutableLiveData<>();
        this.registrationSuccess = new MutableLiveData<>();
        this.logged = new MutableLiveData<>();

        this.oAuthProcessing = new MutableLiveData<>();

        this.auth = FirebaseAuth.getInstance();
    }

    public LiveData<Integer> getEmailError() {
        return emailError;
    }

    public LiveData<Integer> getPasswordError() {
        return passwordError;
    }

    public LiveData<Integer> getConfirmPasswordError() {
        return confirmPasswordError;
    }

    public LiveData<Integer> getFormError() {
        return formError;
    }


    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
        validateEmail();
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.setValue(password);
        validatePassword();
    }

    public MutableLiveData<String> getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword.setValue(confirmPassword);
        validateConfirmPassword();
    }

    public MutableLiveData<Boolean> getAgreement() {
        return agreement;
    }

    public void setAgreement(Boolean agreement) {
        this.agreement.setValue(agreement);
        validateAgreement();
    }

    public MutableLiveData<Integer> getAgreementError() {
        return agreementError;
    }

    private boolean validateEmail() {
        Integer error;
        boolean result;
        if(this.email.getValue() == null || this.email.getValue().trim().isEmpty()) {
            error = R.string.field_required;
            result = false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(this.email.getValue().trim()).matches()) {
            error = R.string.email_not_valid;
            result = false;
        } else {
            error = null;
            result = true;
        }
        this.emailError.setValue(error);
        return result;
    }

    private boolean validatePassword() {
        Integer error;
        boolean result;
        if(this.password.getValue() == null || this.password.getValue().trim().isEmpty()) {
            result = false;
            error = R.string.field_required;
        } else if(this.password.getValue().length() < 6) {
            result = false;
            error = R.string.password_too_short;
        } else {
            result = true;
            error = null;
        }
        this.passwordError.setValue(error);
        return result;
    }

    private boolean validateConfirmPassword() {
        Integer error;
        boolean result;
        if(this.confirmPassword.getValue() == null || this.confirmPassword.getValue().trim().isEmpty()) {
            result = false;
            error = R.string.field_required;
        } else if(!this.confirmPassword.getValue().equals(this.password.getValue())) {
            result = false;
            error = R.string.passwords_not_same;
        } else {
            result = true;
            error = null;
        }
        this.confirmPasswordError.setValue(error);
        return result;
    }

    private boolean validateAgreement() {
        Integer error;
        boolean result;
        if(this.agreement.getValue() == null || !this.agreement.getValue()) {
            error = R.string.field_required;
            result = false;
        } else {
            error = null;
            result = true;
        }
        this.agreementError.setValue(error);
        return result;
    }


    public void processRegistration() {
        if(validateForm()) {
            this.registrationProcessing.setValue(true);
            registerWithEmail();
        }
    }

    private boolean validateForm() {
        return  validateEmail() &&
                validatePassword() &&
                validateConfirmPassword() &&
                validateAgreement();
    }

    private void registerWithEmail() {
        String email = this.email.getValue() != null ? this.email.getValue().trim() : null;
        String password = this.password.getValue();
        if(email != null && password != null) {
            this.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this::onEmailRegistrationComplete);
        }
    }

    private void onEmailRegistrationComplete(@NonNull Task<AuthResult> result) {
        this.registrationSuccess.setValue(result.isSuccessful());
        String email = this.email.getValue() != null ? this.email.getValue().trim() : null;
        String password = this.password.getValue();
        if(!result.isSuccessful()) {
            this.logged.setValue(false);
            this.registrationProcessing.setValue(false);
        } else if(email != null && password != null) {
            this.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this::onSignInResult);
        }

    }

    public LiveData<Boolean> getRegistrationProcessing() {
        return registrationProcessing;
    }

    public LiveData<Boolean> getRegistrationSuccess() {
        return registrationSuccess;
    }

    public LiveData<Boolean> getLogged() {
        return logged;
    }

    public Boolean isRegistrationSucceed() {
        return this.registrationSuccess.getValue();
    }

    public Boolean isLoggedIn() {
        return this.logged.getValue();
    }

    public void onSignInResult(@NonNull Task<AuthResult> result) {
        this.logged.setValue(result.isComplete());
        this.registrationProcessing.setValue(false);
    }

    public LiveData<Boolean> getOAuthProcessing() {
        return oAuthProcessing;
    }

    public boolean isOAuthFailed() {
        return oAuthFailed;
    }

    public void signInWithGoogle(String token) {
        this.oAuthProcessing.setValue(true);
        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
        this.auth.signInWithCredential(credential)
                .addOnCompleteListener(this::onOAuthResult);
    }

    public void onOAuthResult(@NonNull Task<AuthResult> result) {
        this.oAuthFailed = !result.isSuccessful();
        this.logged.setValue(result.isSuccessful());
        this.oAuthProcessing.setValue(false);
    }

}
