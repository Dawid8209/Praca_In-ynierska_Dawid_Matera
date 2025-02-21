package pl.dawid.yourmotobudget.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDataDao {

    @Insert
    void insert(UserData userData);

    @Update
    void update(UserData userData);

    @Delete
    void delete(UserData userData);

    @Query("SELECT * FROM user_data WHERE email = :email")
    List<UserData> getUserDataByUserId(String email);

    @Query("DELETE FROM user_data WHERE id = :id")
    void deleteUserDataById(int id);
}