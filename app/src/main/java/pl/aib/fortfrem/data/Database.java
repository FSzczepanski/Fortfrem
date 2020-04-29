package pl.aib.fortfrem.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import pl.aib.fortfrem.data.dao.OfferDao;
import pl.aib.fortfrem.data.entity.Offer;

@androidx.room.Database(entities = {Offer.class}, version = 2, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract OfferDao offerDao();
    private static Database instance = null;
    private static final String DB_NAME = "fortfrem_db";

    public static Database getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, Database.class, DB_NAME).build();
        }
        return instance;
    }

    @Override
    public void clearAllTables() {
        this.offerDao().clear();
    }
}
