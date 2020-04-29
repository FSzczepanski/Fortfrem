package pl.aib.fortfrem.ui.list.offers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Offer;

public class OfferViewHolder extends RecyclerView.ViewHolder {
    public OfferViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setData(Offer offer) {
        ImageView offerImage = this.itemView.findViewById(R.id.offer_image);
        TextView title = this.itemView.findViewById(R.id.offer_title);
        TextView category = this.itemView.findViewById(R.id.offer_category);
        ImageView storeLogo = this.itemView.findViewById(R.id.store_logo);
        TextView storeName = this.itemView.findViewById(R.id.store_name);
        TextView price = this.itemView.findViewById(R.id.price);
        TextView discount = this.itemView.findViewById(R.id.discount);

        Glide.with(this.itemView.getContext())
                .load(offer.getImageUrl())
                .placeholder(R.drawable.app_logo)
                .into(offerImage);

        Glide.with(this.itemView.getContext())
                .load(offer.getStoreLogoUrl())
                .placeholder(R.drawable.app_logo)
                .into(storeLogo);

        title.setText(offer.getTitle());
        category.setText(offer.getCategory());
        storeName.setText(offer.getStoreName());
        price.setText(offer.getPricePLNString());
        discount.setText(offer.getDiscountPercentageString());
    }
}
