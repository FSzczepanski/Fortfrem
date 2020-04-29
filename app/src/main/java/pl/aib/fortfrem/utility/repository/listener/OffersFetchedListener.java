package pl.aib.fortfrem.utility.repository.listener;

import org.json.JSONObject;

public interface OffersFetchedListener {
    void onOffersFetched(JSONObject requestResponse);
}
