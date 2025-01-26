package pl.dawid.yourmotobudget.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserSalaryDao {

    @Insert
    void insert(UserSalary UserSalary);

    @Update
    void update(UserSalary UserSalary);

    @Query("SELECT * FROM user_salary WHERE email = :email")
    List<UserSalary> getUserSalaryByUserId(String email);

    @Query("DELETE FROM user_salary WHERE id = :id")
    void deleteUserSalaryById(int id);
}