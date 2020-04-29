package pl.aib.fortfrem.utility.task;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;

import pl.aib.fortfrem.data.Database;
import pl.aib.fortfrem.data.entity.Offer;
import pl.aib.fortfrem.utility.api.ApiClient;

public class OffersFetcher extends AsyncTask<Void, Offer, List<Offer>> {

    @NonNull
    private final WeakReference<Context> contextRef;


    public OffersFetcher(Context context) {
        this.contextRef = new WeakReference<Context>(context);
    }

    @Override
    protected List<Offer> doInBackground(Void... voids) {
        Database db = Database.getInstance(this.contextRef.get());
        return db.offerDao().findActive(Calendar.getInstance().getTimeInMillis());
    }
}
