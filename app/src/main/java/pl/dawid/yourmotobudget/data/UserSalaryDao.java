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

    @Query("SELECT * FROM user_salary WHERE email = :email")
    List<UserSalary> getUserSalaryByUserId(String email);

    @Update
    void update(UserSalary UserSalary);

    @Query("SELECT * FROM user_salary WHERE salaryTextView = :salaryTextView")
    UserData getSalary(int salaryTextView);

    @Query("SELECT * FROM user_salary WHERE bonusTextView = :bonusTextView")
    UserData getBonus(int bonusTextView);

    @Query("SELECT * FROM user_salary WHERE supplementTextView = :supplementTextView")
    UserData getSupplement(int supplementTextView);

    @Query("DELETE FROM user_salary WHERE nameTextView = :nameTextView")
    void deleteUserSalaryById(String nameTextView);
}