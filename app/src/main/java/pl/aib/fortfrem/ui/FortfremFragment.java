package pl.aib.fortfrem.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

public class FortfremFragment extends Fragment {

    @Nullable
    protected NavController navController;

    @Nullable
    protected View bottomPopover = null;

    @Nullable
    protected TextView bottomPopoverText = null;

    @Nullable
    protected TextView bottomPopoverAccept = null;

    protected void showBottomPopoverMessage(@Nullable String message) {
        if(this.bottomPopoverText != null) {
            this.bottomPopoverText.setText(message);
        }

        if(this.bottomPopoverAccept != null) {
            this.bottomPopoverAccept.setOnClickListener(v -> this.hideBottomPopover());
        }

        if(this.bottomPopover != null) {
            this.bottomPopover.animate().translationY(0);
        }
    }

    protected void hideBottomPopover() {
        if(this.bottomPopover != null) {
            this.bottomPopover.animate().translationY(this.bottomPopover.getHeight());
        }
    }
}
