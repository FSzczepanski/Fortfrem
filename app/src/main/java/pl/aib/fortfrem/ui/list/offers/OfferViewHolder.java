package pl.aib.fortfrem.ui.list.offers;

import android.graphics.Paint;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Offer;

public class OfferViewHolder extends RecyclerView.ViewHolder {
    private OnOfferSelectedListener selectedListener;

    public OfferViewHolder(@NonNull View itemView, OnOfferSelectedListener selectedListener) {
        super(itemView);
        this.selectedListener = selectedListener;
        TextView olPriceLabel = itemView.findViewById(R.id.old_price_label);
        olPriceLabel.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void setData(Offer offer) {
        ImageView offerImage = this.itemView.findViewById(R.id.offer_image);
        TextView offerTitle = this.itemView.findViewById(R.id.offer_title);
        ImageView storeLogo = this.itemView.findViewById(R.id.store_logo);
        TextView storeName = this.itemView.findViewById(R.id.store_name);
        TextView oldPrice = this.itemView.findViewById(R.id.old_price_label);
        TextView price = this.itemView.findViewById(R.id.price_label);

        offerTitle.setText(offer.getTitle());
        storeName.setText(offer.getStoreName());
        oldPrice.setText(offer.getOldPricePLNString());
        price.setText(offer.getCurrentPricePLNString());

        try {
            resolveImage(offerImage, Uri.parse(offer.getImageUrl()), 25);
            resolveImage(storeLogo, Uri.parse(offer.getStoreLogoUrl()));
        }catch (Exception e) {
            int x = 2*2;
        }

        this.itemView.setOnClickListener(v -> selectedListener.onOfferSelected(offer));
    }

    private void resolveImage(ImageView target, Uri uri) {
        resolveImage(target, uri, 0);
    }

    private void resolveImage(ImageView target, Uri uri, int roundAngle) {
        RequestOptions requestOptions = (new RequestOptions())
                .fitCenter()
                .override(target.getWidth(), target.getHeight())
                .transform(new CenterCrop());

        if(roundAngle > 0) {
            requestOptions = requestOptions.transform(new RoundedCorners(roundAngle));
        }

        Glide.with(target.getContext())
                .load(uri)
                .placeholder(R.drawable.app_logo_round)
                .apply(requestOptions)
                .into(target);
    }

    /*

            RequestOptions requestOptions = (new RequestOptions())
                .fitCenter()
                .override(128)
                .transform(new CenterCrop(), new RoundedCorners(100));

        Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.app_logo)
                .apply(requestOptions)
                .into(this.picture);
     */
}
