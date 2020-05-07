package pl.aib.fortfrem.ui.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.ui.FortfremFragment;

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
    private Button submit;

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
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.agreementText.setText(Html.fromHtml(getCheckboxText(), HtmlCompat.FROM_HTML_MODE_COMPACT));
        this.agreementText.setMovementMethod(LinkMovementMethod.getInstance());


    }

    private void setupCheckbox() {
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
}
