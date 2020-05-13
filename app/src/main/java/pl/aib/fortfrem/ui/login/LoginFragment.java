package pl.aib.fortfrem.ui.login;

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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.ui.FortfremFragment;
import pl.aib.fortfrem.ui.listener.TextChangeListener;

public class LoginFragment extends FortfremFragment {
    private LoginViewModel viewModel;
    private TextView loginAgreement;
    private Button googleSignInButton;
    private View progressBarContainer;
    private EditText emailInput;
    private EditText passwordInput;
    private Button submit;

    private final int RC_GSI_SUCCESS = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        this.loginAgreement = root.findViewById(R.id.login_agreement);
        this.googleSignInButton = root.findViewById(R.id.google);
        this.progressBarContainer = root.findViewById(R.id.progress_overlay);
        this.emailInput = root.findViewById(R.id.email_input);
        this.passwordInput = root.findViewById(R.id.password_input);
        this.submit = root.findViewById(R.id.submit);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(this.navController == null && getView() != null) {
            this.navController = Navigation.findNavController(getView());
        }
        setupAgreements();
        setupInputs();
        this.viewModel.getProcessing().observe(getViewLifecycleOwner(), this::onProcessingChanged);
        this.viewModel.getEmailError().observe(getViewLifecycleOwner(), this::onEmailErrorChanged);
        this.viewModel.getPasswordError().observe(getViewLifecycleOwner(), this::onPasswordErrorChanged);

        this.googleSignInButton.setOnClickListener(v -> onSignInWithGoogleRequested());
        this.submit.setOnClickListener(v -> onSubmit());
    }

    private void setupInputs() {
        this.emailInput.addTextChangedListener(createTextWatcher(this::onEmailChanged));
        this.passwordInput.addTextChangedListener(createTextWatcher(this::onPasswordChanged));
    }

    private TextWatcher createTextWatcher(TextChangeListener changeListener) {
        return  new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeListener.onTextChanged(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    private void setupAgreements() {
        this.loginAgreement.setText(Html.fromHtml(getAgreementText(), HtmlCompat.FROM_HTML_MODE_COMPACT));
        this.loginAgreement.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private String getAgreementText() {
        String text;
        if(getContext() != null) {
            text = getContext().getString(R.string.login_agreement_text);
        } else {
            text = "Logując się akceptujesz <a href='https://www.google.com'>Regulamin serwisu</a> oraz <a href='https://www.google.com'>Politykę prywatności</a>";
        }
        return text;
    }


    private void onEmailChanged(String value) {
        this.viewModel.setEmail(value);
    }

    private void onPasswordChanged(String value) {
        this.viewModel.setPassword(value);
    }

    private void onEmailErrorChanged(@Nullable Integer error) {
        String errorText = error != null && getContext() != null ? getContext().getString(error) : null;
        this.emailInput.setError(errorText);
    }

    private void onPasswordErrorChanged(@Nullable Integer error) {
        String errorText = error != null && getContext() != null ? getContext().getString(error) : null;
        this.passwordInput.setError(errorText);
    }

    private void onSubmit() {
        this.viewModel.processLogin();
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

    private void onProcessingChanged(Boolean processing) {
        int overlayVisibility = processing ? View.VISIBLE : View.GONE;
        this.progressBarContainer.setVisibility(overlayVisibility);
        if(!processing && this.viewModel.isSuccess()) {
            finish();
        } else if(!processing) {
            Toast.makeText(getContext(), R.string.login_failed_simple, Toast.LENGTH_LONG).show();
        }
    }


    private void finish() {
        if(this.navController != null) {
            this.navController.popBackStack();
        }
    }
}
