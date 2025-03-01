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

    @Query("DELETE FROM user_costs WHERE id = :id")
    void deleteCostsById(int id);
}