package pl.aib.fortfrem.utility.api;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import pl.aib.fortfrem.utility.api.listener.RequestResultListener;
import pl.aib.fortfrem.utility.exception.RequestCreationException;
import pl.aib.fortfrem.utility.exception.RequestException;
import pl.aib.fortfrem.utility.repository.listener.OffersErrorListener;
import pl.aib.fortfrem.utility.repository.listener.OffersFetchedListener;

public class ApiClient {

    @NonNull
    private Context context;

    @NonNull
    private String requestTag;

    @NonNull
    private final String baseUrl = "https://fortfrem-api.herokuapp.com";

    private enum Endpoints {
        OFFERS("/offers"),
        OFFERS_COUNT("/offers/count")
        ;
        private String value;

        Endpoints(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public ApiClient(@NonNull Context context) {
        this(context, null);
    }

    public ApiClient(@NotNull Context context, @Nullable String requestTag) {
        this.context = context;
        this.requestTag = requestTag != null ? requestTag : this.getClass().getName();
    }

    public void fetchOffers(final OffersFetchedListener offersFetchedListener, final OffersErrorListener errorListener) {
        final String url = this.baseUrl.concat(Endpoints.OFFERS.value);
        ApiRequest request = new ApiRequest(this.context, url, Request.Method.GET);
        RequestResultListener resultListener = new RequestResultListener() {
            @Override
            public void onSuccess(JSONObject response) {
                offersFetchedListener.onOffersFetched(response);
            }

            @Override
            public void onFail(RequestException e) {
                errorListener.onError(e);
            }
        };
        request.setResultListener(resultListener);
        try {
            request.execute();
        } catch (RequestCreationException e) {
            errorListener.onError(e);
        }
    }
}
