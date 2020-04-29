package pl.aib.fortfrem.utility.task;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.Calendar;

import pl.aib.fortfrem.data.Database;

public class OffersCounter extends AsyncTask<Void, Integer, Integer> {
    private final WeakReference<Context> contextRef;

    public OffersCounter(Context context) {
        this.contextRef = new WeakReference<Context>(context);
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        Database db = Database.getInstance(this.contextRef.get());
        return db.offerDao().countOfActive(Calendar.getInstance().getTimeInMillis());
    }
}
