package pl.aib.fortfrem.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import pl.aib.fortfrem.data.dao.OfferDao;
import pl.aib.fortfrem.data.entity.Offer;

@androidx.room.Database(entities = {Offer.class}, version = 4, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract OfferDao offerDao();
    private static Database instance = null;
    private static final String DB_NAME = "fortfrem_db";

    public static Database getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, Database.class, DB_NAME)
                    .addMigrations(MIGRATION_2_3, MIGRATION_3_4)
                    .build();
        }
        return instance;
    }

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("alter table offer add storeId text");
            database.execSQL("alter table offer add description text");
        }
    };

    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("alter table offer add url text");
        }
    };

    @Override
    public void clearAllTables() {
        this.offerDao().clear();
    }
}
