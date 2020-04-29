package pl.aib.fortfrem.utility.task;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import pl.aib.fortfrem.data.Database;
import pl.aib.fortfrem.data.entity.Offer;
import pl.aib.fortfrem.utility.repository.listener.OffersListener;

public class OfferSaver extends AsyncTask<JSONObject, Offer, List<Offer>> {
    private final WeakReference<Context> contextRef;

    public OfferSaver(Context context) {
        this.contextRef = new WeakReference<Context>(context);
    }

    @Override
    protected List<Offer> doInBackground(JSONObject... offersJson) {
        List<Offer> offers = new ArrayList<>();
        Database db = Database.getInstance(this.contextRef.get());
        for(JSONObject offerJson : offersJson) {
            try {
                Offer offer = new Offer();
                offer.setOid(offerJson.getString("oid"));
                offer.setTitle(offerJson.getString("title"));
                offer.setImageUrl(offerJson.getString("image_url"));
                offer.setStoreName(offerJson.getString("store"));
                offer.setStoreLogoUrl(offerJson.getString("store_logo_url"));
                offer.setCategory(offerJson.getString("category"));
                offer.setSubcategory(offerJson.getString("subcategory"));
                offer.setPrice(offerJson.getInt("price"));
                offer.setOldPrice(offerJson.getInt("old_price"));
                offer.setStartTime(offerJson.getLong("start_time"));
                offer.setEndTime(offerJson.getLong("end_time"));
                offers.add(offer);
            } catch (JSONException ignored) {}
        }
        db.offerDao().insert(offers.toArray(new Offer[]{}));
        return offers;
    }

}
