package pl.aib.fortfrem.ui.list.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.aib.fortfrem.R;

public class ProfileMenuAdapter extends RecyclerView.Adapter<ProfileMenuViewHolder> {
    private enum ViewType {
        HEADER_ITEM(1),
        STANDARD_ITEM(2)
        ;

        private int value;

        ViewType(int value) {
            this.value = value;
        }
    }

    private ProfileMenuItemSelectedListener menuItemSelectedListener;
    private List<ProfileMenuItem> data;

    public ProfileMenuAdapter(ProfileMenuItemSelectedListener menuItemSelectedListener) {
        this.menuItemSelectedListener = menuItemSelectedListener;
        this.data = new ArrayList<>();
    }

    public void setData(List<ProfileMenuItem> data) {
        this.data = data;
        notifyItemRangeChanged(0, this.data.size());
    }

    @NonNull
    @Override
    public ProfileMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        boolean showHeader = viewType == ViewType.HEADER_ITEM.value;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_menu_item, parent, false);
        return new ProfileMenuViewHolder(view, showHeader, this.menuItemSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileMenuViewHolder holder, int position) {
        holder.setData(this.data.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return ViewType.STANDARD_ITEM.value;
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}
