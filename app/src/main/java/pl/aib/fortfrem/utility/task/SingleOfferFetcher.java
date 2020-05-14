package pl.aib.fortfrem.utility.task;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

import pl.aib.fortfrem.data.Database;
import pl.aib.fortfrem.data.entity.Offer;

public class SingleOfferFetcher extends AsyncTask<String, Offer, Offer> {
    private WeakReference<Context> contextRef;

    public SingleOfferFetcher(@NonNull Context context) {
        this.contextRef = new WeakReference<>(context);
    }

    @Override
    protected Offer doInBackground(String... oids) {
        Offer offer = null;
        if(oids.length > 0) {
            Database db = Database.getInstance(this.contextRef.get());
            offer = db.offerDao().findOne(oids[0]);
        }
        return offer;
    }
}
