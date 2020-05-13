package pl.aib.fortfrem.ui.list.profile;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import pl.aib.fortfrem.R;

public class ProfileMenuViewHolder extends RecyclerView.ViewHolder {
    private boolean showHeader;
    private ProfileMenuItemSelectedListener selectedListener;

    public ProfileMenuViewHolder(@NonNull View itemView, boolean showHeader, ProfileMenuItemSelectedListener selectedListener) {
        super(itemView);
        this.showHeader = showHeader;
        this.selectedListener = selectedListener;
    }

    public void setData(ProfileMenuItem item) {
        TextView label = this.itemView.findViewById(R.id.label);
        label.setText(item.getLabelId());
        if(item.getIconId() != null) {
            setIcon(item.getIconId());
        }
        this.itemView.setOnClickListener(v -> this.selectedListener.onProfileMenuItemSelected(item));
    }

    private void setIcon(int iconId) {
        ImageView icon = this.itemView.findViewById(R.id.icon);

        Glide.with(this.itemView)
                .load(iconId)
                .placeholder(R.drawable.app_logo_round)
                .into(icon);
    }
}
