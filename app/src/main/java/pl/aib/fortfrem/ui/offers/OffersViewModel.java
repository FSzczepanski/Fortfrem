package pl.aib.fortfrem.ui.offers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Offer;
import pl.aib.fortfrem.utility.exception.FortfremException;
import pl.aib.fortfrem.utility.repository.OfferRepository;
import pl.aib.fortfrem.utility.repository.listener.OffersErrorListener;
import pl.aib.fortfrem.utility.repository.listener.OffersListener;

public class OffersViewModel extends ViewModel implements OffersErrorListener, OffersListener {
    private MutableLiveData<List<Offer>> offers;
    private MutableLiveData<Boolean> initialized;
    private MutableLiveData<Integer> popoverMessage;

    @Nullable
    private OfferRepository offerRepository = null;

    public OffersViewModel() {
        this.offers = new MutableLiveData<>();
        this.initialized = new MutableLiveData<>();
        this.popoverMessage = new MutableLiveData<>();
        this.initialized.setValue(false);

    }

    public LiveData<List<Offer>> getOffers() {
        return offers;
    }

    public LiveData<Boolean> getInitialized() {
        return initialized;
    }

    public void initOffersIfNeeded(@NonNull Context context) {
        if(this.offerRepository == null) {
            this.offerRepository = new OfferRepository(context, this, this);
        }
        if(this.offers.getValue() == null) {
            this.offerRepository.getOffersList();
        }
    }

    @Override
    public void onError(FortfremException e) {
        this.initialized.setValue(true);
        this.popoverMessage.setValue(this.resolveErrorMessageId(e.getTag()));
    }

    private Integer resolveErrorMessageId(String tag) {
        //TODO Change it to real recognition
        return R.string.example_popover_message;
    }

    @Override
    public void onOffersReceived(List<Offer> offers) {
        this.initialized.setValue(true);
        this.offers.setValue(offers);
    }

    public void refreshOffers(@NonNull Context context) {
        if(this.offerRepository == null) {
            this.offerRepository = new OfferRepository(context, this, this);
        }

        this.offerRepository.getCurrentOffersList(true);
    }

    public LiveData<Integer> getPopoverMessage() {
        return popoverMessage;
    }
}
