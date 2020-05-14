package pl.aib.fortfrem.ui.offerdetails;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutionException;

import pl.aib.fortfrem.data.entity.Offer;
import pl.aib.fortfrem.utility.task.SingleOfferFetcher;

public class OfferDetailsViewModel extends ViewModel {
    private MutableLiveData<Offer> offer;

    public OfferDetailsViewModel() {
        this.offer = new MutableLiveData<>();
    }

    public LiveData<Offer> getOffer() {
        return offer;
    }

    public void fetch(@Nullable Bundle arguments,@NonNull Context context) {
        String oid = fetchOfferId(arguments);
        if(this.offer.getValue() == null && oid != null) {
            this.offer.setValue(fetchOffer(oid, context));
        }
    }

    @Nullable
    private Offer fetchOffer(@NonNull String oid, @NonNull Context context) {
        SingleOfferFetcher fetcher = new SingleOfferFetcher(context);
        Offer offer;
        try {
            offer = fetcher.execute(oid).get();
        } catch (ExecutionException | InterruptedException e) {
            offer = null;
        }
        return offer;
    }

    @Nullable
    private String fetchOfferId(@Nullable Bundle arguments) {
        return arguments != null ? arguments.getString("oid") : null;
    }
}
