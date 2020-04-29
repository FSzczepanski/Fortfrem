package pl.aib.fortfrem.ui.list.offers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Offer;

public class OffersAdapter extends RecyclerView.Adapter<OfferViewHolder> {

    @NonNull
    private Context context;

    @NonNull
    private List<Offer> data;

    public OffersAdapter(@NonNull Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(@NonNull List<Offer> data) {
        this.data = data;
        notifyItemRangeChanged(0, this.data.size());
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.context).inflate(R.layout.offers_list_item, parent, false);
        return new OfferViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        holder.setData(this.data.get(position));
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}
