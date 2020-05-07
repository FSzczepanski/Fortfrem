package pl.aib.fortfrem.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.ui.FortfremFragment;
import pl.aib.fortfrem.ui.listener.TextChangeListener;

public class RegisterFragment extends FortfremFragment {
    private RegisterViewModel viewModel;
    private TextInputLayout emailContainer;
    private EditText emailInput;
    private TextInputLayout passwordContainer;
    private EditText passwordInput;
    private TextInputLayout confirmPasswordContainer;
    private EditText confirmPasswordInput;
    private CheckBox agreementBox;
    private TextView agreementText;
    private TextView agreementError;
    private TextView formError;
    private TextView loginAgreement;
    private Button submit;
    private View progressBarContainer;
    private Button signInWithGoogleButton;

    private final int RC_GSI_SUCCESS = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        this.emailContainer = root.findViewById(R.id.email_input_container);
        this.emailInput = root.findViewById(R.id.email_input);
        this.passwordContainer = root.findViewById(R.id.password_input_container);
        this.passwordInput = root.findViewById(R.id.password_input);
        this.confirmPasswordContainer = root.findViewById(R.id.confirm_password_input_container);
        this.confirmPasswordInput = root.findViewById(R.id.confirm_password_input);
        this.agreementBox = root.findViewById(R.id.agreement);
        this.agreementText = root.findViewById(R.id.agreement_text);
        this.submit = root.findViewById(R.id.submit);
        this.agreementError = root.findViewById(R.id.checkbox_error);
        this.formError = root.findViewById(R.id.form_error);
        this.loginAgreement = root.findViewById(R.id.login_agreement);
        this.submit = root.findViewById(R.id.submit);
        this.progressBarContainer = root.findViewById(R.id.progress_overlay);
        this.signInWithGoogleButton = root.findViewById(R.id.google);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupCheckbox();
        this.viewModel.getEmailError().observe(getViewLifecycleOwner(), this::onEmailErrorChanged);
        this.viewModel.getPasswordError().observe(getViewLifecycleOwner(), this::onPasswordErrorChanged);
        this.viewModel.getConfirmPasswordError().observe(getViewLifecycleOwner(), this::onConfirmPasswordErrorChanged);
        this.viewModel.getAgreementError().observe(getViewLifecycleOwner(), this::onAgreementErrorChanged);
        this.viewModel.getFormError().observe(getViewLifecycleOwner(), this::onFormErrorChanged);
        this.viewModel.getRegistrationProcessing().observe(getViewLifecycleOwner(), this::onRegistrationProcessingChanged);
        this.viewModel.getOAuthProcessing().observe(getViewLifecycleOwner(), this::onOAuthProcessingChanged);
        setupInputs();
        setupLoginAgreement();
        this.submit.setOnClickListener(v -> onSubmit());
        this.signInWithGoogleButton.setOnClickListener(v -> onSignInWithGoogleRequested());
        if(getView() != null) {
            this.navController = Navigation.findNavController(getView());
        }
    }

    private void setupInputs() {
        this.emailInput.addTextChangedListener(createTextWatcher(this::onEmailChanged));
        this.passwordInput.addTextChangedListener(createTextWatcher(this::onPasswordChanged));
        this.confirmPasswordInput.addTextChangedListener(createTextWatcher(this::onConfirmPasswordChanged));
        this.agreementBox.setOnCheckedChangeListener((v, agree) -> this.onAgreementChanged(agree));
    }

    private TextWatcher createTextWatcher(TextChangeListener textChangeListener) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChangeListener.onTextChanged(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    private void setupCheckbox() {
        this.agreementText.setText(Html.fromHtml(getCheckboxText(), HtmlCompat.FROM_HTML_MODE_COMPACT));
        this.agreementText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setupLoginAgreement() {
        this.loginAgreement.setText(Html.fromHtml(getLoginAgreementText(), HtmlCompat.FROM_HTML_MODE_COMPACT));
        this.loginAgreement.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private String getCheckboxText() {
        String text;
        if(getContext() != null) {
            text = getContext().getString(R.string.agreement_text);
        } else {
            text = "Akceptuję <a href='https://www.google.com'>Regulamin serwisu</a> oraz <a href='https://www.google.com'>Politykę prywatności</a>";
        }
        return text;
    }

    private String getLoginAgreementText() {
        String text;
        if(getContext() != null) {
            text = getContext().getString(R.string.login_agreement_text);
        } else {
            text = "Logując się akceptujesz <a href='https://www.google.com'>Regulamin serwisu</a> oraz <a href='https://www.google.com'>Politykę prywatności</a>";
        }
        return text;
    }

    private void onEmailErrorChanged(@Nullable Integer error) {
        String errorText = error != null && getContext() != null ? getContext().getString(error) : null;
        this.emailInput.setError(errorText);
    }

    private void onPasswordErrorChanged(@Nullable Integer error) {
        String errorText = error != null && getContext() != null ? getContext().getString(error) : null;
        this.passwordInput.setError(errorText);
    }

    private void onConfirmPasswordErrorChanged(@Nullable Integer error) {
        String errorText = error != null && getContext() != null ? getContext().getString(error) : null;
        this.confirmPasswordInput.setError(errorText);
    }

    private void onAgreementErrorChanged(@Nullable Integer error) {
        String errorText = error != null && getContext() != null ? getContext().getString(error) : null;
        this.agreementError.setText(errorText);
    }

    private void onFormErrorChanged(@Nullable Integer error) {
        String errorText = error != null && getContext() != null ? getContext().getString(error) : null;
        this.formError.setText(errorText);
    }

    private void onEmailChanged(String email) {
        this.viewModel.setEmail(email);
    }

    private void onPasswordChanged(String password) {
        this.viewModel.setPassword(password);
    }

    private void onConfirmPasswordChanged(String confirmPassword) {
        this.viewModel.setConfirmPassword(confirmPassword);
    }

    private void onAgreementChanged(boolean agree) {
        this.viewModel.setAgreement(agree);
    }


    private void onSubmit() {
        this.viewModel.processRegistration();
    }

    private void onRegistrationProcessingChanged(Boolean processing) {
        int overlayVisibility = processing ? View.VISIBLE : View.GONE;
        this.progressBarContainer.setVisibility(overlayVisibility);
        if(!processing && this.viewModel.isLoggedIn()) {
            this.finish();
        } else if(!processing && this.viewModel.isRegistrationSucceed() && getContext() != null) {
            Toast.makeText(getContext(), R.string.registration_successfull, Toast.LENGTH_LONG).show();
            resetForm();
        } else if(!processing && !this.viewModel.isRegistrationSucceed()) {
            this.formError.setText(R.string.registration_error_unknown);
        }
    }

    private void finish() {
        if(this.navController != null) {
            this.navController.popBackStack();
        }
    }

    private void resetForm() {
        this.emailInput.setText("");
        this.passwordInput.setText("");
        this.confirmPasswordInput.setText("");
        this.agreementBox.setChecked(false);
    }

    private void onSignInWithGoogleRequested() {
        if(getContext() != null) {
            processGoogleSignIn(getContext());
        }
    }

    private void processGoogleSignIn(@NonNull Context context) {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient client = GoogleSignIn.getClient(context, options);
        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent, RC_GSI_SUCCESS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_GSI_SUCCESS) {
            processGoogleSignInResult(data);
        }
    }

    private void processGoogleSignInResult(@Nullable Intent activityData) {
        GoogleSignInAccount googleAccount = fetchGoogleSignInAccountFromIntent(activityData);
        if(googleAccount != null) {
            this.viewModel.signInWithGoogle(googleAccount.getIdToken());
        } else {
            Toast.makeText(getContext(), R.string.login_failed_simple, Toast.LENGTH_LONG).show();
        }
    }

    @Nullable
    private GoogleSignInAccount fetchGoogleSignInAccountFromIntent(@Nullable Intent data) {
        GoogleSignInAccount account;
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            account = task.getResult(ApiException.class);
        } catch (ApiException e) {
            Log.w(this.getClass().getName(), "Google sign in failed", e);
            account = null;
        }
        return  account;
    }

    private void onOAuthProcessingChanged(Boolean processing) {
        int overlayVisibility = processing ? View.VISIBLE : View.GONE;
        this.progressBarContainer.setVisibility(overlayVisibility);
        if(!processing && this.viewModel.isLoggedIn()) {
            finish();
        } else if(!processing && this.viewModel.isOAuthFailed()) {
            Toast.makeText(getContext(), R.string.login_failed_simple, Toast.LENGTH_LONG).show();
        }
    }

}
