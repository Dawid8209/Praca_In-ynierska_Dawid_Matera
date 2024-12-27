package pl.dawid.yourmotobudget.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CostsDao {

    @Insert
    void insert(Costs Costs);

    @Query("SELECT * FROM user_costs WHERE email = :email")
    List<Costs> getCostsByUserId(String email);

    @Update
    void update(Costs Costs);

    @Query("SELECT * FROM user_costs WHERE costsTextView = :costsTextView")
    UserData getCosts(int costsTextView);

    @Query("DELETE FROM user_costs WHERE nameTextView = :nameTextView")
    void deleteCostsById(String nameTextView);
}