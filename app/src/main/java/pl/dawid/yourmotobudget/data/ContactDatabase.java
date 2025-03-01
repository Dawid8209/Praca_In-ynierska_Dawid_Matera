package pl.dawid.yourmotobudget.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class, UserData.class, UserSalary.class, Costs.class}, version = 4, exportSchema = true)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract UserDataDao userDataDao();
    public abstract UserSalaryDao userSalaryDao();
    public abstract CostsDao costsDao();
    public abstract OverallProfitDao overallProfitDao();

    private static ContactDatabase instance;

    public static synchronized ContactDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (ContactDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ContactDatabase.class,
                            "yourmotobudget_database"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
