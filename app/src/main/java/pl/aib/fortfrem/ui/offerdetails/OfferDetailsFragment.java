package pl.aib.fortfrem.ui.offerdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.ui.FortfremFragment;

public class OfferDetailsFragment extends FortfremFragment {
    TextView tv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_offer_details, container, false);
        tv = root.findViewById(R.id.txt);
        Bundle args = getArguments();
        if(args != null) {
            tv.setText(args.getString("oid"));
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(this.navController == null && getView() != null) {
            this.navController = Navigation.findNavController(getView());
        }

    }
}
