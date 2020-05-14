package pl.aib.fortfrem.ui.offerdetails;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Offer;
import pl.aib.fortfrem.ui.FortfremFragment;

public class OfferDetailsFragment extends FortfremFragment {
    private OfferDetailsViewModel viewModel;

    private View dataContainer;
    private View progressBarContainer;
    private ImageView offerPicture;
    private TextView title;
    private TextView oldPrice;
    private TextView price;
    private ImageView storeLogo;
    private TextView storeName;
    private TextView description;
    private TextView category;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.viewModel = ViewModelProviders.of(this).get(OfferDetailsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_offer_details, container, false);
        this.dataContainer = root.findViewById(R.id.container);
        this.progressBarContainer = root.findViewById(R.id.progress_bar_container);
        this.offerPicture = root.findViewById(R.id.offer_image);
        this.title = root.findViewById(R.id.offer_title);
        this.oldPrice = root.findViewById(R.id.old_price);
        this.price = root.findViewById(R.id.price);
        this.oldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        this.storeLogo = root.findViewById(R.id.store_logo);
        this.storeName = root.findViewById(R.id.store_name);
        this.description = root.findViewById(R.id.desc);
        this.category = root.findViewById(R.id.category);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(this.navController == null) {
            this.navController = Navigation.findNavController(view);
        }
        this.viewModel.getOffer().observe(getViewLifecycleOwner(), this::onOfferChanged);
        this.viewModel.fetch(getArguments(), view.getContext());
    }

    private void onOfferChanged(Offer offer) {
        if(offer != null) {
            updateUI(offer);
        } else {
            //TODO Show error
        }
    }

    private void updateUI(@NonNull Offer offer) {
        this.progressBarContainer.setVisibility(View.GONE);
        this.dataContainer.setVisibility(View.VISIBLE);
        this.title.setText(offer.getTitle());
        resolveImage(this.offerPicture, Uri.parse(offer.getImageUrl()));
        this.oldPrice.setText(offer.getOldPricePLNString());
        this.price.setText(offer.getCurrentPricePLNString());
        resolveImage(this.storeLogo, Uri.parse(offer.getStoreLogoUrl()));
        this.storeName.setText(offer.getStoreName());
        resolveDescription(offer);
        this.category.setText(offer.getCategoryPresentLabel());
    }

    private void resolveDescription(@NonNull Offer offer) {
        if(offer.getDescription() != null) {
            this.description.setText(Html.fromHtml(offer.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            this.description.setText(getString(R.string.no_description));
            this.description.setTypeface(null, Typeface.ITALIC);
        }
    }

    private void resolveImage(ImageView target, Uri uri) {
        RequestOptions requestOptions = (new RequestOptions())
                .fitCenter()
                .override(target.getWidth(), target.getHeight())
                .transform(new CenterCrop());
        Glide.with(target.getContext())
                .load(uri)
                .placeholder(R.drawable.app_logo)
                .apply(requestOptions)
                .into(target);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
