package pl.dawid.yourmotobudget.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class, UserData.class}, version = 2, exportSchema = true)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract UserDataDao userDataDao();

    private static ContactDatabase instance;

    public static synchronized ContactDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ContactDatabase.class, "workshop_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
