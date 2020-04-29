package pl.aib.fortfrem.utility.repository;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pl.aib.fortfrem.data.entity.Offer;
import pl.aib.fortfrem.utility.api.ApiClient;
import pl.aib.fortfrem.utility.exception.FortfremException;
import pl.aib.fortfrem.utility.exception.RequestException;
import pl.aib.fortfrem.utility.repository.listener.OffersErrorListener;
import pl.aib.fortfrem.utility.repository.listener.OffersFetchedListener;
import pl.aib.fortfrem.utility.repository.listener.OffersListener;
import pl.aib.fortfrem.utility.task.OfferSaver;
import pl.aib.fortfrem.utility.task.OffersCounter;
import pl.aib.fortfrem.utility.task.OffersFetcher;

public class OfferRepository {

    @NonNull
    private Context context;

    @NonNull
    private OffersListener offersListener;

    @NonNull
    private OffersErrorListener errorListener;

    @NonNull
    private ApiClient api;


    public OfferRepository(@NonNull Context context, @NonNull OffersListener offersListener, @NonNull OffersErrorListener errorListener) {
        this.context = context;
        this.offersListener = offersListener;
        this.errorListener = errorListener;
        this.api = new ApiClient(context, this.getClass().getName());
    }

    public void getOffersList() {
        OffersCounter counter = new OffersCounter(this.context);
        int count;
        try {
            count = counter.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            count = 0;
        }

        this.getCurrentOffersList(count == 0);
    }

    public void getCurrentOffersList(boolean update) {
        if(update) {
            fetchOffersFromApi();
        } else {
            fetchOffersFromDB();
        }
    }

    private void fetchOffersFromApi() {
        this.api.fetchOffers(this::onOffersResponse, this.errorListener);
    }

    private void fetchOffersFromDB() {
        OffersFetcher fetcher = new OffersFetcher(this.context);
        try {
            List<Offer> offers = fetcher.execute().get();
            offersListener.onOffersReceived(offers);
        } catch (ExecutionException | InterruptedException e) {
            errorListener.onError(new FortfremException("local.fetch.failed"));
        }
    }

    private void onOffersResponse(JSONObject response) {
        OfferSaver offerSaver = new OfferSaver(this.context);
        try {
            List<JSONObject> offersJson = this.unwrapOffersFromResponse(response);
            List<Offer> offers = offerSaver.execute(offersJson.toArray(new JSONObject[]{})).get();
            offersListener.onOffersReceived(offers);
        } catch (JSONException e) {
            errorListener.onError(new RequestException("response.format.invalid"));
        } catch (InterruptedException | ExecutionException e) {
            //TODO Change it to Offers exception
            errorListener.onError(new FortfremException("cannot.save"));
        }
    }

    private List<JSONObject> unwrapOffersFromResponse(JSONObject response) throws JSONException {
        List<JSONObject> offers;
        JSONArray offersJson = response.getJSONArray("offers");
        offers = this.unwrapValidOffersFromArray(offersJson);
        return offers;
    }

    private List<JSONObject> unwrapValidOffersFromArray(JSONArray offersJson) {
        List<JSONObject> offers = new ArrayList<>();
        for(int i = 0; i < offersJson.length(); i++) {
            JSONObject offer = this.unwrapSingleOffer(offersJson, i);
            if(offer != null) {
                offers.add(offer);
            }
        }
        return offers;
    }

    @Nullable
    private JSONObject unwrapSingleOffer(JSONArray offersJson, int index) {
        @Nullable JSONObject offer;
        try {
            offer = offersJson.getJSONObject(index);
        } catch (JSONException e) {
            offer = null;
        }
        return offer != null && this.isOfferJsonValid(offer) ? offer : null;
    }

    private boolean isOfferJsonValid(@NonNull JSONObject json) {
        boolean result;
        try {
            json.getString("oid");
            json.getString("title");
            json.getString("image_url");
            json.getString("store");
            json.getString("store_logo_url");
            json.getString("category");
            json.getString("subcategory");
            json.getInt("price");
            json.getInt("old_price");
            json.getLong("start_time");
            json.getLong("end_time");
            result = true;
        } catch (JSONException|NullPointerException e) {
            result = false;
        }
        return  result;
    }
}
